package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class completeOrder extends AppCompatActivity {

    Button backpri;
    RecyclerView dataListFinish;
    DatabaseReference reference;
    completeOrder_adapter adapter1;
    List<String> dateAr;
    List<String> totalAr;
    List<String> statusAr;
    List<String> summaryAr;
    List<String> otherDetAr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_order);

        dateAr = new ArrayList<>();
        totalAr = new ArrayList<>();
        statusAr = new ArrayList<>();
        summaryAr =new ArrayList<>();
        otherDetAr = new ArrayList<>();
        dataListFinish = findViewById(R.id.dataListFinish);
        backpri = findViewById(R.id.backpri);

        backpri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //1 Step 1 Start
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.orderByChild("schedule_date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String status = snapshot1.child("status").getValue().toString();
                    if (status.equals("finish")){
                        String orderId2 = snapshot1.child("order_id_now").getValue().toString();
                        String orderId = snapshot1.child("order_id_ref").getValue().toString();
                        String dateStamp = snapshot1.child("schedule_date").getValue().toString();
                        String location = snapshot1.child("order_location").getValue().toString();
                        String summary = snapshot1.child("order_summary").getValue().toString();
                        String queue_num = snapshot1.child("queue_number").getValue().toString();
                        String fullname = snapshot1.child("fullname").getValue().toString();
                        String total = snapshot1.child("total").getValue().toString();
                        String dateTunay ="";
                        if (!dateStamp.equals("No Schedule")){
                            long etMills = Long.parseLong(dateStamp);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                            String date = simpleDateFormat.format(etMills);
                            String time = simpleTimeFormat.format(etMills);
                            dateTunay = time+ " "+date;
                        }else {
                            dateTunay = dateStamp;
                        }
                        dateAr.add(dateTunay);
                        summaryAr.add(summary);
                        otherDetAr.add(orderId+ "\n"+ fullname +"\n"+location+"\n"+queue_num);
                        statusAr.add(status);
                        totalAr.add("Total: "+ total);
                    }
                }
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Database Error :( ", Toast.LENGTH_LONG).show();
            }
        });

        adapter1 = new completeOrder_adapter(this, dateAr,  totalAr,  statusAr, summaryAr, otherDetAr); // kapg gusto mag add new Array
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        dataListFinish.setLayoutManager(gridLayoutManager);
        dataListFinish.setAdapter(adapter1);




    }
}