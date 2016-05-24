package com.jianshu_.fragment.main;

import android.os.Bundle;

import com.jianshu_.R;
import com.jianshu_.base.BaseFragment;

/**
 * Created by ${wyk} on 2016/5/23 0023.
 */
public class MessageFragment extends BaseFragment {

    public static MessageFragment newInstance(String param) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_message;
    }
}
