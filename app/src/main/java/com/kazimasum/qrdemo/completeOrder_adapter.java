package com.kazimasum.qrdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class completeOrder_adapter extends RecyclerView.Adapter<completeOrder_adapter.ViewHolder> {

    List<String> dateAr;
    List<String> totalAr;
    List<String> statusAr;
    List<String> summaryAr;
    List<String> otherDetAr;
    Context context;
    LayoutInflater inflater;

    public completeOrder_adapter(Context ctx, List<String> dateAr, List<String> totalAr, List<String> statusAr,
                                 List<String> summaryAr, List<String> otherDetAr){
        this.dateAr = dateAr;
        this.totalAr = totalAr;
        this.statusAr = statusAr;
        this.summaryAr = summaryAr;
        this.otherDetAr = otherDetAr;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.complete_order_customlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateF.setText(dateAr.get(position));
        holder.totalF.setText(totalAr.get(position));
        holder.txtstatusF.setText(statusAr.get(position));
        holder.textSummaryF.setText(summaryAr.get(position));// 88888 kapg gusto mag add new Array 88888
        holder.otherDetails.setText(otherDetAr.get(position));
    }

    @Override
    public int getItemCount() {
        return dateAr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView otherDetails, dateF, textSummaryF, txtstatusF, totalF;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherDetails = itemView.findViewById(R.id.otherDetails);
            dateF =itemView.findViewById(R.id.dateF);
            textSummaryF = itemView.findViewById(R.id.textSummaryF);
            txtstatusF =itemView.findViewById(R.id.txtstatusF);
            totalF = itemView.findViewById(R.id.totalF);
        }
    }
}
