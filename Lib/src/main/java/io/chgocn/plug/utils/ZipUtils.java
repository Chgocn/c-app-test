package io.chgocn.plug.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by chgocn(chgocn@gmail.com).
 */
public class ZipUtils {
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    public static void zipFile(File resFile, ZipOutputStream zipOut, String rootPath) throws IOException {
        rootPath = rootPath + (rootPath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        rootPath = new String(rootPath.getBytes("8859_1"), "GB2312");
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipOut, rootPath);
            }
        } else {
            byte buffer[] = new byte[BUFF_SIZE];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
            zipOut.putNextEntry(new ZipEntry(rootPath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipOut.write(buffer, 0, realLength);
            }
            in.close();
            zipOut.flush();
            zipOut.closeEntry();
        }
    }

    public static void zipFile(List<File> fileList, ZipOutputStream zipOut, String rootPath) throws IOException {
        for (File file : fileList) {
            if(file.exists()){
                zipFile(file, zipOut, rootPath);
            }
        }
    }

    public static List<File> pathToFile(String[] filePaths){
        List<File> fileList = new ArrayList<>();
        for (String filePath : filePaths){
            fileList.add(new File(filePath));
        }
        return fileList;
    }

}
