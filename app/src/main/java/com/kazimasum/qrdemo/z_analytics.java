package com.kazimasum.qrdemo;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class z_analytics {
    DatabaseReference reference, reference1;
    newItemHash newItemHashname;
    sessionManagement sessionEto;
    List<String> blankArr, storeName, storIncre;
    int blah1;
    HashMap updVa= new HashMap();

    public z_analytics(Context ctx) {
        newItemHashname = new newItemHash();
        sessionEto = new sessionManagement(ctx);
        blankArr = new ArrayList<>(); storeName = new ArrayList<>(); storIncre = new ArrayList<>();

    }

    // --------------- Analytics -------
    public void analyticsfunc(final String OrderId){
        final long DateToday = System.currentTimeMillis();
        final String DateTodayString = String.valueOf(DateToday);

        reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String todaySession = snapshot.child("todaySessionDate").getValue().toString();
                long todaySessionLong = Long.valueOf(todaySession);
                long tommorowDate= todaySessionLong + 86400000;

                //check if pasok sa ngayon
                long DateConv = Long.valueOf(DateToday);

                if (DateConv <= tommorowDate){
                    //then count yung order ngayon
                    StepCount(todaySession, OrderId);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Step4(todaySession);
                        }
                    }, 1200);
                }
                else{
                    //else gawa ng bagong DB based sa date ngayon
                    HashMap updTom = new HashMap();
                    updTom.put("todaySessionDate",DateTodayString);
                    reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
                    reference.updateChildren(updTom);
                    // Tapos create new DB
                    reference1 = FirebaseDatabase.getInstance().getReference("analytics_counter");
                    reference1.child("daySession").child(DateTodayString).setValue(newItemHashname.newItemHash()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Toast.makeText(getApplicationContext(), "Orde", Toast.LENGTH_LONG).show();
                            analyticsfunc(OrderId);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void StepCount(final String todaySession, final String OrderId){
        sessionEto.setThisArrayIncre(blankArr);
        sessionEto.setThisArray(blankArr);
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(OrderId).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String orderRef = snapshot.child("order_id_ref").getValue().toString();
                String orderLoc = snapshot.child("order_location").getValue().toString();
                StepCount2(orderLoc, orderRef,todaySession);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void StepCount2(String orderLoc,String orderRef, final String todaySession){
        reference = FirebaseDatabase.getInstance().getReference(orderLoc);
        reference.child(orderRef).child("order").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    String itemId = snapshot1.child("itemId").getValue().toString();
                    if (newItemHash.returnIfExisting(itemId)){
                        storeName.add(itemId);
                        //Toast.makeText(getApplicationContext(), "two-"+itemId, Toast.LENGTH_SHORT).show();
                    }
                }
                sessionEto.setThisArray(storeName);
                StepCount3pansa(todaySession);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void StepCount3pansa(String todaySession){

        reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
        reference.child("daySession").child(todaySession).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeName = sessionEto.getThisArray();
                blah1 = storeName.size();
                sessionEto.setStep(0);

                for (int i = 0; i < blah1; i++) {
                    String itemName = storeName.get(i);
                    String itemCount = snapshot.child(itemName).getValue().toString();
                    int incre= Integer.valueOf(itemCount);
                    incre++;
                    String increString = String.valueOf(incre);
                    storIncre.add(increString);
                }
                sessionEto.setThisArrayIncre(storIncre);
                //int numb= storIncre.size();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void Step4(String todaySession){

        storeName = sessionEto.getThisArray();
        storIncre = sessionEto.getThisArrayIncre();
        for (int i = 0; i < storeName.size(); i++) {
            String itemName = storeName.get(i);
            String number= storIncre.get(i);
            updVa.put(itemName, number);
        }

        reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
        reference.child("daySession").child(todaySession).updateChildren(updVa);
        storeName.clear();
        storIncre.clear();
        sessionEto.setThisArray(blankArr);
        sessionEto.setThisArrayIncre(blankArr);
    }
}
