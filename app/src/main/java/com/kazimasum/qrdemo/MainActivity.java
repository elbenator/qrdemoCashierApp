package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button loginbtn;
    TextView username,password, cashierapp;
    newItemHash newItemHashname;
    sessionManagement sessionManagement;
    DatabaseReference reference, reference2;
   // CheckBox checkb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cashierapp = findViewById(R.id.cashierapp);
        sessionManagement = new sessionManagement(getApplicationContext());
         loginbtn =findViewById(R.id.loginbtn);
        //checkb.setChecked(true);

        //=======check kung nakalogin na
        if(sessionManagement.getLogin()){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletethis();
                //checkInput();
                /*newItemHashname = new newItemHash();
                HashMap updTom = new HashMap();
                updTom.put("todaySessionDate","1642570097504");
                reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
                reference.updateChildren(updTom);
                // Tapos create new DB
                reference = FirebaseDatabase.getInstance().getReference("analytics_counter");
                reference.child("daySession").child("1642570097504").setValue(newItemHashname.newItemHash());
                */
                 }});
    }
    //======Functions======================================================
    public void checkInput(){
        String usrName = username.getText().toString().trim();
        String pass = password.getText().toString();
        if(!usrName.isEmpty() && !pass.isEmpty()){
            readData(usrName,pass);
        }
        else {
            Toast.makeText(MainActivity.this, "Please Insert Correct Data", Toast.LENGTH_LONG).show();
        }
    }

    // ======= Step 2 =======
    public  void readData(final String username, final String password1){
        reference = FirebaseDatabase.getInstance().getReference("admin_users");
        reference.orderByKey().equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String passComp = dataSnapshot.child(username).child("password").getValue().toString();
                    if (passComp.equals(password1)) {
                        Toast.makeText(MainActivity.this, "Hello " + username, Toast.LENGTH_LONG).show();
                        openAdminDashboard(username);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    // ====== Step 3 ===========
    public void openAdminDashboard(String username_key){
        Intent intent = new Intent(this, Home.class);
        sessionManagement.setLogin(true);
        sessionManagement.setUsername(username_key);
        startActivity(intent);
        //finish();
    }

    public void deletethis(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    }

