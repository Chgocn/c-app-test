package io.chgocn.plug.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.chgocn.plug.BaseApplication;

/**
 * write log to sdcard & close easily when published.
 *
 * @ author BaoHang ref:{http://xiangqianppp-163-com.iteye.com/blog/1417743}
 * @ version 1.0
 * @ date 2012-2-20
 * @ author chgocn
 */
public class LogManager {
    private static Boolean MYLOG_SWITCH = true;
    private static Boolean MYLOG_WRITE_TO_FILE = true;
    private static char MYLOG_TYPE = 'v';
    private static String MYLOG_PATH_SDCARD_DIR = "/sdcard/";
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 3;
    private static String MYLOGFILEName = BaseApplication.get("ksid", "Ks");
    //private static String MYLOGFILEName = "ks-run";
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");

    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String msg) {
        log(tag, msg, 'w');
    }

    public static void e(String tag, String msg) {
        log(tag, msg, 'e');
    }

    public static void d(String tag, String msg) {
        log(tag, msg, 'd');
    }

    public static void i(String tag, String msg) {
        log(tag, msg, 'i');
    }

    public static void v(String tag, String msg) {
        log(tag, msg, 'v');
    }

    /**
     * print log with tag, msg & level.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param level Priority constant for the println method.
     * @since v 1.0
     */
    private static void log(String tag, String msg, char level) {
        if (MYLOG_SWITCH) {
            if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.w(tag, msg);
            } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.d(tag, msg);
            } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
            if (MYLOG_WRITE_TO_FILE)
                writeLogToFile(String.valueOf(level), tag, msg);
        }
    }

    /**
     * write log to sdcard.
     * @param level Priority constant for the println method.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * */
    private static void writeLogToFile(String level, String tag, String msg) {
        Date nowTime = new Date();
        String timeFormat = logfile.format(nowTime);
        String needWriteMessage = myLogSdf.format(nowTime) + "    " + level + "    " + tag + "    " + msg;
        //File file = new File(MYLOG_PATH_SDCARD_DIR,MYLOGFILEName + timeFormat+".txt");
        File file = new File(FileHandleHelper.getOutputFileMultiPath(MYLOGFILEName + timeFormat + ".txt", "Ks", "log"));
        FileWriter fileWriter = null;
        BufferedWriter bufWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(bufWriter != null){
                try {
                    bufWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * delete the specified log file.
     * */
    public static void doLogCheckDelete(int days) {
        String needDelFile = logfile.format(getDateBefore(days));
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Ks/log", MYLOGFILEName + needDelFile + ".txt");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * get the time a few days before just to delete the log file name.
     * */
    private static Date getDateBefore(int dayBefore) {
        Date nowTime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowTime);
        now.set(Calendar.DATE, now.get(Calendar.DATE)
                - dayBefore);
        return now.getTime();
    }

    public static String getCurrentLogPath(){
        String today = logfile.format(System.currentTimeMillis());
        return Environment.getExternalStorageDirectory() + File.separator + "Ks/log/" + MYLOGFILEName + today + ".txt";
    }

    public static String[] getCurrentLogPaths(int days){
        String [] logPaths = new String[days];
        for (int i = 0; i < days; i++) {
            logPaths[i] = logfile.format(System.currentTimeMillis() - days*24*60*60*1000);
        }
        return logPaths;
    }

    public static String getCurrentLogRootPath(){
        return Environment.getExternalStorageDirectory() + File.separator + "Ks/log";
    }

    public static String getLogFileOutputPath(){
        String today = logfile.format(System.currentTimeMillis());
        return Environment.getExternalStorageDirectory() + File.separator + "Ks/log/" + MYLOGFILEName +"_"+ today + ".zip";
    }

}
