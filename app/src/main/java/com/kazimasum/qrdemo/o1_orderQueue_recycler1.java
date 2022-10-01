package com.kazimasum.qrdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class o1_orderQueue_recycler1 extends RecyclerView.Adapter<o1_orderQueue_recycler1.ViewHolder> {

    private RecyclerClick listener;
    List<String> orderId;
    List<String> date;
    List<String> summaryArray;
    List<String> statuS;
    List<String> Numbering;
    Context context;
    LayoutInflater inflater;
    DatabaseReference reference;


    public o1_orderQueue_recycler1(Context ctx, List<String> orderId, List<String> date, List<String> summaryArray,
                                   List<String> statuS, List<String> Numbering, RecyclerClick listener){ // for the click
        this.orderId = orderId;
        this.date =date;
        this.statuS = statuS;
        this.inflater = LayoutInflater.from(ctx);
        this.listener = listener; // for the click
        this.summaryArray = summaryArray;
        this.Numbering = Numbering;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.o1_custom_orderqueue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.orderID.setText(orderId.get(position));
        holder.dateQ.setText(date.get(position));
        holder.textSummary.setText(summaryArray.get(position));
        holder.txtstatus.setText(statuS.get(position));
        holder.NumberingDis.setText(Numbering.get(position));
    }

    @Override
    public int getItemCount() {
        return orderId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//======For the click 3:10
        public TextView orderID, dateQ, summary, textSummary,txtstatus, NumberingDis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderIdQ);
            dateQ = itemView.findViewById(R.id.dateQ);
            summary = itemView.findViewById(R.id.textSummary);
            textSummary =itemView.findViewById(R.id.textSummary);
            txtstatus = itemView.findViewById(R.id.ttxtstatus);
            NumberingDis = itemView.findViewById(R.id.NumberingDis);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //======For the click
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    //======For the click
    public interface RecyclerClick{
        void onClick(View v, int position);
    }



}
