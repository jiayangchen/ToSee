package com.jianshu_;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

public class ReviewResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.review_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        reviewer_header();
    }

    private void reviewer_header(){
        SimpleDraweeView reviewer1_header;
        SimpleDraweeView reviewer2_header;

        TextView reviewer1_name;
        TextView reviewer2_name;
        TextView reedit;

        reviewer1_name = (TextView) findViewById(R.id.reviewAct_reviewer1_name);
        reviewer2_name = (TextView) findViewById(R.id.reviewAct_reviewer2_name);
        reedit = (TextView) findViewById(R.id.reviewAct_reedit);

        reviewer1_name.setText("Nancy Cristin");
        reviewer2_name.setText("Andrew Kris");

        Uri uri1 = Uri.parse("http://o9oomuync.bkt.clouddn.com/eisreviewer1.png");
        Uri uri2 = Uri.parse("http://o9oomuync.bkt.clouddn.com/eisreviewer2.png");
        reviewer1_header = (SimpleDraweeView) findViewById(R.id.result_reviewer1_header);
        reviewer2_header = (SimpleDraweeView) findViewById(R.id.result_reviewer2_header);

        reviewer1_header.setImageURI(uri1);
        reviewer2_header.setImageURI(uri2);

        reedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewResultActivity.this,EditorActivity.class));
            }
        });
    }
}
