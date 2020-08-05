package com.capstondesign.miraeadmin;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UploadDBActivity extends AppCompatActivity {
    private static final String TAG = "UploadDBActivity";

    Toolbar toolbar;
    TextView viewTitleText;

    Button btnReadDB;
    Button btnUpload;

    TextView tvLogPreview;
    TextView tvResultLogs;

    int succeed = 0;
    int failed = 0;

    //private FirebaseUser user;
    private FirebaseFirestore db;

    private List<TheaterSample> TheaterSamples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_db);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("DB 업로드");

        btnReadDB = findViewById(R.id.btnReadDB);
        btnUpload = findViewById(R.id.btnUpload);

        tvLogPreview = findViewById(R.id.tvLogPreview);
        tvResultLogs = findViewById(R.id.tvResultLogs);

        tvResultLogs.setMovementMethod(new ScrollingMovementMethod());

        db = FirebaseFirestore.getInstance();

        btnReadDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultLogs.setText("");

                TheaterSamples.clear();

                readTheaterData();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultLogs.setText("");

                succeed = 0;
                failed = 0;

                uploadTheaterData();
            }
        });

    }

    private void readTheaterData() {
        InputStream is = getResources().openRawResource(R.raw.theater_db);
        //InputStream is = getResources().openRawResource(R.raw.test_db);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            // Step over headers
            reader.readLine();

            // read data lines
            while ((line = reader.readLine()) != null) {
                // split by ','
                String[] tokens = line.split(",");

                // read the data
                TheaterSample sample = new TheaterSample();
                // 공연장코드,공연시설명,공연장명,공연장이미지,객석배치도,배치간격정보,검색횟수
                sample.setTheaterCode(tokens[0]);
                sample.setTheaterName(tokens[1]);
                sample.setHallName(tokens[2]);

                if(tokens[3].equals("")) {
                    sample.setTheaterImage(null);
                } else {
                    sample.setTheaterImage(tokens[3]);
                }

                if(tokens[4].equals("")) {
                    sample.setSeatplan(null);
                } else {
                    sample.setSeatplan(tokens[4]);
                }

                if(tokens[5].equals("")) {
                    sample.setArrangedInfo(null);
                } else {
                    sample.setArrangedInfo(tokens[5]);
                }

                sample.setSearchedNum(Integer.parseInt(tokens[6]));

                TheaterSamples.add(sample);
                tvResultLogs.append(sample+"\n");
                Log.d("MyActivity", "Just created: "+sample);
            }

            tvResultLogs.append("데이터 읽어들이기에 성공했습니다.");

        } catch (IOException e) {
            Log.wtf(TAG, "Error reading data file on line " + line, e);
            e.printStackTrace();

            tvResultLogs.setText("ERROR! >> Error reading data file on line " + line);
        }
    }

    private void uploadTheaterData() {
        int size = TheaterSamples.size();
        int i = 0;

        for(i = 0; i < size; i++) {
            final TheaterSample theaterItem = TheaterSamples.get(i);
            final String itemCode = theaterItem.getTheaterCode();
            db.collection("TheaterInfo").add(theaterItem)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            tvResultLogs.append(itemCode+" 업로드 성공\n");
                            succeed += 1;
                            tvLogPreview.setText("성공: "+succeed+"건, 실패: "+failed+"건");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document(DB)", e);
                            tvResultLogs.append("ERROR!! "+itemCode+" 업로드 실패\n");
                            failed += 1;
                            tvLogPreview.setText("성공: "+succeed+"건, 실패: "+failed+"건");
                        }
                    });
        }
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
