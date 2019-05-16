package com.example.vika.Activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vika.Classes.Constants;
import com.example.vika.Classes.Users;
import com.example.vika.R;

import java.util.Objects;

import io.paperdb.Paper;
import rx.subscriptions.CompositeSubscription;

public class CreateUserActivity extends AppCompatActivity {

    TextView cancel_TV;
    EditText userName_ET, userPassword_ET, verificationCode_ET, status, city, university, quote;
    Button createUserButton;
    CompositeSubscription subscription;
    Users mUser;
    AppBarLayout app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        initViews();
        setStyle();
        Paper.init(CreateUserActivity.this);
        subscription = new CompositeSubscription();
        cancel_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Objects.equals(userName_ET.getText().toString(), "") && !Objects.equals(userPassword_ET.getText().toString(), "")) {
                    if (verificationCode_ET.getText().toString().equals(Paper.book().read(Constants.RNDMNUMBER))) {
                        Toast.makeText(CreateUserActivity.this, "Succsess!", Toast.LENGTH_LONG).show();
                        goToMainMenu();
                        mUser = new Users(userName_ET.getText().toString(), userPassword_ET.getText().toString(),status.getText().toString(),
                                quote.getText().toString(), city.getText().toString(), university.getText().toString(), "https://vk.com/images/camera_100.png");
                        Paper.book().write(Constants.USERDATA, mUser);
                        Paper.book().write(Constants.ENTERMAINMENU, 1);
                    } else {
                        Toast.makeText(CreateUserActivity.this, "Введен неверный код", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateUserActivity.this, "Введите данные", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initViews(){
        cancel_TV = findViewById(R.id.cancelUserCreation);
        userName_ET = findViewById(R.id.userName_ET);
        userPassword_ET = findViewById(R.id.userPassword_ET);
        verificationCode_ET = findViewById(R.id.verification_code_ET);
        createUserButton = findViewById(R.id.createUserButton);
        status = findViewById(R.id.userStatus_ET);
        city = findViewById(R.id.userCity_ET);
        university = findViewById(R.id.userUniversity_ET);
        quote = findViewById(R.id.userQuote_ET);
        app_bar = findViewById(R.id.app_bar);
    }
    public void goBack(){
        Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    public void goToMainMenu() {
        Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void setStyle(){
        if(!Objects.equals(Paper.book().read(Constants.THEME), null)
                && Objects.equals(Paper.book().read(Constants.THEME), "1")){
            app_bar.setBackgroundColor(getColor(R.color.colorGreenTheme));
        } else {
            app_bar.setBackgroundColor(getColor(R.color.colorPrimary));
        }
    }
}