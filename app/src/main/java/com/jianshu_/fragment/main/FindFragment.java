package com.jianshu_.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jianshu_.MainActivity;
import com.jianshu_.R;
import com.jianshu_.ReadActivity;
import com.jianshu_.base.BaseFragment;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import q.rorbin.badgeview.QBadgeView;

public class FindFragment extends BaseFragment {

    Context context;

    private String[] mainimgs = {
            "http://o9oomuync.bkt.clouddn.com/findTIM%E6%88%AA%E5%9B%BE20170420222428.png",
            "http://o9oomuync.bkt.clouddn.com/2016-07-16W020150202528792308061.jpg",
            "http://o9oomuync.bkt.clouddn.com/t012c4a5637b9fda886.png",
            "http://o9oomuync.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20151109233438.jpg",
            "http://o9oomuync.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20151109233421.jpg"
    };

    private String[] headerimgs = {
            "http://o9oomuync.bkt.clouddn.com/eisreviewer1.png",
            "http://o9oomuync.bkt.clouddn.com/eisreviewer2.png",
            "http://o9oomuync.bkt.clouddn.com/eisreviewer1.png",
            "http://o9oomuync.bkt.clouddn.com/eistimg.png",
            "http://o9oomuync.bkt.clouddn.com/eisreviewer2.png"
    };

    public static FindFragment newInstance(String param) {
        FindFragment fragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = mRootView.getContext();
        setRollViewPage();
        setZhuyeHeader();
        setHotColumn();
        setHotArticle();

    }


    private void setRollViewPage(){
        RollPagerView mRollViewPager;
        mRollViewPager = (RollPagerView) getActivity().findViewById(R.id.roll_view_pager);
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));
    }

    private void setZhuyeHeader(){
        SimpleDraweeView zhuyeHeader;
        Uri uri = Uri.parse("http://o9oomuync.bkt.clouddn.com/eistimg.png");
        zhuyeHeader = (SimpleDraweeView) mRootView.findViewById(R.id.zhuyeHeaderImg);
        zhuyeHeader.setImageURI(uri);
        new QBadgeView(mRootView.getContext()).bindTarget(zhuyeHeader).setBadgeNumber(2).setGravityOffset(-2.5f,-2.5f,true);
    }

    private void setHotColumn(){
        int[] hotimgs = {R.mipmap.hot1,R.mipmap.hot2,R.mipmap.hot3,R.mipmap.hot4,R.mipmap.hot5};
        final String[] columnName = {"七月出海","梦里花落","景·记专栏","穷游圈","驴友天下"};
        LinearLayout linearLayout;
        linearLayout = (LinearLayout) mRootView.findViewById(R.id.zhuye_hotcolumn);
        for(int i=0;i<5;i++)
        {
            final View view = LayoutInflater.from(mRootView.getContext()).inflate(R.layout.hotcolumn,null);
            TextView hotcolumn = (TextView) view.findViewById(R.id.hotcolumnName);
            CardView cardView = (CardView) view.findViewById(R.id.hotcolumnbg);
            cardView.setBackgroundResource(hotimgs[i]);
            hotcolumn.setText(columnName[i]);
            view.setId(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,String.valueOf(view.getId()),Toast.LENGTH_SHORT).show();
                }
            });
            linearLayout.addView(view);
        }

    }

    private void setHotArticle(){
        new Thread(getHotArticles).start();
    }

    Handler article_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("get_all_articles_response");
            Log.d("hot",val);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(val);
                JSONArray jsonArray = jsonObject.getJSONArray("Article");

                LinearLayout linearLayout;
                linearLayout = (LinearLayout) mRootView.findViewById(R.id.zhuye_hotarticle);
                for(int i=0;i<jsonArray.length();i++)
                {
                    final JSONObject json = jsonArray.getJSONObject(i);

                    final View view = LayoutInflater.from(mRootView.getContext()).inflate(R.layout.hotarticle,null);

                    CardView cardView = (CardView) view.findViewById(R.id.hotarticle);
                    SimpleDraweeView hotArticleHeader = (SimpleDraweeView) view.findViewById(R.id.cateHeader);
                    TextView hotArticleName = (TextView) view.findViewById(R.id.cateAuthor);
                    SimpleDraweeView hotArticleMain = (SimpleDraweeView) view.findViewById(R.id.cateMainImg);
                    TextView hotArticleTitle = (TextView) view.findViewById(R.id.cateTitle);
                    TextView hotArticleText = (TextView) view.findViewById(R.id.cateText);
                    TextView hotArticlePlace = (TextView) view.findViewById(R.id.cate_place);

                    view.setId(i);
                    hotArticleHeader.setImageURI(Uri.parse(headerimgs[i]));
                    hotArticleMain.setImageURI(Uri.parse(mainimgs[i]));
                    hotArticleName.setText(json.getJSONObject("ar_author").getString("u_name"));
                    hotArticleTitle.setText(json.getString("ar_title"));
                    hotArticleText.setText(json.getString("ar_content").substring(0,30)+"... ...");
                    if(json.getString("ar_place").length()>2){
                        hotArticlePlace.setText(json.getString("ar_place").substring(0,2)+"..");
                    }else{
                        hotArticlePlace.setText(json.getString("ar_place"));
                    }


                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(context,String.valueOf(view.getId()),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ReadActivity.class);
                            intent.putExtra("ar_json",json.toString());
                            startActivity(intent);
                        }
                    });

                    linearLayout.addView(view);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Toast.makeText(getActivity(), val, Toast.LENGTH_SHORT).show();
        }
    };

    Runnable getHotArticles = new Runnable() {
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

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_find;
    }

    //图片轮播
    private class TestLoopAdapter extends LoopPagerAdapter {
        private String[] imgs = {
                "http://o9oomuync.bkt.clouddn.com/hot1.png",
                "http://o9oomuync.bkt.clouddn.com/hot2.png",
                "http://o9oomuync.bkt.clouddn.com/hot3.png",
                "http://o9oomuync.bkt.clouddn.com/hot4.png",
                "http://o9oomuync.bkt.clouddn.com/hot5.png"
        };

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            //view.setImageResource(imgs[position]);
            Glide.with(mRootView.getContext()).load(imgs[position]).into(view);

            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }

    /*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }*/
}
