package com.cctv.swah.iot.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.cctv.swah.iot.AES256Cipher;
import com.cctv.swah.iot.InfoManager;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;


/**
 * Created by byungwoo on 2016-08-02.
 */
public class ReceiveTemHum extends AsyncTask<String, Integer, String> {

    Context context;
    ////////////////////////////////////////////

    String json;
    String name;
    String pw;
    String model;
    TextView temperature, humidity;

    public ReceiveTemHum(Context context, String name, String pw, String model, TextView temperature, TextView humidity) {
        this.context = context;
        this.name = name;
        this.pw = pw;
        this.model = model;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Override
    protected String doInBackground(String... params) {
        // 연결 시작


//        String url = "http://wkdgusdn3.dothome.co.kr/mamimap/review_list.php?store_id="+store_id;
        String url = InfoManager.url + "phone/iot_key.php";

        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody body = new FormEncodingBuilder()
                    .add("name", name)
                    .add("pw", pw)
                    .add("iot","tem_hum")
                    .add("model", model)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            json = response.body().string();    // 받아온 것 단순히 넣어주는 중..

//            Log.e("json", json);
        } catch (Exception e) {
//            Log.e("여기서?", e.toString());
            e.printStackTrace();
        }
        //Toast.makeText(context, "좌표" + saved_latitude + " : " + saved_longitude, Toast.LENGTH_SHORT).show();
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.e("AAAAAAAAAAAAA", json);

        String code = null;
        String key = null;

        String tem = null;
        String hum = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            code = URLDecoder.decode(jsonObject.getString("code"), "utf-8");
            key = URLDecoder.decode(jsonObject.getString("key"), "utf-8");
//            key = URLDecoder.decode(jsonObject.getString("key"), "utf-8");
//            key = jsonObject.getString("key");
//            String key2 = URLDecoder.decode(key, "utf-8");
            Log.e("code", code);
            Log.e("key", key);
//            key = "abcdefghijklmnopqrstuvwxyz123456";

//            Log.e("key2", key2);
            Log.e("KeySize", key.length()+"");
            Log.e("KeySize_length", key.getBytes().length +"");
//            Log.e("KeySize", key2.length()+"");

            JSONArray tem_hum_Info = jsonObject.getJSONArray("iot");
            JSONObject infoObject = tem_hum_Info.getJSONObject(0);

//            tem = URLDecoder.decode(infoObject.getString("tem"), "utf-8");
//            hum = URLDecoder.decode(infoObject.getString("hum"), "utf-8");
            tem = URLDecoder.decode(infoObject.getString("tem"), "utf-8");
            hum = URLDecoder.decode(infoObject.getString("hum"), "utf-8");


            Log.e("TEST", tem + " :::: " + hum);
            tem = tem.replace(" ", "+");
            hum = hum.replace(" ", "+");
            Log.e("TEST2", tem + " :::: " + hum);
            String decode_tem = null;
            String decode_hum = null;

            try {
                decode_tem = AES256Cipher.AES_Decode(tem, key);
            } catch (Exception var8) {
                var8.printStackTrace();
            }



            Log.e("AES256_Decode_tem", decode_tem);

            try {
                decode_hum = AES256Cipher.AES_Decode(hum, key);
            } catch (Exception var7) {
                var7.printStackTrace();
            }

            Log.e("AES256_Decode_hum", decode_hum);

            temperature.setText(decode_tem+"°C");
            humidity.setText(decode_hum+"%");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
