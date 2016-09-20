package com.cctv.swah.iot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.cctv.swah.iot.Model.DeviceInfo;
import com.cctv.swah.iot.Network.ReceiveCCTV;
import com.cctv.swah.iot.Network.VideoDecoderThread;

public class CCTVActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    VideoDecoderThread mVideoDecoder;

//    ImageView image_cctv;
    Surface image_cctv;

    ImageView activity_cctv_backBtn;
    DeviceInfo deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_activity);
        Log.e("TAAAAAAAAAAAAAAAA", "----");
        setCall();
        setListener();
        setNetwork();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("TAAAAAAAAAAAAAAAA", "++++");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
        Log.e("TAAAAAAAAAAAAAAAA", "----___________________");
//        if (mVideoDecoder != null) {
            Log.e("TAG", "여기시작!!!!!!!!!!!!!!!!!!!!!!!");
            ReceiveCCTV task = new ReceiveCCTV(CCTVActivity.this, holder, deviceInfo.getName(), deviceInfo.getPw(), deviceInfo.getModel(), image_cctv);
            task.execute();
//            if (mVideoDecoder.init(holder.getSurface(), "?????????????????")) {
//                mVideoDecoder.start();
//
//            } else {
//                mVideoDecoder = null;
//            }

//        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("TAAAAAAAAAAAAAAAA", "---SDAASdasdasd");
        if (mVideoDecoder != null) {
            mVideoDecoder.close();
        }
    }

    void setCall() {
        deviceInfo = (DeviceInfo) getIntent().getSerializableExtra("DEVICEINFO");
//        image_cctv = (ImageView)findViewById(R.id.image_cctv);
//        image_cctv = (Surface)findViewById(R.id.image_cctv);
        activity_cctv_backBtn = (ImageView)findViewById(R.id.activity_cctv_backBtn);

        SurfaceView surfaceView = new SurfaceView(this);
        surfaceView.getHolder().addCallback(this);
        setContentView(surfaceView);


    }

    void setNetwork() {
//        ReceiveCCTV task = new ReceiveCCTV(CCTVActivity.this, CCTVActivity.this, deviceInfo.getName(), deviceInfo.getPw(), deviceInfo.getModel(), image_cctv);
//        task.execute();

    }

    void setListener() {
        activity_cctv_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
