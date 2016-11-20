package com.cctv.swah.iot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.cctv.swah.iot.Model.DeviceInfo;

public class TestActivity extends AppCompatActivity {


    ImageView activity_cctv_backBtn;
    VideoView v;
    TextView main_cctv_text;
    DeviceInfo device_Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        setView();
        setListener();
        setStart();
    }

    void setView() {
        activity_cctv_backBtn = (ImageView)findViewById(R.id.activity_cctv_backBtn);
        v = (VideoView) findViewById(R.id.videoView);
        main_cctv_text = (TextView)findViewById(R.id.main_cctv_text);
        Intent intent = getIntent();
        device_Info = (DeviceInfo)intent.getSerializableExtra("DEVICEINFO");
        main_cctv_text.setText(device_Info.getModel());
    }

    void setListener() {
        activity_cctv_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void setStart() {
        Log.e("TAG", "cctv_url"+InfoManager.text1);
        v.setVideoURI(Uri.parse(InfoManager.text1));
        //v.setMediaController( new MediaController( this ) );
        final MediaController mc = new MediaController(this);
        v.setMediaController(mc);
        v.requestFocus();
        v.start();
    }

//
////        video.setMediaController(null); //
////        // video.setMediaController(null); 상태바 지울때 사용
////        video.setVideoPath("rtsp ip")
////        video.start();
//
//
//
//    }
////
////    v.requestFocus();
////        v.start();
////    }
}
