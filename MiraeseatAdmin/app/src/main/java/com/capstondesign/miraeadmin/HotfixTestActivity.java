package com.capstondesign.miraeadmin;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class HotfixTestActivity extends AppCompatActivity {
    private static final String TAG = "HotfixTest";

    Toolbar toolbar;
    TextView viewTitleText;

    Button btnReadTC;
    Button btnReadPI;

    TextView tvReadTC;
    TextView tvReadPI;

    private FirebaseFirestore db;

    int totalWidth;
    int totalHeight;
    int marginLeft;
    int marginTop;
    int marginRow;
    int marginCol;
    int maxRow;
    int maxCol;
    ArrayList<Integer> floorRow;
    Map<Integer, ArrayList<Integer>> rowStartEnd;
    ArrayList<Integer> aisleSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotfix_test);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("추가기능 작동 테스트");

        db = FirebaseFirestore.getInstance();

        db.collection("SeatPlanInfo").document("FC000012-01").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            totalWidth = documentSnapshot.getLong("totalWidth").intValue();
                            totalHeight = documentSnapshot.getLong("totalHeight").intValue();

                            marginLeft = documentSnapshot.getLong("marginLeft").intValue();
                            marginTop = documentSnapshot.getLong("marginTop").intValue();

                            marginRow = documentSnapshot.getLong("marginRow").intValue();
                            marginCol = documentSnapshot.getLong("marginCol").intValue();

                            maxRow = documentSnapshot.getLong("maxRow").intValue();
                            maxCol = documentSnapshot.getLong("maxCol").intValue();

                            floorRow = (ArrayList<Integer>) documentSnapshot.get("floorRow");

                            aisleSeat = (ArrayList<Integer>) documentSnapshot.get("aisleSeat");


                            Log.d("totalWidth: ", String.valueOf(totalWidth));
                            Log.d("totalHeight: ", String.valueOf(totalHeight));
                            Log.d("marginLeft: ", String.valueOf(marginLeft));
                            Log.d("marginTop: ", String.valueOf(marginTop));
                            Log.d("marginRow: ", String.valueOf(marginRow));
                            Log.d("marginCol: ", String.valueOf(marginCol));
                            Log.d("maxRow: ", String.valueOf(maxRow));
                            Log.d("maxCol: ", String.valueOf(maxCol));

                            Log.d("floorRow: ", String.valueOf(floorRow));
                            for(int i = 0; i < floorRow.size(); i++) {
                                Log.d("floorRow: ", String.valueOf(i)+" = "+String.valueOf(floorRow.get(i)));
                            }
                            for(int i = 0; i < aisleSeat.size(); i++) {
                                Log.d("aisleSeat: ", String.valueOf(i)+" = "+String.valueOf(aisleSeat.get(i)));
                            }



                            rowStartEnd = (Map<Integer, ArrayList<Integer>>) documentSnapshot.get("rowStartEnd");

                            for(int i = 0; i < rowStartEnd.size(); i++) {
                                //Log.d("rowStartEnd: ", String.valueOf(i+1)+" = "+String.valueOf((ArrayList<Integer>)rowStartEnd.get(String.valueOf(i+1))));
                                Log.d("rowStartEnd: ", (i+1) + " = ["+ rowStartEnd.get(String.valueOf(i+1)).get(0) + ", " + rowStartEnd.get(String.valueOf(i+1)).get(1) + "]");
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "error occured", e);
                        Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });

//        btnReadTC = findViewById(R.id.btnReadTC);
//        btnReadPI = findViewById(R.id.btnReadPI);
//
//        tvReadTC = findViewById(R.id.tvTermCondition);
//        tvReadPI = findViewById(R.id.tvPersonalInfo);
//
//        tvReadTC.setMovementMethod(new ScrollingMovementMethod());
//        tvReadPI.setMovementMethod(new ScrollingMovementMethod());
//
//        btnReadTC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.collection("TermsDB").document("TermsDocument").get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                String TC = documentSnapshot.getString("TermCondition").replace("\\\\n", "\n");
//                                tvReadTC.setText(TC);
//                            }
//                        });
//            }
//        });
//
//        btnReadPI.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.collection("TermsDB").document("TermsDocument").get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                String PI = documentSnapshot.getString("PersonalInfo").replace("\\\\n", "\n");
//                                tvReadPI.setText(PI);
//                            }
//                        });
//            }
//        });
    }

    // 뒤로가기 버튼(홈버튼)을 누르면 창이 꺼지는 메소드
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
