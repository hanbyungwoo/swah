package com.cctv.swah.iot.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cctv.swah.iot.InfoManager;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.net.URLDecoder;


/**
 * Created by byungwoo on 2016-07-26.
 */
public class ReceiveLoginInfo extends AsyncTask<String, Integer, String> {

    Context context;
    ////////////////////////////////////////////

    String json;
    String name;
    String pw;
    TextView login, logout;

    public ReceiveLoginInfo(Context context, String name, String pw, TextView login, TextView logout) {
        this.context = context;
        this.name = name;
        this.pw = pw;
        this.login = login;
        this.logout = logout;
    }

    @Override
    protected String doInBackground(String... params) {
        // 연결 시작



//        String url = "http://wkdgusdn3.dothome.co.kr/mamimap/review_list.php?store_id="+store_id;
        String url = InfoManager.url + "phone/device_list.php";

        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody body = new FormEncodingBuilder()
                    .add("name", name)
                    .add("pw", pw).build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();

            Response response = client.newCall(request).execute();
            json = response.body().string();    // 받아온 것 단순히 넣어주는 중..

//            Log.e("json", json);
        } catch (Exception e) {
//            Log.e("여기서?", e.toString());
            e.printStackTrace();
        }
        //Toast.makeText(context, "좌표" + saved_latitude + " : " + saved_longitude, Toast.LENGTH_SHORT).show();
        Log.e("JSON", json);
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.e("JSON--", user_id+":"+password);
//        Log.e("JSON", s);

        Log.e("JSON", json);
//  code =000
//        "id"=>$row["id"],
//                "model"=>$row["model"],
//                "name"=>$row["name"],
//                "pw"=>$row["pw"]

        String code = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            code = URLDecoder.decode(jsonObject.getString("code"), "utf-8");

            Log.e("code", code);

            if(code.equals("001")) {
                Toast.makeText(context, "등록된 기기가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "기기를 불러왔습니다.", Toast.LENGTH_SHORT).show();
                SharedPreferences login_info = context.getSharedPreferences("Mylogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = login_info.edit();
                editor.putString("user_id", name);
                editor.putString("pw", pw);
                editor.commit();

                logout.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
