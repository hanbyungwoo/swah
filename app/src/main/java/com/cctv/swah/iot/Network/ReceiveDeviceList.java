package com.cctv.swah.iot.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cctv.swah.iot.Adapter.DeviceListAdapter;
import com.cctv.swah.iot.InfoManager;
import com.cctv.swah.iot.Model.DeviceInfo;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;


/**
 * Created by byungwoo on 2016-07-26.
 */
public class ReceiveDeviceList extends AsyncTask<String, Integer, String> {

    Context context;
    ////////////////////////////////////////////

    String json;
    String name;
    String pw;
    ListView list;
    TextView result;
    DeviceListAdapter deviceListAdapter;

    public ReceiveDeviceList(Context context, ListView list, String name, String pw, TextView result) {
        this.context = context;
        this.list = list;
        this.name = name;
        this.pw = pw;
        this.result = result;
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

        deviceListAdapter = new DeviceListAdapter(context);
        Log.e("JSON", json);
//  code =000
//        "id"=>$row["id"],
//                "model"=>$row["model"],
//                "name"=>$row["name"],
//                "pw"=>$row["pw"]

        String code = null;
        String camera = null;
        String tem_hum = null;

        String id = null;
        String model = null;
        String name = null;
        String pw = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            code = URLDecoder.decode(jsonObject.getString("code"), "utf-8");

            Log.e("code", code);

            if(code.equals("001")) {
                list.setVisibility(View.GONE);
                result.setVisibility(View.VISIBLE);
            }

            JSONArray deviceInfo = jsonObject.getJSONArray("cctv");
            JSONArray deviceInfo2 = jsonObject.getJSONArray("tem_hum");

            Log.e("JSON", deviceInfo+"!!!!!");

            for(int i=0; i<deviceInfo.length(); i++) {
                JSONObject infoObject = deviceInfo.getJSONObject(i);

                Log.e("JSON", infoObject+"????info");
                id = URLDecoder.decode(infoObject.getString("id"), "utf-8");
                model = URLDecoder.decode(infoObject.getString("model"), "utf-8");
                name = URLDecoder.decode(infoObject.getString("name"), "utf-8");
                pw = URLDecoder.decode(infoObject.getString("pw"), "utf-8");

                Log.e("JSON", id+" "+model+" "+name+" "+pw+"--"+i);
                // CCTV
                DeviceInfo device_info = new DeviceInfo(0, id, model, name, pw);
                Log.e("JSON", "DEVICE_INFO" + device_info.toString() +"________"+ device_info.getId());
                deviceListAdapter.add(device_info);

            }

            for(int i=0; i<deviceInfo2.length(); i++) {
                JSONObject infoObject = deviceInfo2.getJSONObject(i);

                Log.e("JSON", infoObject+"????info");
                id = URLDecoder.decode(infoObject.getString("id"), "utf-8");
                model = URLDecoder.decode(infoObject.getString("model"), "utf-8");
                name = URLDecoder.decode(infoObject.getString("name"), "utf-8");
                pw = URLDecoder.decode(infoObject.getString("pw"), "utf-8");

                Log.e("JSON", id+" "+model+" "+name+" "+pw+"--"+i);
                // TEMPERATURE_HUMIDITY
                DeviceInfo device_info = new DeviceInfo(1, id, model, name, pw);
                Log.e("JSON", "DEVICE_INFO" + device_info.toString() +"________"+ device_info.getId());
                deviceListAdapter.add(device_info);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        list.setAdapter(deviceListAdapter);
    }

}
