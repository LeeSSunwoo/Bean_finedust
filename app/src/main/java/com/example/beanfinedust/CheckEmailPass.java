package com.example.beanfinedust;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

public class CheckEmailPass {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,16}$");
    // 이메일 유효성 검사
    public static boolean isValidEmail(String email, Context context) {
        if (email.isEmpty()) {
            // 이메일 공백

            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            Toast.makeText(context, "이메일 문제", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    public static boolean isValidPasswd(String password, Context context) {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            Toast.makeText(context, password+"비번 문제", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
