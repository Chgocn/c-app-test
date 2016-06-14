package io.chgocn.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

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
        LeakCanary.install(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
