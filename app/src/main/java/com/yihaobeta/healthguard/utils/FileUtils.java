package com.yihaobeta.healthguard.utils;

import java.io.File;

/**
 * Created by yihaobeta on 2017/3/23.
 * 文件工具类
 */

public class FileUtils {


    // 获取指定文件夹内所有文件大小的和
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 删除指定目录下文件
     *
     * @param file
     * @return
     */
    public static boolean delTree(File file) {
        if (file == null) return false;
        if (!file.isDirectory()) {
            return file.delete();
        } else {
            for (File subFile : file.listFiles()) {
                boolean result = delTree(subFile);
                if (!result)
                    return false;
            }
            return file.delete();
        }
    }

}
