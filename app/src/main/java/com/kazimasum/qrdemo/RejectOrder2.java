package com.kazimasum.qrdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
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


public class RejectOrder2 extends AppCompatActivity {
    DatabaseReference reference;
    TextView dateq,orderIdQ,textSummary,txtpayment,txtstat,otherDetails;
    ImageView imagep;
    Button btnaccept,btnreject,buttonBack;
    sessionManagement sessionEto;
    List<String> titles;
    List<Integer> images;
    List<String> hiddenId;
    List<String> prices;
    List<String> quantity; // kapg gusto mag add new Array
    List<String> description;

    private EditText mobilenum, mensahe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reject_order2);
        dateq = findViewById(R.id.dateQ);
        orderIdQ = findViewById(R.id.orderIdQ);
        textSummary = findViewById(R.id.textSummary);
        txtpayment = findViewById(R.id.txtpayment);
        txtstat = findViewById(R.id.txtstat);
        imagep = findViewById(R.id.imagep);otherDetails = findViewById(R.id.otherDetails);
        titles = new ArrayList<>();
        images = new ArrayList<>();
        hiddenId = new ArrayList<>();
        prices = new ArrayList<>();
        quantity = new ArrayList<>();
        description = new ArrayList<>();

        buttonBack = findViewById(R.id.buttonBack);
        btnaccept = findViewById(R.id.btnaccept);
        btnreject = findViewById(R.id.btnreject);
        sessionEto = new sessionManagement(getApplicationContext());
        mobilenum = findViewById(R.id.mobileNo);
        mensahe = findViewById(R.id.mensahe);
        final String orderidniya = getIntent().getStringExtra("orderID2Now");
        ActivityCompat.requestPermissions(RejectOrder2.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);



        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderidniya).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String status = snapshot.child("status").getValue().toString();
                String order_id_now = snapshot.child("order_id_now").getValue().toString();
                String dateStamp = snapshot.child("schedule_date").getValue().toString();
                String order_summary = snapshot.child("order_summary").getValue().toString();
                //String location = snapshot.child("order_location").getValue().toString();
               // String mobileno = snapshot.child("mobileno").getValue().toString();
                String fullname = snapshot.child("fullname").getValue().toString();
                String queueNum = snapshot.child("queue_number").getValue().toString();
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

                otherDetails.setText(order_id_now+"\n"+
                        dateTotoo+"\n"+
                        fullname+"\n"+
                        queueNum+"\n");

                textSummary.setText(order_summary);
                txtstat.setText(status);
                txtpayment.setText(status);
                String proof = snapshot.child("imageUrl").getValue().toString();
                Picasso.get().load(proof).into(imagep);

                /*

                if (status.equals("rejected") && dateStamp.equals("No Schedule")) {

                } else if (status.equals("rejected")){

                    dateq.setText("");
                    orderIdQ.setText(order_id_now);
                    textSummary.setText(order_summary);
                    txtstat.setText(status);
                    txtpayment.setText(status);
                        }else{
                    imagep.setVisibility(View.INVISIBLE);
                    txtpayment.setText("Paid");
                    btnaccept.setVisibility(View.INVISIBLE);
                    btnreject.setVisibility(View.INVISIBLE);

                    long etMills = Long.parseLong(dateStamp);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String date = simpleDateFormat.format(etMills);
                    String time = simpleTimeFormat.format(etMills);

                    dateq.setText(time +" "+ date);
                    orderIdQ.setText(order_id_now);
                    textSummary.setText(order_summary);
                    txtstat.setText(status);
                }*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectOrder2.this, "Database Error :( ", Toast.LENGTH_LONG).show();
            }
        });


        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("PAYMENT STATUS");
                builder.setMessage("Do You Want to Accept this Order Now?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acceptpay(orderidniya);
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

        btnreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("PAYMENT STATUS");
                builder.setMessage("Are You Sure You Want To Delete this Order?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rejectpay(orderidniya);
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

        imagep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectOrder2.this, Zoomreject.class);
                intent.putExtra("ket_proof2", orderidniya);
                startActivity(intent);
            }
        });
    }
    //=================================================//Toast.makeText(getApplicationContext(), "read ", Toast.LENGTH_LONG).show();
    public void  acceptpay(String orderidniya){
        HashMap prepfood = new HashMap();
        prepfood.put("status","preparing");
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderidniya).updateChildren(prepfood).addOnCompleteListener(new OnCompleteListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onComplete(@NonNull Task task) {
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED);
                    acceptsms();
                }else{
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                }*/
                finish();
            }
        });

    }

    public void rejectpay(String orderidniya){
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderidniya).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED);
                    rejects();
                }else{
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                }*/
                finish();
            }
        });



    }
   /* public void acceptsms(){
        mobilenum.setText(sessionEto.getMobileno());
        mensahe.setText("Good Day " + sessionEto.getFullname() +",\nYour order is now finally accepted\nPlease wait for the message upon receiving your order. :)" );

        String number = mobilenum.getText().toString();
        String message = mensahe.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
    }*/
    /*public void rejects(){
        mobilenum.setText(sessionEto.getMobileno());
        mensahe.setText("Good Day " + sessionEto.getFullname() + ",\nWe're sorry to say but your order is invalid and cancelled\n Thank you for choosing us" );
        String number = mobilenum.getText().toString();
        String message = mensahe.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
    }*/
}