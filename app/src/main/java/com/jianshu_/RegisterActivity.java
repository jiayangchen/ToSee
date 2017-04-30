package com.jianshu_;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.jianshu_.base.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class RegisterActivity extends AppCompatActivity {

    EditText regUserEtx;
    EditText regPassword;
    EditText regEpassword;
    EditText regemail;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUserEtx = (EditText) findViewById(R.id.reg_username);
        regPassword = (EditText) findViewById(R.id.reg_password);
        regEpassword = (EditText) findViewById(R.id.reg_epassword);
        regemail = (EditText) findViewById(R.id.reg_email);
        regBtn = (Button) findViewById(R.id.regBtn);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String r_name = regUserEtx.getText().toString();
                String r_pwd = regPassword.getText().toString();
                String r_epwd = regEpassword.getText().toString();
                String r_email = regemail.getText().toString();

                if(r_pwd.equals(r_epwd)){
                    JSONObject json = new JSONObject();

                    try {

                        json.put("u_id",0);
                        json.put("u_name",r_name);
                        json.put("u_mail",r_email);
                        json.put("u_password",r_pwd);
                        json.put("u_role",0);

                        String reg_url = "http://59.78.45.250:8070/User/Reg";
                        String reg_res = post(reg_url,json.toString());

                        ToastUtil.show(reg_res);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    ToastUtil.show("两次密码不一致");
                }
            }
        });
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
