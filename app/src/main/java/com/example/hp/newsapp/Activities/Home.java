package com.example.hp.newsapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hp.newsapp.Models.Data;
import com.example.hp.newsapp.Models.News;
import com.example.hp.newsapp.Adapters.NewsAdapter;
import com.example.hp.newsapp.Interfaces.NewsApiInterface;
import com.example.hp.newsapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    @BindView(R.id.grid_view) GridView gridView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    final static private String BASE_URL = "https://newsapi.org/v1/";
    private NewsAdapter newsAdapter;
    private ArrayList<News> data;
    private LoadNews loadNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        loadNews = new LoadNews();
        loadNews.starts();

        data = new ArrayList<News>();
        newsAdapter = new NewsAdapter(this, data);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new NewsAdapter(this, data));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Home.this, DetailedView.class);
                intent.putExtra("news", (Serializable) data.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        loadNews.cancel();
    }

    private class LoadNews implements Callback<Data> {
        public Call<Data> call;

        public void starts() {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            NewsApiInterface gerritAPI = retrofit.create(NewsApiInterface.class);

            call = gerritAPI.getNews("techcrunch", "latest", "8724756853d7477fa20d0bbba673cc7e");
            call.enqueue(this);

        }

        @Override
        public void onResponse(Call<Data> call, Response<Data> response) {
            if (response.isSuccessful()) {
                progressBar.setVisibility(View.INVISIBLE);
                Data res = response.body();

                for (News news : res.getArticles()) {
                    Log.e("News", news.getTitle());
                    data.add(news);
                }
                newsAdapter.setData(data);
                newsAdapter.notifyDataSetChanged();
                gridView.invalidateViews();
            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<Data> call, Throwable t) {
            if (call.isCanceled()) {
                Log.e("Retrofit call", "request was cancelled");
            } else {
                Log.e("Retrofit call", "other larger issue, i.e. no network connection?");
            }
            Toast.makeText(Home.this, "Call failure", Toast.LENGTH_SHORT).show();
        }

        public void cancel(){
            call.cancel();
        }
    }
}
