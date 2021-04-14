package com.capstondesign.miraeadmin;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginPage";

    // Firebase 인증 변수
    private FirebaseAuth loginAuth;
    private String adminEmail = "***ADMIN_EMAIL***";

    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutPwd;

    EditText inputEmail;
    EditText inputPwd;

    CheckBox checkSaveEmail;

    Button btnLogin;

    boolean isSaveEmailChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.InputEmail);
        inputLayoutPwd = (TextInputLayout) findViewById(R.id.InputPwd);

        checkSaveEmail = (CheckBox) findViewById(R.id.checkSaveEmail);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        inputEmail = inputLayoutEmail.getEditText();
        inputPwd = inputLayoutPwd.getEditText();

        // Initialize Firebase Auth
        loginAuth = FirebaseAuth.getInstance();

        if(SaveSharedPreference.getSavedEmail(getApplicationContext()) != null)
            inputEmail.setText(SaveSharedPreference.getSavedEmail(getApplicationContext()));

        if(SaveSharedPreference.getIsEmailSaved(getApplicationContext()) == true)
            checkSaveEmail.setChecked(true);

        btnLogin.setOnClickListener(clickLoginListener);
    }

    OnOneOffClickListener clickLoginListener = new OnOneOffClickListener() {
        @Override
        public void onOneClick(View v) {
            final String givenEmail = inputEmail.getText().toString().trim();
            String givenPwd = inputPwd.getText().toString();

            isSaveEmailChecked = checkSaveEmail.isChecked();
            ConnectivityManager conManager = (ConnectivityManager) LoginActivity.this.getSystemService(CONNECTIVITY_SERVICE);

            // 이메일 칸에 입력된 것이 없을 경우
            if(givenEmail.getBytes().length <= 0)
            {
                inputLayoutEmail.setError("이메일을 입력하세요");
                reset();
                inputEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    // 아이디 창에 무언가 입력하면 에러 메시지를 지워줌
                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().length() > 0) {
                            inputLayoutEmail.setError(null);
                            // 에러메시지가 사라진 자리에 남는 빈칸을 없애기 위해 Enabled 를 false로 변경
                            inputLayoutEmail.setErrorEnabled(false);
                        }
                    }
                });
            }

            else if(givenPwd.getBytes().length <= 0)
            {
                inputLayoutPwd.setError("비밀번호를 입력하세요");
                reset();

                inputPwd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().length() > 0) {
                            inputLayoutPwd.setError(null);
                            inputLayoutPwd.setErrorEnabled(false);
                        }
                    }
                });
            }
            // 인터넷 연결 확인 먼저
            else if(conManager.getActiveNetworkInfo() == null) {
                reset();
                Toast.makeText(getApplicationContext(),"인터넷 연결을 먼저 확인해주세요.",Toast.LENGTH_LONG).show();
            }
            // 이메일과 비밀번호 모두 입력되어 있으면
            else
            {
                if(!givenEmail.equals(adminEmail))
                {
                    inputLayoutPwd.setError("잘못된 로그인 정보입니다. 이메일과 비밀번호를 확인하세요.");
                    reset();
                }
                else
                {
                    loginAuth.signInWithEmailAndPassword(givenEmail, givenPwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        // 로그인 성공
                                        SaveSharedPreference.setIsEmailSaved(getApplicationContext(), isSaveEmailChecked);

                                        if(isSaveEmailChecked) {
                                            SaveSharedPreference.setSavedEmail(getApplicationContext(), adminEmail);
                                        }
                                        else {
                                            SaveSharedPreference.setSavedEmail(getApplicationContext(), null);
                                        }

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        // 열려있던 모든 액티비티를 닫고 지정된 액티비티(메인)만 열도록
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else {
                                        // 로그인 실패
                                        // Log.w(TAG, "LogInWithEmail:failure", task.getException());
                                        reset();
                                        Toast.makeText(getApplicationContext(), "잘못된 로그인 정보입니다. 이메일과 비밀번호를 확인하세요.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        }
    };
}
