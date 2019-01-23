## 添加依赖

项目build.gradle 
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

模块build.gradle
```
dependencies {
    implementation 'com.github.GentlemanAyy:StatusLayout:Tag'
}
```
### 基本用法

xml中
```xml
    <com.zy.statuslayout.StatusLayoutView
        android:id="@+id/slv"
        android:layout_width="match_parent"
        app:layout_content="@layout/你的主内容布局"
        android:layout_height="match_parent"/>
```
activity中

```java
    StatusLayoutView mSlv = findViewById(R.id.slv);
    mSlv.showContent();//展示主内容布局
    mSlv.showError();//展示错误布局
    mSlv.showEmpty();//展示数据为空布局
    mSlv.showNetwork();//展示网络异常问题
    
    View contentLayout = mSlv.getContentLayout();//获取主内容界面
    TextView mTv = contentLayout.findViewById(R.id.tv);
    mTv.setText("......");
```

## 属性说明
name | 说明
---|---
``app:layout_content`` | 主内容布局
``app:layout_error``| 加载错误布局
``app:layout_empty``| 加载内容为空布局
``app:layout_network``| 网络异常布局
``app:layout_loading``| 加载中布局
### 自定义的根布局必须给一个id

设置有两个接口

```
    public interface LayoutClick {
        void onTryRequestListener();//加载错误布局,点击设置(再次点击)

        void onOpenRequestListener();//网络异常布局,点击设置(打开设置页面)
    }

    mSlv.setLayoutClick(new StatusLayoutView.LayoutClick() {
        @Override
        public void onTryRequestListener() {
            //重新加载数据
            loadData();
        }

        @Override
        public void onOpenRequestListener() {
            //打开设置页面
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
        }
    });
```


