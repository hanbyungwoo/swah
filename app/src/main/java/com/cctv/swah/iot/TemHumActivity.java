package com.cctv.swah.iot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.swah.iot.Model.DeviceInfo;
import com.cctv.swah.iot.Network.ReceiveTemHum;

public class TemHumActivity extends AppCompatActivity {


    TextView text_temperature, text_humidity;
    ImageView activity_tem_hum_backBtn, refresh;
    DeviceInfo deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem_hum);

        setCall();
        setNetwork();
        setListener();
    }

    void setCall() {
        deviceInfo = (DeviceInfo) getIntent().getSerializableExtra("DEVICEINFO");
        activity_tem_hum_backBtn = (ImageView)findViewById(R.id.activity_tem_hum_backBtn);
        text_humidity = (TextView)findViewById(R.id.text_humidity);
        text_temperature = (TextView)findViewById(R.id.text_temperature);
        refresh = (ImageView)findViewById(R.id.refresh);
    }
    void setNetwork() {
        ReceiveTemHum task = new ReceiveTemHum(TemHumActivity.this, deviceInfo.getName(), deviceInfo.getPw(), deviceInfo.getModel(), text_temperature, text_humidity);
        task.execute();
    }
    void setListener() {
        activity_tem_hum_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCall();
                setNetwork();
            }
        });
    }
}
