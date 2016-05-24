package com.jianshu_.fragment.main;

import android.os.Bundle;

import com.jianshu_.R;
import com.jianshu_.base.BaseFragment;

/**
 * Created by ${wyk} on 2016/5/23 0023.
 */
public class FollowFragment extends BaseFragment {

    public static FollowFragment newInstance(String param) {
        FollowFragment fragment = new FollowFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_follow;
    }
}
