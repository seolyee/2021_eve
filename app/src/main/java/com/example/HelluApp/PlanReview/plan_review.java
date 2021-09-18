package com.example.HelluApp.PlanReview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.HelluApp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// ai 플랜 다시보기 화면
public class plan_review extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_review);

        // 텍스트뷰
        TextView present_weight = findViewById(R.id.present_weight);     // 현재 체중
        TextView goal_weight = findViewById(R.id.goal_weight);           // 목표 체중
        TextView exercise_plan = findViewById(R.id.exercise_plan);       // 운동 계획(일주일에 몇 번 운동할지)
        TextView prefer_ex = findViewById(R.id.prefer_ex);               // 선호 운동(ai)
        TextView usual_act = findViewById(R.id.usual_act);               // 평소 활동량
        TextView basal_meta = findViewById(R.id.basal_meta);             // 기초 대사량
        TextView meal_guide = findViewById(R.id.meal_guide);             // 식단 가이드
        TextView exercise_guide = findViewById(R.id.exercise_guide);     // 운동 가이드

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User_Plan");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String Uid = user.getUid();

        // path: /User_Plan/UID
        mDatabase.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nowWeight = dataSnapshot.child("현재 체중").getValue(String.class);
                String goalWeight = dataSnapshot.child("목표 체중").getValue(String.class);
                String planExercise = dataSnapshot.child("운동 주 횟수").getValue(String.class);
                String exerciseType = dataSnapshot.child("추천운동").getValue(String.class);
                String usualAct = dataSnapshot.child("평소 활동량").getValue(String.class);
                String basalMeta = dataSnapshot.child("기초대사량").getValue(String.class);

                // 출력
                present_weight.setText(nowWeight);
                goal_weight.setText(goalWeight);
                exercise_plan.setText(planExercise);
                prefer_ex.setText(exerciseType);
                usual_act.setText(usualAct);
                basal_meta.setText(basalMeta);
                }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}