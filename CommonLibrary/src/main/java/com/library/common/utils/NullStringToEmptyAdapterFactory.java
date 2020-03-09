package com.library.common.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/*************************************************************************************************
 * 日期：2020/3/7 21:35
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class NullStringToEmptyAdapterFactory <T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class) {
            return null;
        }
        return (TypeAdapter<T>) new StringNullAdapter();
    }
}
