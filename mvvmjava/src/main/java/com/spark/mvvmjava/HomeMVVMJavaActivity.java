package com.spark.mvvmjava;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableList;
import androidx.databinding.ObservableMap;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spark.mvvmjava.databinding.HomeMVVMJavaBinding;
import com.spark.mvvmjava.databinding.ItemSimpleBinding;
import com.spark.mvvmjava.bean.Dog;
import com.spark.mvvmjava.bean.YellowDog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeMVVMJavaActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "HomeMVVMJavaActivity";

    private HomeMVVMJavaBinding binding;

    private SimpleAdapter simpleAdapter;
    private List<MVVMData> simples = new ArrayList<>();

    private Dog dog = new Dog("默认名称", "名言颜色");
    private YellowDog yellowDog = new YellowDog("默认名称", 0);

    private ObservableList<String> list = new ObservableArrayList<>();//
    private ObservableMap<String, String> map = new ObservableArrayMap<>();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_mvvm_java);
        binding.setTextStr("点击一下，查看log");
        binding.setUser(new User("李云", 29));
        binding.setOnClickListener(this);
        binding.setMainActivity(this);

        binding.setUserSecond(new com.spark.mvvmjava.alis.User("别名李云", 29));/* <!--import和Alis的使用-->*/

        /*在recyclerView里的使用  也可Fragment  开始*/
        simples.add(new MVVMData(getString(R.string.base) + 1, getString(R.string.baseDesc) + 1));
        simples.add(new MVVMData(getString(R.string.base) + 2, getString(R.string.baseDesc) + 2));
        simpleAdapter = new SimpleAdapter(this);
        binding.rv.setAdapter(simpleAdapter);
        simpleAdapter.updateItems(simples);
        /*在recyclerView里的使用  也可Fragment  结束*/

        /*单向数据绑定  开始*/
        binding.setDog(dog); //BaseObservable  bean对象需要继承 BaseObservable
        addListener();//BaseObservable  bean对象需要继承 BaseObservable

        binding.setYellowDog(yellowDog); //ObservableField  其实这个ObservableField就是对BaseObservable的简化，不用继承，不用主动调刷新代码。

        /**
         * ObservableCollection是集合，这里和我们常用的 List Map一样。
         * 只不过这里的ObservableList、ObservableMap是封装好的。
         * 当我们改变集合里的数据时。xml也会改变。
         * 唯一要注意的是，在xml里引用这些集合的时候<类型>，
         * 这些符号，会影响xml格式所以要转义。用&lt; 代表<;用&gt代表>（这些转义符，同样支持Mark Down）；
         * 想了解更多可自行百度 DataBinding转义符。
         */
        list.add("list = 1");
        map.put("map", "map = 1");
        binding.setList(list);
        binding.setMap(map);
        binding.setIndex(0);
        binding.setKey("map");
        /*单向数据绑定  结束*/

        /*双向数据绑定  开始*/
        /*双向数据绑定  结束*/

        /*include  开始*/
        binding.setUser(new User("命根子", 1));
        /*include  结束*/

        /*viewStub  开始*/
        /**
         * 简单介绍下viewStub：被viewStub包裹的。即使页面显示的时候，被包裹的布局也不会加载，除非调用inflate。
         * 这样算是对布局卡顿的优化了。include则算是代码里的布局优化。
         */
        binding.viewStub.getViewStub().inflate();
        /*viewStub  结束*/
    }

    @Override
    public void onClick(View v) {
        int randowInt = new Random().nextInt(100);
        switch (v.getId()) {
            case R.id.tv:
                System.out.println("123456");
                break;
            case R.id.tvBaseObservableName: //如果在同一个控件内 那么全部显示
                dog.setDataName("二哈", "黑白相间");
                break;
            case R.id.tvBaseObservableAll:
                dog.setDataAll("金毛", "金黄色");
                break;
            case R.id.tvObservableFieldName: //如果在同一个控件内 那么全部显示
                yellowDog.name.set("金色的小狗");
                break;
            case R.id.tvObservableFieldAge:
                yellowDog.age.set(1);
                break;
            case R.id.tvObservableCollectionList:
                list.add(0, "list = " + randowInt);
                break;
            case R.id.tvObservableCollectionMap:
                map.put("map", "map = " + randowInt);
                break;
            case R.id.btnText:
                binding.btnText.setText(yellowDog.name.get());
                break;
        }
    }

    /*单向数据绑定 BaseObservable*/
    private void addListener() {
        dog.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == com.spark.mvvmjava.BR.name) {
                    Log.i(TAG, "onPropertyChanged: 名称");
                } else if (propertyId == BR._all) {
                    Log.i(TAG, "onPropertyChanged: 全部");
                } else {
                    Log.i(TAG, "onPropertyChanged: 未知错误");
                }
            }
        });
    }

    /**
     * 在recyclerView里的使用  也可Fragment  开始
     */
    class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater inflater;
        private List<MVVMData> list;

        public SimpleAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void updateItems(List<MVVMData> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public void addItems(List<MVVMData> list) {
            if (this.list == null) this.list = new ArrayList<>();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = inflater.inflate(R.layout.item_simple, parent, false);
            ViewDataBinding itemSimpleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_simple, parent, false);
            return new ViewHolder(itemSimpleBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ItemSimpleBinding itemSimpleBinding = (ItemSimpleBinding) holder.binding;
            MVVMData item = list.get(position);
            itemSimpleBinding.tvTitle.setText(item.getTitle());
            itemSimpleBinding.tvDesc.setText(item.getDesc());
            itemSimpleBinding.setImageUrl("http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853");
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewDataBinding binding;

            public ViewHolder(final ViewDataBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    @BindingAdapter("ivGlide")
    public static void loadImage(ImageView iv, String url) {
        Glide.with(iv).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transition(withCrossFade())
                .centerCrop()
                .into(iv);
    }

    class MVVMDataAdapter extends RecyclerView.Adapter<MVVMDataAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater inflater;
        private List<MVVMData> list;

        public MVVMDataAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void updateItems(List<MVVMData> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public void addItems(List<MVVMData> list) {
            if (this.list == null) this.list = new ArrayList<>();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_mvvm
                    , parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MVVMData item = list.get(position);
            holder.setData(item, position);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            private View rootView;
            private TextView tvDesc;
            private TextView tvTitle;
            private TextView tvKotlin;
            private TextView tvJava;
            private TextView tvContent;

            public ViewHolder(final View itemView) {
                super(itemView);
                rootView = itemView.findViewById(R.id.rootView);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
                tvKotlin = (TextView) itemView.findViewById(R.id.tvKotlin);
                tvJava = (TextView) itemView.findViewById(R.id.tvJava);
                tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            }

            public void setData(MVVMData item, final int position) {
                if (item != null) {
                    String title = item.getTitle();
                    tvTitle.setText(title);
                    tvDesc.setText(item.getDesc());
                    tvJava.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (title.equals(getString(R.string.base))) {

                            } else if (title.equals(getString(R.string.baseDesc))) {

                            }

                        }
                    });
                }
            }
        }
    }
}
