package io.chgocn.app.activity;

import android.os.Bundle;

import io.chgocn.app.R;
import io.chgocn.app.adapter.HomeFragmentAdapter;
import io.chgocn.plug.activity.TabPagerActivity;

/**
 * Home Activity
 * this activity will be alive in all application lifecycle if user logged
 * Created by chgocn(chgocn@gmail.com).
 *
 * @author chgocn
 */
public class HomeActivity extends TabPagerActivity<HomeFragmentAdapter> {

    @Override
    protected int getContentView() {
        return R.layout.home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureTabPager();
        int defaultPosition = 1;
        onPageSelected(defaultPosition);
        //UpdateChecker.doUmengCheckUpdateAction(this,false);
    }

    @Override
    protected HomeFragmentAdapter createAdapter() {
        return new HomeFragmentAdapter(this);
    }

}
