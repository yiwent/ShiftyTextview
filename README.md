# ShiftyTextview
具有数字增加动画的 TextView (A increasing Number TextView with animation)
    
![ShiftyTextview演示gif](https://github.com/yiwent/ShiftyTextview/blob/master/gif/GIF.gif)

## Features

1. 添加前缀、后缀
2. 支持任意大小的整数或小数
3. 可选择是否添加千位分隔符
4. 可选择数字变化才有动画
5. 超过某个数才有变化，可选择变化的区间
5. API 简单

## Usage

```java
    // 设置最终值，开始动画
    mShiftyTextview.setNumberString("98765432.75");
```

## Option

```java
    // 设置前缀
    mShiftyTextview.setPrefixString("¥");
    // 设置后缀
    mShiftyTextview.setPostfixString("%");
    // 设置动画时长
    mShiftyTextview.setDuration(2000);
    // 设置数字增加范围
    mShiftyTextview.setNumberString("19.75", "99.75");
    
    // 禁用动画
    mShiftyTextview1.setEnableAnim(false);
```
## Customization
* app:duration [integer def:2000] -->animation Duration
* app:minNum [float def:0.1f] --> the minNub
* app: numStart[string def:0]-->start Nub
* app: numEnd[string def: ]-->end Nub
* app: prefixString[string def:]-->prefixion,eg:$ 
* app: postfixString[string def: ]-->postfix ,eg:%
* app: useCommaFormat[boolean def:true ]-->useCommaFormat,eg:123,145
* app: runWhenChange[boolean def:true ]-->runWhenChange,if no changes,no animation
* app: isEnableAnim[boolean def:true ]-->EnableAnim

##    自定义
         <!--动画时间-->
        <attr name="duration" format="integer"></attr>
        <!--最小的数，小于这个数没有动画，默认为0.1-->
        <attr name="minNum" format="float"></attr>
        <!--动画开始的数-->
        <attr name="numStart" format="string|reference"></attr>
        <!--动画最后的数-->
        <attr name="numEnd" format="string|reference"></attr>
        <!--前缀-->
        <attr name="prefixString" format="string|reference"></attr>
        <!--后缀-->
        <attr name="postfixString" format="string|reference"></attr>
        <!--是否使用动画-->
        <attr name="isEnableAnim" format="boolean"></attr>
        <!--是否使用每三位数字一个逗号-->
        <attr name="useCommaFormat" format="boolean"></attr>
        <!--是否当内容改变的时候使用动画，不改变则不使用动画-->
        <attr name="runWhenChange" format="boolean"></attr> 

##    导入方式

在项目根目录下的build.gradle中的allprojects{}中，添加jitpack仓库地址，如下：
```java
    allprojects {
        repositories {
            jcenter()
            maven { url 'https://jitpack.io' }//添加jitpack仓库地址
        }
    }
```
打开app的module中的build.gradle，在dependencies{}中，添加依赖，如下：
```java
    dependencies {
            compile 'com.github.yiwent:ShiftyTextviewLibrary:1.1.0'
    }
```
##  使用

#### Coding in layout xml
```java
<com.yiwent.viewlib.ShiftyTextview
        android:id="@+id/text1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:layout_marginTop="47dp"
        android:textSize="28sp"
        app:useCommaFormat="false"
        app:runWhenChange="false"
        />
```
#### Coding in the Activity or Fragment
```java
        mShiftyTextview.setPrefixString("¥");
        mShiftyTextview.setNumberString("99998.123456");

        //        mShiftyTextview1.setEnableAnim(true);
        mShiftyTextview1.setNumberString("123456.09");

        mShiftyTextview2.setPostfixString("%");
        mShiftyTextview2.setNumberString("99.75");
```
## Thanks
[NumberAnimTextView](https://github.com/Bakumon/NumberAnimTextView)