package com.zhihaoliang.util.http.zokhttpsimple;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zhihaoliang.util.http.HttpCallBack;
import com.zhihaoliang.util.http.OkHttpClientUtil;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOkHttpClientUtil.initInstance().httpGet(getApplicationContext(), "https://www.baidu.com/", new HttpCallBack() {
                    @Override
                    public void onConnnectFaile(int state) {
                        Snackbar.make(mFloatingActionButton, "链接失败", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onConnnectSucess() {
                        Snackbar.make(mFloatingActionButton, "链接成功", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Snackbar.make(mFloatingActionButton, "获取数据失败", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Snackbar.make(mFloatingActionButton, "获取数据失败", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                });

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
