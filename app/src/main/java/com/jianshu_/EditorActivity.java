package com.jianshu_;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import io.github.mthli.knife.KnifeText;

public class EditorActivity extends AppCompatActivity {

    private EditText editText;
    private KnifeText knife;
    private String user_hash;
    private JSONArray tagArray = new JSONArray();
    String tag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Intent intent = getIntent();
        user_hash = intent.getStringExtra("user_hash");

        editText = (EditText) findViewById(R.id.writeTitle);
        knife = (KnifeText) findViewById(R.id.knife);
        // ImageGetter coming soon...

        setupBold();
        setupUndo();
        setupRedo();
        setupImage();
        setupItalic();
        setupUnderline();
        setupStrikethrough();
        setupBullet();
        setupQuote();
        setupLink();
        setupClear();

        publish();
    }

    private void publish(){
        TextView publish = (TextView) findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final EditText tagEtx = new EditText(EditorActivity.this);
            new AlertDialog.Builder(EditorActivity.this).setTitle("添加分类标签").setView(tagEtx)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tag = tagEtx.getText().toString();
                            if(tag.equals("国内") ||
                                    tag.equals("国外")||
                                    tag.equals("写景")||
                                    tag.equals("抒情")||
                                    tag.equals("自然风光")||
                                    tag.equals("社会人文")){
                                new Thread(uploadArticle).start();
                            }else{
                                Toast.makeText(EditorActivity.this, "分类标签不正确", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("editor_log", "请求结果为-->" + val);
            Toast.makeText(EditorActivity.this,val,Toast.LENGTH_SHORT).show();
        }
    };

    Runnable uploadArticle = new Runnable() {
        @Override
        public void run() {
            try {
                String title = editText.getText().toString();
                String content = knife.getEditableText().toString();

                JSONObject json = new JSONObject();

                    json.put("ar_title", title);
                    json.put("ar_place", "首尔");
                    json.put("ar_category", tag);
                    json.put("ar_content", content);

                    String[] mainimgs = {
                            "http://o9oomuync.bkt.clouddn.com/716760859261034685.jpg",
                            "http://o9oomuync.bkt.clouddn.com/2016-07-16W020150202528792308061.jpg",
                            "http://o9oomuync.bkt.clouddn.com/t012c4a5637b9fda886.png",
                            "http://o9oomuync.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20151109233438.jpg",
                            "http://o9oomuync.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20151109233421.jpg"
                    };

                    JSONArray array = new JSONArray();
                    for(int i=0;i<mainimgs.length;i++){
                        JSONObject imgjson = new JSONObject();
                        imgjson.put("ar_url",mainimgs[i]);
                        array.put(imgjson);
                    }

                    json.put("ar_url_list", array);

                    Log.d("ARTICLE",json.toString());

                    if(!user_hash.isEmpty()){

                        String reg_url = "http://202.120.40.139:8082/traveller/Article";
                        String reg_res = post(user_hash,reg_url,json.toString());
                        Log.d("reg_res",reg_res);
                        if (!reg_res.isEmpty()) {
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putString("value", reg_res);
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }else{
                            Toast.makeText(EditorActivity.this,"调用返回结果为空",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(EditorActivity.this,"user_hash 为空",Toast.LENGTH_SHORT).show();
                    }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String user_hash,String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("User-Hash","1185566514")
                .addHeader("Username","liangdong")
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void setupBold() {
        ImageButton bold = (ImageButton) findViewById(R.id.bold);

        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.bold(!knife.contains(KnifeText.FORMAT_BOLD));
            }
        });

        bold.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_bold, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupUndo(){
        ImageButton undo = (ImageButton) findViewById(R.id.undo);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.undo();
            }
        });

        undo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, "Undo", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupRedo(){
        ImageButton redo = (ImageButton) findViewById(R.id.redo);

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.redo();
            }
        });

        redo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, "Redo", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupImage(){
        ImageButton img = (ImageButton) findViewById(R.id.edit_image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorActivity.start(EditorActivity.this, 9, 1, true,true,false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            for(String s : images){
                Log.d("TAG",s);
            }
        }
    }

    private void setupItalic() {
        ImageButton italic = (ImageButton) findViewById(R.id.italic);

        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.italic(!knife.contains(KnifeText.FORMAT_ITALIC));
            }
        });

        italic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_italic, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupUnderline() {
        ImageButton underline = (ImageButton) findViewById(R.id.underline);

        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.underline(!knife.contains(KnifeText.FORMAT_UNDERLINED));
            }
        });

        underline.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_underline, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupStrikethrough() {
        ImageButton strikethrough = (ImageButton) findViewById(R.id.strikethrough);

        strikethrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.strikethrough(!knife.contains(KnifeText.FORMAT_STRIKETHROUGH));
            }
        });

        strikethrough.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_strikethrough, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupBullet() {
        ImageButton bullet = (ImageButton) findViewById(R.id.bullet);

        bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.bullet(!knife.contains(KnifeText.FORMAT_BULLET));
            }
        });


        bullet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_bullet, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupQuote() {
        ImageButton quote = (ImageButton) findViewById(R.id.quote);

        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.quote(!knife.contains(KnifeText.FORMAT_QUOTE));
            }
        });

        quote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_quote, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupLink() {
        ImageButton link = (ImageButton) findViewById(R.id.link);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLinkDialog();
            }
        });

        link.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_insert_link, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupClear() {
        ImageButton clear = (ImageButton) findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knife.clearFormats();
            }
        });

        clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(EditorActivity.this, R.string.toast_format_clear, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void showLinkDialog() {
        final int start = knife.getSelectionStart();
        final int end = knife.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = editText.getText().toString().trim();
                if (TextUtils.isEmpty(link)) {
                    return;
                }

                // When KnifeText lose focus, use this method
                knife.link(link, start, end);
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING HERE
            }
        });

        builder.create().show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                knife.undo();
                break;
            case R.id.redo:
                knife.redo();
                break;
            case R.id.github:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_repo)));
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }*/
}

