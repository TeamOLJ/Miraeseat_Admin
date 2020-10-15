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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HotfixTestActivity extends AppCompatActivity {
    private static final String TAG = "HotfixTest";

    Toolbar toolbar;
    TextView viewTitleText;

    private FirebaseFirestore db;

    String documentID;
    SeatPlanInfo seatinfo;

    int totalWidth;
    int totalHeight;
    int marginLeft;
    int marginTop;
    int marginRow;
    int marginCol;
    int maxRow;
    int maxCol;

    ArrayList<Integer> floorRow;
    Map<String, ArrayList<Integer>> rowStartEnd;
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

        documentID = "00TESTDOCUMENT";

        totalWidth = 0;
        totalHeight = 0;
        marginLeft = 0;
        marginTop = 0;
        marginRow = 0;
        marginCol = 0;
        maxRow = 0;
        maxCol = 0;

        floorRow = new ArrayList<Integer>(Arrays.asList(0, 20, 32));
        aisleSeat = new ArrayList<Integer>(Arrays.asList(14, 30));

        rowStartEnd = new HashMap<>();

        int[][] start_end_indexes = {{15, 30}, {11, 34}, {10, 35}, {9, 36}, {8, 37}, {7, 38}, {7, 38}, {6, 39}, {5, 40}, {5, 40}, {4, 41}, {3, 42}, {3, 42}, {2, 43}, {1, 44}, {1, 44}, {1, 44}, {1, 44},
                                     {1, 44}, {1, 44}, {2, 43}, {2, 43}, {1, 44}, {1, 44}, {1, 44}, {1, 44}, {1, 44}, {1, 44}, {1, 44}, {1, 44}, {1, 44}, {1, 44}};

        for(int i = 0; i < start_end_indexes.length; i++) {
            rowStartEnd.put(Integer.toString(i+1), new ArrayList(Arrays.asList(start_end_indexes[i][0], start_end_indexes[i][1])));
        }

        //(int seat_width, int seat_height, int margin_left, int margin_top, int margin_row, int margin_col, int max_row, int max_col, ArrayList<Integer> floor_row, Map<Integer, ArrayList<Integer>> row_start_end, ArrayList<Integer> aisle_col)
        seatinfo = new SeatPlanInfo(totalWidth, totalHeight, marginLeft, marginTop, marginRow, marginCol, maxRow, maxCol, floorRow, rowStartEnd, aisleSeat);

        db.collection("SeatPlanInfo").document(documentID).set(seatinfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), documentID + " 업로드되었습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
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
