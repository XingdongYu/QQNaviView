# QQNaviView
模仿手机QQ底部导航栏Icon拖拽效果

介绍：http://blog.csdn.net/kobeyxd123/article/details/72829288

效果图
---
![image](http://img.blog.csdn.net/20170601105942711?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQva29iZXl4ZDEyMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

Gradle
---
[![](https://jitpack.io/v/XingdongYu/QQNaviView.svg)](https://jitpack.io/#XingdongYu/QQNaviView)

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
	compile 'com.github.XingdongYu:QQNaviView:v1.0.1'
}
```
使用
---
```xml
<com.robog.library.QQNaviView
	android:id="@+id/qq_view_bubble"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:gravity="center"
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
```
支持属性
---
| name         | format    | description |
| -------------|-----------| ------------|
| bigIconSrc   | reference |  大图标资源  |
| smallIconSrc | reference |  小图标资源  |
| iconWidth    | dimension |  图标宽度    |
| iconHeight   | dimension |  图标高度    |
| range        | float     |  可拖动范围  |
  
