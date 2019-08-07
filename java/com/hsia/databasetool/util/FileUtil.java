package com.hsia.databasetool.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author hsia
 * 文件读写工具类
 */
public class FileUtil {

    public static File createFile() {
        File directory = new File(".");
        String path = null;
        try {
            path = directory.getCanonicalPath();

            File file = new File(path);
            if (!file.exists() && !file.isDirectory()) {
                System.out.println("//不存在");
                file.mkdir();
            } else {
                System.out.println("//目录存在");
            }
            file = new File(path + "\\markdown.md");

            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param fileOutputStream
     * @param data
     */
    public static void write2File(String data, FileOutputStream fileOutputStream, boolean isEnd) {
        if (data == null) {
            data = "NULL";
        }
        try {
            byte[] bData = data.getBytes("utf-8");
            fileOutputStream.write(bData);
            if (isEnd) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileOutputStream createFileOutPutStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
