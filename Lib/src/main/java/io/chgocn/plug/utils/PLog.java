package io.chgocn.plug.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.chgocn.plug.BuildConfig;

/**
 * Created by zk on 2015/12/24.
 * 使用此Log类将会把信息打印在日志文件和LogCat中
 *
 * update by hugo on 2016/5/5 thanks for bruce
 * @author hugo
 */
public class PLog {
    public static boolean DEBUG = BuildConfig.LOG_DEBUG;
    //public static final String PATH = BaseApplication.getCachePath() + "/log";
    public static final String PATH = getOutputLogPath();
    public static final String P_LOG_FILE_NAME = "crash.log";

    /**
     * 是否写入日志文件
     */
    public static final boolean P_LOG_WRITE_TO_FILE = true;

    /**
     * 错误信息
     */
    public static void e(String tag, String msg) {
        Log.e(tag, log(msg));
        if (P_LOG_WRITE_TO_FILE) {
            writeLog2File("e", tag, msg);
        }
    }

    /**
     * 警告信息
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, log(msg));
            if (P_LOG_WRITE_TO_FILE) {
                writeLog2File("w", tag, msg);
            }
        }
    }

    /**
     * 调试信息
     */

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, log(msg));
            if (P_LOG_WRITE_TO_FILE) {
                writeLog2File("d", tag, msg);
            }
        }
    }

    /**
     * 提示信息
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, log(msg));
            if (P_LOG_WRITE_TO_FILE) {
                writeLog2File("i", tag, msg);
            }
        }
    }

    public static void e(String msg) {
        e(getClassName(), msg);
    }

    public static void w(String msg) {
        w(getClassName(), msg);
    }

    public static void d(String msg) {
        d(getClassName(), msg);
    }

    public static void i(String msg) {
        i(getClassName(), msg);
    }

    /**
     * 写入日志到文件中
     */
    private static void writeLog2File(String logType, String tag, String msg) {
        isExist(PATH);
        //isDel();
        String needWriteMessage = "\r\n"
           // + Time.getNowMDHMSTime()
            + "\r\n"
            + logType
            + "    "
            + tag
            + "\r\n"
            + msg;
        File file = new File(PATH, P_LOG_FILE_NAME);
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除日志文件
     */
    public static void delFile() {

        File file = new File(PATH, P_LOG_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 判断文件夹是否存在,如果不存在则创建文件夹
     */
    public static void isExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {

        String result;
        StackTraceElement thisMethodStack = Thread.currentThread().getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1);

        int i = result.indexOf("$");// 剔除匿名内部类名

        return i == -1 ? result : result.substring(0, i);
    }

    /**
     * 打印 Log 行数位置
     */
    private static String log(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[5];
        String className = targetElement.getClassName();
        className = className.substring(className.lastIndexOf('.') + 1) + ".java";
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0){
            lineNumber = 0;
        }
        return "(" + className + ":" + lineNumber + ") " + message;
    }

    private static String getOutputLogPath(){
        File ksDir = new File(Environment.getExternalStorageDirectory(), "Log");
        if (!ksDir.exists()) {
            if (!ksDir.mkdirs()) {
                return null;
            }
        }
        //return ksDir.getPath() + File.separator + fileName;
        return ksDir.getPath();
    }
}
