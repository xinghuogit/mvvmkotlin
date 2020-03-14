package com.library.common.callback;

import android.view.View;

/*************************************************************************************************
 * 日期：2020/3/13 15:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public interface OnItemClickListener extends View.OnClickListener{
    void onItemClick(int position, Object o);
}
