package com.capstondesign.miraeadmin.inquiry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capstondesign.miraeadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class InquiryContext extends AppCompatActivity {
    private static final String TAG = "InquiryContext";

    Toolbar toolbar;
    TextView viewTitleText;

    TextView inquiryTitle;
    TextView inquiryDate;
    TextView userName;
    TextView userEmail;
    TextView inquiryContext;

    private String isChecked;
    private String documentID;

    Button btnSetCheck;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_context);

        final Intent intent = getIntent();
        Inquiry inquiry = (Inquiry)intent.getParcelableExtra("SelectedInquiry");
        final int position = (int)intent.getIntExtra("SelectedPosition", 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("문의사항 확인");

        db = FirebaseFirestore.getInstance();

        inquiryTitle = (TextView)findViewById(R.id.textInquiryTitle);
        inquiryDate = (TextView)findViewById(R.id.textInquiryDate);
        userName = (TextView)findViewById(R.id.textUserName);
        userEmail = (TextView)findViewById(R.id.textUserEmail);
        inquiryContext = (TextView)findViewById(R.id.textInquiryContext);

        btnSetCheck = (Button)findViewById(R.id.bthSetChecked);

        inquiryTitle.setText(inquiry.getInquiryTitle());
        inquiryDate.setText(inquiry.getInquiryTime());
        userName.setText(inquiry.getUserName());
        userEmail.setText(inquiry.getUserEmail());
        inquiryContext.setText(inquiry.getInquiryContext().replace("\\\\n", "\n"));

        isChecked = inquiry.getIsChecked();
        documentID = inquiry.getDocumentID();

        final Intent returnIntent = new Intent();
        returnIntent.putExtra("Position", position);

        if(isChecked.equals("true")) {
            btnSetCheck.setBackgroundColor(Color.parseColor("#A6A6A6"));
            btnSetCheck.setText("확인 완료");
        }

        btnSetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked.equals("true")) {
                    db.collection("UserInquiry").document(documentID)
                            .update("isChecked", false)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    btnSetCheck.setBackgroundColor(Color.parseColor("#5FC4BD"));
                                    btnSetCheck.setText("확인 처리");
                                    isChecked = "false";

                                    Toast.makeText(getApplicationContext(), "확인 취소 처리되었습니다.", Toast.LENGTH_LONG).show();

                                    setResult(1, returnIntent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else {
                    db.collection("UserInquiry").document(documentID)
                            .update("isChecked", true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    btnSetCheck.setBackgroundColor(Color.parseColor("#A6A6A6"));
                                    btnSetCheck.setText("확인 완료");
                                    isChecked = "true";

                                    Toast.makeText(getApplicationContext(), "확인 완료 처리되었습니다.", Toast.LENGTH_LONG).show();

                                    setResult(2, returnIntent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}
