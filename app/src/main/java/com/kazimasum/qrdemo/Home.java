package com.kazimasum.qrdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    Button scanbtn, orderQbtn, btnlogout, btnComplete,Ordreject;

    sessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        scanbtn = findViewById(R.id.scanbtn);
        orderQbtn = findViewById(R.id.orderQbtn);
        btnlogout = findViewById(R.id.btnlogout);
        btnComplete = findViewById(R.id.btnComplete);
        Ordreject = findViewById(R.id.Ordreject);
        sessionManagement = new sessionManagement(getApplicationContext());

        sessionManagement.setIlanorder("0");
        String ilansila = sessionManagement.getIlanorder();
        //int ilanbaa = Integer.valueOf(ilansila);


        orderQbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, o1_OrderQueue.class);
                startActivity(intent);
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, completeOrder.class);
                startActivity(intent);
            }
        });

        Ordreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, RejectOrder.class);
                startActivity(intent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, MainActivity.class);
                sessionManagement.setLogin(false);
                sessionManagement.setUsername("");

                startActivity(intent);
                finish();
            }
        });

            scanbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),scannerView.class));
                }
            });
    }
}