package com.jianshu_.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jianshu_.R;
import com.jianshu_.ReadActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CategoryAdapter extends BaseAdapter {

    private boolean isPressed = false;
    private Context context;
    private List<Map<String, Object>> list;
    private LayoutInflater inflater;
    private int p = 0;

    public CategoryAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(convertView == null){
            inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.category_item, null);
            viewHolder = new ViewHolder();

            viewHolder.cardView = (CardView) convertView.findViewById(R.id.cate_cardview);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p = position;
                    new Thread(getAllArticles).start();
                }
            });
            viewHolder.Cateheader = (SimpleDraweeView) convertView.findViewById(R.id.cateHeader);
            viewHolder.CateAuthor = (TextView) convertView.findViewById(R.id.cateAuthor);
            viewHolder.CateMainImg = (SimpleDraweeView) convertView.findViewById(R.id.cateMainImg);
            viewHolder.CateTitle = (TextView) convertView.findViewById(R.id.cateTitle);
            viewHolder.CateText = (TextView) convertView.findViewById(R.id.cateText);
            viewHolder.Catelike = (ImageButton) convertView.findViewById(R.id.cate_like);
            viewHolder.CatePlace = (TextView) convertView.findViewById(R.id.cate_place);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ImageButton imageButton = viewHolder.Catelike;

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(isPressed == false){
                        imageButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.likepress));
                        isPressed = true;
                    }
                    else {
                        imageButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.like));
                        isPressed = false;
                    }
                    //重新设置按下时的背景图片

                }else if(event.getAction() == MotionEvent.ACTION_UP){ //松开
                    //再修改为抬起时的正常图片
                    if(isPressed == false){
                        imageButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.like));
                    }
                    else{
                        imageButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.likepress));
                    }

                }
                return false;
            }
        });

        String headerUrl = list.get(position).get("cate_headerUrl").toString();
        String author = list.get(position).get("cate_author").toString();
        String mainImgUrl = list.get(position).get("cate_mainImg").toString();
        String title = list.get(position).get("cate_title").toString();
        String text = list.get(position).get("cate_text").toString();

        String place = list.get(position).get("cate_place").toString();

        viewHolder.Cateheader.setImageURI(Uri.parse(headerUrl));
        viewHolder.CateMainImg.setImageURI(Uri.parse(mainImgUrl));
        viewHolder.CateTitle.setText(title);
        viewHolder.CateText.setText(text);
        viewHolder.CateAuthor.setText(author);

        if(place.length()>2){
            viewHolder.CatePlace.setText(place.substring(0,2)+"..");
        }else{
            viewHolder.CatePlace.setText(place);
        }

        return convertView;
    }

    Handler article_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("get_all_articles_response");
            Log.d("all",val);

            try {
                JSONObject jsonObject = new JSONObject(val);
                JSONArray jsonArray = jsonObject.getJSONArray("Article");
                JSONObject json = jsonArray.getJSONObject(p);

                Log.d("json",json.toString());

                Intent intent = new Intent(context, ReadActivity.class);
                intent.putExtra("ar_json",json.toString());
                intent.putExtra("ar_id",json.getString("id"));
                context.startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable getAllArticles = new Runnable() {
        @Override
        public void run() {
            // TODO
            String str = null;
            try {
                str = getArticlesRun("http://202.120.40.139:8082/traveller/Article");
                if(!str.isEmpty()) {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("get_all_articles_response", str);
                    msg.setData(data);
                    article_handler.sendMessage(msg);
                }
            } catch (IOException e) {
                //Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    OkHttpClient client1 = new OkHttpClient();
    String getArticlesRun(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("User-Hash","1185566514")
                .addHeader("Username","liangdong")
                .url(url)
                .build();
        Response response = client1.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    static class ViewHolder{
        CardView cardView;
        SimpleDraweeView Cateheader;
        TextView CateAuthor;
        SimpleDraweeView CateMainImg;
        TextView CateTitle;
        TextView CateText;
        ImageButton Catelike;
        TextView CatePlace;
    }

}
