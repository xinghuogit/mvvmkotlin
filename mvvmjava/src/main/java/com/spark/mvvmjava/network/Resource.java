package com.spark.mvvmjava.network;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/*************************************************************************************************
 * 日期：2020/1/15 16:55
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class Resource<T> {
    //状态  这里有多个状态 0表示加载中；1表示成功；2表示联网失败；3表示接口虽然走通，但走的失败（如：关注失败）
    public static final int Loading = 0;
    public static final int Success = 1;
    public static final int Error = 2;
    public static final int Fail = 3;
    public static final int Progress = 4;//注意只有下载文件和上传图片时才会有

    public int state;

    public T data;
    public String errorMsg;
    public Throwable error;

    public int progress;//文件下载进度
    public long total;//文件总大小

    public Resource(int state, T data, String errorMsg) {
        this.state = state;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public Resource(int state, Throwable error) {
        this.state = state;
        this.error = error;
    }

    public Resource(int state, int progress, long total) {
        this.state = state;
        this.progress = progress;
        this.total = total;
    }

    public static <T> Resource<T> loading(String msg) {
        return new Resource<>(Loading, null, msg);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Success, data, null);
    }

    public static <T> Resource<T> response(ResponseModel<T> data) {
        if (data != null) {
            if (data.isSuccess()) {
                return new Resource<>(Success, data.getData(), null);
            }
            return new Resource<>(Fail, null, data.getErrorMsg());
        }
        return new Resource<>(Error, null, data.getErrorMsg());
    }

    public static <T> Resource<T> failure(String msg) {
        return new Resource<>(Error, null, msg);
    }

    public static <T> Resource<T> error(Throwable t) {
        return new Resource<>(Error, t);
    }

    public static <T> Resource<T> progress(int progress, long total) {
        return new Resource<>(Progress, progress, total);
    }

    public void handler(OnHandleCallback<T> callback) {
        switch (state) {
            case Loading:
                callback.onLoading(errorMsg);
                break;
            case Success:
                callback.onSuccess(data);
                break;
            case Fail:
                callback.onFailure(errorMsg);
                break;
            case Error:
                callback.onError(error);
                break;
            case Progress:
                callback.onProgress(progress, total);
                break;
        }
        if (state != Loading) {
            callback.onCompleted();
        }
    }

    public void handler(OnHandleCallback<T> callback, SmartRefreshLayout smartRefreshLayout) {
        switch (state) {
            case Loading:
                callback.onLoading(errorMsg);
                break;
            case Success:
                callback.onSuccess(data);
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
                break;
            case Fail:
                callback.onFailure(errorMsg);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
            case Error:
                callback.onError(error);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
            case Progress:
                callback.onProgress(progress, total);
                break;
        }
        if (state != Loading) {
            callback.onCompleted();
        }
    }

    public interface OnHandleCallback<T> {
        void onLoading(String message);

        void onSuccess(T data);

        void onFailure(String message);

        void onError(Throwable error);

        void onCompleted();

        void onProgress(int progress, long total);
    }
}














