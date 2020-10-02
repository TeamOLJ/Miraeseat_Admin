package com.capstondesign.miraeadmin.report;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.capstondesign.miraeadmin.R;
import com.capstondesign.miraeadmin.inquiry.Inquiry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ReportContext extends AppCompatActivity {
    private static final String TAG = "ReportContext";

    Toolbar toolbar;
    TextView viewTitleText;

    RelativeLayout layoutLoading;
    LinearLayout layoutReview;

    TextView textLoading;

    TextView reportDate;
    TextView userEmail;

    TextView reportTarget;
    TextView reportContext;

    TextView targetUser;
    TextView targetSeat;
    TextView targetDate;
    TextView targetContext;

    ImageView targetImage;

    private String isChecked;
    private String documentID;

    Button btnSetCheck;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_context);

        final Intent intent = getIntent();
        Report report = (Report) intent.getParcelableExtra("SelectedReport");
        final int position = (int) intent.getIntExtra("SelectedPosition", 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("신고사항 확인");

        db = FirebaseFirestore.getInstance();

        layoutLoading = (RelativeLayout)findViewById(R.id.layoutLoading);
        layoutReview = (LinearLayout)findViewById(R.id.layoutReview);

        textLoading = (TextView)findViewById(R.id.textLoading);

        reportDate = (TextView)findViewById(R.id.textReportTime);
        userEmail = (TextView)findViewById(R.id.textReportUser);

        reportTarget = (TextView)findViewById(R.id.textReportTarget);
        reportContext = (TextView)findViewById(R.id.textReportContext);

        targetUser = (TextView)findViewById(R.id.textTargetUser);
        targetSeat = (TextView)findViewById(R.id.textTargetSeat);
        targetDate = (TextView)findViewById(R.id.textTargetDate);
        targetContext = (TextView)findViewById(R.id.textTargetContext);

        targetImage = (ImageView)findViewById(R.id.imageTarget);

        btnSetCheck = (Button)findViewById(R.id.bthSetChecked);

        reportDate.setText(report.getReportTime());
        userEmail.setText(report.getUserEmail());

        reportTarget.setText(report.getTargetReview());
        reportContext.setText(report.getReportContext());

        isChecked = report.getIsChecked();
        documentID = report.getDocumentID();

        db.collection("SeatReview").document(report.getTargetReview()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Review review = documentSnapshot.toObject(Review.class);

                        if(review != null) {
                            targetUser.setText(review.getOwnerUser());
                            targetSeat.setText(review.getTheaterName() + " " + review.getSeatNum());
                            targetDate.setText(review.getReviewDate());
                            targetContext.setText(review.getReviewText().replace("\\\\n", "\n"));

                            Glide.with(getApplicationContext()).load(review.getImagepath()).into(targetImage);

                            layoutLoading.setVisibility(View.GONE);
                            layoutReview.setVisibility(View.VISIBLE);
                        }
                        else {
                            textLoading.setText("원문 불러오기에 실패했습니다.\n원문이 삭제되었거나 연결에 오류가 있을 수 있습니다.");
                            layoutLoading.setVisibility(View.VISIBLE);
                            layoutReview.setVisibility(View.GONE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "원문 DB 읽어오기 실패 " + e);

                        textLoading.setText("원문 불러오기에 실패했습니다.\n원문이 삭제되었거나 연결에 오류가 있을 수 있습니다.");
                        layoutLoading.setVisibility(View.VISIBLE);
                        layoutReview.setVisibility(View.GONE);
                    }
                });


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
                    db.collection("UserReport").document(documentID)
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
                    db.collection("UserReport").document(documentID)
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
