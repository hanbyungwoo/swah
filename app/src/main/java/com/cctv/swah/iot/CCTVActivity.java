package com.cctv.swah.iot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.cctv.swah.iot.Model.DeviceInfo;
import com.cctv.swah.iot.Network.VideoDecoderThread;


public class CCTVActivity extends AppCompatActivity {//implements SurfaceHolder.Callback {

    VideoDecoderThread mVideoDecoder;

    //    ImageView image_cctv;
    Surface image_cctv;

    ImageView activity_cctv_backBtn;
    DeviceInfo deviceInfo;

    EditText editText;
    VideoView v;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_activity);


        //String uri = "rtsp://192.168.42.51:8555/unicast";
        v = (VideoView) findViewById(R.id.videoView);
        button = (Button) findViewById(R.id.button1);
        editText = (EditText) findViewById(R.id.editText);

        setListener();


//        MediaConturi.parseroller nc = new MediaController(this);
//        nc.setAnchorView(video);
//
////        nc.setMediaPlayer(video);
////        video.setVideoPath(R.raw.test);
////        video.start();
//        String uri = "archive.org/download/testmp3testfile/mpthreetest.mp3";//android.resource://" + getPackageName() + "/" + R.raw.test;
//        video.setMediaController(nc);
//        video.setVideoURI(Uri.parse(uri));
//        video.requestFocus();
//        video.start();


//        videoView.setVideoPath(R.raw.test);
        // videoView.setVideoURL(url);
        //
//        final MediaController mediaController =
//                new MediaController(this);
//        videoView.setMediaController(mediaController);
    }

    void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.putExtra("TEST", text);
                startActivity(intent);
            }
        });
    }


//        setCall();
//        setListener();
//        setNetwork();
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        Log.e("TAAAAAAAAAAAAAAAA", "++++");
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
//        Log.e("TAAAAAAAAAAAAAAAA", "----___________________");
////        if (mVideoDecoder != null) {
//            Log.e("TAG", "여기시작!!!!!!!!!!!!!!!!!!!!!!!");
//            ReceiveCCTV task = new ReceiveCCTV(CCTVActivity.this, holder, deviceInfo.getName(), deviceInfo.getPw(), deviceInfo.getModel(), image_cctv);
//            task.execute();
////            if (mVideoDecoder.init(holder.getSurface(), "?????????????????")) {
////                mVideoDecoder.start();
////
////            } else {
////                mVideoDecoder = null;
////            }
//
////        }
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.e("TAAAAAAAAAAAAAAAA", "---SDAASdasdasd");
////        if (mVideoDecoder != null) {
//
////        }
//        try {
//            Log.e("TT", Info.data+"");
//            fileSave();
//
////            FileOutputStream outstream = openFileOutput("test.h264", Activity.MODE_WORLD_WRITEABLE);
////
////            outstream.write(Info.data.getBytes());
////            Log.e("TTTTTTTTTTTTTTTTTT", "저장됨");
//        } catch (Exception e) {
//            Log.e("TTTTTTTTTT", "쥐쥐;;;;");
//        }
//
////        mVideoDecoder.close();
//
//    }
//
//    public void fileSave() {
//        String data = Info.data;
//
//        try {
//            FileOutputStream fos = openFileOutput
//                    ("myfile.h264", // 파일명 지정
//                            Context.MODE_APPEND);// 저장모드
//            PrintWriter out = new PrintWriter(fos);
//            out.println(data);
//            out.close();
//
//            Log.e("!!!!!!!!!!!!!!!!!!!","파일 저장 완료");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("!!!!!!!!!!!!!!!!!!!","파일 저장 실패");
//        }
//
//        try {
//            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
//            StringBuffer data2 = new StringBuffer();
//            FileInputStream fis = openFileInput("myfile.h264");//파일명
//            BufferedReader buffer = new BufferedReader
//                    (new InputStreamReader(fis));
//            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
//            while (str != null) {
//                data2.append(str + "\n");
//                str = buffer.readLine();
//            }
//            Log.e("???????????????", data2+"");
//            buffer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////
////        String state= Environment.getExternalStorageState(); //외부저장소(SDcard)의 상태 얻어오기
////
////        File path;    //저장 데이터가 존재하는 디렉토리경로
////        File file;     //파일명까지 포함한 경로
////
////        if(!state.equals(Environment.MEDIA_MOUNTED)){ // SDcard 의 상태가 쓰기 가능한 상태로 마운트되었는지 확인
////            Toast.makeText(this, "SDcard Not Mounted", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        String data= Info.data;  //EditText의 Text 얻어오기
////
////        //SDcard에 데이터 종류(Type)에 따라 자동으로 분류된 저장 폴더 경로선택
////        //Environment.DIRECTORY_DOWNLOADS : 사용자에의해 다운로드가 된 파일들이 놓여지는 표준 저장소
////        path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
////
////        file= new File(path, "test.h264"); //파일명까지 포함함 경로의 File 객체 생성
////
////        try {
////            //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)
////            FileWriter wr = new FileWriter(file, true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.
////
////            PrintWriter writer = new PrintWriter(wr);
////            writer.println(data);
////            writer.close();
////        } catch(Exception e) {
////            e.printStackTrace();
////        }
////
////            String path = "/sdcard/Test"; // 파일이 저장될 경로
////        String fileName = "android.h264"; // 파일 이름
////
////
////        File file = new File(path + fileName);
////
////        String text = Info.data; // 저장될 내용
////
////        FileOutputStream fos = null;
////
////        try {
////
////            fos = new FileOutputStream(file); // 파일 생성
////
////            fos.write((text).getBytes()); // 파일에 내용 저장
////
////            fos.close(); // 파일 닫기
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//
//    void setCall() {
//        deviceInfo = (DeviceInfo) getIntent().getSerializableExtra("DEVICEINFO");
////        image_cctv = (ImageView)findViewById(R.id.image_cctv);
////        image_cctv = (Surface)findViewById(R.id.image_cctv);
//        activity_cctv_backBtn = (ImageView)findViewById(R.id.activity_cctv_backBtn);
//
//        SurfaceView surfaceView = new SurfaceView(this);
//        surfaceView.getHolder().addCallback(this);
//        setContentView(surfaceView);
//
//
//    }
//
//    void setNetwork() {
////        ReceiveCCTV task = new ReceiveCCTV(CCTVActivity.this, CCTVActivity.this, deviceInfo.getName(), deviceInfo.getPw(), deviceInfo.getModel(), image_cctv);
////        task.execute();
//
//    }
//
//    void setListener() {
//        activity_cctv_backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }
}
