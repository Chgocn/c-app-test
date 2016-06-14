package io.chgocn.app;

import android.app.Application;

import io.chgocn.plug.utils.CrashHandler;

/**
 * Created by chgocn(chgocn@gmail.com).
 */
public class AppContext extends Application{
    private static String TAG = AppContext.class.getSimpleName();

    private static AppContext instance;

    /**
     * support a method to get a instance for the outside
     */
    public synchronized static AppContext getInstance() {
        if (null == instance) {
            instance = new AppContext();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.install(getApplicationContext());
        //CrashHandler.setErrorActivityClass(CustomErrorActivity.class);
        //CrashHandler.init(new CrashHandler(getApplicationContext()));
    }
}
