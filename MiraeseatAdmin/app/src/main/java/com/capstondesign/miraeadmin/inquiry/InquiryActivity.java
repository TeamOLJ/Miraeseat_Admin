package com.capstondesign.miraeadmin.inquiry;

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

public class InquiryActivity extends AppCompatActivity {
    private static final String TAG = "InquiryActivity";

    Toolbar toolbar;
    TextView viewTitleText;

    RecyclerView recyclerView;
    InquiryAdapter adapter;

    Inquiry inquiry;

    private FirebaseFirestore db;

    final ArrayList<Inquiry> dataSet = new ArrayList<Inquiry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewTitleText = (TextView) findViewById(R.id.viewTitleText);
        viewTitleText.setText("문의사항 확인");

        db = FirebaseFirestore.getInstance();

        recyclerView =(RecyclerView)findViewById(R.id.recyclerInquiryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        // 항목 사이에 구분선 넣어주는 코드
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));

        // 어댑터 선언
        adapter = new InquiryAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnInquiryClickListener(new OnInquiryClickListener() {
            @Override
            public void onItemClick(InquiryAdapter.ViewHolder holder, View view, int position) {
                Inquiry item = adapter.getItem(position);

                Intent intent = new Intent(InquiryActivity.this, InquiryContext.class);
                intent.putExtra("SelectedInquiry", item);
                intent.putExtra("SelectedPosition", position);

                startActivityForResult(intent, 1234);
            }
        });

        loadNoticeListData();
    }

    // 서버에서 DB 읽어오는 함수
    public void loadNoticeListData() {

        db = FirebaseFirestore.getInstance();
        final SimpleDateFormat convert_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // inquiryTime 따라 정렬한 후 읽어옴
        db.collection("UserInquiry").orderBy("inquiryTime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // DB를 읽는 데에 성공했으면,
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // 각각의 쿼리 반환값을 dataSet에 추가
                        // String documentID, String userName, String userEmail, String inquiryTitle, String inquiryContext, Boolean isChecked, Date inquiryTime
                        inquiry = new Inquiry(document.getId(), document.getString("userName"), document.getString("userEmail"),
                                                document.getString("inquiryTitle"), document.getString("inquiryContext"),
                                                String.valueOf(document.getBoolean("isChecked")), convert_format.format(document.getDate("inquiryTime")));
                        dataSet.add(inquiry);
                    }

                    // 어댑터에 데이터 삽입
                    adapter.setItems(dataSet);
                    adapter.notifyDataSetChanged();
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
