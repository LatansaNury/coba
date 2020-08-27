package com.example.coba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Notif extends Activity {

    TextView lihatDetail, posisiText;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notif);
        FirebaseMessaging.getInstance().subscribeToTopic("android");

        lihatDetail = (TextView) findViewById(R.id.LihatDetail);
        posisiText = (TextView) findViewById(R.id.textView3);

        myRef = FirebaseDatabase.getInstance().getReference("acc");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String posisi = dataSnapshot.child("posisi").getValue(String.class);
                String posisinormal = "Normal";
                if (posisi.equals(posisinormal)){
                    posisiText.setText("");
                }
                else {
                    posisiText.setText(posisi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ObjectAnimator animator = ObjectAnimator.ofInt(lihatDetail, "textColor", Color.WHITE, Color.YELLOW);
        animator.setDuration(500);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatMode(Animation.REVERSE);
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();

        lihatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(view);
            }
        });
    }

    private void intent(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}