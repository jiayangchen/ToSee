package com.jianshu_.fragment.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jianshu_.R;
import com.jianshu_.base.BaseFragment;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class FindFragment extends BaseFragment {

    private RollPagerView mRollViewPager;
    //下拉刷新控件
    private SwipeRefreshLayout swipeRefreshLayout;

    String[] from={"name","id"};              //这里是ListView显示内容每一列的列名
    int[] to={R.id.zhuye_headerImg,R.id.zhuye_content};   //这里是ListView显示每一列对应的list_item中控件的id
    String[] testText = {"【连载】体量感——产品思维与设计思维（18）",
                        "火箭到底有多复杂，为什么某些落后国家也会做？",
            "不锈钢盘上的家的日常","招募 | 一日一绘之21天手绘挑战营","吐槽内容有点多，但并不水 | 我的微博精华内容汇总"};

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
        mRollViewPager = (RollPagerView) getActivity().findViewById(R.id.roll_view_pager);
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));

        List<HashMap<String,String>> aList = new ArrayList<>();
        for(int i=0; i<5; i++)
        {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("name", "Jack Martin");
            hm.put("id",testText[i]);
            aList.add(hm);
        }


        ListView list = (ListView)mRootView.findViewById(R.id.zhuye_listview);
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.zhuye_article, from, to);
        list.setAdapter(adapter);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_find;
    }

    //图片轮播
    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.mipmap.zhuye1,
                R.mipmap.zhuye2,
                R.mipmap.zhuye3,
                R.mipmap.zhuye4,
        };

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }
}
