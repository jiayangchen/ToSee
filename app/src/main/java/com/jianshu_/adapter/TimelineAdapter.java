package com.jianshu_.adapter;

import java.util.List;
import java.util.Map;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.jianshu_.R;
import com.jianshu_.ReviewResultActivity;
import com.jianshu_.base.ToastUtil;
import com.jianshu_.fragment.main.ReviewFragment;

public class TimelineAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> list;
    private LayoutInflater inflater;

    public TimelineAdapter(Context context, List<Map<String, Object>> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.timeline_item, null);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.status = (TextView) convertView.findViewById(R.id.zhuye_article_status);
            viewHolder.reviewer1_name = (TextView) convertView.findViewById(R.id.review1_name);
            viewHolder.reviewer1Header = (BootstrapCircleThumbnail) convertView.findViewById(R.id.reviewer1);
            viewHolder.reviewer2_name = (TextView) convertView.findViewById(R.id.review2_name);
            viewHolder.reviewer2Header = (BootstrapCircleThumbnail) convertView.findViewById(R.id.reviewer2);
            viewHolder.reviewers = (RelativeLayout) convertView.findViewById(R.id.reviewers);
            viewHolder.show_time = (TextView) convertView.findViewById(R.id.show_time);
            viewHolder.datetime = (TextView) convertView.findViewById(R.id.timeline_datetime);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String titleStr = list.get(position).get("title").toString();
        String statusStr = list.get(position).get("status").toString();
        Object reviewer1_name = list.get(position).get("reviewer1_name");
        Object reviewer2_name = list.get(position).get("reviewer2_name");
        Object reviewer1Header = list.get(position).get("reviewer1Header");
        Object reviewer2Header = list.get(position).get("reviewer2Header");

        viewHolder.title.setText(titleStr);
        viewHolder.status.setText(statusStr);

        final Context ctx = convertView.getContext();
        switch (position){
            case 0:
                viewHolder.datetime.setText("10:45");
                viewHolder.show_time.setText("2017年03月20日");
                break;
            case 1:
                viewHolder.status.setTextColor(convertView.getResources().getColor(R.color.timeline1));
                viewHolder.datetime.setText("13:24");
                viewHolder.show_time.setText("2017年03月21日");
                break;
            case 2:
                viewHolder.status.setTextColor(convertView.getResources().getColor(R.color.timeline2));
                viewHolder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ReviewResultActivity.class));
                    }
                });
                viewHolder.datetime.setText("18:05");
                viewHolder.show_time.setText("2017年03月22日");
                break;
            case 3:
                viewHolder.status.setTextColor(convertView.getResources().getColor(R.color.timeline2));
                viewHolder.datetime.setText("20:36");
                viewHolder.show_time.setText("2017年03月24日");
                break;
            case 4:
                viewHolder.datetime.setText("08:15");
                viewHolder.show_time.setText("2017年03月25日");
                break;
            default:
                break;
        }


        if(reviewer1_name == null){
            viewHolder.reviewers.setVisibility(View.GONE);
            //viewHolder.reviewer1_name.setVisibility(View.GONE);
        }else{
            viewHolder.reviewer1_name.setText(reviewer1_name.toString());
        }
        if(reviewer2_name == null){
            //viewHolder.reviewers.setVisibility(View.GONE);
            viewHolder.reviewer2_name.setVisibility(View.GONE);
        }else{
            viewHolder.reviewer2_name.setText(reviewer2_name.toString());
        }

        if(reviewer1Header == null){
            viewHolder.reviewers.setVisibility(View.GONE);
            //viewHolder.reviewer1Header.setVisibility(View.GONE);
        }else{
            viewHolder.reviewer1Header.setImageResource((Integer) reviewer1Header);
        }
        if(reviewer2Header == null){
            //viewHolder.reviewers.setVisibility(View.GONE);
            viewHolder.reviewer2Header.setVisibility(View.GONE);
        }else{
            viewHolder.reviewer2Header.setImageResource((Integer) reviewer2Header);
        }

        return convertView;
    }

    static class ViewHolder {
        public BootstrapCircleThumbnail reviewer1Header;
        public BootstrapCircleThumbnail reviewer2Header;
        public TextView reviewer1_name;
        public TextView reviewer2_name;
        public TextView status;
        public TextView title;
        public RelativeLayout reviewers;
        public TextView datetime;
        public TextView show_time;
    }
}
