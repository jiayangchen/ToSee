package com.jianshu_.fragment.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ListView;
import com.jianshu_.R;
import com.jianshu_.adapter.TimelineAdapter;
import com.jianshu_.base.BaseFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFragment extends BaseFragment {

    private ListView listView;
    List<String> data ;
    private TimelineAdapter timelineAdapter;


    public static MessageFragment newInstance(String param) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) mRootView.findViewById(R.id.timelineListView);
        listView.setDividerHeight(0);
        timelineAdapter = new TimelineAdapter(getActivity(), getData());
        listView.setAdapter(timelineAdapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();

        for(int i=0; i<5; i++)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "毛里求斯：不得不流连的天堂原乡");
            switch (i){
                case 0:
                    map.put("status","已提交审核，等待分配评审 By");
                    map.put("reviewer1_name","Andy Black");
                    map.put("reviewer1Header",R.mipmap.editor);
                    map.put("reviewer2_name",null);
                    map.put("reviewer2Header",null);
                    break;
                case 1:
                    map.put("status","评审分配完成 By");
                    map.put("reviewer1_name","Nancy Cristin");
                    map.put("reviewer1Header",R.mipmap.reviewer1);
                    map.put("reviewer2_name","Andrew Kris");
                    map.put("reviewer2Header",R.mipmap.reviewer2);
                    break;
                case 2:
                    map.put("status","初审结果");
                    map.put("reviewer1_name",null);
                    map.put("reviewer1Header",null);
                    map.put("reviewer2_name",null);
                    map.put("reviewer2Header",null);
                    break;
                case 3:
                    map.put("status","终审结果 By");
                    map.put("reviewer1_name","Andy Black");
                    map.put("reviewer1Header",R.mipmap.editor);
                    map.put("reviewer2_name",null);
                    map.put("reviewer2Header",null);
                    break;
                case 4:
                    map.put("status","发表");
                    map.put("reviewer1_name",null);
                    map.put("reviewer1Header",null);
                    map.put("reviewer2_name",null);
                    map.put("reviewer2Header",null);
                    break;
                default:
                    map.put("status","空");
                    map.put("reviewer1_name",null);
                    map.put("reviewer1Header",null);
                    map.put("reviewer2_name",null);
                    map.put("reviewer2Header",null);
                    break;
            }
            list.add(map);
        }

        return list;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_message;
    }
}
