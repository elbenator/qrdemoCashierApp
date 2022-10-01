package com.kazimasum.qrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Zoomreject extends AppCompatActivity {
    ImageView imagep,zoomback;
    DatabaseReference reference;
    private ScaleGestureDetector scaleGestureDetector;
    private float FACTOR = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomreject);
        imagep = findViewById(R.id.imagep);
        zoomback = findViewById(R.id.zoomback);
        String orderidniya2 = getIntent().getStringExtra("ket_proof2");

        //Zoom
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListner());



        //SET Sa Simula
        reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.child(orderidniya2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("imageUrl").exists()){
                    String image = dataSnapshot.child("imageUrl").getValue().toString();
                    Picasso.get().load(image).into(imagep);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        zoomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class ScaleListner extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            FACTOR *= detector.getScaleFactor();
            FACTOR = Math.max(0.1f, Math.min(FACTOR, 10.f));
            imagep.setScaleX(FACTOR);
            imagep.setScaleY(FACTOR);
            return true;
        }
    }
}