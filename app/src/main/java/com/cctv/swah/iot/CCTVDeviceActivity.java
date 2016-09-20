package com.cctv.swah.iot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cctv.swah.iot.Model.DeviceInfo;
import com.cctv.swah.iot.Network.ReceiveCCTVDevice;

public class CCTVDeviceActivity extends AppCompatActivity {


    ListView cctv_list;
    TextView noDevice;
    ImageView activity_cctv_backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_device);

        setCall();
        setNetwork();
        setListener();

    }

    void setCall() {
        cctv_list = (ListView)findViewById(R.id.cctv_device);
        noDevice = (TextView)findViewById(R.id.noDevice);
        activity_cctv_backBtn = (ImageView)findViewById(R.id.activity_cctv_backBtn);
    }

    void setListener() {
        cctv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), CCTVActivity.class);
                DeviceInfo device_Info = (DeviceInfo) parent.getAdapter().getItem(position);
                intent.putExtra("DEVICEINFO", device_Info);
                startActivity(intent);


//                InfoManager.page = 1;
//                Intent intent = new Intent(view.getContext(), StoreActivity.class);
//                StoreForm storeInfo = (StoreForm) parent.getAdapter().getItem(position);
//                intent.putExtra("SELECT", "around");
//                intent.putExtra("STOREINFO", storeInfo);
//                startActivity(intent);

            }
        });

        activity_cctv_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void setNetwork() {
        SharedPreferences login_info = getApplication().getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
        String name = login_info.getString("user_id", "no");
        String pw = login_info.getString("pw", "no");
        Log.e("aaaaaaaaaaa", name + "--" + pw);
        if(name.equals("no") && pw.equals("no")) {
            Toast.makeText(CCTVDeviceActivity.this, "기기 목록을 불러와주세요.", Toast.LENGTH_SHORT).show();
            cctv_list.setVisibility(View.GONE);
            noDevice.setVisibility(View.VISIBLE);
        } else {
            ReceiveCCTVDevice task = new ReceiveCCTVDevice(CCTVDeviceActivity.this, cctv_list, name, pw, noDevice);
            task.execute();
        }

    }
}
