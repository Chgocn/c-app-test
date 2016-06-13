package io.chgocn.plug.utils;

import android.content.Context;
import android.os.Looper;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Properties;

/**
 * UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。
 * 如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 * 实现该接口并注册为程序中的默认未捕获异常处理
 * 这样当未捕获异常发生时，就可以做些异常处理操作
 * 例如：收集异常信息，发送错误报告 等。
 * <p/>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {


    public static interface OnCrashHandler {
        boolean onCrash(Throwable e);
    }

    private OnCrashHandler onCrashHandler;

    /**
     * Debug Log Tag
     */
    public static final String TAG = CrashHandler.class.getSimpleName();
    /**
     * 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能
     */
    public static final boolean DEBUG = true;
    /**
     * CrashHandler实例
     */
    private static CrashHandler instance;
    /**
     * 程序的Context对象
     */
    private Context mContext;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".log";

    private Properties mDeviceCrashInfo;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null)
            instance = new CrashHandler();
        return instance;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public static void init(Context ctx) {
        init(ctx, null);
    }

    public static void init(Context ctx, OnCrashHandler onCrashHandler) {
        CrashHandler crashHandler = getInstance();
        crashHandler.onCrashHandler = onCrashHandler;
        crashHandler.mContext = ctx;
        crashHandler.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, throwable);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param throwable
     * @return true:如果处理了该异常信息;否则返回false
     */
    protected boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return true;
        }
        if (onCrashHandler != null && onCrashHandler.onCrash(throwable)) {
            return true;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
                //Toast.makeText(mContext, mContext.getString(R.string.error_toast_msg), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        //LogManager.e(TAG, "CrashHandler,deviceInfo: \n" + AndroidUtils.collectDeviceInfoStr(mContext) + throwable);
        return true;
    }

}