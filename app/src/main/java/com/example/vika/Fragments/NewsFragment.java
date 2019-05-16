package com.example.vika.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vika.Activities.NewsItemActivity;
import com.example.vika.Classes.Constants;
import com.example.vika.Classes.MyWebViewClient;
import com.example.vika.Classes.Post;
import com.example.vika.Networks.NetworkUtil;
import com.example.vika.R;

import java.util.List;

import io.paperdb.Paper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class NewsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = Constants.NEWSFRAGMENTTAG;
    private String mParam1;
    private String mParam2;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    CompositeSubscription subscription;
    WebView mWebView;
    private OnFragmentInteractionListener mListener;

    public NewsFragment() {}

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        subscription = new CompositeSubscription();
        Paper.init(getActivity());
        initViews();
        getData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initViews(){
        mWebView = getView().findViewById(R.id.webView);
        recyclerView = getView().findViewById(R.id.news_list);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void openWebView(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadUrl(Constants.AUES_NEWS);
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

        private List<Post> news_list;
        private Context context;

        RecyclerViewAdapter(List<Post> news_list, Context context){
            this.news_list = news_list;
            this.context = context;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView logo;
            LinearLayout view;
            TextView title;
            TextView date;
            TextView desc;

            ViewHolder(View itemView){
                super(itemView);
                view = itemView.findViewById(R.id.news_view);
                logo = itemView.findViewById(R.id.news_logo);
                title = itemView.findViewById(R.id.news_text);
                date = itemView.findViewById(R.id.news_date);
                desc = itemView.findViewById(R.id.news_desc);
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_recyclerview_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.title.setText(news_list.get(position).getTitle());
            holder.desc.setText(news_list.get(position).getDesc());
            holder.date.setText(news_list.get(position).getDate());
            Glide.with(context).load(news_list.get(position).getAvatar()).into(holder.logo);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = news_list.get(position);
                    Paper.book().write(Constants.NEWSITEM, post);
                    Intent intent = new Intent(getActivity(), NewsItemActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {return news_list.size();}
    }

    public void getData(){
        subscription.add(NetworkUtil.getRetrofitPost()
                .getAllPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handlePostResponse, this::handlePostError));
    }
    public void handlePostResponse(List<Post> posts_list){setRecyclerView(posts_list);}
    public void handlePostError(Throwable throwable){
        Toast.makeText(getContext(), "Ooops", Toast.LENGTH_SHORT).show();
        openWebView();
        recyclerView.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);

    }

    public void setRecyclerView(List<Post> postList){
        adapter = new RecyclerViewAdapter(postList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}