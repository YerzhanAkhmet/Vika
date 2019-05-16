package com.example.vika.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.vika.Activities.MainActivity;
import com.example.vika.Classes.Constants;
import com.example.vika.R;

import java.util.Objects;

import io.paperdb.Paper;

public class SettingsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = Constants.SETTINGSFRAGMENTTAG;
    LinearLayout themeButtons, languageButtons;
    private String mParam1;
    private String mParam2;
    Button appThemeButton, blueThemeButton, greenThemeButton, exitThemeButton, appLanguageButton, exitLanguageButton;
    Button kzLanguage, ruLanguage, enLanguage;
    ConstraintLayout settingsLayout;
    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {}


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Paper.init(getContext());
        appThemeButton = getView().findViewById(R.id.appThemeButton);
        blueThemeButton = getView().findViewById(R.id.blueThemeButton);
        greenThemeButton = getView().findViewById(R.id.greenThemeButton);
        themeButtons = getView().findViewById(R.id.themeButtons);
        settingsLayout = getView().findViewById(R.id.settingsLayout);
        exitThemeButton = getView().findViewById(R.id.exitThemeButton);
        appLanguageButton = getView().findViewById(R.id.appLanguage);
        exitLanguageButton = getView().findViewById(R.id.exitLanguageButton);
        languageButtons = getView().findViewById(R.id.languageButtons);
        kzLanguage = getView().findViewById(R.id.kzLanguageButton);
        ruLanguage = getView().findViewById(R.id.ruLanguageButton);
        enLanguage = getView().findViewById(R.id.enLanguageButton);
        setButtonsTexts();
        setButtonsColors();
        appThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeButtons.setVisibility(View.VISIBLE);
                settingsLayout.setVisibility(View.GONE);
            }
        });
        exitThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeButtons.setVisibility(View.GONE);
                settingsLayout.setVisibility(View.VISIBLE);
            }
        });
        greenThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write(Constants.THEME, "1");
                greenThemeButton.setBackground(getResources().getDrawable(R.drawable.button_borders_1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        greenThemeButton.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                    }
                }, 100);
                sendIntent();
            }
        });
        blueThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write(Constants.THEME, "0");
                blueThemeButton.setBackground(getResources().getDrawable(R.drawable.button_borders_1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blueThemeButton.setBackground(getResources().getDrawable(R.drawable.button_borders));
                    }
                }, 100);
                sendIntent();
            }
        });
        appLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageButtons.setVisibility(View.VISIBLE);
                settingsLayout.setVisibility(View.GONE);
            }
        });
        exitLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageButtons.setVisibility(View.GONE);
                settingsLayout.setVisibility(View.VISIBLE);
            }
        });
        kzLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write(Constants.SET_LANGUAGE, "KZ");
                kzLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders_1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        kzLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders));
                    }
                }, 100);
                sendIntent();
            }
        });
        ruLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write(Constants.SET_LANGUAGE, "RU");
                ruLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders_1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ruLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders));
                    }
                }, 100);
                sendIntent();
            }
        });
        enLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write(Constants.SET_LANGUAGE, "EN");
                enLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders_1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders));
                    }
                }, 100);
                sendIntent();
            }
        });
    }

    @Override
    public void onAttach(Context context) {super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {void onFragmentInteraction(Uri uri);}

    public void setButtonsTexts(){
        if (Paper.book().contains(Constants.SET_LANGUAGE)){
            if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), "EN")){
                exitLanguageButton.setText(getString(R.string.goBackEn));
                exitThemeButton.setText(getString(R.string.goBackEn));
                appThemeButton.setText(getString(R.string.AppThemeEn));
                appLanguageButton.setText(getString(R.string.appSettingsEn));
                blueThemeButton.setText(getString(R.string.blueThemeEn));
                greenThemeButton.setText(getString(R.string.greenThemeEn));
            } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE),"KZ")){
                exitLanguageButton.setText(getString(R.string.goBackKz));
                exitThemeButton.setText(getString(R.string.goBackKz));
                appThemeButton.setText(getString(R.string.AppThemeKz));
                appLanguageButton.setText(getString(R.string.appSettingsKz));
                blueThemeButton.setText(getString(R.string.blueThemeKz));
                greenThemeButton.setText(getString(R.string.greenThemeKz));
            } else {
                exitLanguageButton.setText(getString(R.string.goBack));
                exitThemeButton.setText(getString(R.string.goBack));
                appThemeButton.setText(getString(R.string.AppTheme));
                appLanguageButton.setText(getString(R.string.appSettings));
                blueThemeButton.setText(getString(R.string.blueTheme));
                greenThemeButton.setText(getString(R.string.greenTheme));
            }
        }
    }

    public void setButtonsColors(){
        if (Paper.book().contains(Constants.THEME)){
            if(Objects.equals(Paper.book().read(Constants.THEME), "1")){
                exitLanguageButton.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                exitThemeButton.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                appThemeButton.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                appLanguageButton.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                kzLanguage.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                enLanguage.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
                ruLanguage.setBackground(getResources().getDrawable(R.drawable.green_theme_button));
            } else {
                exitLanguageButton.setBackground(getResources().getDrawable(R.drawable.button_borders));
                exitThemeButton.setBackground(getResources().getDrawable(R.drawable.button_borders));
                appThemeButton.setBackground(getResources().getDrawable(R.drawable.button_borders));
                appLanguageButton.setBackground(getResources().getDrawable(R.drawable.button_borders));
                kzLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders));
                enLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders));
                ruLanguage.setBackground(getResources().getDrawable(R.drawable.button_borders));
            }
        }
    }

    public void sendIntent(){
        Intent i = new Intent(getContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("intent", 1);
        startActivity(i);
    }
}
