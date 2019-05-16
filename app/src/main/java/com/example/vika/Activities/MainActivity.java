package com.example.vika.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vika.Classes.Constants;
import com.example.vika.Classes.ListMenu;
import com.example.vika.Classes.Users;
import com.example.vika.Fragments.NewsFragment;
import com.example.vika.Fragments.NotesFragment;
import com.example.vika.Fragments.ScheduleFragment;
import com.example.vika.Fragments.SettingsFragment;
import com.example.vika.Fragments.UserFragment;
import com.example.vika.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
        implements NewsFragment.OnFragmentInteractionListener {
    NavigationView navigationView;
    DrawerLayout drawer;
    TextView name, position, toolbar_TV;
    ImageButton menu_Button, exit_Button;
    ImageView header_IV;
    RecyclerViewAdapter adapter;
    List<ListMenu> listMenu;
    Drawable ic_settings, ic_profile, ic_news, ic_notes, ic_exit, ic_schedule;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Users user = new Users();
    Toolbar toolbar;
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        initViews();
        menu_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPress();
            }
        });
        initData();
        Intent i = getIntent();
        int num = i.getIntExtra("intent", 0);
        if (num == 0){
            openNewsFragment();
            if(Paper.book().contains(Constants.SET_LANGUAGE)){
                if(Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.kz))){
                    toolbar_TV.setText("Жаңалықтар");
                } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.ru))){
                    toolbar_TV.setText("Новости");
                } else {
                    toolbar_TV.setText("News");
                }
            }
        } else {
            openSettingsFragment();
            if(Paper.book().contains(Constants.SET_LANGUAGE)){
                if(Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.kz))){
                    toolbar_TV.setText("Параметрлер");
                } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.ru))){
                    toolbar_TV.setText("Настройки");
                } else {
                    toolbar_TV.setText("Settings");
                }
            }
        }
        Intent intent = getIntent();
        int status = intent.getIntExtra("Status", 2);
        if (status == 0 || status == 1) {
            openNotesFragment();
            if (Paper.book().contains(Constants.SET_LANGUAGE)) {
                if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.kz))) {
                    toolbar_TV.setText("Ескертпелер");
                } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.ru))) {
                    toolbar_TV.setText("Заметки");
                } else {
                    toolbar_TV.setText("Notes");
                }
            }
        }
    }

    public void initViews(){
        navigationView = findViewById(R.id.nav_view);
        menu_Button = findViewById(R.id.menu_IB);
        exit_Button = findViewById(R.id.exit_IB);
        drawer = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.nav_menu_list1);
        header_IV = findViewById(R.id.imageView);
        name = findViewById(R.id.fullName_TV);
        position = findViewById(R.id.position_TV);
        toolbar_TV = findViewById(R.id.toolbar_TV);
    }

    public void initData(){
        exitButtonPress();
        ic_settings = getDrawable(R.drawable.ic_settings);
        ic_profile = getDrawable(R.drawable.ic_profile);
        ic_news = getDrawable(R.drawable.ic_news);
        ic_notes = getDrawable(R.drawable.ic_notes);
        ic_exit = getDrawable(R.drawable.ic_exit);
        ic_schedule = getDrawable(R.drawable.ic_schedule);
        fillList();
        setStyle();
        adapter = new RecyclerViewAdapter(listMenu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        if (Paper.book().read(Constants.USERDATA) != null){
            user = Paper.book().read(Constants.USERDATA);
            name.setText(user.getName());
            if (Paper.book().contains(Constants.SET_LANGUAGE)){
                if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.ru))){
                    position.setText(String.format("Доброго дня, %s", user.getName()));
                } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getString(R.string.kz))){
                    position.setText(String.format("Қайырлы күн, %s", user.getName()));
                } else {
                    position.setText(String.format("Have a nice day, %s", user.getName()));
                }
            }
            if (user.getName().equals("Vika")){
                Glide.with(this).load("https://pp.userapi.com/c639219/v639219350/34196/rVLjj0QH-cw.jpg").into(header_IV);
            } else {
                Glide.with(this).load("https://vk.com/images/camera_100.png").into(header_IV);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit){
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


    public void menuPress (){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
            fillList();
        }
    }

    public void exitButtonPress(){
        exit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().delete(Constants.ENTERMAINMENU);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<ListMenu> nav_menu;

        RecyclerViewAdapter(List<ListMenu> nav_menu) {
            this.nav_menu = nav_menu;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView logo;
            ConstraintLayout view;
            private TextView desc;

            ViewHolder(View itemView) {
                super(itemView);
                view = itemView.findViewById(R.id.view);
                logo = itemView.findViewById(R.id.menu_logo);
                desc = itemView.findViewById(R.id.menu_desc);
            }
        }
        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.desc.setText(nav_menu.get(position).getDesc());
            holder.logo.setImageDrawable(nav_menu.get(position).getLogo());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (nav_menu.get(position).getPosition()) {
                        case 0:
                            openUserFragment();
                            menuPress();
                            toolbar_TV.setText(nav_menu.get(position).getTitle());
                            break;
                        case 1:
                            openNewsFragment();
                            menuPress();
                            toolbar_TV.setText(nav_menu.get(position).getTitle());
                            break;
                        case 2:
                            openNotesFragment();
                            menuPress();
                            toolbar_TV.setText(nav_menu.get(position).getTitle());
                            break;
                        case 3:
                            openScheduleFragment();
                            menuPress();
                            toolbar_TV.setText(nav_menu.get(position).getTitle());
                            break;
                        case 4:
                            openSettingsFragment();
                            menuPress();
                            toolbar_TV.setText(nav_menu.get(position).getTitle());
                            break;
                        case 5:
                            Paper.book().delete(Constants.ENTERMAINMENU);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return nav_menu.size();
        }
    }
    public void openUserFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        UserFragment userFragment = new UserFragment();
        fragmentTransaction.replace(R.id.main_activity_frame, userFragment, UserFragment.TAG);
        fragmentTransaction.commit();
    }
    public void openNewsFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        NewsFragment newsFragment = new NewsFragment();
        fragmentTransaction.replace(R.id.main_activity_frame, newsFragment, NewsFragment.TAG);
        fragmentTransaction.commit();
    }
    public void openScheduleFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        fragmentTransaction.replace(R.id.main_activity_frame, scheduleFragment, ScheduleFragment.TAG);
        fragmentTransaction.commit();
    }
    public void openSettingsFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.replace(R.id.main_activity_frame, settingsFragment, SettingsFragment.TAG);
        fragmentTransaction.commit();
    }
    public void openNotesFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        NotesFragment notesFragment = new NotesFragment();
        fragmentTransaction.replace(R.id.main_activity_frame, notesFragment, NotesFragment.TAG);
        fragmentTransaction.commit();
    }
    public void fillList(){
        listMenu = new ArrayList<>();
        if (Paper.book().contains(Constants.SET_LANGUAGE)){
            if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getResources().getString(R.string.kz))){
                listMenu.add(0, new ListMenu(ic_profile, "Профиль", 0, "Профиль"));
                listMenu.add(1, new ListMenu(ic_news, "Жаңалықтар", 1, "Жаңалықтар"));
                listMenu.add(2, new ListMenu(ic_notes, "Ескертпелер", 2, "Ескертпелер"));
                listMenu.add(3, new ListMenu(ic_schedule, "Сабақ кестесі", 3, "Сабақ кестесі"));
                listMenu.add(4, new ListMenu(ic_settings, "Параметрлер", 4, "Параметрлер"));
                listMenu.add(5, new ListMenu(ic_exit, "Шығу", 5, "Шығу"));
            } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getResources().getString(R.string.ru))){
                listMenu.add(0, new ListMenu(ic_profile, "Профиль", 0, "Профиль"));
                listMenu.add(1, new ListMenu(ic_news, "Новости", 1, "Новости"));
                listMenu.add(2, new ListMenu(ic_notes, "Заметки", 2, "Заметки"));
                listMenu.add(3, new ListMenu(ic_schedule, "Расписание", 3, "Расписание"));
                listMenu.add(4, new ListMenu(ic_settings, "Настройки", 4, "Настройки"));
                listMenu.add(5, new ListMenu(ic_exit, "Выход", 5, "Выход"));
            } else if (Objects.equals(Paper.book().read(Constants.SET_LANGUAGE), getResources().getString(R.string.en))){
                listMenu.add(0, new ListMenu(ic_profile, "Profile", 0, "Profile"));
                listMenu.add(1, new ListMenu(ic_news, "News", 1, "News"));
                listMenu.add(2, new ListMenu(ic_notes, "Notes", 2, "Notes"));
                listMenu.add(3, new ListMenu(ic_schedule, "Timetable", 3, "Timetable"));
                listMenu.add(4, new ListMenu(ic_settings, "Settings", 4, "Settings"));
                listMenu.add(5, new ListMenu(ic_exit, "Exit", 5, "Exit"));
            }
        } else {
            listMenu.add(0, new ListMenu(ic_profile, "Профиль", 0, "Профиль"));
            listMenu.add(1, new ListMenu(ic_news, "Жаңалықтар", 1, "Жаңалықтар"));
            listMenu.add(2, new ListMenu(ic_notes, "Ескертпелер", 2, "Ескертпелер"));
            listMenu.add(3, new ListMenu(ic_schedule, "Сабақ кестесі", 3, "Сабақ кестесі"));
            listMenu.add(4, new ListMenu(ic_settings, "Параметрлер", 4, "Параметрлер"));
            listMenu.add(5, new ListMenu(ic_exit, "Шығу", 5, "Шығу"));
        }
    }

    public void setStyle(){
        if(!Objects.equals(Paper.book().read(Constants.THEME), null)
                && Objects.equals(Paper.book().read(Constants.THEME), "1")){
            toolbar.setBackgroundColor(getColor(R.color.colorGreenTheme));
            navigationView.setBackgroundColor(getColor(R.color.colorGreenTheme));
        } else {
            toolbar.setBackgroundColor(getColor(R.color.colorPrimary));
            navigationView.setBackgroundColor(getColor(R.color.colorPrimary));
        }
    }
}
