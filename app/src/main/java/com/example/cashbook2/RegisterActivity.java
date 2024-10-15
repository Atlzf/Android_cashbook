package com.example.cashbook2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private EditText UsernameEditText;
    private EditText PasswordEditText;
    private EditText ConfirmPasswordEditText;
    private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UsernameEditText = findViewById(R.id.textViewUsername);
        PasswordEditText = findViewById(R.id.editTextTextPassword);
        ConfirmPasswordEditText = findViewById(R.id.editTextTextPassword2);
        RegisterButton = findViewById(R.id.register);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UsernameEditText.getText().toString().trim();
                String password = PasswordEditText.getText().toString().trim();
                String confirmPassword = ConfirmPasswordEditText.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // 用户注册成功后，生成一个唯一的用户ID
                    String userId = generateUserId();

                    editor.putString(userId + "_username", username);
                    editor.putString(userId + "_password", password);
                    editor.putString(username + "_userId", userId); // 添加一个username和id的映射
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    // 创建一个新的Handler并调用postDelayed方法
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 在这里启动新的Activity
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                            finish(); // 关闭当前Activity
                        }
                    }, 3000);
                } else {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String generateUserId() {
        // 生成一个UUID，并将其转换为字符串
        return UUID.randomUUID().toString();
    }
}