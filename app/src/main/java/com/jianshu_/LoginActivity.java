package com.jianshu_;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jianshu_.base.ToastUtil;

import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText pwd;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String password = pwd.getText().toString();

                if(userName.equals("123") && password.equals("123"))
                {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else
                {
                    ToastUtil.show("not equal");
                }
            }
        });
    }
}
