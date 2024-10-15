package com.example.cashbook2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.*;

public class HomePageActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Homepage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");

        String userId = intent.getStringExtra("userId"); // 获取用户ID
        Toast.makeText(HomePageActivity.this,"欢迎"+username+"使用喵呜记账",Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomepageFragment homepageFragment = new HomepageFragment();

        // 创建一个新的Bundle来保存用户ID
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);

        // 将Bundle设置为Fragment的参数
        homepageFragment.setArguments(bundle);

        // Replace the mainContent layout with the HomepageFragment
        fragmentTransaction.replace(R.id.mainContent, homepageFragment);
        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        ImageButton addCashButton = findViewById(R.id.imageButtonadd);
        ImageButton meButton = findViewById(R.id.imageButtonme);
        ImageButton HomeButton = findViewById(R.id.imageButtonhome);
        addCashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 AddCashFragment 实例
                AddcashFragment addcashFragment = new AddcashFragment();
                // 将Bundle设置为Fragment的参数
                addcashFragment.setArguments(bundle);
                // 使用 FragmentManager 和 FragmentTransaction 来替换 Fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContent, addcashFragment);
                fragmentTransaction.commit();
            }
        });

        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 MeFragment 实例
                user_settingFragment meFragment = new user_settingFragment();
                // 将Bundle设置为Fragment的参数
                meFragment.setArguments(bundle);
                // 使用 FragmentManager 和 FragmentTransaction 来替换 Fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContent, meFragment);
                fragmentTransaction.commit();
            }
        });

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 HomepageFragment 实例
                HomepageFragment homepageFragment = new HomepageFragment();
                // 将Bundle设置为Fragment的参数
                homepageFragment.setArguments(bundle);
                // 使用 FragmentManager 和 FragmentTransaction 来替换 Fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContent, homepageFragment);
                fragmentTransaction.commit();
            }
        });
    }


}
