package com.example.fecafootdemo.ui.dashboard.control;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.VolleyError;
import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.network.ApiCallback;
import com.example.fecafootdemo.data.network.ApiRequests;
import com.example.fecafootdemo.utils.ErrorUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.json.JSONObject;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 11/18/22)
 */
public class TicketSummaryFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private Ticket ticket;
    private Button cancelBtn, controlBtn;
    private ProgressBar loader;
    private ControlActivity.ControlListener listener;

    private TicketSummaryFragment() {
    }

    private TicketSummaryFragment(Ticket ticket, ControlActivity.ControlListener listener) {
        this.ticket = ticket;
        this.listener = listener;
    }

    public static TicketSummaryFragment getInstance(Ticket ticket, ControlActivity.ControlListener listener) {
        return new TicketSummaryFragment(ticket, listener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ticket_summary_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.ticket_code)).setText(ticket.getTicketCode());
        ((TextView) view.findViewById(R.id.amount_tv)).setText(ticket.getAmount());
        ((TextView) view.findViewById(R.id.issued_date)).setText(ticket.getStartDate());
        if (ticket.getIsCtrl() != 0) {
            view.findViewById(R.id.ticketControlled).setVisibility(View.VISIBLE);
            view.findViewById(R.id.control_container).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.ticketControlled).setVisibility(View.GONE);
            view.findViewById(R.id.control_container).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ticketFound).setVisibility(View.VISIBLE);
        }
        cancelBtn = view.findViewById(R.id.cancel_btn);
        view.findViewById(R.id.cancel_btn).setOnClickListener(v -> dismiss());
        controlBtn = view.findViewById(R.id.control_button);
        controlBtn.setOnClickListener(this);

        loader = view.findViewById(R.id.loader);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog dialogc = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = dialogc.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            assert bottomSheet != null;
            BottomSheetBehavior<?> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        });
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.control_button) {
            enableLoading(true);
            controlTicket(ticket);
        }
    }

    private void enableLoading(boolean state) {
        loader.setVisibility(state ? View.VISIBLE : View.GONE);
        cancelBtn.setEnabled(!state);
        controlBtn.setVisibility(state ? View.INVISIBLE : View.VISIBLE);
    }
    private void controlTicket(Ticket ticket) {
        new ApiRequests().getControl(ticket.getTicketCode(), "", new ApiCallback() {
            @Override
            public <T> void Success(T t) {
                JSONObject data = (JSONObject) t;
                Log.i("TAG", data.toString());
                ticket.setIsCtrl(1);
                listener.controlTicket(ticket);
                dismiss();
            }

            @Override
            public void Failure(Exception exception) {
                Log.e("TAG", new Gson().toJson(exception.getMessage()));
                enableLoading(false);
                Toast.makeText(requireContext(), "" + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void VolleyException(VolleyError error) {
                Log.e("TAG", new Gson().toJson(error.getMessage()));
                Toast.makeText(requireContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                enableLoading(false);
            }

            @Override
            public void ServerErrorCode(int code) {
                String message = ErrorUtils.getMessage(code, AppLoader.getInstance());
                Log.e("TAG", "" + code);
                Toast.makeText(requireContext(), "" + message, Toast.LENGTH_SHORT).show();
                enableLoading(false);
            }
        });
    }
}
