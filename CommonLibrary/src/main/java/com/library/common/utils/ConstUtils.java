package com.library.common.utils;

/*************************************************************************************************
 * 日期：2020/3/14 10:24
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：单位工具类
 ************************************************************************************************/
public class ConstUtils {

//    public static final int STATE_OPEN = 0;
//    public static final int STATE_CLOSE = 1;
//    public static final int STATE_BROKEN = 2;

//    @IntDef({STATE_OPEN, STATE_CLOSE, STATE_BROKEN})
//    public @interface  DoorState {}
//
//    private void setDoorState(@DoorState int state) {
//        //some Code
//    }


    private ConstUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /******************** 存储相关常量 ********************/
    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB   = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB   = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB   = 1073741824;

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    /******************** 时间相关常量 ********************/
    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC  = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN  = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY  = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

}