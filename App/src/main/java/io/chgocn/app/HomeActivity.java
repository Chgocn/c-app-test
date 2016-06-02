package io.chgocn.app;

import android.os.Bundle;

import com.kingsmith.plug.umeng.UpdateChecker;

import io.chgocn.plug.activity.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateChecker.doUmengCheckUpdateAction(this,false);
    }

    @Override
    protected int getContentView() {
        return R.layout.home;
    }
}
