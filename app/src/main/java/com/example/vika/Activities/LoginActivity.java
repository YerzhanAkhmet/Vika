package com.example.vika.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vika.Classes.Constants;
import com.example.vika.Classes.Users;
import com.example.vika.R;

import java.util.Objects;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    EditText name, password;
    TextView login_TV, language_TV, signup;
    ConstraintLayout login_activity;
    ImageButton language_IB;
    LinearLayout layout;
    String name_1, password_1, kaz, rus, eng, authorize_kz, authorize_ru, authorize_en,
            login_kz, login_ru, login_en, password_kz, password_ru, password_en,
            register_kz, register_ru, register_en, signin_kz, signin_ru, signin_en;
    Drawable ic_kz, ic_ru, ic_en;
    Users user = new Users();
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        Paper.init(LoginActivity.this);
        login();
        setLanguage();
        setStyle();
        signUp();
    }

    public void initViews(){
        login_activity = findViewById(R.id.login_activity);
        login_button = findViewById(R.id.login_button);
        name = findViewById(R.id.login_ET);
        password = findViewById(R.id.password_ET);
        login_TV = findViewById(R.id.login_TV);
        language_IB = findViewById(R.id.login_IB);
        language_TV = findViewById(R.id.language_TV);
        layout = findViewById(R.id.language_layout);
        signup = findViewById(R.id.sign_up_button);
    }

    public void login(){
        name_1 = "Vika";
        password_1 = "12345";
        Users mUser = new Users();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Paper.book().read(Constants.USERDATA) == null){
                    if(name.getText().toString().equals(name_1)&&
                            password.getText().toString().equals(password_1)){
                        mUser.setName(name.getText().toString());
                        mUser.setPhone(password.getText().toString());
                        Paper.book().write(Constants.USERDATA, mUser);
                        Paper.book().write(Constants.ENTERMAINMENU, 1);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } else if (name.getText().toString().equals(name_1)){
                        Toast.makeText(LoginActivity.this, "Введен неверный пароль!", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().equals(password_1)){
                        Toast.makeText(LoginActivity.this, "Введено неверное имя!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Введены неверные данные", Toast.LENGTH_LONG).show();
                    }
                } else {
                    user = Paper.book().read(Constants.USERDATA);
                    if((name.getText().toString().equals(name_1) ||
                            name.getText().toString().equals(user.getName()))&&
                            (password.getText().toString().equals(password_1) || password.getText().toString().equals(user.getPhone()))){
                        mUser.setName(name.getText().toString());
                        mUser.setPhone(password.getText().toString());
                        Paper.book().write(Constants.USERDATA, mUser);
                        Paper.book().write(Constants.ENTERMAINMENU, 1);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } else if (name.getText().toString().equals(name_1) ||
                            name.getText().toString().equals(user.getName())){
                        Toast.makeText(LoginActivity.this, "Введен неверный пароль!", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().equals(password_1) || password.getText().toString().equals(user.getPhone())){
                        Toast.makeText(LoginActivity.this, "Введено неверное имя!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Введены неверные данные", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public void setLanguage() {
        kaz = getString(R.string.kz);
        rus = getString(R.string.ru);
        eng = getString(R.string.en);
        ic_kz = getDrawable(R.mipmap.ic_kazhakstan);
        ic_ru = getDrawable(R.mipmap.ic_russia);
        ic_en = getDrawable(R.mipmap.ic_uk);
        authorize_kz = getString(R.string.authorization_rus);
        authorize_ru = getString(R.string.authorization_rus);
        authorize_en = getString(R.string.authorization_eng);
        register_kz = getString(R.string.register_kaz);
        register_ru = getString(R.string.register_rus);
        register_en = getString(R.string.register_eng);
        login_kz = getString(R.string.login_ET_kaz);
        login_ru = getString(R.string.login_ET_rus);
        login_en = getString(R.string.login_ET_eng);
        password_kz = getString(R.string.password_kaz);
        password_ru = getString(R.string.password_rus);
        password_en = getString(R.string.password_eng);
        signin_kz = getString(R.string.login_kaz);
        signin_ru = getString(R.string.login_rus);
        signin_en = getString(R.string.login_eng);
        if(!Paper.book().contains(Constants.SET_LANGUAGE)){
            login_button.setText(signin_kz);
            name.setHint(login_kz);
            password.setHint(password_kz);
            login_TV.setText(authorize_kz);
            language_IB.setBackground(ic_kz);
            language_TV.setText(kaz);
            signup.setText(register_kz);
            Paper.book().write(Constants.SET_LANGUAGE, kaz);
        }
        else if(Paper.book().read(Constants.SET_LANGUAGE).equals(kaz)){
            login_button.setText(signin_kz);
            name.setHint(login_kz);
            password.setHint(password_kz);
            login_TV.setText(authorize_kz);
            language_IB.setBackground(ic_kz);
            language_TV.setText(kaz);
            signup.setText(register_kz);
        } else if(Paper.book().read(Constants.SET_LANGUAGE).equals(rus)){
            login_button.setText(signin_ru);
            name.setHint(login_ru);
            password.setHint(password_ru);
            login_TV.setText(authorize_ru);
            language_IB.setBackground(ic_ru);
            language_TV.setText(rus);
            signup.setText(register_ru);
        } else if(Paper.book().read(Constants.SET_LANGUAGE).equals(eng)){
            login_button.setText(signin_en);
            name.setHint(login_en);
            password.setHint(password_en);
            login_TV.setText(authorize_en);
            language_IB.setBackground(ic_en);
            language_TV.setText(eng);
            signup.setText(register_en);
        }
        language_IB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(Paper.book().read(Constants.SET_LANGUAGE).equals(null)){
                    login_button.setText(signin_kz);
                    name.setHint(login_kz);
                    password.setHint(password_kz);
                    login_TV.setText(authorize_kz);
                    language_IB.setBackground(ic_kz);
                    language_TV.setText(kaz);
                    signup.setText(register_kz);
                    Paper.book().write(Constants.SET_LANGUAGE, kaz);
                }
                else if(Paper.book().read(Constants.SET_LANGUAGE).equals(kaz)){
                    login_button.setText(signin_ru);
                    name.setHint(login_ru);
                    password.setHint(password_ru);
                    login_TV.setText(authorize_ru);
                    language_IB.setBackground(ic_ru);
                    language_TV.setText(rus);
                    signup.setText(register_ru);
                    Paper.book().write(Constants.SET_LANGUAGE, rus);
                } else if(Paper.book().read(Constants.SET_LANGUAGE).equals(rus)){
                    login_button.setText(signin_en);
                    name.setHint(login_en);
                    password.setHint(password_en);
                    login_TV.setText(authorize_en);
                    language_IB.setBackground(ic_en);
                    language_TV.setText(eng);
                    signup.setText(register_en);
                    Paper.book().write(Constants.SET_LANGUAGE, eng);
                } else if(Paper.book().read(Constants.SET_LANGUAGE).equals(eng)){
                    login_button.setText(signin_kz);
                    name.setHint(login_kz);
                    password.setHint(password_kz);
                    login_TV.setText(authorize_kz);
                    language_IB.setBackground(ic_kz);
                    language_TV.setText(kaz);
                    signup.setText(register_kz);
                    Paper.book().write(Constants.SET_LANGUAGE, kaz);
                }
            }
        });
    }

    public void signUp(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    public void setStyle(){
        if(!Objects.equals(Paper.book().read(Constants.THEME), null)
                && Objects.equals(Paper.book().read(Constants.THEME), "1")){
            login_activity.setBackgroundColor(getColor(R.color.colorGreenTheme));
        } else {
            login_activity.setBackgroundColor(getColor(R.color.colorPrimary));
        }
    }
    @Override
    public void onBackPressed(){
        if(exit){
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Нажмите кнопку \"НАЗАД\" еще раз для выхода.", Toast.LENGTH_LONG).show();
            exit = true;
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    exit = false;
                }
            }, 3*1000);
        }
    }
}
