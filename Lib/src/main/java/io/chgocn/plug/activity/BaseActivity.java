package io.chgocn.plug.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

import io.chgocn.plug.R;
import io.chgocn.plug.utils.AppManager;
import io.chgocn.plug.utils.SystemBarTintManager;

/**
 * Base Activity of all activities of Application
 *
 * @author chgocn
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getSimpleName();

    private boolean statusBarTranslucent = false;
    private boolean showHomeAsUp = true;
    private boolean showCustomNavView = false;

    private boolean dialogCancelAble = false;

    protected Toolbar toolbar;
    protected ImageView toolbarImage;
    protected TextView toolbarTitle;

    private int primaryColor;
    private int primaryDarkColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Log.d(TAG, getClass().getSimpleName() + ".onCreate...");

        Resources.Theme theme = this.getTheme();
        TypedValue typedValue = new TypedValue();
        // get primary color
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primaryColor = typedValue.data;
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        primaryDarkColor = typedValue.data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TypedArray windowTranslucentAttribute = theme.obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentStatus});
            statusBarTranslucent = windowTranslucentAttribute.getBoolean(0, false);
        }

        if(isShowCustomNavView()){
            initToolbar();
        }else {
            initActionBar(isShowHomeAsUp());
        }

        setDialogCancelAble(getDialogCancelAble());
        // add the activity into the stack
        AppManager.getAppManager().addActivity(this);
        MobclickAgent.openActivityDurationTrack(false);
    }

    protected boolean getDialogCancelAble() {
        return false;
    }

    private void setDialogCancelAble(boolean dialogCancelAble) {
        this.dialogCancelAble = dialogCancelAble;
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected abstract int getContentView();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(getSingleMenuTitle())) {
            getMenuInflater().inflate(R.menu.single, menu);
            MenuItem menuItem = menu.findItem(R.id.menu_single);
            menuItem.setTitle(getSingleMenuTitle());
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    singleOptionMenuSelected();
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * single option menu selected
     */
    protected void singleOptionMenuSelected() {
    }

    /**
     * get single menu title, null if do not need a sigle option menu
     *
     * @return single menu title
     */
    protected String getSingleMenuTitle() {
        return null;
    }

    // [+] actionbar
    public Toolbar getToolbar() {
        return toolbar;
    }

    protected void hideToolbar(boolean hidden) {
        if (null != toolbar) {
            if (hidden && toolbar.isShown()) {
                toolbar.setVisibility(View.GONE);
            } else if (!hidden && !toolbar.isShown()) {
                toolbar.setVisibility(View.VISIBLE);
            }
        }
    }

    protected final void initToolbar() {
        // set Toolbar as actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getNavigationIcon());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationOnClickListener(v);
            }
        });
        if(null != toolbar){
            toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarImage = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        }
        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        // see {https://github.com/jgilfelt/SystemBarTint}
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarTint(darkenColor(primaryColor));
        }
    }

    /**
     * Get navigation icon to be used when {@link #isShowCustomNavView()}  is override && return true.
     * @return your own navigation drawable resId;
     */
    protected int getNavigationIcon(){
        return R.drawable.ic_nav;
    }

    /**
     * make your own navigation click listener when {@link #isShowCustomNavView()}  is override && return true.
     * @param viewClicked clicked view.
     */
    protected void getNavigationOnClickListener(View viewClicked){

    }

    /**
     * initActionBar
     */
    protected final void initActionBar(boolean showHomeAsUp) {
        // set Toolbar as actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (null != actionBar) {
                // actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
                actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
            }
            toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarImage = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        }

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        // see {https://github.com/jgilfelt/SystemBarTint}
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarTint(darkenColor(primaryColor));
        }
    }

    protected boolean isTranslucentStatusBar() {
        return statusBarTranslucent;
    }

    protected boolean isShowHomeAsUp() {
        return showHomeAsUp;
    }

    public boolean isShowCustomNavView() {
        return showCustomNavView;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (null != toolbarTitle) {
            toolbarTitle.setText(title);
        } else {
            super.setTitle(title);
        }
    }

    /**
     * init toolbar image
     * bring to front and set visible
     */
    protected void initToolbarImage() {
        if (null != toolbar) {
            toolbar.bringToFront();
        }

        if (null != toolbarImage) {
            toolbarImage.setVisibility(View.VISIBLE);
        }

        if (null != toolbarTitle) {
            toolbarTitle.setVisibility(View.GONE);
        }
    }

    // [-] actionbar

    // [+]translucent system bar

    /**
     * darken color
     */
    protected int darkenColor(int color) {
        if (color == primaryColor) {
            return primaryDarkColor;
        } else {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            hsv[2] *= 0.8f; // value component
            return Color.HSVToColor(hsv);
        }
    }

    /**
     * change toolbar color
     */
    public void setToolbarColor(int primaryColor, int primaryDarkColor) {
        // set toolbar color
        if (null != toolbar) {
            toolbar.setBackgroundColor(primaryColor);
        }

        // set StatusBar color
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarTint(primaryDarkColor);
        }
    }

    /**
     * set the statusBar tint
     */
    protected void setStatusBarTint(int primaryDarkColor) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(primaryDarkColor);
    }

    /**
     * set the navigationBar tint
     */
    protected void setNavigationBarTint(int resId) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(resId);
    }
    // [-]translucent system bar

    // [+] Progress Dialog
    /**
     * Shows the progress UI and hides the login_bg form.
     */
    private MaterialDialog progressDialog;

    public void hiddenProgress() {
        if (null != progressDialog && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    public void showProgress(int contentResId) {
        showProgress(getString(contentResId));
    }

    public void showProgress(String content) {
        if (null == progressDialog) {
            progressDialog = new MaterialDialog.Builder(this)
                    .content(content)
                    .cancelable(dialogCancelAble)
                    .progress(true, 0)
                    .show();
        } else {
            progressDialog.setContent(content);
            progressDialog.show();
        }
    }
    // [-] Progress Dialog

    // [+]get intent extra

    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return long
     */
    protected long getLongExtra(final String name) {
        return getIntent().getLongExtra(name, -1l);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int array
     */
    protected int[] getIntArrayExtra(final String name) {
        return getIntent().getIntArrayExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return boolean
     */
    protected boolean getBooleanExtra(final String name) {
        return getIntent().getBooleanExtra(name, false);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return boolean array
     */
    protected boolean[] getBooleanArrayExtra(final String name) {
        return getIntent().getBooleanArrayExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return char sequence array
     */
    protected CharSequence[] getCharSequenceArrayExtra(final String name) {
        return getIntent().getCharSequenceArrayExtra(name);
    }
    // [-]get intent extra

    // [+] umeng analytics
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
    // [-] umeng analytics

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // end the Activity & remove it from stack
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

}
