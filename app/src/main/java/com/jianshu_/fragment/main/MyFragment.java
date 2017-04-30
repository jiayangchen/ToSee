package com.jianshu_.fragment.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jianshu_.R;
import com.jianshu_.base.BaseFragment;

import q.rorbin.badgeview.QBadgeView;

public class MyFragment extends BaseFragment {

    public static MyFragment newInstance(String param) {
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setpersonHeader();
        setClickListeners();
    }

    private void setpersonHeader(){
        SimpleDraweeView perHeader;
        Uri uri = Uri.parse("http://o9oomuync.bkt.clouddn.com/eistimg.png");
        perHeader = (SimpleDraweeView) mRootView.findViewById(R.id.personHeader);
        perHeader.setImageURI(uri);
        TextView perName;
        perName = (TextView) mRootView.findViewById(R.id.personName);
        perName.setText("陈嘉扬");
    }

    private void setClickListeners(){
        CardView cardView_person;
        CardView cardView_his;
        CardView cardView_timeline;
        CardView cardView_settings;

        cardView_person = (CardView) mRootView.findViewById(R.id.person);
        cardView_his = (CardView) mRootView.findViewById(R.id.item1);
        cardView_timeline = (CardView) mRootView.findViewById(R.id.item2);
        cardView_settings = (CardView) mRootView.findViewById(R.id.item3);

        cardView_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_my;
    }
}
