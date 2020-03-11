package com.spark.mvvmjava.base.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.trello.rxlifecycle3.LifecycleTransformer;

import java.util.ArrayList;

/*************************************************************************************************
 * 日期：2020/1/13 13:05
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：基础ViewModel //继承AndroidViewModel，是因为里面要用context时候直接可以getApplication()
 ************************************************************************************************/
public class BaseViewModel<T extends BaseModel> extends AndroidViewModel {
    private T repository;
    private ArrayList<String> onNetTag;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        createRepository();
        onNetTag = new ArrayList<>();
        repository.setOnNetTags(onNetTag);
    }

    public T getRepository() {
        return repository;
    }


    private void createRepository() {
        if (repository == null) {
            repository = (T) new RepositoryImpl();
        }
    }

    public void setLifecycleTransformer(LifecycleTransformer lifecycleTransformer) {
        if (repository != null) {
            repository.setLifecycleTransformer(lifecycleTransformer);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
