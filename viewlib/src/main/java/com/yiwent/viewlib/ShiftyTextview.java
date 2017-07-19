package com.yiwent.viewlib;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/7/19
 * Time: 10:18
 */
@SuppressLint("AppCompatCustomView")
public class ShiftyTextview extends TextView {
    private String mNumStart = "0";  // 起始值 默认 0
    private String mNumEnd; // 结束值
    private long    mDuration      = 1000; // 动画总时间 默认 1000 毫秒
    private String  mPrefixString  = ""; // 前缀
    private String  mPostfixString = ""; // 后缀
    private boolean isEnableAnim   = true; // 是否开启动画
    private boolean useCommaFormat;//是否使用每三位数字一个逗号的格式，让数字显得比较好看，默认使用
    private boolean runWhenChange;//是否当内容有改变才使用动画,默认是
    private float   minNum;//显示数字最少要达到这个数字才滚动 默认为0.1
    private String preStr = "0";

    public ShiftyTextview(Context context) {
        this(context, null);
    }

    public ShiftyTextview(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ShiftyTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShiftyTextview);
        mDuration = ta.getInt(R.styleable.ShiftyTextview_duration, 1000);
        minNum = ta.getFloat(R.styleable.ShiftyTextview_minNum, 0.1f);
        useCommaFormat = ta.getBoolean(R.styleable.ShiftyTextview_useCommaFormat, true);
        runWhenChange = ta.getBoolean(R.styleable.ShiftyTextview_runWhenChange, true);
        isEnableAnim = ta.getBoolean(R.styleable.ShiftyTextview_isEnableAnim, true);
        mNumStart = ta.getString(R.styleable.ShiftyTextview_numStart);
        if (TextUtils.isEmpty(mNumStart))
            mNumStart = "0";
        mNumEnd = ta.getString(R.styleable.ShiftyTextview_numEnd);
        if (TextUtils.isEmpty(mNumEnd))
            mNumEnd = "";
        mPrefixString = ta.getString(R.styleable.ShiftyTextview_prefixString);
        if (TextUtils.isEmpty(mPrefixString))
            mPrefixString = "";
        mPostfixString = ta.getString(R.styleable.ShiftyTextview_postfixString);
        if (TextUtils.isEmpty(mPrefixString))
            mPostfixString = "";
        ta.recycle();
    }

    public void setNumberString(String number) {
        setNumberString("0", number);
    }

    /**
     *
     * @param numberStart
     * @param numberEnd 必须为int或float
     */
    public void setNumberString(String numberStart, String numberEnd) {
        mNumStart = numberStart;
        mNumEnd = numberEnd;
        if (checkNumString(numberStart, numberEnd) && Float.parseFloat(mNumEnd) > minNum) {
            // 数字合法　开始数字动画
            start();
        } else {
            // 数字不合法　直接调用　setText　设置最终值
            setText(mPrefixString + numberEnd + mPostfixString);
        }
    }

    public void setEnableAnim(boolean enableAnim) {
        isEnableAnim = enableAnim;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public void setPrefixString(String mPrefixString) {
        this.mPrefixString = mPrefixString;
    }

    public void setPostfixString(String mPostfixString) {
        this.mPostfixString = mPostfixString;
    }

    public void setMinNumString(float minNum) {
        this.minNum = minNum;
    }

    public void setUseCommaFormat(boolean useCommaFormat) {
        this.useCommaFormat = useCommaFormat;
    }

    public void setRunWhenChange(boolean runWhenChange) {
        this.runWhenChange = runWhenChange;
    }

    private boolean isInt; // 是否是整数

    /**
     * 校验数字的合法性
     *
     * @param numberStart 　开始的数字
     * @param numberEnd   　结束的数字
     * @return 合法性
     */
    private boolean checkNumString(String numberStart, String numberEnd) {

        String regexInteger = "-?\\d*";
        isInt = numberEnd.matches(regexInteger) && numberStart.matches(regexInteger);
        if (isInt) {
            BigInteger start = new BigInteger(numberStart);
            BigInteger end = new BigInteger(numberEnd);
            return end.compareTo(start) >= 0;
        }
        String regexDecimal = "-?[1-9]\\d*.\\d*|-?0.\\d*[1-9]\\d*";
        if ("0".equals(numberStart)) {
            if (numberEnd.matches(regexDecimal)) {
                BigDecimal start = new BigDecimal(numberStart);
                BigDecimal end = new BigDecimal(numberEnd);
                return end.compareTo(start) > 0;
            }
        }
        if (numberEnd.matches(regexDecimal) && numberStart.matches(regexDecimal)) {
            BigDecimal start = new BigDecimal(numberStart);
            BigDecimal end = new BigDecimal(numberEnd);
            return end.compareTo(start) > 0;
        }
        return false;
    }

    private void start() {
        if (!isEnableAnim) { // 禁止动画
            if (useCommaFormat) {
                setText(mPrefixString + format(new BigDecimal(mNumEnd)) + mPostfixString);
            } else {
                setText(mPrefixString + mNumEnd + mPostfixString);
            }
            return;
        }
        if (runWhenChange) {
            if (preStr.equals(mNumEnd)) {
                //如果两次内容一致，则不做处理
                setText(mNumEnd);
                return;
            }
            preStr = mNumEnd;//如果两次内容不一致，记录最新的str
        }
        ValueAnimator animator = ValueAnimator.ofObject(new BigDecimalEvaluator(), new BigDecimal(mNumStart), new BigDecimal(mNumEnd));
        animator.setDuration(mDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BigDecimal value = (BigDecimal) valueAnimator.getAnimatedValue();
                setText(mPrefixString + format(value) + mPostfixString);
            }
        });
        animator.start();
    }

    /**
     * 格式化 BigDecimal ,小数部分时保留两位小数并四舍五入
     *
     * @param bd 　BigDecimal
     * @return 格式化后的 String
     */
    private String format(BigDecimal bd) {
        StringBuilder pattern = new StringBuilder();
        if (isInt) {
            if (useCommaFormat) {
                pattern.append("#,###");
            } else {
                pattern.append("#");
            }

        } else {
            int length = 0;
            String decimals = mNumEnd.split("\\.")[1];
            if (decimals != null) {
                length = decimals.length();
            }
            if (useCommaFormat) {
                pattern.append("#,##0");
            } else {
                pattern.append("#0");
            }
            if (length > 0) {
                pattern.append(".");
                for (int i = 0; i < length; i++) {
                    pattern.append("0");
                }
            }
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(bd);
    }

    // 不加 static 关键字，也不会引起内存泄露，因为这里也没有开启线程
    // 加上 static 关键字，是因为该内部类不需要持有外部类的引用，习惯加上
    private static class BigDecimalEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            BigDecimal start = (BigDecimal) startValue;
            BigDecimal end = (BigDecimal) endValue;
            BigDecimal result = end.subtract(start);
            return result.multiply(new BigDecimal("" + fraction)).add(start);
        }
    }
}
