package io.chgocn.app.activity;

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
        //test();
    }

    private void initView() {
        Button testBtn =  (Button) findViewById(R.id.test);
        assert testBtn != null;
        testBtn.setOnClickListener(this);
    }

    private void test() {
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
            case R.id.test:
                test();
                break;
        }
    }
}
