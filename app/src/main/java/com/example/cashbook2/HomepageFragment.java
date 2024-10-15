package com.example.cashbook2;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;
import java.io.*;


public class HomepageFragment extends Fragment implements MyAdapter.OnItemClickListener { // 实现MyAdapter.OnItemClickListener接口

    private MyAdapter adapter;
    private boolean deleteMode = false; // 添加一个deleteMode成员变量，用于控制是否可以删除项
    private CashDatabaseHelper myDb;

    private String userId;
    private RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        mRecyclerView = view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Cash> cashList = new ArrayList<>();
        myDb = new CashDatabaseHelper(getContext());

        Bundle bundle = getArguments();

        if (bundle != null) {
            userId = bundle.getString("userId");
        }

        Cursor res = myDb.getAllData(userId);

        while (res.moveToNext()) {
            int id = res.getInt(res.getColumnIndexOrThrow("id"));
            String name = res.getString(res.getColumnIndexOrThrow("name"));
            String date = res.getString(res.getColumnIndexOrThrow("date"));
            String type = res.getString(res.getColumnIndexOrThrow("type"));
            String amount = String.valueOf(res.getFloat(res.getColumnIndexOrThrow("amount")));
            String description = res.getString(res.getColumnIndexOrThrow("description"));

            cashList.add(new Cash(id,name, date, type, amount, description));
        }

        adapter = new MyAdapter(cashList);
        adapter.setOnItemClickListener(this); // 设置OnItemClickListener
        mRecyclerView.setAdapter(adapter);


        ImageButton deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMode = true; // 在这里设置deleteMode为true
                adapter.setDeleteMode(true); // 通知adapter进入删除模式
                deleteButton.setColorFilter(Color.GRAY);
            }
        });
            // 查询数据库获取用户的总收入和总支出
            float totalIncome = myDb.getTotalIncome(userId);
            float totalExpense = myDb.getTotalExpense(userId);

            // 显示用户的总收入和总支出
            TextView showIncome = view.findViewById(R.id.showincome);
            TextView showPay = view.findViewById(R.id.showpay);
            showIncome.setText(String.valueOf(totalIncome));
            showPay.setText(String.valueOf(totalExpense));
        return view;
    }

    // 实现OnItemClickListener的onItemClick方法，在这里删除数据库中的内容
    @Override
    public void onItemClick(Cash cash) {
        if (deleteMode) {
            SQLiteDatabase db = myDb.getWritableDatabase();
            db.delete(myDb.getTableName(), "id = ?", new String[]{String.valueOf(cash.getId())});
            deleteMode = false; // 在删除了一项后，设置deleteMode为false
            adapter.setDeleteMode(false); // 通知adapter退出删除模式
            ImageButton deleteButton = getView().findViewById(R.id.deleteButton);
            deleteButton.clearColorFilter(); // 删除操作完成后，恢复deleteButton的颜色

            // 重新从数据库获取数据并更新RecyclerView的Adapter
            List<Cash> cashList = new ArrayList<>();

            Cursor res = myDb.getAllData(userId);
            while (res.moveToNext()) {
                int id = res.getInt(res.getColumnIndexOrThrow("id"));
                String name = res.getString(res.getColumnIndexOrThrow("name"));
                String date = res.getString(res.getColumnIndexOrThrow("date"));
                String type = res.getString(res.getColumnIndexOrThrow("type"));
                String amount = String.valueOf(res.getFloat(res.getColumnIndexOrThrow("amount")));
                String description = res.getString(res.getColumnIndexOrThrow("description"));

                cashList.add(new Cash(id, name, date, type, amount, description));
            }
            adapter = new MyAdapter(cashList);
            adapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(adapter);

            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        }
    }


}