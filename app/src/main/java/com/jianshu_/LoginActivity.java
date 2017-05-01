package com.jianshu_;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jianshu_.application.MyApplication;
import com.jianshu_.base.ToastUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText pwd;
    Button loginBtn;
    Button jumpRegBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        jumpRegBtn = (Button) findViewById(R.id.jumpRegBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
                new Thread(networkTask).start();

            }
        });

        jumpRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();*/
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // TODO
            if(!val.isEmpty()) {
                try {
                    JSONObject json = new JSONObject(val);
                    if(json.has("message")){
                        Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_LONG).show();
                    }else{
                        String user_hash = json.getString("u_hash");
                        //Toast.makeText(LoginActivity.this,user_hash,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user_hash",user_hash);
                        //Toast.makeText(LoginActivity.this,user_hash,Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(LoginActivity.this,"返回结果为空",Toast.LENGTH_SHORT).show();
            }
        }
    };

    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            // TODO
            String str = null;
            try {
                String userName = username.getText().toString();
                String password = pwd.getText().toString();

                if(!userName.isEmpty() && !password.isEmpty()){
                    str = netWorkRun("http://202.120.40.139:8082/traveller/User/Login?u_name="+userName+"&u_password="+password);
                    if(!str.isEmpty()) {
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putString("value", str);
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                }

            } catch (IOException e) {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    OkHttpClient client = new OkHttpClient();
    String netWorkRun(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
