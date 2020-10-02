package com.capstondesign.miraeadmin.unused;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capstondesign.miraeadmin.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchDBActivity extends AppCompatActivity {
    private static final String TAG = "SearchDBActivity";

    Toolbar toolbar;
    TextView viewTitleText;

    Spinner CollectionSpinner;
    Spinner FieldSpinner;

    TextInputLayout inputLayoutKeyword;
    Button btnSearch;

    TextView tvResultSummary;
    TextView tvResultDocuments;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_db);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("DB 검색");

        db = FirebaseFirestore.getInstance();

        inputLayoutKeyword =(TextInputLayout)findViewById(R.id.textlayoutKeyword);
        btnSearch = (Button)findViewById(R.id.btnSearchDB);

        tvResultSummary = (TextView)findViewById(R.id.tvResultSummary);
        tvResultDocuments = (TextView)findViewById(R.id.tvResultDocuments);

    }
}
