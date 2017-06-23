package com.example.hp.newsapp.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.newsapp.Models.News;
import com.example.hp.newsapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedView extends AppCompatActivity {
    private Toolbar toolbar;
    @BindView(R.id.detail_view_title) TextView title;
    @BindView(R.id.detail_view_description) TextView description;
    @BindView(R.id.detail_view_iamgeview) ImageView imageview;
    @BindView(R.id.detail_view_source) TextView author;
    @BindView(R.id.detail_view_date) TextView date;
    @BindView(R.id.read_complete) Button readComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        ButterKnife.bind(this);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        News news = (News) i.getSerializableExtra("news");

        title.setText(news.getTitle());
        description.setText(news.getDescription());
        author.setText(news.getAuthor());
        date.setText(news.getPublishedAt());
        Picasso.with(this).load(news.getUrlToImage()).placeholder(R.drawable.news).into(imageview);

        final String url = news.getUrl();
        readComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedView.this, CompleteArticle.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
