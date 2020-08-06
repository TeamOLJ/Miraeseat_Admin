package com.capstondesign.miraeadmin;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HotfixTestActivity extends AppCompatActivity {
    private static final String TAG = "HotfixTest";

    Toolbar toolbar;
    TextView viewTitleText;

    Button btnReadTC;
    Button btnReadPI;

    TextView tvReadTC;
    TextView tvReadPI;

    private FirebaseFirestore db;

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

        btnReadTC = findViewById(R.id.btnReadTC);
        btnReadPI = findViewById(R.id.btnReadPI);

        tvReadTC = findViewById(R.id.tvTermCondition);
        tvReadPI = findViewById(R.id.tvPersonalInfo);

        tvReadTC.setMovementMethod(new ScrollingMovementMethod());
        tvReadPI.setMovementMethod(new ScrollingMovementMethod());

        btnReadTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("TermsDB").document("TermsDocument").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String TC = documentSnapshot.getString("TermCondition").replace("\\\\n", "\n");
                                tvReadTC.setText(TC);
                            }
                        });
            }
        });

        btnReadPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("TermsDB").document("TermsDocument").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String PI = documentSnapshot.getString("PersonalInfo").replace("\\\\n", "\n");
                                tvReadPI.setText(PI);
                            }
                        });
            }
        });
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
