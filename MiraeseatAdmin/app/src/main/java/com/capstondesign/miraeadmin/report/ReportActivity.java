package com.capstondesign.miraeadmin.report;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstondesign.miraeadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = "ReportActivity";

    Toolbar toolbar;
    TextView viewTitleText;

    RecyclerView recyclerView;
    ReportAdapter adapter;

    TextView textNoReport;

    Report report;

    private FirebaseFirestore db;

    final ArrayList<Report> dataSet = new ArrayList<Report>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("신고사항 확인");

        db = FirebaseFirestore.getInstance();

        recyclerView =(RecyclerView)findViewById(R.id.recyclerReportList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        // 항목 사이에 구분선 넣어주는 코드
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));

        // 어댑터 선언
        adapter = new ReportAdapter();
        recyclerView.setAdapter(adapter);

        textNoReport = (TextView)findViewById(R.id.textNoReport);

        adapter.setOnReportClickListener(new OnReportClickListener() {
            @Override
            public void onItemClick(ReportAdapter.ViewHolder holder, View view, int position) {
                Report item = adapter.getItem(position);

                Intent intent = new Intent(ReportActivity.this, ReportContext.class);
                intent.putExtra("SelectedReport", item);
                intent.putExtra("SelectedPosition", position);

                startActivityForResult(intent, 1234);
            }
        });

        loadReportListData();
    }

    // 서버에서 DB 읽어오는 함수
    public void loadReportListData() {

        db = FirebaseFirestore.getInstance();
        final SimpleDateFormat convert_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // reportTime 따라 정렬한 후 읽어옴
        db.collection("UserReport").orderBy("reportTime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        textNoReport.setVisibility(View.VISIBLE);
                    }
                    else {
                        // DB를 읽는 데에 성공했으면,
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // 각각의 쿼리 반환값을 dataSet에 추가
                            // String documentID, String userEmail, String targetReview, String reportContext, String isChecked, String reportTime
                            report = new Report(document.getId(), document.getString("userEmail"), document.getString("targetReview"), document.getString("reportContext"),
                                    String.valueOf(document.getBoolean("isChecked")), convert_format.format(document.getDate("reportTime")));
                            dataSet.add(report);
                        }

                        // 어댑터에 데이터 삽입
                        adapter.setItems(dataSet);
                        adapter.notifyDataSetChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                        textNoReport.setVisibility(View.GONE);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            adapter.getItem(data.getIntExtra("Position", 0)).setIsChecked("false");
        }
        else if(resultCode == 2) {
            adapter.getItem(data.getIntExtra("Position", 0)).setIsChecked("true");
        }
        adapter.setItems(dataSet);
        adapter.notifyDataSetChanged();
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
