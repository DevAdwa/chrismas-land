package com.example.fecafootdemo.ui.dashboard.control;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.asynctask.AsyncSoftBarCode;
import com.example.fecafootdemo.asynctask.PrintReceipt;
import com.example.fecafootdemo.data.dao.entities.CardInfo;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.databinding.ActivityControlBinding;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.example.fecafootdemo.utils.DataUtils;
import com.example.fecafootdemo.utils.KeypadUtils;
import com.example.fecafootdemo.utils.LocationUtils;
import com.example.fecafootdemo.utils.ViewAnimationUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android_serialport_api.PrinterAPI;

public class ControlActivity extends BaseActivity implements ControlView, AsyncSoftBarCode.IBarCodeListener,
        PrinterAPI.printerStatusListener, View.OnClickListener {

    ActivityControlBinding binding;
    private ControlPresenter<ControlView> presenter;
    private BroadcastReceiver receiver;
    private AsyncSoftBarCode api;
    private PrinterAPI printer;
    private ViewAnimationUtils viewAnimationUtils;
    private Dialog dialog;
    private LocationUtils locationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //startService(new Intent(this, ScanSerice.class));
        //api = new AsyncSoftBarCode(this, this);
        presenter = new ControlPresenter<>(this);
        //locationUtils = new LocationUtils(this, getLifecycle());
        onSetup();
    }

    @Override
    public void onSetup() {
        super.onSetup();
        setSupportActionBar(binding.toolbar);
        binding.checkCodeBtn.setOnClickListener(this);
        binding.scanCodeButton.setOnClickListener(this);
        viewAnimationUtils = new ViewAnimationUtils(this, binding.shineIv);
    }

    @Override
    protected void onResume() {
        super.onResume();
       /** if (printer == null) printer = new PrinterAPI();
        if (!SerialPortManager.getInstance().isPrintOpen() && !SerialPortManager.getInstance().openSerialPortPrinter()) {
            onShowError(R.string.error_occur);
            return;
        }
        super.onResume();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (IBroadcastAction.ACTION_DECODE_DATA.equals(action)) {
                    String data = intent.getStringExtra(IBroadcastAction.INTENT_BARCODE_DATA);
                    Log.w("jokey", "Time3==  " + System.currentTimeMillis());
                    onScanCodeSuccessful(data);
                } else {
                    Log.w("jokey", "Time2==  " + System.currentTimeMillis());
                    onScanCodeFail();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(IBroadcastAction.ACTION_DECODE_DATA);
        filter.setPriority(Integer.MAX_VALUE);
        filter.addAction(IBroadcastAction.TIME_OUT);
        registerReceiver(receiver, filter);**/
    }

    @Override
    public void work() {
        onShowError(R.string.printer_is_working);
    }

    @Override
    public void hot() {
        onShowError(R.string.printer_is_hot);
    }

    @Override
    public void noPaper() {
        onShowError(R.string.no_paper_in_printer);
    }

    @Override
    public void end() {
        onShowError(R.string.successful_printing);
    }

    @Override
    public void onStartScanning() {
        viewAnimationUtils.startAnimation(binding.scanCodeButton);
        binding.checkCodeBtn.setEnabled(false);
        binding.cardCodeEt.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.check_code_btn) {
            if (!TextUtils.isEmpty(binding.cardCodeEt.getText().toString())) {
                String code = binding.cardCodeEt.getText().toString();
                presenter.checkCode(code, false);
                KeypadUtils.hideSoftKey(this);
                return;
            }
            //onShowError(R.string.invalid_code);
            binding.cardCodeEt.setError(getString(R.string.invalid_code));
        } else if (view.getId() == R.id.scan_code_button) {
            //api.startScanner();
            KeypadUtils.hideSoftKey(this);

            IntentIntegrator intentIntegrator = new IntentIntegrator(ControlActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setCameraId(0);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setPrompt("scanning");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.initiateScan();

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScanCodeSuccessful(String data) {
        binding.cardCodeEt.setText("");
        viewAnimationUtils.stopAnimation(binding.scanCodeButton);
        presenter.controlCardCode(data);
        binding.checkCodeBtn.setEnabled(true);
        binding.cardCodeEt.setEnabled(true);
    }

    @Override
    public void onScanCodeFail() {
        //onShowError(R.string.scanning_failed);
        viewAnimationUtils.stopAnimation(binding.scanCodeButton);
        binding.checkCodeBtn.setEnabled(true);
        binding.cardCodeEt.setEnabled(true);
    }

    @Override
    public void onShowControlView(CardInfo cardInfo, String user, @StringRes int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.result_layout, null, false);

        ((TextView)view.findViewById(R.id.class_tv)).setText(getString(DataUtils.getClass(Integer.parseInt(cardInfo.getCartClass()))));
        ((TextView)view.findViewById(R.id.spectator_name_tv)).setText(cardInfo.getPersonName());
        ((TextView)view.findViewById(R.id.code_unique_tv)).setText(cardInfo.getFidelityCode());
        ((TextView)view.findViewById(R.id.category_tv)).setText(getString(DataUtils.getCategory(Integer.parseInt(cardInfo.getCartCategorie()))));

        ((TextView)view.findViewById(R.id.user_tv)).setText(user);
        ((TextView)view.findViewById(R.id.date_tv)).setText(cardInfo.getCardEndDate());
        if (message != 0){
            view.findViewById(R.id.message_tv).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.message_tv)).setText(getString(message));
            view.findViewById(R.id.close_iv).setVisibility(View.VISIBLE);
            view.findViewById(R.id.close_iv).setOnClickListener(v -> dialog.dismiss());
            view.findViewById(R.id.button_container).setVisibility(View.GONE);
        }
        view.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.control_button).setOnClickListener(v -> {

        });
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }
    @Override
    public void onDismiss(String content) {
        Toast.makeText(this, getString(R.string.card_successfully_controlled), Toast.LENGTH_SHORT).show();
        if (dialog != null)dialog.dismiss();
        AsyncTask<Void, Void, Bitmap> print = new PrintReceipt(this, content);
        print.execute();
    }

    @Override
    public void executePrinting() {

    }

    @Override
    public void onPrintReceipt(Bitmap b) {
        printer.printPic(b, this);
    }

    @Override
    public void onShowSummary(Ticket ticket) {
        if (ticket.getIsCtrl() != 0){
            binding.cardCodeEt.setText(null);
        }
        new Handler().postDelayed(() -> {
            TicketSummaryFragment summaryFragment = TicketSummaryFragment.getInstance(ticket, new ControlListener() {
                @Override
                public void controlTicket(Ticket ticket) {
                    presenter.control(ticket);
                }
            });

            summaryFragment.show(getSupportFragmentManager(), "Ticket Summary");
        }, 700);

    }

    @Override
    protected void onPause() {
        //api.CloseScanning();
        super.onPause();
        //unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        //api.stopScanner();
        //api.downGpio();
        super.onDestroy();
    }

    public interface ControlListener {
        void controlTicket(Ticket ticket);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null){
            new Handler().postDelayed(() -> {
                presenter.checkCode(intentResult.getContents(), true);
            }, 500);

        }else{
            //TODO
        }
    }
}