package com.yiwent.shiftytextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yiwent.viewlib.ShiftyTextview;

public class MainActivity extends AppCompatActivity {

    private ShiftyTextview     mShiftyTextview;
    private ShiftyTextview mShiftyTextview1;
    private ShiftyTextview mShiftyTextview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShiftyTextview = (ShiftyTextview) findViewById(R.id.text);
        mShiftyTextview1 = (ShiftyTextview) findViewById(R.id.text1);
        mShiftyTextview2 = (ShiftyTextview) findViewById(R.id.text2);
    }

    public void start(View view) {
        mShiftyTextview.setPrefixString("¥");
        mShiftyTextview.setNumberString("99998.123456");

        //        mShiftyTextview1.setEnableAnim(true);
        mShiftyTextview1.setNumberString("123456.09");

        mShiftyTextview2.setPostfixString("%");
        mShiftyTextview2.setNumberString("99.75");

    }
}
