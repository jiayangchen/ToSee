package com.jianshu_;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadActivity extends AppCompatActivity {

    private String[] readimgs = {
            "http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420223230.png",
            "http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420223346.png",
            "http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420223249.png",
            "http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420223319.png",
            "http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420222428.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        try {
            setScrollImgs();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setScrollImgs() throws JSONException {

        Intent intent = getIntent();
        String jsonStr = intent.getStringExtra("ar_json");
        JSONObject json = new JSONObject(jsonStr);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.read_imgs);
        for(int i=0;i<5;i++)
        {
            final View view = LayoutInflater.from(this).inflate(R.layout.read_imgs_item,null);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.read_img);
            simpleDraweeView.setImageURI(Uri.parse(readimgs[i]));

            linearLayout.addView(view);
        }

        SimpleDraweeView header = (SimpleDraweeView) findViewById(R.id.readHeader);
        header.setImageURI(Uri.parse("http://o9oomuync.bkt.clouddn.com/eisreviewer1.png"));
        TextView read_author = (TextView) findViewById(R.id.read_author);
        TextView read_publish_time = (TextView) findViewById(R.id.read_publish_time);
        TextView read_title = (TextView) findViewById(R.id.read_title);
        TextView read_content = (TextView) findViewById(R.id.read_content);
        ImageButton tag = (ImageButton) findViewById(R.id.read_tag);

        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(ReadActivity.this);
                new AlertDialog.Builder(ReadActivity.this).setTitle("添加标签，空格隔开").setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tag = editText.getText().toString();
                                Toast.makeText(getApplicationContext(),tag, Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        read_author.setText(json.getJSONObject("ar_author").getString("u_name"));
        read_publish_time.setText(json.getJSONObject("ar_time_list").getString("ar_time"));
        read_title.setText(json.getString("ar_title"));
        read_content.setText(json.getString("ar_content"));
    }
}
