package com.spark.mvvmjava.base;

import android.content.Context;
import android.os.Environment;

import com.library.common.utils.FileUtils;
import com.library.common.utils.SDCardUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.spark.mvvmjava.R;

import java.io.File;
import java.text.SimpleDateFormat;

/*************************************************************************************************
 * 日期：2020/3/14 10:24
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：全局配置信息声明
 ************************************************************************************************/
public class AppConfig {

    /************************
     * 是否是调试模式 &  日志打印配置信息
     ***************************/
    public static final boolean isDebug = true;

    /************************
     * 文件目录配置信息
     ***************************/
    public static final String APP_DIR = "aaMVVMJava";//程序根目录
    public static final String BASE_SD_URL = Environment.getExternalStorageDirectory().getPath() + File.separator + APP_DIR + File.separator;
    public static final String BASE_INNER_URL = BaseApplication.getInstance().getCacheDir().getPath() + File.separator + APP_DIR + File.separator;
    public static final String BASE_URL = SDCardUtils.isSDCardEnable() ? BASE_SD_URL : BASE_INNER_URL;

    public static final String DIR_LOG = BASE_URL + "Log" + File.separator;
    public static final String DIR_DOWN = BASE_URL + "Down" + File.separator;
    public static final String DIR_BOOK = BASE_URL + "Book" + File.separator;
    public static final String DIR_Data = BASE_URL + "Data" + File.separator;
    public static final String DIR_Voice = BASE_URL + "Voice" + File.separator;
    public static final String DIR_Video = BASE_URL + "Video" + File.separator;
    public static final String DIR_IMAGE = BASE_URL + "Image" + File.separator;
    public static final String DIR_CACHE_SD = BASE_URL + "Cache" + File.separator;
    public static final String DIR_TEMP_CACHE_DATA = BASE_URL + ".TempCache" + File.separator;


    public static void initFilesDir() {
        FileUtils.createOrExistsDir(DIR_LOG);
        FileUtils.createOrExistsDir(DIR_DOWN);
        FileUtils.createOrExistsDir(DIR_BOOK);
        FileUtils.createOrExistsDir(DIR_Voice);
        FileUtils.createOrExistsDir(DIR_Video);
        FileUtils.createOrExistsDir(DIR_IMAGE);
        FileUtils.createOrExistsDir(DIR_CACHE_SD);
        FileUtils.createOrExistsDir(DIR_TEMP_CACHE_DATA);
    }

    public static void initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new SimpleDateFormat("上次更新时间 yyyy-MM-dd HH:mm:ss"));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}
