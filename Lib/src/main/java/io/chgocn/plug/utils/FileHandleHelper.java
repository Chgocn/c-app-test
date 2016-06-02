package io.chgocn.plug.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by chgocn(chgocn@gmail.com).
 */
public class FileHandleHelper {

    private final static String TAG = FileHandleHelper.class.getSimpleName();

    /**
     * return a output file path with file name.
     * @param fileName
     * @return output file path.
     */
    public static String getOutputFilePath(String fileName) {
        File ksDir = new File(Environment.getExternalStorageDirectory(), "Ks");
        if (!ksDir.exists()) {
            if (!ksDir.mkdirs()) {
                Log.e(TAG, "failed to create directory");
                return null;
            }
        }
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        return ksDir.getPath() + File.separator + fileName;
    }

    public static String getOutputFileMultiPath(String fileName,String ... rootDirName){
        File rootDir = new File(Environment.getExternalStorageDirectory(),"temp");
        StringBuilder sb = new StringBuilder(Environment.getExternalStorageDirectory().getPath());
        for (int i = 0; i < rootDirName.length; i++) {
            sb.append("/" + rootDirName[i]);
            rootDir = new File(sb.toString());
            if (!rootDir.exists()) {
                if (!rootDir.mkdirs()) {
                    Log.e(TAG, "failed to create directory");
                    return Environment.getExternalStorageDirectory().getPath();
                }
            }
        }
        return rootDir.getPath() + File.separator + fileName;
    }

    public static void copyDbToSdcardKsLog(String database_name) {
        String oldPath = "data/data/com.kingsmith.run/databases/" + database_name;
        String newPath = getOutputFileMultiPath(database_name,"Ks","log");
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            if (oldFile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Log.e(TAG,"复制单个文件操作出错" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void copyDbToSdcard(String database_name) {
        String oldPath = "data/data/com.kingsmith.run/databases/" + database_name;
        String newPath = getOutputFileMultiPath(database_name,"Ks","databases");
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            if (oldFile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Log.e(TAG,"复制单个文件操作出错" + e.getMessage());
            e.printStackTrace();
        }
    }
}
