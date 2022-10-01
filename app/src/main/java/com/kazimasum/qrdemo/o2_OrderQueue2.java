package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class o2_OrderQueue2 extends AppCompatActivity {

    DatabaseReference reference,reference1;
    TextView otherDetails,dateq,textSummary,txtpayment,txtstat,textView12;
    ImageView imagep;
    private EditText mobilenum, mensahe;
    Button markAsComp,btnaccept,btnreject,buttonBack;
    sessionManagement sessionEto;
    List<String> titles;   List<Integer> images;
    List<String> hiddenId; List<String> prices;
    List<String> description;
    List<String> quantity; // kapg gusto mag add new Array
    String orderID2;
    newItemHash newItemHashname;
    List<String> storeName; List<String> storIncre;
    List<String> blankArr;
    HashMap updVa= new HashMap();
    newItemHash newItemHashadap;
    int blah1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.o2_order_queue2);
        dateq = findViewById(R.id.dateQ);
        //orderIdQ = findViewById(R.id.orderIdQ);
        textSummary = findViewById(R.id.textSummary); txtpayment = findViewById(R.id.txtpayment);
        txtstat = findViewById(R.id.txtstat); imagep = findViewById(R.id.imagep);
        titles = new ArrayList<>(); images = new ArrayList<>();
        hiddenId = new ArrayList<>();  prices = new ArrayList<>();
        quantity = new ArrayList<>();  description = new ArrayList<>();
        storeName = new ArrayList<>(); storIncre = new ArrayList<>(); blankArr = new ArrayList<>();
        buttonBack = findViewById(R.id.buttonBack);  btnaccept = findViewById(R.id.btnaccept);
        btnreject = findViewById(R.id.btnreject);  markAsComp = findViewById(R.id.markAsComp);
        sessionEto = new sessionManagement(getApplicationContext());
        otherDetails = findViewById(R.id.otherDetails);
        mobilenum = findViewById(R.id.mobileNo);
        mensahe = findViewById(R.id.mensahe);
        textView12 =findViewById(R.id.textView12);
        newItemHashname=new newItemHash();
        //newItemHashadap = new newItemHash();
      //  scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListner());

        ActivityCompat.requestPermissions(o2_OrderQueue2.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
//SET sa simula ==================

        //if approval, preparing,
        String compare = sessionEto.getThisShit();
        switch (compare){
            case "approval":
                orderID2 = sessionEto.getThisOrderId();
                Toast.makeText(getApplicationContext(), "Order still needs Approval", Toast.LENGTH_LONG).show();
                sessionEto.setThisShit("");
                sessionEto.setThisOrderId("");
                break;
            case "preparing":
                orderID2 = sessionEto.getThisOrderId();
                Toast.makeText(getApplicationContext(), "Order is still Preparing", Toast.LENGTH_LONG).show();
                sessionEto.setThisShit("");
                sessionEto.setThisOrderId("");
                break;
            case "ready":
                orderID2 = sessionEto.getThisOrderId();//"-MqAgU0hNjoGMo6B8Q_Y";// sessionEto.getThisOrderId();
                Toast.makeText(getApplicationContext(), "Order is Done and ready to Pick-up", Toast.LENGTH_LONG).show();
                sessionEto.setThisShit("");
                sessionEto.setThisOrderId("");
                break;
            default:
                orderID2 = getIntent().getStringExtra("orderID2Now");
                sessionEto.setThisShit("");
                sessionEto.setThisOrderId("");
                break;
        }
       // Toast.makeText(o2_OrderQueue2.this, "Datab-- "+orderID2, Toast.LENGTH_LONG).show();
         reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderID2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullname = snapshot.child("fullname").getValue().toString();
                if (snapshot.child("mobileno").exists()){
                    String numm = snapshot.child("mobileno").getValue().toString();
                    sessionEto.setMobileno(numm);}
                String order_id_now= snapshot.child("order_id_now").getValue().toString();
                String location = snapshot.child("order_location").getValue().toString();
                String order_summary= snapshot.child("order_summary").getValue().toString();
                String dateStamp = snapshot.child("schedule_date").getValue().toString();
               String status= snapshot.child("status").getValue().toString();
                String totalp = snapshot.child("total").getValue().toString();
                String queue_number = snapshot.child("queue_number").getValue().toString();

               sessionEto.setFullname(fullname);

                //Kapag TABLET =======
                if (location.equals("preset_tablet")){
                    dateq.setText(dateStamp);
                    otherDetails.setText(
                            order_id_now+"\n"+
                                    fullname+"\n"+
                                    location+"\n"+
                                    totalp);
                    textSummary.setText(order_summary);
                    txtstat.setText(status);
                    txtpayment.setText("paid");
                    //btnaccept.setVisibility(View.INVISIBLE);
                    //btnreject.setVisibility(View.INVISIBLE);
                    imagep.setVisibility(View.INVISIBLE);
                    textView12.setText(queue_number);

                }
                //Kapag Sa mobile app ===========
                else if (location.equals("preset_order")){

             //Set Date000000000000000000
                    String dateTunay ="";
                    if (!dateStamp.equals("No Schedule")){
                        long etMills = Long.parseLong(dateStamp);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                        String date = simpleDateFormat.format(etMills);
                        String time = simpleTimeFormat.format(etMills);
                        dateTunay = time+ " "+date;
                        dateq.setText(dateTunay);
                        //Toast.makeText(o2_OrderQueue2.this, "Datab-- "+dateTunay, Toast.LENGTH_LONG).show();
                    }else {
                        dateTunay = dateStamp;
                        dateq.setText(dateTunay);
                    }


 //Set Image00000000000000000000000000
                    if (snapshot.child("imageUrl").exists()){
                        String proof = snapshot.child("imageUrl").getValue().toString();
                        Picasso.get().load(proof).into(imagep);
                    }else {
                        imagep.setVisibility(View.INVISIBLE);
                    }


        //Set for all-----------
                    otherDetails.setText(
                            order_id_now+"\n"+
                                    fullname+"\n"+
                                    location+"\n"+
                                    totalp);
                    textSummary.setText(order_summary);
                    txtstat.setText(status);
                    txtpayment.setText(status);
                    textView12.setText(queue_number);
                }
//Set Buttons000000000000000000000000000
                if (status.equals("preparing")){

                    btnreject.setVisibility(View.INVISIBLE);
                    btnaccept.setText("Mark As Ready");
                }else if (status.equals("ready")){
                    btnreject.setVisibility(View.INVISIBLE);
                    btnaccept.setText("Mark As Preparing");
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(o2_OrderQueue2.this, "Database Error :( ", Toast.LENGTH_LONG).show();
            }
        });

//Buttons =============================

        imagep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(o2_OrderQueue2.this, Zoomorderqueue.class);
                intent.putExtra("key_proof", orderID2);
                startActivity(intent);

            }
        });

        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textngbutton = btnaccept.getText().toString();
                if (textngbutton.equals("Mark As Ready")){
                    reference = FirebaseDatabase.getInstance().getReference("orders");
                    reference.child(orderID2).child("status").setValue("ready").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            back2();
                            textready();
                        }
                    });

                }else if (textngbutton.equals("Mark As Preparing")){
                    reference = FirebaseDatabase.getInstance().getReference("orders");
                    reference.child(orderID2).child("status").setValue("preparing");
                    back2();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("PAYMENT STATUS");
                    builder.setMessage("Are you sure you want to accept this order?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            acceptpay(orderID2);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog =builder.create();
                    alertDialog.show();
                }



            }
        });

        btnreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("PAYMENT STATUS");
                builder.setMessage("Are you sure you want to reject this order?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rejectpay(orderID2);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog =builder.create();
                alertDialog.show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        markAsComp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View view ) {
                String statu = txtstat.getText().toString();

                if (statu.equals("preparing")||statu.equals("ready")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Order Completed");
                    builder.setMessage("Finish this transaction?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialog, int which ) {
                            markascompletefunc(orderID2);

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialog, int which ) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

    }

    //=================================================//Toast.makeText(getApplicationContext(), "read ", Toast.LENGTH_LONG).show();
    public void  acceptpay(String orderID2){
        HashMap prepfood = new HashMap();
        prepfood.put("status","preparing");
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderID2).updateChildren(prepfood).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                textaccept();

                Intent intent = new Intent(o2_OrderQueue2.this, o1_OrderQueue.class);
                Toast.makeText(getApplicationContext(), "Order Accepted", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });
    }

    public void rejectpay(String orderID2){
        HashMap prepfood = new HashMap();
        prepfood.put("status","rejected");
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderID2).updateChildren(prepfood).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Intent intent = new Intent(o2_OrderQueue2.this, o1_OrderQueue.class);
                Toast.makeText(getApplicationContext(), "Order Rejected", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });

    }

    public void markascompletefunc(final String OrderId){
        //analyticsfunc(OrderId);

       HashMap finish = new HashMap();
        finish.put("status", "finish");
        String samoi=dateq.getText().toString();
        if(samoi.equals("No Schedule")){
            final long DateToday = System.currentTimeMillis();
            finish.put("schedule_date", DateToday);
        }


        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderID2).updateChildren(finish).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
               // analyticsfunc(OrderId);
                Intent intent = new Intent(getApplicationContext(), z_analytics_pasahan.class);
                intent.putExtra("orderId", OrderId);
                startActivity(intent);
                finish();
            }
        });

       /*  mobilenum.setText(sessionEto.getMobileno());
        mensahe.setText("Good Day, " + sessionEto.getFullname() + "\n" + "Your order is now ready to pickup." + "\n" +" Please show the confirmation QR code at the cashier");
       String number = mobilenum.getText().toString();
        String message = mensahe.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);*/

    }

    /*/ --------------- Analytics -------
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
                Toast.makeText(getApplicationContext(), "two.2", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "three"+blah1, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "four ", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void Step4(String todaySession){

        storeName = sessionEto.getThisArray();
        storIncre = sessionEto.getThisArrayIncre();
        Toast.makeText(getApplicationContext(), "Five"+storIncre.size(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < storeName.size(); i++) {
            String itemName = storeName.get(i);
            String number= storIncre.get(i);
            updVa.put(itemName, number);
        }

        reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
        reference.child("daySession").child(todaySession).updateChildren(updVa);
        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
        storeName.clear();
        storIncre.clear();
        sessionEto.setThisArray(blankArr);
        sessionEto.setThisArrayIncre(blankArr);
       // Intent intent = new Intent(getApplicationContext(), Home.class);
       // startActivity(intent);
        back2();
    }*/
    // --------------- Analytics -------

    //SMSAccept
    public void textaccept(){
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderID2).addListenerForSingleValueEvent(new ValueEventListener() {
          //  @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             //       if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
               //         ;
                    acceptsms();
             //   } else {
                 //   requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
              //  }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //SMSReady
    public  void textready(){
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderID2).addListenerForSingleValueEvent(new ValueEventListener() {
           // @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                   //     if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED);
                        readysms();
                 //  }else{
                   //     requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                   // }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //--SMSAccepted
    public void acceptsms(){
        mobilenum.setText(sessionEto.getMobileno());
        mensahe.setText("Good Day " + sessionEto.getFullname() +",\nYour order is accepted and is being prepared.\nPlease wait for the message upon receiving your order. Thank You." );

        String number = mobilenum.getText().toString();
        String message = mensahe.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
    }
    public void  readysms(){
        mobilenum.setText(sessionEto.getMobileno());
        mensahe.setText("Good Day " + sessionEto.getFullname() + ",\nYour order is now ready to pickup.\nPlease show the confirmation QR code at the cashier. Thank You.");
        String number = mobilenum.getText().toString();
        String message = mensahe.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
    }
    //--SMS

    public void back2(){
        //Intent intent = new Intent(o2_OrderQueue2.this,Home.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        finish();
    }
}

