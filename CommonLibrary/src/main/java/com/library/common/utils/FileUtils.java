package com.library.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*************************************************************************************************
 * 日期：2020/3/14 10:24
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：文件目录工具类
 ************************************************************************************************/
public class FileUtils {

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return {@code File}: 返回一个文件
     */
    public static File getFileByPath(String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {// 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return {@code String}: 返回一个文件的大小
     */
    public static String getFileSize(String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return @return {@code String}: 返回一个文件的大小
     */
    public static String getFileSize(File file) {
        if (!isFileExists(file)) return "";
        return ConvertUtils.byte2FitSize(file.length());
    }

    //    public static File saveFile(String filePath, ResponseBody body) {
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        File file = null;
//        try {
//            if (StringUtils.isEmpty(filePath)) {
//                return null;
//            }
//            file = new File(filePath);
//            if (file == null || !file.exists()) {
//                file.createNewFile();
//            }
//            long fileSize = body.contentLength();
//            long fileSizeDownload = 0;
//            byte[] fileReader = new byte[4096];
//            inputStream = body.byteStream();
//            outputStream = new FileOutputStream(file);
//            while (true) {
//                int read = inputStream.read(fileReader);
//                if (read == -1) break;
//                outputStream.write(fileReader, 0, read);
//                fileSizeDownload += read;
//            }
//            outputStream.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return file;
//    }

    public static void copyFromAssets(AssetManager assets, String source, String dest, boolean isCover)
            throws IOException {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = assets.open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                }
            }
        }
    }

    // 创建一个临时目录，用于复制临时文件，如assets目录下的离线资源文件
    public static String createTmpDir(Context context) {
        String sampleDir = "baiduTTS";
        String tmpDir = Environment.getExternalStorageDirectory().toString() + "/" + sampleDir;
        if (!FileUtils.makeDir(tmpDir)) {
            tmpDir = context.getExternalFilesDir(sampleDir).getAbsolutePath();
            if (!FileUtils.makeDir(sampleDir)) {
                throw new RuntimeException("create model resources dir failed :" + tmpDir);
            }
        }
        return tmpDir;
    }

    public static boolean makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }
}
