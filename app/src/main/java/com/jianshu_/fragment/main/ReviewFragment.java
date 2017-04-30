package com.jianshu_.fragment.main;

import android.os.Bundle;
import com.jianshu_.R;
import com.jianshu_.base.BaseFragment;

public class ReviewFragment extends BaseFragment{

    public static ReviewFragment newInstance(String param) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_review;
    }
}
