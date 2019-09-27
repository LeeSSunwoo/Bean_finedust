package com.bean.beanfinedust.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.bean.beanfinedust.CheckEmailPass;
import com.bean.beanfinedust.R;
import com.bean.beanfinedust.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        editTextEmail = binding.email;
        editTextPassword = binding.password;

        firebaseAuth = FirebaseAuth.getInstance();

        binding.signupBtn.setOnClickListener(v -> {
            singUp();
        });
    }

    public void singUp() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();


        if (CheckEmailPass.isValidEmail(email, this) && CheckEmailPass.isValidPasswd(password, this)) {
            if (password.equals(binding.passCheck.getText().toString())) {
                createUser(email, password);
            } else {
                Toast.makeText(this, "비번 다름", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "이메일 비번문제", Toast.LENGTH_SHORT).show();
        }
    }

    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // 회원가입 성공
                        Toast.makeText(RegisterActivity.this, "성공", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 회원가입 실패
                        Toast.makeText(RegisterActivity.this, "실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
