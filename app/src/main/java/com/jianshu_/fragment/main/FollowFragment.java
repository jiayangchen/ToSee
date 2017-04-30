package com.jianshu_.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.jianshu_.R;
import com.jianshu_.adapter.CategoryAdapter;
import com.jianshu_.base.BaseFragment;
import com.jianshu_.base.ToastUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowFragment extends BaseFragment {

    BottomNavigationBar mBbBottom;
    ListView listView;
    CategoryAdapter categoryAdapter;
    Context context;
    String[] taglistname = {"国内","国外","写景","记情","自然风光","社会人文","自然风光","社会人文"};
    String[] HeaderImgs = {"http://o9oomuync.bkt.clouddn.com/eistimg.png",
            "http://o9oomuync.bkt.clouddn.com/eistimg.png",
            "http://o9oomuync.bkt.clouddn.com/eistimg.png",
            "http://o9oomuync.bkt.clouddn.com/eistimg.png",
            "http://o9oomuync.bkt.clouddn.com/eistimg.png"};
    String[] mainImgs = {"http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420224051.png",
            "http://o9oomuync.bkt.clouddn.com/2016-07-16W020150202528792308061.jpg",
            "http://o9oomuync.bkt.clouddn.com/t012c4a5637b9fda886.png",
            "http://o9oomuync.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20151109233438.jpg",
            "http://o9oomuync.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20151109233421.jpg"};


    public static FollowFragment newInstance(String param) {
        FollowFragment fragment = new FollowFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = mRootView.getContext();
        setTagList();

        mBbBottom = (BottomNavigationBar) getActivity().findViewById(R.id.bb_bottom);

        listView = (ListView) mRootView.findViewById(R.id.cate_listview);
        listView.setDividerHeight(0);
        new Thread(getAllArticles).start();
    }

    private void setTagList(){
        LinearLayout linearLayout;
        linearLayout = (LinearLayout) mRootView.findViewById(R.id.cate_catetaglist);
        for(int i=0;i<8;i++)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.cate_taglist,null);
            TextView tag = (TextView) view.findViewById(R.id.cate_tagitem);
            tag.setText(taglistname[i]);
            view.setId(i);
            view.setOnClickListener(this);
            linearLayout.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        Toast.makeText(context,String.valueOf(id),Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> getData(String val) throws JSONException {

        JSONObject jsonObject = new JSONObject(val);
        JSONArray jsonArray = jsonObject.getJSONArray("Article");


        List<Map<String, Object>> list = new ArrayList<>();

        for(int i=0; i<jsonArray.length();i++)
        {
            JSONObject json = jsonArray.getJSONObject(i);
            Map<String, Object> map = new HashMap<>();

            map.put("cate_headerUrl",HeaderImgs[i]);
            map.put("cate_author",json.getJSONObject("ar_author").getString("u_name"));
            map.put("cate_mainImg",mainImgs[i]);
            map.put("cate_text",json.getString("ar_content").substring(0,60));
            map.put("cate_title",json.getString("ar_title"));
            map.put("cate_place",json.getString("ar_place"));

            list.add(map);
        }
        return list;
    }


    Handler article_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("get_all_articles_response");
            Log.d("all",val);

            try {
                categoryAdapter = new CategoryAdapter(getActivity(),getData(val));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listView.setAdapter(categoryAdapter);
            //Toast.makeText(getActivity(), val, Toast.LENGTH_SHORT).show();
        }
    };

    Runnable getAllArticles = new Runnable() {
        @Override
        public void run() {
            // TODO
            String str = null;
            try {
                str = getArticlesRun("http://202.120.40.139:8082/traveller/Article/MostViewed?number=10&offset=0");
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


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_follow;
    }
}
