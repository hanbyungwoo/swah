package com.cctv.swah.iot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HiddenActivity extends AppCompatActivity {


    ImageView activity_hidden_backBtn;
    EditText editText1;
    EditText editText2;
    EditText editText3, editText4, editText5;
    TextView text1, text2;


    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);

        pref = getSharedPreferences("pref", MODE_PRIVATE);

        setView();
        setListener();

    }
    void setView() {
        activity_hidden_backBtn = (ImageView)findViewById(R.id.activity_hidden_backBtn);
        editText1 = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);

        Log.e("TAG_cctv", InfoManager.text1);
        Log.e("TAG_url", InfoManager.url);
        editText1.setText(InfoManager.text1);
        editText2.setText(InfoManager.url);
        editText3.setText(InfoManager.text2);
        editText4.setText(InfoManager.text3);
        editText5.setText(InfoManager.text4);


    }

    void setListener() {
        activity_hidden_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String url = editText2.getText().toString();
                String text2 = editText3.getText().toString();
                String text3 = editText4.getText().toString();
                String text4 = editText5.getText().toString();

                SharedPreferences.Editor editor = pref.edit();
                Log.e("TTTTTTTTTTTTTTTT", text1);
                editor.clear();
                editor.putString("cctv", text1);
                editor.putString("url", url);
                editor.putString("text2", text2);
                editor.putString("text3", text3);
                editor.putString("text4", text4);
                InfoManager.text1 = text1;
                InfoManager.url = url;
                InfoManager.text2 = text2;
                InfoManager.text3 = text3;
                InfoManager.text4 = text4;
//                InfoManager.url = editText2.getText().toString();

//                editor.clear();
//                editor.putString("cctv", InfoManager.text1);
//                editor.putString("url", InfoManager.url);
                editor.commit();

                finish();
            }
        });
    }

}
