package com.cctv.swah.iot;

/**
 * Created by byungwoo on 2015-12-30.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class Login_CustomDialog extends Dialog {

    private Button mButton;
    private ImageView mCancel;

    private View.OnClickListener mSendClickListener;
    private View.OnClickListener mCancelClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.customdialog_login);

        mButton = (Button) findViewById(R.id.send_data);
        mCancel = (ImageView) findViewById(R.id.login_popup_end);


        // 클릭 이벤트 셋팅
        if (mSendClickListener != null && mCancelClickListener != null) {
            mButton.setOnClickListener(mSendClickListener);
            mCancel.setOnClickListener(mCancelClickListener);
        } else if (mSendClickListener != null
                && mCancelClickListener == null) {
            mButton.setOnClickListener(mSendClickListener);
        } else {

        }

    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public Login_CustomDialog(Context context,
                              View.OnClickListener singleListener,
                              View.OnClickListener twoListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mSendClickListener = singleListener;
        this.mCancelClickListener = twoListener;
    }


}
