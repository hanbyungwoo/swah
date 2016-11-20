package com.cctv.swah.iot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cctv.swah.iot.Network.ReceiveLoginInfo;

import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    Login_CustomDialog login_customDialog;
    Button cctv_button, _button;
    TextView login, logout, hidden_ip;
    ImageView device;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String content = "f";
        byte[] array = content.getBytes();
        ByteBuffer info = ByteBuffer.wrap(array);

        Log.e("TAG1", content);
        Log.e("TAG2", array.toString()+"::::::::::"+array);
        Log.e("TAG3", info.toString()+"");


        setCall();
        setListener();
//        setTest();

    }

//    void setTest() {
//
//        String key = "abcdefghijklmnopqrstuvwxyz123456";
//        String encodeText = null;
//        String decodeText = null;
//        String plainText = "한글을 테스트 합니다.";
//
//        try {
//            encodeText = AES256Cipher.AES_Encode(plainText, key);
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        }
//
//        Log.d("AES256_Encode", encodeText);
//
//        try {
//            decodeText = AES256Cipher.AES_Decode("8YXXICxokM83pbNtE40tjw==", key);
//        } catch (Exception var7) {
//            var7.printStackTrace();
//        }
//
//        Log.d("AES256_Decode", decodeText);
//
//    }




    void setCall() {
        cctv_button = (Button)findViewById(R.id.cctv_button);
        _button = (Button)findViewById(R.id._button);
        device = (ImageView)findViewById(R.id.device);
        backPressCloseHandler = new BackPressCloseHandler(this);
        login = (TextView)findViewById(R.id.login);
        logout = (TextView)findViewById(R.id.logout);
        hidden_ip = (TextView)findViewById(R.id.hidden_ip);

        SharedPreferences login_info = getApplication().getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
        String name = login_info.getString("user_id", "no");
        String pw = login_info.getString("pw", "no");
        if(name.equals("no") && pw.equals("no")) {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        } else {
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
        }

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        InfoManager.text1 = pref.getString("cctv", "");
        InfoManager.url = pref.getString("url", "");
        InfoManager.text2 = pref.getString("text2", "");
        InfoManager.text3 = pref.getString("text3", "");
        InfoManager.text4 = pref.getString("text4", "");

    }

    void setListener() {
        hidden_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HiddenActivity.class);
                startActivity(intent);
            }
        });

        cctv_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CCTVDeviceActivity.class);
                startActivity(intent);
            }
        });

        device.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeviceListActivity.class);
                startActivity(intent);

            }
        });


        _button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TemHumDeviceActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences login_info = getApplication().getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
                String name = login_info.getString("user_id", "no");
                String pw = login_info.getString("pw", "no");
                Log.e("aaaaaaaaaaa", name + "--" + pw);
                if(name.equals("no") && pw.equals("no")) {
                    Toast.makeText(MainActivity.this, "기기 목록을 불러와주세요.", Toast.LENGTH_SHORT).show();
                    login_customDialog = new Login_CustomDialog(MainActivity.this, mSendClickListener, mCancelClickListener);
                    login_customDialog.show();
                } else {
                    Toast.makeText(MainActivity.this, "이미 불러왔습니다.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences login_pref = getSharedPreferences("Mylogin", MODE_PRIVATE);
                login_pref.edit().clear().commit();

                logout.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    private View.OnClickListener mSendClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText editText_name = (EditText) login_customDialog.findViewById(R.id.user_name);
            EditText editText_pw = (EditText) login_customDialog.findViewById(R.id.password);
            String name = editText_name.getText().toString();
            String pw = editText_pw.getText().toString();
            if(editText_name.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
            } else if(editText_pw.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                ReceiveLoginInfo task = new ReceiveLoginInfo(MainActivity.this, name, pw, login, logout);
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



//    private Socket socket;
//    BufferedReader socket_in;
//    PrintWriter socket_out;
//    EditText input;
//    Button button;
//    VideoView output;
//
//    String data;
//
////    socket = new Socket("192.168.43.75", 8000);
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        input = (EditText) findViewById(R.id.input);
//        button = (Button) findViewById(R.id.button);
//        output = (VideoView) findViewById(R.id.output);
//
//        Log.e("TAG", "버튼 전");
//
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.e("TAG", "버튼 후");
//                String data = input.getText().toString();
//                Log.w("TAG", "NETWORK" + data);
//                if(data != null) {
//                    socket_out.println(data);
//                }
//            }
//        });
//
//        Thread worker = new Thread() {
//            public void run() {
//                try {
//                    socket = new Socket("192.168.43.75", 2000);
//                    Log.e("TAG", "연결됨111111111");
//                    socket_out = new PrintWriter(socket.getOutputStream(), true);
//                    socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    Log.e("TAG", "연결됨");
//                } catch (IOException e) {
//                    Log.e("TAG", "에러 bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//                    e.printStackTrace();
//                }
//                try {
//                    while (true) {
//                        data = socket_in.readLine();
//                        output.post(new Runnable() {
//                            public void run() {
//                                output.setImageBitmap(data);
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                }
//            }
//        };
//        Log.e("TAG", "333333");
//        worker.start();
//        Log.e("TAG", "444444444");
//    }
//
//    @Override
//    protected void onStop() {
//        // TODO Auto-generated method stub
//        super.onStop();
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
