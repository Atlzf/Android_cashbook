package com.example.cashbook2;
import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnregister;
    private Button btnlogin;
    private RadioButton mRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取存储的用户名和密码
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String rememberedUsername = sharedPreferences.getString("remembered_username", "");
        String rememberedPassword = sharedPreferences.getString("remembered_password", "");

        // 获取用户名和密码的EditText
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);

        // 获取记住密码的RadioButton
        RadioButton remember = findViewById(R.id.remember);

        // 如果记住密码选项被选中，填充用户名和密码
        if (remember.isChecked()) {
            usernameEditText.setText(rememberedUsername);
            passwordEditText.setText(rememberedPassword);
        }

        mRadioButton = findViewById(R.id.radioButton);
        btnregister= findViewById(R.id.registerbutton);
        btnregister.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
        btnlogin=findViewById(R.id.loginbutton);
        btnlogin.setOnClickListener(v -> {
            if (!mRadioButton.isChecked()) { // Add this block
                Toast.makeText(MainActivity.this, "请先同意用户协议", Toast.LENGTH_SHORT).show();
                return;
            }
            String strusername = ((EditText) findViewById(R.id.username)).getText().toString().trim();
            String strpassword = ((EditText) findViewById(R.id.password)).getText().toString().trim();
            // 获取存储的用户名和密码

            String userId = sharedPreferences.getString(strusername + "_userId", "");
            String storedUsername = sharedPreferences.getString(userId + "_username", "");
            String storedPassword = sharedPreferences.getString(userId + "_password", "");
            if(strusername.equals(storedUsername) && strpassword.equals(storedPassword)) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                sleep(2000);
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                intent.putExtra("username",strusername);
                intent.putExtra("userId", userId);
                startActivity(intent);

                // 如果记住密码选项被选中，保存用户名和密码
                if (remember.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remembered_username", strusername);
                    editor.putString("remembered_password", strpassword);
                    editor.apply();
                }
            }else {
                Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}