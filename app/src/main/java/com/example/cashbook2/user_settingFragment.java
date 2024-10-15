package com.example.cashbook2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class user_settingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_setting, container, false);

        // 获取保存的用户信息
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        // 获取当前用户的userId
        final String userId = getArguments().getString("userId");

        // 找到 textViewusernamesetting
        final TextView textViewUsername = view.findViewById(R.id.textViewusernamesetting);

        // 将当前登录用户的用户名设置为 textViewusernamesetting 的文本
        String username = sharedPreferences.getString(userId + "_username", "");
        textViewUsername.setText(username);

        // 设置点击监听器
        textViewUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个输入对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入新的用户名");

                // 设置输入框
                final EditText input = new EditText(getActivity());
                builder.setView(input);

                // 设置对话框的确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUsername = input.getText().toString();

                        // 获取原用户名的密码
                        String oldPassword = sharedPreferences.getString(userId + "_password", "");

                        // 移除原用户名的信息
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(userId + "_username");
                        editor.remove(userId + "_password");
                        editor.remove(username + "_userId"); // 移除旧的username和id的映射

                        // 将新的用户名、原密码存入SharedPreferences
                        editor.putString(userId + "_username", newUsername);
                        editor.putString(userId + "_password", oldPassword);
                        editor.putString(newUsername + "_userId", userId); // 添加新的username和id的映射

                        // 更新记住的用户名
                        editor.putString("remembered_username", newUsername);
                        editor.apply();

                        // 更新textViewusernamesetting的文本
                        textViewUsername.setText(newUsername);
                    }
                });

                // 设置对话框的取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // 显示对话框
                builder.show();
            }
        });

        Button logoutButton = view.findViewById(R.id.button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录页面
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // 找到 button2
        Button button2 = view.findViewById(R.id.button2);

        // 设置点击监听器
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个输入对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入原密码和新密码");

                // 设置输入框
                final EditText inputOldPassword = new EditText(getActivity());
                inputOldPassword.setHint("原密码");
                final EditText inputNewPassword = new EditText(getActivity());
                inputNewPassword.setHint("新密码");
                final EditText inputConfirmPassword = new EditText(getActivity());
                inputConfirmPassword.setHint("确认新密码");

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(inputOldPassword);
                layout.addView(inputNewPassword);
                layout.addView(inputConfirmPassword);
                builder.setView(layout);

                // 设置对话框的确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPassword = inputOldPassword.getText().toString();
                        String newPassword = inputNewPassword.getText().toString();
                        String confirmPassword = inputConfirmPassword.getText().toString();

                        // 获取保存的密码
                        String savedPassword = sharedPreferences.getString(userId + "_password", "");

                        // 检查原密码是否正确，新密码是否一致
                        if (savedPassword.equals(oldPassword) && newPassword.equals(confirmPassword)) {
                            // 更新密码
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(userId + "_password", newPassword);
                            editor.apply();
                            Toast.makeText(getActivity(), "密码修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "密码修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // 设置对话框的取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // 显示对话框
                builder.show();
            }
        });
        return view;
    }
}