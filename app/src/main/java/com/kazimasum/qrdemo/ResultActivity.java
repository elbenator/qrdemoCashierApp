package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    public static TextView result;

    TextView totalCH, statusText,summaryOrder;
    CheckBox payment,discountcheck;
    Button btnback1, QueueOrder;
    List<String> titles;
    List<Integer> images;
    List<String> hiddenId;
    public List<String> prices;
    List<String> quantity; // kapg gusto mag add new Array
    List<String> description;

    List<String> PriceTotal;
    List<String> QtyTotal;
    List<String> SummaryWithAddon;
    List<String> SummarySpaces;

    sessionManagement sessionManagement;
    ResultActivity_adapter adapter;
    RecyclerView dataList;
    ImageView imageViewf;
    imageClass imageNaClass;
    String order_location;
    String orderIdpublic; boolean selectedIdent=false;

    DatabaseReference reference, reference1;
    newItemHash newItemHashname;
    sessionManagement sessionEto;
    List<String> blankArr, storeName, storIncre;
    int blah1;
    HashMap updVa= new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        dataList = findViewById(R.id.datalist);
        titles = new ArrayList<>();
        images = new ArrayList<>();
        hiddenId = new ArrayList<>();
        prices = new ArrayList<>();
        quantity = new ArrayList<>();
        description = new ArrayList<>();
        totalCH = findViewById(R.id.totalCH);
        QueueOrder = findViewById(R.id.finishBtn);
        btnback1 = findViewById(R.id.btnback1);
        payment = findViewById(R.id.payment);
        discountcheck = findViewById(R.id.discountcheck);
        imageViewf = findViewById(R.id.imageViewF);
        statusText = findViewById(R.id.statusText);
        imageNaClass = new imageClass();
        PriceTotal = new ArrayList<>();
        QtyTotal = new ArrayList<>();
        SummaryWithAddon = new ArrayList<>();
        SummarySpaces = new ArrayList<>(); summaryOrder = findViewById(R.id.summaryOrder);
        final String textqrResult = getIntent().getStringExtra("RESULT");

        newItemHashname = new newItemHash();
        sessionManagement = new sessionManagement(getApplicationContext());
        blankArr = new ArrayList<>(); storeName = new ArrayList<>(); storIncre = new ArrayList<>();

//Buttons00000000
        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }});

        QueueOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIdent){
                    finishButton(textqrResult);
                }else {
                    Toast.makeText(getApplicationContext(), "Payment is Required", Toast.LENGTH_SHORT).show();
                }

            }});

        payment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //QueueOrder.setEnabled(true);
                    String dati = statusText.getText().toString();
                    statusText.setText(dati+"\nPaid");
                    selectedIdent = true;
                }else {
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RESULT", sessionManagement.getTempId());
                    startActivity(intent);
                    finish();
                    selectedIdent = false;
                }
            }
        });

        discountcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    updateDiscount();
                }else {
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RESULT", sessionManagement.getTempId());
                    startActivity(intent);
                    finish();
                }
            }
        });

//ShowQR000000000000000000000000

        sessionManagement.setTempId(textqrResult);
        checkQrandGet(textqrResult);

        adapter= new ResultActivity_adapter(this,titles, images, hiddenId, prices, quantity, description); // kapg gusto mag add new Array
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
        //   Toast.makeText(o1_OrderQueue.this, "Database Error :( ", Toast.LENGTH_LONG).show();
    }
    // ============ Functions ============================================

    //Step 1 check kung nasa ======== preset_tablet =======
    public void checkQrandGet(final String textqrResult){
        reference = FirebaseDatabase.getInstance().getReference("preset_tablet");
        reference.orderByKey().equalTo(textqrResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                   String presetSaan = "preset_tablet";
                    //Toast.makeText(getApplicationContext(), "Datror :( ", Toast.LENGTH_LONG).show();
                    step2(textqrResult, presetSaan);
                    sessionManagement.setLocationOrder(presetSaan);
                    sessionManagement.setUserIdUmorder(presetSaan);
                }
                else {
                    checkKungNasaPreset_order(textqrResult);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //step 1 PERO preset_order
    public void checkKungNasaPreset_order(final String textqrResult){
        reference = FirebaseDatabase.getInstance().getReference("preset_order");
        reference.orderByKey().equalTo(textqrResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Toast.makeText(getApplicationContext(), "dito", Toast.LENGTH_LONG).show();
                    String presetSaan = "preset_order";
                    step2(textqrResult, presetSaan);
                    sessionManagement.setLocationOrder(presetSaan);
                }
                else {
                    checkKungNasaOrders(textqrResult);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    //step 1 PERO sa Orders Queue na
    public void checkKungNasaOrders(final String textqrResult){
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.orderByKey().equalTo(textqrResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String status = dataSnapshot.child(textqrResult).child("status").getValue().toString();
                    switch (status){
                        case "approval":
                            sessionManagement.setThisOrderId(textqrResult);
                            sessionManagement.setThisShit(status);
                            Intent intent2 = new Intent(getApplicationContext(), o2_OrderQueue2.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case "preparing":
                            sessionManagement.setThisOrderId(textqrResult);
                            sessionManagement.setThisShit("preparing");
                          //  Toast.makeText(getApplicationContext(), "dito ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), o2_OrderQueue2.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "ready":
                            //punta sa order Queue2.class ng order niya
                            sessionManagement.setThisOrderId(textqrResult);
                            sessionManagement.setThisShit("ready");
                          //  Toast.makeText(getApplicationContext(), "dito ", Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(getApplicationContext(), o2_OrderQueue2.class);
                            startActivity(intent3);
                            finish();
                            break;
                        case "finish":
                            dialogPop();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "something ", Toast.LENGTH_LONG).show();
                            break;

                    }
                }
                else {
                    Toast.makeText(ResultActivity.this, "QR Does not Exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    //STEP 2 ====== For Preset ================
    public void step2(final String textqrResult, final String presetSaan){
        order_location = presetSaan;
        orderIdpublic = textqrResult;

        reference = FirebaseDatabase.getInstance().getReference(presetSaan);
        reference.child(textqrResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String totalp = dataSnapshot.child("info").child("total").getValue().toString();
                String status = dataSnapshot.child("info").child("status").getValue().toString();
                String fullname = dataSnapshot.child("info").child("userId").getValue().toString();
                String summary = dataSnapshot.child("info").child("summary").getValue().toString();
                String queueNum="";
                if (dataSnapshot.child("info").child("queue_number").exists()){
                    queueNum = dataSnapshot.child("info").child("queue_number").getValue().toString();}
                sessionManagement.setThisShit(queueNum);

                String otherString="";
                for(DataSnapshot snapshot1: dataSnapshot.child("order").getChildren()){
                    String adOns_identifier = snapshot1.child("adOns_identifier").getValue().toString();

                    if (adOns_identifier.equals("true") || adOns_identifier.equals("false")){
                        String itemName = snapshot1.child("item").getValue().toString();
                        String qty = snapshot1.child("qty").getValue().toString();
                        String itemId= snapshot1.child("itemId").getValue().toString();
                        String priceK = snapshot1.child("price").getValue().toString();
                        String categ= snapshot1.child("category").getValue().toString();
                        //Toast.makeText(getApplicationContext(), "  "+itemName, Toast.LENGTH_SHORT).show();

                        if(snapshot1.child("sugarLvl").exists()){
                            String sugar= snapshot1.child("sugarLvl").getValue().toString();
                            otherString = "\nSgrLvl: "+sugar;
                        }
                        else{
                            otherString="";
                        }

                        images.add(imageNaClass.passthepic(itemId));
                        titles.add(categ+"\n"+itemName);
                       //quantity.add(qty); // kapg gusto mag add new Array
                       hiddenId.add(itemId);
                       description.add("₱"+priceK+"    x"+qty +otherString);

                        SummarySpaces.add(""+qty);
                        SummaryWithAddon.add(itemId + otherString);
                   }
                   else {
                       String qty = snapshot1.child("qty").getValue().toString();
                       String itemName = snapshot1.child("item_name").getValue().toString();
                        SummarySpaces.add("  -- "+qty);
                        SummaryWithAddon.add(itemName );
                   }
                }
                adapter.notifyDataSetChanged();
               // String total= String.valueOf(returnTotal());

                statusText.setText(status);
                summaryOrder.setText(summary);
                totalCH.setText(totalp);
                sessionManagement.setUserIdUmorder(fullname);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //dialogbox
    public void dialogPop(){

        //setinvisbble 000000000
        summaryOrder.setVisibility(View.INVISIBLE);
        totalCH.setVisibility(View.INVISIBLE);
        QueueOrder.setVisibility(View.INVISIBLE);
        statusText.setVisibility(View.INVISIBLE);
        payment.setVisibility(View.INVISIBLE); discountcheck.setVisibility(View.INVISIBLE);

        //000000000000000000
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Order has Already been Completed");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        /*builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //summary
    public String returnString(){
        int blah = SummaryWithAddon.size();
        //blah--;
        String summaryNg = "";
        for (int i = 0; i < blah; i++) {
            String item = SummaryWithAddon.get(i);
            String qty = QtyTotal.get(i);
            String spaces = SummarySpaces.get(i);
            summaryNg = summaryNg + spaces+ qty+" "+item + "\n";
        }
        return summaryNg;
    }

    public void updateDiscount(){
        //total price
        double totalpayment;
        double discountedprice;
        double discount = 0.2;
        String dede= totalCH.getText().toString();
        int pepe = Integer.valueOf(dede);
        discountedprice = (float) (pepe * discount);
        totalpayment = pepe - discountedprice;
        String blah2 = String.valueOf(totalpayment);
        totalCH.setText(blah2);

    }

    public String getNumberDB(String location, String orderId){
        reference = FirebaseDatabase.getInstance().getReference(location);
        reference.child(orderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                String numberNg = dataSnapshot.child("queue_number").getValue().toString();
                sessionManagement.setThisShit(numberNg);
            }
            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        });
        String eto = sessionManagement.getThisShit();
        sessionManagement.setThisShit("");
        return eto;
    }

    public void finishButton(String textResult) {
        //1 new orders queue
        reference = FirebaseDatabase.getInstance().getReference("orders").push();
        final String keypush = reference.getKey();
        String timeMili = String.valueOf(System.currentTimeMillis());
        //Toast.makeText(ResultActivity.this, hiddenId.get(0), Toast.LENGTH_SHORT).show();
        String Summ = summaryOrder.getText().toString();
        String totalP = totalCH.getText().toString();


        HashMap orderNew = new HashMap();
        orderNew.put("fullname", "");
        orderNew.put("order_id_now",keypush);
        orderNew.put("order_id_ref",textResult);
        orderNew.put("order_location", sessionManagement.getLocationOrder());
        orderNew.put("order_summary", Summ);
        orderNew.put("schedule_date", timeMili);
        orderNew.put("status","finish");
        orderNew.put("total", totalP);
        orderNew.put("user_id", sessionManagement.getUserIdUmorder());

   //IF nasa preset order000000000000000
        String comp = sessionManagement.getLocationOrder();
        if (comp.equals("preset_order")){
            int queNum = sessionManagement.getRandom();
            String que2 = "P"+queNum;
            orderNew.put("queue_number",que2);
            queNum++; sessionManagement.setRandom(queNum);
        }else {
            orderNew.put("queue_number",sessionManagement.getThisShit());
        }

        reference.setValue(orderNew).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(), z_analytics_pasahan.class);
                intent.putExtra("orderId", keypush);
                startActivity(intent);
                finish();
            }
        });

        //Toast.makeText(ResultActivity.this, "Order Scanned", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(ResultActivity.this, Home.class);
        //startActivity(intent);

    }





}
