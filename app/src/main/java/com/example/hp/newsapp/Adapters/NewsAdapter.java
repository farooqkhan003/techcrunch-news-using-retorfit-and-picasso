package com.example.hp.newsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.newsapp.Models.News;
import com.example.hp.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 6/20/2017.
 */

public class NewsAdapter extends BaseAdapter {
    @BindView(R.id.grid_title)
    TextView _textView;
    @BindView(R.id.grid_image)
    ImageView _imageView;

    private Context context;
    private ArrayList<News> data;

    public NewsAdapter(Context context, ArrayList<News> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            grid = new View(context);
            grid = inflater.inflate(R.layout.grid_item, null);
            ButterKnife.bind(this, grid);

            _textView.setText(data.get(position).getTitle());
            Picasso.with(context).load(data.get(position).getUrlToImage()).placeholder(R.drawable.news).into(_imageView);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
    }
}
