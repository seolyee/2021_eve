package com.example.HelluApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class plan_choose_result extends AppCompatActivity {
    TextView present_weight, goal_weight, exercise_plan, prefer_ex, usual_act, basal_meta, meal_guide;
    float max_result;
    int index;
    String EditWeight, GoalWeight, numberOfWeekOfExercise, normalActivity;
    List<String> meal_feedback = new ArrayList<>();
    String Gender, Height, Age;
    String EditHeight, LoseWeight, PresentWeight, amountOfExercise;
    List<String> mealTime = new ArrayList<>();
    String purposeOfExercise;
    String exercise;

    // 파이어베이스 연결하는 코드 같아서 훔쳐옴(from. plan_choose.java)
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    // 파이어베이스 유저 아이디 가져오는 코드인 듯? From. daily_stamp_write.java
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String Uid = user.getUid();         // user의 고유 랜덤 값(?)을 문자열로 반환해줌

    //'ai로 선별해주는 플랜 결과 보기' 버튼을 누르면 나오는 화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 훔쳐온 코드
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_plan_choose_result);

        // 텍스트뷰
        present_weight = findViewById(R.id.present_weight);     // 현재 체중
        goal_weight = findViewById(R.id.goal_weight);           // 목표 체중
        prefer_ex = findViewById(R.id.prefer_ex);               // 선호 운동(ai)
        exercise_plan = findViewById(R.id.exercise_plan);       // 운동 계획(일주일에 몇 번 운동할지)
        usual_act = findViewById(R.id.usual_act);               // 평소 활동량
        basal_meta = findViewById(R.id.basal_meta);             // 기초 대사량
        meal_guide = findViewById(R.id.meal_guide);          // 식단 가이드

        // plan_choose에서 넘어온 값 받기
        Intent intent = getIntent();
        // bundle을 통해 Extra를 모두 가져온다.
        Bundle bundle = intent.getExtras();

        EditWeight = bundle.getString("EditWeight");
        GoalWeight = bundle.getString("GoalWeight");
        numberOfWeekOfExercise = bundle.getString("numberOfWeekOfExercise");
        normalActivity = bundle.getString("normalActivity");
        Gender = bundle.getString("Gender");
        Height = bundle.getString("EditHeight");
        Age = bundle.getString("Age");
        purposeOfExercise = bundle.getString("purposeOfExercise");
        meal_feedback = bundle.getStringArrayList("meal_feedback");
        LoseWeight = bundle.getString("LoseWeight");
        mealTime = bundle.getStringArrayList("mealTime");
        amountOfExercise = bundle.getString("amountOfExercise");
        PresentWeight = bundle.getString("PresentWeight");

        StringBuilder str_meal = new StringBuilder();
        for (String meal_feedback : meal_feedback) {
            str_meal.append(meal_feedback);
            str_meal.append("\n");
        }

        // 출력
        present_weight.setText(EditWeight);
        goal_weight.setText(GoalWeight);
        exercise_plan.setText(numberOfWeekOfExercise);
        usual_act.setText(normalActivity);
        meal_guide.setText(str_meal);
        
        HashMap<String, Object> Plan_result = new HashMap<>();

        Plan_result.put("성별", Gender);
        Plan_result.put("나이", Age);
        Plan_result.put("신장", Height);      // 저장 안 됨
        Plan_result.put("체중", EditWeight);
        Plan_result.put("목표 감량 체중", LoseWeight);
        Plan_result.put("현재 체중", PresentWeight);    // 저장 안 됨
        Plan_result.put("목표 체중", GoalWeight);
        Plan_result.put("운동 주 횟수", numberOfWeekOfExercise);
        Plan_result.put("하루 목표 운동량", amountOfExercise);
        Plan_result.put("평소 활동량", normalActivity);
        Plan_result.put("식사 시간", mealTime);
        Plan_result.put("운동 목적", purposeOfExercise);
        Plan_result.put("식단 가이드", meal_feedback);

        // 선호 운동 출력
        //String retnExercise = prefer_Exercises(Plan_result, Gender, Age, normalActivity, purposeOfExercise);
        prefer_Exercises(Gender, Age, normalActivity, purposeOfExercise);
//        System.out.println(purposeOfExercise);
        // 기초대사량 계산
        String BEE = CalculateBEE(Gender, EditWeight, Height, Age);

        // 파이어베이스에 항목 저장
        Plan_result.put("기초대사량", BEE);

        // 파이어베이스에 저장하는 코드인가?
        databaseReference.child("User_Plan").child(Uid).setValue(Plan_result);
    }

    // AI에 필요한 입력 값: 성별, 나이(구간), 평소 활동량, 운동 목적
    public void prefer_Exercises(String Gender, String Age, String Time_spent, String Motivation){
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("model_surveyAI", DownloadType.LOCAL_MODEL, conditions)
                .addOnSuccessListener(model -> {
                    // Download complete. Depending on your app, you could enable the ML
                    // feature, or switch from the local model to the remote model, etc.

                    // The CustomModel object contains the local path of the model file,
                    // which you can use to instantiate a TensorFlow Lite interpreter.
                    File modelFile = model.getFile();
                    if (modelFile != null) {
                        Interpreter interpreter = new Interpreter(modelFile);

                        // 1. 라디오 버튼의 값을 하나하나 받아서 input에 넣을 값 구분.
                        // 2. 위치에 맞게 배열
                        // 3. 그리고 input에 넣기.
                        // 모델의 Input
                        //float[][] input = {{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
                        // 입력값 모두 0으로 초기화
                        float[][] input = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                        float[][] output = new float[1][7]; // 모델의 Output

                        input = surveyValueRetn(Gender, Age, Time_spent, Motivation, input);

                        // 결과 출력
                        //print(interpreter,input,output);

                        interpreter.run(input, output); // 모델 실행

                        // 모델 결과값 출력하기
                        for (int i = 0; i < output[0].length; i++) {
                            // 결과값 중에 최댓값 선정하기
                            if(i == 0){
                                index = 0;
                                max_result = output[0][i];
                            }
                            if(max_result < output[0][i]){
                                index = i;
                                max_result = output[0][i];
                            }
                        }

                        switch(index){
                            case 0:
                                exercise = "체육관 운동";
                                break;

                            case 1:
                                exercise = "웨이트 트레이닝";
                                break;

                            case 2:
                                exercise = "수영";
                                break;

                            case 3:
                                exercise = "팀 스포츠";
                                break;

                            case 4:
                                exercise = "조깅";
                                break;

                            case 5:
                                exercise = "요가";
                                break;

                            case 6:
                                exercise = "줌바 댄스";
                                break;

                            default:
                                break;
                        }
                        /*
                        for(int i = 0; i < input[0].length; i++){    // 입력값 테스트
                            System.out.println(input[0][i] + "\n");
                        }
                        */
                        // 변수에 저장해서 출력하기
                        prefer_ex.setText(exercise);
                        //System.out.println(exercise);   // 얘는 출력이 되고

                        // 파베 저장 코드
                        databaseReference.child("User_Plan").child(Uid).child("추천운동").setValue(exercise);

                        interpreter.close();    // 인터프리터 종료
                    }
                });
        //System.out.println(exercise);       // 얘는 출력이 안 됨.
        //return exercise;
    }

    public String CalculateBEE(String Gender, String strWeight, String strHeight, String strAge){
        double resultBEE = 0;
        // 소수점 두 번째 자리까지
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if(Gender.equals("여")){
            resultBEE = 655.1 + (9.56 * Double.parseDouble(strWeight)) + (1.85 * Double.parseDouble(strHeight)) - (4.68 * Integer.parseInt(strAge));
        }else if(Gender.equals("남")){
            resultBEE = 66.5 + (13.75 * Double.parseDouble(strWeight)) + (5 * Double.parseDouble(strHeight)) - (6.76 * Integer.parseInt(strAge));
        }

        String BEE = decimalFormat.format(resultBEE);
        basal_meta.setText(BEE);

        return BEE;
    }

    float[][] surveyValueRetn(String Gender, String Age, String Time_spent,
                              String Motivation, float[][] input){
        // Gender
        if(Gender.equals("여")){
            input[0][0] = 1;
        }else if (Gender.equals("남")){
            input[0][1] = 1;
        }

        // Age
        int intAge = Integer.parseInt(Age);

        if(intAge >= 15 && intAge <= 18){
            input[0][2] = 1;
        }else if(intAge >= 19 && intAge <= 25){
            input[0][3] = 1;
        }else if(intAge >= 26 && intAge < 30){
            input[0][4] = 1;
        }else if(intAge >= 30 && intAge < 40){
            input[0][5] = 1;
        }else if(intAge >= 40){
            input[0][6] = 1;
        }

        // Time_spent
        switch (Time_spent) {
            case "1시간":
                input[0][7] = 1;
                break;
            case "2시간":
                input[0][8] = 1;
                break;
            case "3시간 이상":
                input[0][9] = 1;
                break;
            case "30분":
                input[0][10] = 1;
                break;
            case "운동을 하지 않는다.":
                input[0][11] = 1;
                break;
        }

        // Motivation
        switch (Motivation) {
            case "건강":
                input[0][12] = 1;
                break;
            case "균형":
                input[0][13] = 1;
                break;
            case "체력 단련":
                input[0][14] = 1;
                break;
            case "다이어트":
                input[0][15] = 1;
                break;
            case "스트레스 해소":
                input[0][16] = 1;
                break;
        }

        return input;
    }
}