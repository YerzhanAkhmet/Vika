package com.example.vika.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vika.Classes.Constants;
import com.example.vika.Classes.Users;
import com.example.vika.Networks.NetworkUtil;
import com.example.vika.R;

import java.util.Objects;

import io.paperdb.Paper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UserFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = Constants.USERFRAGMENTTAG;
    private String mParam1;
    private String mParam2;
    ImageView ava;
    TextView name, status, city, university, quote, city_TV, uni_TV, quote_TV;
    Users mUser;
    private OnFragmentInteractionListener mListener;
    CompositeSubscription subscription;

    public UserFragment() {}

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Paper.init(getActivity());
        subscription = new CompositeSubscription();
        initViews();
        setLanguage();
        mUser = Paper.book().read(Constants.USERDATA);
        if (mUser.getName().equals("Vika")){
            getData();
        } else {
            Glide.with(getContext()).load(mUser.getAvatar()).into(ava);
            name.setText(mUser.getName());
            status.setText(mUser.getStatus());
            city.setText(mUser.getCity());
            university.setText(mUser.getUniversity());
            quote.setText(mUser.getQuote());
        }
    }

    @Override
    public void onAttach(Context context) {super.onAttach(context);}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getData(){
        subscription.add(NetworkUtil.getRetrofitPost()
                .getUserByID(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleUsersResponse, this::handleUsersError));
    }
    public void handleUsersResponse(Users user){
        name.setText(user.getName());
        status.setText(user.getStatus());
        quote.setText(user.getQuote());
        Glide.with(getContext()).load(user.getAvatar()).into(ava);
    }
    public void handleUsersError(Throwable throwable){}

    public void initViews(){
        name = getView().findViewById(R.id.name_TV);
        ava = getView().findViewById(R.id.ava_IV);
        status = getView().findViewById(R.id.desc_TV);
        city = getView().findViewById(R.id.city_TV);
        university = getView().findViewById(R.id.university_TV);
        quote = getView().findViewById(R.id.quote_TV);
        city_TV = getView().findViewById(R.id.userCity_TV);
        uni_TV = getView().findViewById(R.id.userUniversity_TV);
        quote_TV = getView().findViewById(R.id.userQuote_TV);
    }
    public void setLanguage(){
        if(Paper.book().contains(Constants.SET_LANGUAGE)){
            if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.kz))){
                city_TV.setText("Қала");
                uni_TV.setText("Университет");
                quote_TV.setText("Дәйексөз");
            } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.ru))){
                city_TV.setText("Город");
                uni_TV.setText("Университет");
                quote_TV.setText("Цитата");
            } else {
                city_TV.setText("City");
                uni_TV.setText("University");
                quote_TV.setText("Quote");
            }
        }
    }
}
