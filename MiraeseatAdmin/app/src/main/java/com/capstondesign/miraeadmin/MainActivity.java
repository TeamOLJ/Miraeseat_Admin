package com.capstondesign.miraeadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainScreen";

    Toolbar toolbar;
    TextView viewTitleText;

    BackPressCloseHandler backpress;

    CustomButton btnGotoUpload;
    CustomButton btnGotoHotfix;
    CustomButton btnGotoInquiry;
    CustomButton btnGotoReport;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("미래자리 관리자용 어플");

        backpress = new BackPressCloseHandler(this);

        btnGotoUpload = findViewById(R.id.goto_upload);
        btnGotoHotfix = findViewById(R.id.goto_hotfix);
        btnGotoInquiry = findViewById(R.id.goto_inquiry);
        btnGotoReport = findViewById(R.id.goto_report);

        btnLogout = findViewById(R.id.btnLogout);

        btnGotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadDBActivity.class);
                startActivity(intent);
            }
        });

        btnGotoHotfix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HotfixTestActivity.class);
                startActivity(intent);
            }
        });

        btnGotoInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InquiryActivity.class);
                startActivity(intent);
            }
        });

        btnGotoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askbackDialog();
            }
        });
    }

    private void askbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(null);
        builder.setMessage("로그아웃하고 어플을 종료하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        builder.setNegativeButton("취소", null);

        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        backpress.onBackPressed();
    }
}
