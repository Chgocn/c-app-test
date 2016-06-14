package io.chgocn.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kingsmith.plug.umeng.UpdateChecker;

import io.chgocn.app.R;
import io.chgocn.plug.activity.BaseActivity;
import io.chgocn.plug.utils.Log;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        UpdateChecker.doUmengCheckUpdateAction(this,false);
        //errorTest();
    }

    private void initView() {
        Button errorTestBtn =  (Button) findViewById(R.id.error_test);
        Button leakTestBtn =  (Button) findViewById(R.id.leak_test);
        assert errorTestBtn != null;
        assert leakTestBtn != null;
        errorTestBtn.setOnClickListener(this);
        leakTestBtn.setOnClickListener(this);
    }

    private void errorTest() {
        int i = 0;
        Log.e("TAG",3/i);
    }

    @Override
    protected int getContentView() {
        return R.layout.home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.error_test:
                errorTest();
                break;
            case R.id.leak_test:
                leakTest();
                break;
        }
    }

    private void leakTest() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("TAG","leak test");
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver,filter);
    }
}
