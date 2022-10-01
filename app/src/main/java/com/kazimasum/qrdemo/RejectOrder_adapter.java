package com.kazimasum.qrdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class RejectOrder_adapter extends RecyclerView.Adapter<RejectOrder_adapter.ViewHolder> {
    private RejectOrder_adapter.RecyclerClick listener;
    List<String> orderId;
    List<String> date;
    List<String> summaryArray;
    List<String> statuS;
    List<String> Numbering;
    List<String> name;
    Context context;
    LayoutInflater inflater;
    DatabaseReference reference;


    public RejectOrder_adapter(Context ctx, List<String> orderId, List<String> date, List<String> summaryArray,
                               List<String> statuS, List<String> Numbering, List<String> name, RejectOrder_adapter.RecyclerClick listener){ // for the click
        this.orderId = orderId;
        this.date =date;
        this.statuS = statuS;
        this.inflater = LayoutInflater.from(ctx);
        this.listener = listener; // for the click
        this.summaryArray = summaryArray;
        this.Numbering = Numbering;
        this.name = name;
    }

    @NonNull
    @Override
    public RejectOrder_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reject_order_custom, parent, false);
        return new RejectOrder_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectOrder_adapter.ViewHolder holder, int position) {
        //holder.orderID.setText(orderId.get(position));
        //holder.dateQ.setText(date.get(position));
        holder.textSummary.setText(summaryArray.get(position));
        holder.txtstatus.setText(statuS.get(position));
        //holder.NumberingDis.setText(Numbering.get(position));
        holder.otherDetails.setText(
                orderId.get(position)+"\n"+
                date.get(position)+"\n"+
                name.get(position)+"\n"+
                Numbering.get(position)
        );

    }

    @Override
    public int getItemCount() {
        return orderId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView orderID, dateQ, summary, textSummary,txtstatus, NumberingDis, otherDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // orderID = itemView.findViewById(R.id.orderIdQ);
            //dateQ = itemView.findViewById(R.id.dateQ);
            summary = itemView.findViewById(R.id.textSummary);
            textSummary =itemView.findViewById(R.id.textSummary);
            txtstatus = itemView.findViewById(R.id.ttxtstatus);
            //NumberingDis = itemView.findViewById(R.id.NumberingDis);
            otherDetails = itemView.findViewById(R.id.otherDetails);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface RecyclerClick {
        void onClick(View v, int position);
    }
}
