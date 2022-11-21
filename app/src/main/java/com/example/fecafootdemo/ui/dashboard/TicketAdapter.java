package com.example.fecafootdemo.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Ticket;

import java.util.ArrayList;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 11/18/22)
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private ArrayList<Ticket> tickets;

    public TicketAdapter(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTicket(tickets.get(position));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ticketCode, ticketAmount, issuedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketCode = itemView.findViewById(R.id.ticket_code_tv);
            ticketAmount = itemView.findViewById(R.id.amount_tv);
            issuedDate = itemView.findViewById(R.id.issued_date_tv);
        }

        public void bindTicket(Ticket ticket) {
            ticketCode.setText(ticket.getTicketCode());
            ticketAmount.setText(ticket.getAmount().concat(" fcfa"));
            issuedDate.setText(ticket.getStartDate());
        }
    }
}
