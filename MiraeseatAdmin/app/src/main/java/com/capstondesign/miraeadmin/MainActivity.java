package com.capstondesign.miraeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainScreen";

    Toolbar toolbar;
    TextView viewTitleText;

    BackPressCloseHandler backpress;

    CustomButton btnGotoUpload;

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

        btnGotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadDBActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backpress.onBackPressed();
    }
}
