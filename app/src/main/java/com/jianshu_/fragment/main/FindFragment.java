package com.jianshu_.fragment.main;

import android.os.Bundle;

import com.jianshu_.R;
import com.jianshu_.base.BaseFragment;

/**
 * Created by ${wyk} on 2016/5/23 0023.
 */
public class FindFragment extends BaseFragment {

    public static FindFragment newInstance(String param) {
        FindFragment fragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_find;
    }
}
