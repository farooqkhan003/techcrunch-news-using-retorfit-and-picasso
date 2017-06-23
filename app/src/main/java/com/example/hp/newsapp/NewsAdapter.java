package com.example.hp.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hp on 6/20/2017.
 */

public class NewsAdapter extends BaseAdapter {
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
            TextView textView = (TextView) grid.findViewById(R.id.grid_title);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(data.get(position).getTitle());
            Picasso.with(context).load(data.get(position).getUrlToImage()).placeholder(R.drawable.news).into(imageView);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    public void setData(ArrayList<News>data){
        this.data = data;
    }
}
