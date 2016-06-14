package io.chgocn.plug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Base Application
 * Toast shower
 *
 * @author chgocn (chgocn@gmail.com)
 */
public class BaseApplication extends Application {

    public static String cacheDir = "";
    protected static String PREF_NAME = "cache.pref";
    private static String REFRESH_TIME = "refresh_time.pref";
    private static String NO_CLEAR = "no_clear.pref";

    static Context _context;
    static Resources _resource;

    public static float sDensity;
    public static int sWidthDp;
    public static int sWidthPix;
    public static int sHeightPix;
    public static float sScaledDensity;
    private static Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();

        calcDisplayMetrics();
        initCachePath();
    }

    private void initCachePath() {
        if (getApplicationContext().getExternalCacheDir() != null && Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    public static String getCachePath(){
        return cacheDir;
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    private void calcDisplayMetrics() {
        sDensity = getResources().getDisplayMetrics().density;
        sWidthPix = getResources().getDisplayMetrics().widthPixels;
        sHeightPix = getResources().getDisplayMetrics().heightPixels;
        sScaledDensity = getResources().getDisplayMetrics().scaledDensity;
        sWidthDp = (int) (sWidthPix / sDensity);
    }

    // [+] Shared Preference
    public static SharedPreferences getPreferences() {
        return getPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
    }

    public static SharedPreferences getPreferences(String name) {
        return getPreferences(name, Context.MODE_MULTI_PROCESS);
    }

    public static SharedPreferences getPreferences(String name, int mode) {
        return context().getSharedPreferences(name, mode);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void set(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void set(String key, Serializable entity) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, JSON.toJSONString(entity));
        editor.apply();
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static <T> T get(String key, Class<T> clazz, T t) {
        String strValue = getPreferences().getString(key, "");
        if (TextUtils.isEmpty(strValue)) {
            return t;
        } else {
            return JSON.parseObject(strValue, clazz);
        }
    }

    // [-] Shared Preference

    // [+] Last Refresh Time
    public static void setRefreshTime(String key, long value) {
        SharedPreferences preferences = getPreferences(REFRESH_TIME);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getRefreshTime(String key) {
        return getPreferences(REFRESH_TIME).getLong(key, 0L);
    }
    // [-] Last Refresh Time

    // [+] No Clear String Xml
    public static void setNoClearString(String key, String value) {
        SharedPreferences preferences = getPreferences(NO_CLEAR);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getNoClearString(String key, String defaultValue) {
        return getPreferences(NO_CLEAR).getString(key, defaultValue);
    }
    // [-] No Clear String Xml

    // [+] Display Screen Param
    public static int[] getDisplaySize() {
        SharedPreferences pref = getPreferences();
        return new int[]{
                pref.getInt("screen_width", 480),
                pref.getInt("screen_height", 854)};
    }

    public static void saveDisplaySize(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt("screen_width", displaymetrics.widthPixels);
        editor.putInt("screen_height", displaymetrics.heightPixels);
        editor.putFloat("density", displaymetrics.density);
        editor.apply();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    // [-] Display Screen Param

    // [+] Show Toast
    private static String lastToast = "";
    private static long lastToastTime;

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon, int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon, int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon, int gravity) {
        // return if message is empty
        if (TextUtils.isEmpty(message))
            return;

        // return if message same as the last in a short time(2s)
        if (message.equalsIgnoreCase(lastToast) && Math.abs(System.currentTimeMillis() - lastToastTime) < 2000)
            return;
        if (toast == null) {
            toast = new Toast(context());
        }
        View view = LayoutInflater.from(context()).inflate(R.layout.toast, null);
        ((TextView) view.findViewById(R.id.toast_message)).setText(message);
        if (0 != icon) {
            ((ImageView) view.findViewById(R.id.toast_icon)).setImageResource(icon);
            view.findViewById(R.id.toast_icon).setVisibility(View.VISIBLE);
        }
        toast.setView(view);
        toast.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.getView().setVisibility(View.INVISIBLE);
            }
        });

        if (gravity == Gravity.CENTER) {
            toast.setGravity(gravity, 0, 0);
        } else {
            toast.setGravity(gravity, 0, 35);
        }

        toast.setDuration(duration);
        toast.show();
        lastToast = message;
        lastToastTime = System.currentTimeMillis();
    }
    // [-] Show Toast

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // auto gc when the memory is low
        System.gc();
    }

}
