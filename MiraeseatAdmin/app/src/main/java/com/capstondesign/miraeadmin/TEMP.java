package com.capstondesign.miraeadmin;//package com.capstondesign.miraeadmin;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainScreen";
//
//    Button btnLoadDB;
//    Button btnUpload;
//
//    TextView tvResultLogs;
//
//    int succeed = 0;
//    int failed = 0;
//
//    private List<TheaterSample> TheaterSamples = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnLoadDB = findViewById(R.id.btnLoadDB);
//        btnUpload = findViewById(R.id.btnUpload);
//
//        tvResultLogs = findViewById(R.id.tvResultLogs);
//
//        btnLoadDB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readTheaterData();
//            }
//        });
//
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//    }
//
//    private void readTheaterData() {
//        // InputStream is = getResources().openRawResource(R.raw.theater_db);
//        InputStream is = getResources().openRawResource(R.raw.test_db);
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(is, Charset.forName("UTF-8"))
//        );
//
//        String line = "";
//        try {
//            // Step over headers
//            reader.readLine();
//
//            // read data lines
//            while ((line = reader.readLine()) != null) {
//                // split by ','
//                String[] tokens = line.split(",");
//
//                // read the data
//                TheaterSample sample = new TheaterSample();
//                // 공연장코드,공연시설명,공연장명,공연장이미지,객석배치도,배치간격정보,검색횟수
//                sample.setTheaterCode(tokens[0]);
//                sample.setTheaterName(tokens[1]);
//                sample.setHallName(tokens[2]);
//                sample.setTheaterImage(tokens[3]);
//                sample.setSeatplan(tokens[4]);
//                sample.setArrangedInfo(tokens[5]);
//                sample.setSearchedNum(Integer.parseInt(tokens[6]));
//
//                TheaterSamples.add(sample);
//                // Log.d("MyActivity", "Just created: "+sample);
//            }
//        } catch (IOException e) {
//            Log.wtf(TAG, "Error reading data file on line " + line, e);
//            e.printStackTrace();
//
//            tvResultLogs.setText("ERROR! >> Error reading data file on line " + line);
//        }
//    }
//}
