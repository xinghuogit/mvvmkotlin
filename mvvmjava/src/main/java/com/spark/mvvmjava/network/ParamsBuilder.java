package com.spark.mvvmjava.network;

/*************************************************************************************************
 * 日期：2020/3/11 15:39
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class ParamsBuilder {
    private int onlineCacheTime;//okhttp 在线缓存时间，不设置就是不用
    private int offlineCacheTime;//okhttp 离线缓存时间，不设置就是不用

    private int retryCount;//重连次数，默认为0 不重连。大于0 开启重连
    private String oneTag;//同一网络还在加载时，有且只能请求一次(默认可以请求多次)   同一网络，oneTag只能用一次

    private boolean isShowLoading;//是否显示加载loading （默认显示）
    private String loadingMessage;//加载进度条上是否显示文字（默认不显示文字）

    public static ParamsBuilder build() {
        return new ParamsBuilder();
    }

    public ParamsBuilder loadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
        return this;
    }

    public ParamsBuilder isShowLoading(boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
        return this;
    }

    public ParamsBuilder oneTag(String oneTag) {
        this.oneTag = oneTag;
        return this;
    }

    public ParamsBuilder isRetry(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public ParamsBuilder offlineCacheTime(int offlineCacheTime) {
        this.offlineCacheTime = offlineCacheTime;
        return this;
    }

    public ParamsBuilder onlineCacheTime(int onlineCacheTime) {
        this.onlineCacheTime = onlineCacheTime;
        return this;
    }

    public int getOnlineCacheTime() {
        return onlineCacheTime;
    }

    public void setOnlineCacheTime(int onlineCacheTime) {
        this.onlineCacheTime = onlineCacheTime;
    }

    public int getOfflineCacheTime() {
        return offlineCacheTime;
    }

    public void setOfflineCacheTime(int offlineCacheTime) {
        this.offlineCacheTime = offlineCacheTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getOneTag() {
        return oneTag;
    }

    public void setOneTag(String oneTag) {
        this.oneTag = oneTag;
    }

    public boolean isShowLoading() {
        return isShowLoading;
    }

    public void setShowLoading(boolean showLoading) {
        isShowLoading = showLoading;
    }

    public String getLoadingMessage() {
        return loadingMessage;
    }

    public void setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
    }
}
