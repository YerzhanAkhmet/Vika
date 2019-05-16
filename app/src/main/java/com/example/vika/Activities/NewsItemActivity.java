package com.example.vika.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vika.Classes.Constants;
import com.example.vika.Classes.Post;
import com.example.vika.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class NewsItemActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager news_item_VP;
    TextView news_item_title, news_item_body, news_item_date;
    Post newsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);
        initViews();
        Paper.init(NewsItemActivity.this);
        newsItem = Paper.book().read(Constants.NEWSITEM);
        initData();
        List<String> imagesList = new ArrayList<>();
        imagesList.add(newsItem.getAvatar());
        imagesList.add(newsItem.getPhoto());
        imagesList.add(newsItem.getFoto());
        Adapter adapter = new Adapter(NewsItemActivity.this,imagesList);
        news_item_VP.setAdapter(adapter);
        tabLayout.setupWithViewPager(news_item_VP, true);
    }
    public void initViews(){
        tabLayout = findViewById(R.id.tab_layout);
        news_item_VP = findViewById(R.id.news_VP);
        news_item_title = findViewById(R.id.news_item_title);
        news_item_body = findViewById(R.id.news_item_body);
        news_item_date = findViewById(R.id.news_item_date);
    }
    private void initData() {
        news_item_title.setText(newsItem.getTitle());
        news_item_body.setText(newsItem.getDesc());
        news_item_date.setText(newsItem.getDate());
    }

    public class Adapter extends PagerAdapter {
        Context context;
        List<String> images = new ArrayList<>();
        LayoutInflater mLayoutInflater;

        Adapter(Context context, List<String> images) {
            this.context = context;
            this.images = images;
            this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {return images.size();}

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mLayoutInflater.inflate(R.layout.pager_item, container, false);
            ImageView imageView = view.findViewById(R.id.pager_imageView);
            Glide.with(context).load(images.get(position)).into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}

