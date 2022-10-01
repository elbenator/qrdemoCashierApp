package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
public class o1_OrderQueue extends AppCompatActivity {
    RecyclerView dataListQueue;
    TextView textView8;
    DatabaseReference reference;
    Button backQ, scanQR;
    List<String> orderID;
    List<String> dateAR;
    List<String> summaryArray;
    o1_orderQueue_recycler1 adapter;
    List<String> locationArray;
    List<String> orderId2Arr;
    List<String> statuSArr;
    List<String> Numbering;
    sessionManagement sessionManagement;


    private o1_orderQueue_recycler1.RecyclerClick listener; // Para sa Clickable recycler view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.o1_order_queue);
        dataListQueue = findViewById(R.id.dataListQueue);
        backQ = findViewById(R.id.backQ);
        scanQR = findViewById(R.id.scanQR);
        orderID = new ArrayList<>();
        dateAR = new ArrayList<>();
        summaryArray = new ArrayList<>();
        locationArray = new ArrayList<>();
        orderId2Arr = new ArrayList<>();
        statuSArr = new ArrayList<>();
        Numbering = new ArrayList<>();
        textView8 = findViewById(R.id.textView8);
        sessionManagement = new sessionManagement(getApplicationContext());

        setOnClickListener(); //kailangan sa taas to
       // Toast.makeText(o1_OrderQueue.this, "Dito1", Toast.LENGTH_SHORT).show();

        //Back button
        backQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(o1_OrderQueue.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(o1_OrderQueue.this, scannerView.class);
                startActivity(intent);
                finish();
            }
        });

        /*orderID.add("orderId");
        dateAR.add("dddd");
        summaryArray.add("summary");
        statuSArr.add("status");
        Numbering.add("1");*/

        //1 Step 1 Start
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.orderByChild("schedule_date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderID.clear();statuSArr.clear(); Numbering.clear();
                dateAR.clear();summaryArray.clear();

               for (DataSnapshot snapshot1: snapshot.getChildren()){
                   String status = snapshot1.child("status").getValue().toString();

                   if (!status.equals("finish")&& (!status.equals("rejected"))){
                       String orderId2 = snapshot1.child("order_id_now").getValue().toString();
                       String orderId = snapshot1.child("order_id_ref").getValue().toString();
                       String location = snapshot1.child("order_location").getValue().toString();
                       String summary = snapshot1.child("order_summary").getValue().toString();
                       String queue_num = snapshot1.child("queue_number").getValue().toString();
                       String dateStamp = snapshot1.child("schedule_date").getValue().toString();
                       String fullname = snapshot1.child("fullname").getValue().toString();
                       if (dateStamp.equals("No Schedule")){
                           orderID.add(orderId2+"\n"+ fullname);
                           dateAR.add(dateStamp);
                           summaryArray.add(summary);
                           locationArray.add(location);
                           orderId2Arr.add(orderId2);
                           statuSArr.add(status);
                           Numbering.add(queue_num);
                       }
                       else {
                           long etMills = Long.parseLong(dateStamp);
                           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                           SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

                           String date = simpleDateFormat.format(etMills);
                           String time = simpleTimeFormat.format(etMills);
                           orderID.add(orderId2+"\n"+ fullname);
                           dateAR.add(time + " " + date);
                           summaryArray.add(summary);
                           locationArray.add(location);
                           orderId2Arr.add(orderId2);
                           statuSArr.add(status);
                           Numbering.add(queue_num);
                           //numberingDigi++;
                       }
                   }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(o1_OrderQueue.this, "Database Error :( ", Toast.LENGTH_LONG).show();
            }
        });

        adapter = new o1_orderQueue_recycler1(this,orderID,dateAR,summaryArray,statuSArr, Numbering, listener); // kapg gusto mag add new Array
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        dataListQueue.setLayoutManager(gridLayoutManager);
        dataListQueue.setAdapter(adapter);
        //ilangordersmeron
        int bilangmoko = adapter.getItemCount();
        String bilanginmo = String.valueOf(bilangmoko);
        sessionManagement.setIlanorder(bilanginmo);
    }
    //=======Function ================

    private void setOnClickListener() {
// Para sa Clickable recycler view 4:51
        listener = new o1_orderQueue_recycler1.RecyclerClick(){
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getApplicationContext(), o2_OrderQueue2.class);
                String id = orderId2Arr.get(position);
               // String locationNg= locationArray.get(position);
                String orderId2 = orderId2Arr.get(position);
                //intent.putExtra("orderId", id);
                //intent.putExtra("locationNg", locationNg);
                intent.putExtra("orderID2Now", orderId2);
                sessionManagement.setThisShit(statuSArr.get(position));
                sessionManagement.setThisOrderId(id);
                startActivity(intent);


            }
        };
    }



}