package com.cctv.swah.iot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cctv.swah.iot.Network.ReceiveDeviceList;
import com.cctv.swah.iot.Network.ReceiveLoginInfo;

public class DeviceListActivity extends AppCompatActivity {

    ListView device_list;
    TextView result_text, login_text, logout_text;
    ImageView activity_device_backBtn;
    Login_CustomDialog login_customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        setCall();
        setNetwork();
        setListener();
    }

    void setCall() {
        device_list = (ListView)findViewById(R.id.device_list);
        activity_device_backBtn = (ImageView)findViewById(R.id.activity_device_backBtn);
        result_text = (TextView)findViewById(R.id.noDevice);
        login_text = (TextView)findViewById(R.id.login);
        logout_text = (TextView)findViewById(R.id.logout);

        SharedPreferences login_info = getApplication().getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
        String name = login_info.getString("user_id", "no");
        String pw = login_info.getString("pw", "no");
        if(name.equals("no") && pw.equals("no")) {
            login_text.setVisibility(View.VISIBLE);
            logout_text.setVisibility(View.GONE);
        } else {
            login_text.setVisibility(View.GONE);
            logout_text.setVisibility(View.VISIBLE);
        }
    }

    void setNetwork() {

        SharedPreferences login_info = getApplication().getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
        String name = login_info.getString("user_id", "no");
        String pw = login_info.getString("pw", "no");
        Log.e("aaaaaaaaaaa", name + "--" + pw);
        if(name.equals("no") && pw.equals("no")) {
            Toast.makeText(DeviceListActivity.this, "기기 목록을 불러와주세요.", Toast.LENGTH_SHORT).show();
            device_list.setVisibility(View.GONE);
            result_text.setVisibility(View.VISIBLE);
        } else {
            ReceiveDeviceList task = new ReceiveDeviceList(DeviceListActivity.this, device_list, name, pw, result_text);
            task.execute();
        }

    }

    void setListener() {
        device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                InfoManager.page = 1;
//                Intent intent = new Intent(view.getContext(), StoreActivity.class);
//                StoreForm storeInfo = (StoreForm) parent.getAdapter().getItem(position);
//                intent.putExtra("SELECT", "around");
//                intent.putExtra("STOREINFO", storeInfo);
//                startActivity(intent);

            }
        });

        activity_device_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences login_info = getApplication().getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
                String name = login_info.getString("user_id", "no");
                String pw = login_info.getString("pw", "no");
                Log.e("aaaaaaaaaaa", name + "--" + pw);
                if(name.equals("no") && pw.equals("no")) {
                    Toast.makeText(DeviceListActivity.this, "기기 목록을 불러와주세요.", Toast.LENGTH_SHORT).show();
                    login_customDialog = new Login_CustomDialog(DeviceListActivity.this, mSendClickListener, mCancelClickListener);
                    login_customDialog.show();
                } else {
                    Toast.makeText(DeviceListActivity.this, "이미 불러왔습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logout_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences login_pref = getSharedPreferences("Mylogin", MODE_PRIVATE);
                login_pref.edit().clear().commit();

                logout_text.setVisibility(View.GONE);
                login_text.setVisibility(View.VISIBLE);
                Toast.makeText(DeviceListActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private View.OnClickListener mSendClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText editText_name = (EditText) login_customDialog.findViewById(R.id.user_name);
            EditText editText_pw = (EditText) login_customDialog.findViewById(R.id.password);
            String name = editText_name.getText().toString();
            String pw = editText_pw.getText().toString();
            if(editText_name.getText().toString().equals("")) {
                Toast.makeText(DeviceListActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
            } else if(editText_pw.getText().toString().equals("")) {
                Toast.makeText(DeviceListActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                ReceiveLoginInfo task = new ReceiveLoginInfo(DeviceListActivity.this, name, pw, login_text, logout_text);
                task.execute();

                login_customDialog.dismiss();
            }

        }
    };

    private View.OnClickListener mCancelClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            login_customDialog.dismiss();

        }
    };

}
