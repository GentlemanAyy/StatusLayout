package com.zy.statuslayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zy.statuslayout.StatusLayoutView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private StatusLayoutView mSlv;
    private RecyclerView rlv;
    private RlvAdapter rlvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlv = findViewById(R.id.slv);
        mSlv.setLayoutClick(new StatusLayoutView.LayoutClick() {
            @Override
            public void onTryRequestListener() {
                loadData();
            }

            @Override
            public void onOpenRequestListener() {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });

        View contentLayout = mSlv.getContentLayout();//获取主内容界面
        rlv = contentLayout.findViewById(R.id.recycle);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlvAdapter = new RlvAdapter(this);
        rlv.setAdapter(rlvAdapter);

        loadData();
    }

    private Handler mHandler = new Handler(this);

    private void loadData() {
        mSlv.showLoading();
        //公式 (int) (最小值+Math.random()*(最大值-最小值)+1   )
        int random = (int) (0 + Math.random() * (3 - 0 + 1));//0-3的随机整数
        mHandler.sendEmptyMessageDelayed(random, 1300);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                mSlv.showContent();
                rlvAdapter.setData(getData());
                break;
            case 1:
                mSlv.showError();
                break;
            case 2:
                mSlv.showEmpty();
                break;
            case 3:
                mSlv.showNetwork();
                break;
        }
        return false;
    }

    private ArrayList<String> getData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("data"+i);
        }
        return strings;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                loadData();
                break;
            case R.id.menu_content:
                mSlv.showContent();
                break;
            case R.id.menu_error:
                mSlv.showError();
                break;
            case R.id.menu_empty:
                mSlv.showEmpty();
                break;
            case R.id.menu_loading:
                mSlv.showLoading();
                break;
            case R.id.menu_network:
                mSlv.showNetwork();
                break;
        }
        return true;
    }


}
