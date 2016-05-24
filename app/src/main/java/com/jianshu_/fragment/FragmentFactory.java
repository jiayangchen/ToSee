package com.jianshu_.fragment;

import android.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import com.jianshu_.fragment.main.CenterFragment;
import com.jianshu_.fragment.main.FindFragment;
import com.jianshu_.fragment.main.FollowFragment;
import com.jianshu_.fragment.main.MessageFragment;
import com.jianshu_.fragment.main.MyFragment;

/**
 * Created by ${wyk} on 2016/5/23 0023.
 */
public class FragmentFactory {

    private static SparseArrayCompat<Fragment> mCaches = new SparseArrayCompat<Fragment>();

    public static Fragment getFragment(int position) {

        Fragment fragment = mCaches.get(position);

        if (fragment == null) {
            return fragment;  // 读取缓存
        }

        switch (position) {
            case 0:
                fragment = new FindFragment();
                break;
            case 1:
                fragment = new FollowFragment();
                break;
            case 2:
                fragment = new CenterFragment();
                break;
            case 3:
                fragment = new MessageFragment();
                break;
            case 4:
                fragment = new MyFragment();
                break;

        }
        // 存储
        mCaches.put(position,fragment);
        return fragment;
    }
}
