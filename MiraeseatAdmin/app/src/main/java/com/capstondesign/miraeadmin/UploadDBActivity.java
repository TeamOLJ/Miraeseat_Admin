package com.capstondesign.miraeadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UploadDBActivity extends AppCompatActivity {
    private static final String TAG = "UploadDBActivity";

    static final int PICKFILE_RESULT_CODE = 7777;

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
                getFile();
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

    private void getFile() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("text/*");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }

    private void uploadTheaterData() {
        final int size = TheaterSamples.size();
        int i = 0;

        for(i = 0; i < size; i++) {
            final TheaterSample theaterItem = TheaterSamples.get(i);
            final String itemCode = theaterItem.getTheaterCode();
            final int finalI = i;
            db.collection("TheaterInfo").add(theaterItem)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            tvResultLogs.append(itemCode+" 업로드 성공\n");
                            succeed += 1;
                            tvLogPreview.setText("성공: "+succeed+"건, 실패: "+failed+"건");

                            if(finalI ==size - 1) {
                                showFinished();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document(DB)", e);
                            tvResultLogs.append("ERROR!! "+itemCode+" 업로드 실패\n");
                            failed += 1;
                            tvLogPreview.setText("성공: "+succeed+"건, 실패: "+failed+"건");

                            if(finalI ==size - 1) {
                                showFinished();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            if(data.getData() != null){
                Uri uri = data.getData();
                InputStream is = null;

                String line = "";

                try {
                    is = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName("UTF-8"))
                    );

                    // Step over headers
                    reader.readLine();

                    // read data lines
                    while ((line = reader.readLine()) != null) {
                        // split by ','
                        String[] tokens = line.split(",");

                        // read the data
                        TheaterSample sample = new TheaterSample();
                        // 공연시설코드,공연장코드,공연시설명,공연장명,공연장이미지,객석배치도유무,검색횟수
                        sample.setTheaterCode(tokens[0]);
                        sample.setHallCode(tokens[1]);
                        sample.setTheaterName(tokens[2]);
                        sample.setHallName(tokens[3]);

                        if(tokens[4].equals("")) {
                            sample.setTheaterImage(null);
                        } else {
                            sample.setTheaterImage(tokens[4]);
                        }

                        if(tokens[5].equals("true")) {
                            sample.setSeatplan(true);
                        } else {
                            sample.setSeatplan(false);
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
        }
    }

    public void showFinished() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(null);
        builder.setMessage("DB 업로드가 완료되었습니다.");

        builder.setPositiveButton("확인", null);

        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
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
