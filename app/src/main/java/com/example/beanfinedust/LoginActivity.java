package com.example.beanfinedust;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beanfinedust.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        editTextEmail = binding.email;
        editTextPassword = binding.password;

        firebaseAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(v -> {
            signIn();
        });

        binding.SIGNUP.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }



    public void signIn() {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if(CheckEmailPass.isValidEmail(email, this) && CheckEmailPass.isValidPasswd(password, this)) {
            loginUser(email, password);
        }
    }

    // 로그인
    private void loginUser(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // 로그인 성공
                        Toast.makeText(LoginActivity.this, "성공", Toast.LENGTH_SHORT).show();
                    } else {
                        // 로그인 실패
                        Toast.makeText(LoginActivity.this, "실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
