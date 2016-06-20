package io.chgocn.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import io.chgocn.plug.utils.CrashHandler;

/**
 * @author chgocn(chgocn@gmail.com).
 */
public class AppContext extends Application{
    private static String TAG = AppContext.class.getSimpleName();

    private static AppContext INSTANCE;

    /**
     * support a method to get a instance for the outside
     * @return instance of AppContext.
     */
    public static synchronized  AppContext getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new AppContext();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.install(getApplicationContext());
        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        if(BuildConfig.DEBUG){
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
        }
    }
}
