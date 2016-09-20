package com.cctv.swah.iot.Network;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.cctv.swah.iot.AES256Cipher;
import com.cctv.swah.iot.InfoManager;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/**
 * Created by byungwoo on 2016-08-11.
 */
public class ReceiveCCTV extends AsyncTask<String, Integer, String> {

    Context context;
    ////////////////////////////////////////////

    String json;
    String name;
    String pw;
    String model;
    Surface image;
    AnimationDrawable mAnimationDrawable;
    String decode_video = null;
    ArrayList<Object> image_url = new ArrayList<Object>();
    SurfaceHolder holder;

    public ReceiveCCTV(Context context, SurfaceHolder holder, String name, String pw, String model, Surface image) {
        this.context = context;
        this.holder = holder;
        this.name = name;
        this.pw = pw;
        this.model = model;
        this.image = image;
    }

    @Override
    protected String doInBackground(String... params) {
        // 연결 시작


//        String url = "http://wkdgusdn3.dothome.co.kr/mamimap/review_list.php?store_id="+store_id;
        String url = InfoManager.url + "phone/iot_key2.php";

        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody body = new FormEncodingBuilder()
                    .add("name", name)
                    .add("pw", pw)
                    .add("iot","cctv")
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
//        static final String VIDEO_URL = "http://2xx.xxx.xxx.xxx/avitest.avi";
//
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(VIDEO_URL);
//        i.setDataAndType(uri, "video/*");
//        startActivity(i);
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        mAnimationDrawable = new AnimationDrawable();
//        mAnimationDrawable.setOneShot(false);
//
//        BitmapDrawable mBitmapDrawable_1 = (BitmapDrawable)context.getResources().getDrawable(R.drawable.back);
//        mAnimationDrawable.addFrame(mBitmapDrawable_1,100);

        Log.e("AAAAAAAAAAAAA", json);

        String code = null;
        String key = null;

        String video = null;
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
            video = URLDecoder.decode(infoObject.getString("video"), "utf-8");
//            hum = URLDecoder.decode(infoObject.getString("hum"), "utf-8");


//            Log.e("TEST", tem + " :::: " + hum);
            video = video.replace(" ", "+");
//            hum = hum.replace(" ", "+");
            Log.e("TEST2", video);


            try {
                decode_video = AES256Cipher.AES_Decode(video, key);
            } catch (Exception var8) {
                var8.printStackTrace();
            }



            Log.e("AES256_Decode_video", decode_video);

            start();






        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void start() {
        VideoDecoderThread thread = new VideoDecoderThread();
        thread.init(holder.getSurface(), decode_video);

//        MediaFormat format = MediaFormat.createVideoFormat("video/avc", 320, 240);
//        MediaCodec codec = MediaCodec.createDecoderByType("video/avc");
//        codec.configure (format, image, null, 0);
//        codec.start();
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(video);
//        i.setDataAndType(uri, "video/*");
//        activity.startActivity(i);

    }

//
//
//    public class VideoDecoderThread extends Thread {
//        private static final String VIDEO = "video/";
//        private static final String TAG = "VideoDecoder";
//        private MediaExtractor mExtractor;
//        private MediaCodec mDecoder;
//
//        private boolean eosReceived;
//
//        public boolean init(Surface surface, String filePath) {
//
//            eosReceived = false;
//            try {
//                mExtractor = new MediaExtractor();
////                mExtractor.setDataSource(filePath);
//
//                for (int i = 0; i < mExtractor.getTrackCount(); i++) {
//                    MediaFormat format = mExtractor.getTrackFormat(i);
//
//                    String mime = format.getString(MediaFormat.KEY_MIME);
//                    if (mime.startsWith(VIDEO)) {
//                        mExtractor.selectTrack(i);
//                        mDecoder = MediaCodec.createDecoderByType(mime);
//                        try {
//                            Log.d(TAG, "format : " + format);
//                            mDecoder.configure(format, surface, null, 0 /* Decoder */);
//
//                        } catch (IllegalStateException e) {
//                            Log.e(TAG, "codec '" + mime + "' failed configuration. " + e);
//                            return false;
//                        }
//
//                        mDecoder.start();
//                        break;
//                    }
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return true;
//        }
//
//        @Override
//        public void run() {
//            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
//            /////////////////////// string 을 byte로 변경
//            ByteBuffer inputBuffers = null;
//            try {
//                byte[] b = decode_video.getBytes("US-ASCII");
//                inputBuffers = ByteBuffer.wrap(b);
//            } catch(Exception e) {
//                Log.e("error", e+"");
//            }
////            ByteBuffer[] inputBuffers = mDecoder.getInputBuffers();
//            mDecoder.getOutputBuffers();
//
//            boolean isInput = true;
//            boolean first = false;
//            long startWhen = 0;
//
//            while (!eosReceived) {
//                if (isInput) {
//                    int inputIndex = mDecoder.dequeueInputBuffer(10000);
//                    if (inputIndex >= 0) {
//                        // fill inputBuffers[inputBufferIndex] with valid data
//
//                        ByteBuffer inputBuffer = inputBuffers[inputIndex];
//
//                        int sampleSize = mExtractor.readSampleData(inputBuffer, 0);
//
//                        if (mExtractor.advance() && sampleSize > 0) {
//                            mDecoder.queueInputBuffer(inputIndex, 0, sampleSize, mExtractor.getSampleTime(), 0);
//
//                        } else {
//                            Log.d(TAG, "InputBuffer BUFFER_FLAG_END_OF_STREAM");
//                            mDecoder.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
//                            isInput = false;
//                        }
//                    }
//                }
//
//                int outIndex = mDecoder.dequeueOutputBuffer(info, 10000);
//                switch (outIndex) {
//                    case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
//                        Log.d(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
//                        mDecoder.getOutputBuffers();
//                        break;
//
//                    case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
//                        Log.d(TAG, "INFO_OUTPUT_FORMAT_CHANGED format : " + mDecoder.getOutputFormat());
//                        break;
//
//                    case MediaCodec.INFO_TRY_AGAIN_LATER:
////				Log.d(TAG, "INFO_TRY_AGAIN_LATER");
//                        break;
//
//                    default:
//                        if (!first) {
//                            startWhen = System.currentTimeMillis();
//                            first = true;
//                        }
//                        try {
//                            long sleepTime = (1000000 / 1000) - (System.currentTimeMillis() - startWhen);
//
//                            Log.d(TAG, "info.presentationTimeUs : " + (1000000 / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);
//
//                            if (sleepTime > 0)
//                                Thread.sleep(sleepTime);
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                        mDecoder.releaseOutputBuffer(outIndex, true /* Surface init */);
//                        break;
//                }
//
//                // All decoded frames have been rendered, we can stop playing now
//                if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//                    Log.d(TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
//                    break;
//                }
//            }
//
//            mDecoder.stop();
//            mDecoder.release();
//            mExtractor.release();
//        }
//
//        public void close() {
//            eosReceived = true;
//        }
//    }
//
//    public static ByteBuffer str_to_bb(String msg, Charset charset){
//        return ByteBuffer.wrap(msg.getBytes(charset));
//    }
}
