package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RejectOrder extends AppCompatActivity {
    DatabaseReference reference;
    Button backpri;
    RecyclerView datalistRejectQueue;
    RejectOrder_adapter reject_adapter;
    List<String> orderID;
    List<String> dateAR;
    List<String> summaryArray;
    List<String> locationArray;
    List<String> orderId2Arr;
    List<String> statuSArr;
    List<String> Numbering;
    List<String> name;
    private RejectOrder_adapter.RecyclerClick listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reject_order);
        datalistRejectQueue = findViewById(R.id.dataListRejectQueue);
        backpri = findViewById(R.id.backpri);
        orderID = new ArrayList<>();
        dateAR = new ArrayList<>();
        summaryArray = new ArrayList<>();
        locationArray = new ArrayList<>();
        orderId2Arr = new ArrayList<>();
        statuSArr = new ArrayList<>();
        Numbering = new ArrayList<>();name =new ArrayList<>();
        setOnClickListener(); //kailangan sa taas to
        // Toast.makeText(o1_OrderQueue.this, "Dito1", Toast.LENGTH_SHORT).show();
        backpri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectOrder.this, Home.class);
               startActivity(intent);
                finish();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.orderByChild("schedule_date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String status = snapshot1.child("status").getValue().toString();
                    if (status.equals("rejected")){
                        String orderId2 = snapshot1.child("order_id_now").getValue().toString();
                        String orderId = snapshot1.child("order_id_ref").getValue().toString();
                        String dateStamp = snapshot1.child("schedule_date").getValue().toString();
                        String location = snapshot1.child("order_location").getValue().toString();
                        String summary = snapshot1.child("order_summary").getValue().toString();
                        String queue_num = snapshot1.child("queue_number").getValue().toString();
                        String namee = snapshot1.child("fullname").getValue().toString();
                        String dateTotoo="";

                        if (!dateStamp.equals("No Schedule")){
                            long etMills = Long.parseLong(dateStamp);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                            String date = simpleDateFormat.format(etMills);
                            String time = simpleTimeFormat.format(etMills);
                            dateTotoo = time+" "+date;
                        }else{
                            dateTotoo = dateStamp;
                        }

                        orderID.add(orderId);
                        dateAR.add(dateTotoo);
                        summaryArray.add(summary);
                        locationArray.add(location);
                        orderId2Arr.add(orderId2);
                        statuSArr.add(status);
                        Numbering.add(queue_num);
                        name.add(namee);

                    }
                }
                reject_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectOrder.this, "Database Error :( ", Toast.LENGTH_LONG).show();
            }
        });
        reject_adapter= new RejectOrder_adapter(this,orderID,dateAR,summaryArray,statuSArr, Numbering, name, listener); // kapg gusto mag add new Array
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        datalistRejectQueue.setLayoutManager(gridLayoutManager);
        datalistRejectQueue.setAdapter(reject_adapter);
    }
    private void setOnClickListener() {
// Para sa Clickable recycler view 4:51
        listener = new RejectOrder_adapter.RecyclerClick(){
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getApplicationContext(), RejectOrder2.class);
                String id = orderID.get(position);
                String locationNg= locationArray.get(position);
                String orderId2 = orderId2Arr.get(position);
                intent.putExtra("orderId", id);
                intent.putExtra("locationNg", locationNg);
                intent.putExtra("orderID2Now", orderId2);
                startActivity(intent);
            }
        };
    }
}