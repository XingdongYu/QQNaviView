# QQNaviView
模仿手机QQ底部导航栏Icon拖拽效果

介绍：http://blog.csdn.net/kobeyxd123/article/details/72829288

效果图
---
![image](http://img.blog.csdn.net/20170601105942711?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQva29iZXl4ZDEyMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

Gradle
---

/build.gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

/app/build.gradle
```
dependencies {
	compile 'com.github.XingdongYu:QQNaviView:v1.0.0'
}
```
使用
---
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal"
        android:background="#ffffff"
        android:paddingBottom="4dp"
        android:elevation="4dp">

        <com.robog.library.QQNaviView
            android:id="@+id/qq_view_bubble"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:onClick="onClick"
            android:gravity="center"
            android:orientation="horizontal"
            app:bigIconSrc="@drawable/pre_bubble_big"
            app:smallIconSrc="@drawable/pre_bubble_small"
            app:iconWidth="50dp"
            app:iconHeight="50dp"
            app:range="1.2">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="12sp"
                android:text="消息"/>

        </com.robog.library.QQNaviView>

        <com.robog.library.QQNaviView
            android:id="@+id/qq_view_person"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:onClick="onClick"
            app:bigIconSrc="@drawable/pre_person_big"
            app:smallIconSrc="@drawable/pre_person_small"
            app:iconWidth="50dp"
            app:iconHeight="50dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="12sp"
                android:text="联系人" />

        </com.robog.library.QQNaviView>

        <com.robog.library.QQNaviView
            android:id="@+id/qq_view_star"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:onClick="onClick"
            app:bigIconSrc="@drawable/pre_star_big"
            app:smallIconSrc="@drawable/pre_star_small"
            app:iconWidth="50dp"
            app:iconHeight="50dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="12sp"
                android:text="动态"/>

        </com.robog.library.QQNaviView>

    </LinearLayout>

</RelativeLayout>
```
属性
---
| name         | format    | description |
| -------------|-----------| ------------|
| bigIconSrc   | reference |  大图标资源  |
| smallIconSrc | reference |  小图标资源  |
| iconWidth    | dimension |  图标宽度    |
| iconHeight   | dimension |  图标高度    |
| range        | float     |  可拖动范围  |
  
