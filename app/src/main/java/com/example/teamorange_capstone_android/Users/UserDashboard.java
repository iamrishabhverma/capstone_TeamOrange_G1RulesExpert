package com.example.teamorange_capstone_android.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.teamorange_capstone_android.R;
import com.example.teamorange_capstone_android.DatabaseHelper.SessionManager;
import com.example.teamorange_capstone_android.HelperClasses.LocaleHelper;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Locale;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    String fullName, phoneNo, email, gender, dob,  password;
    TextView UserDetailsText, UserNameInitials, changeLocale ;
    boolean lang_selected;
    Context context;
    RelativeLayout progressbar;
    Resources resources;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menuIcon);
        UserDetailsText = findViewById(R.id.welcome_user);
        UserNameInitials = findViewById(R.id.userNameInitial);

        changeLocale = findViewById(R.id.change_language);

        progressbar = findViewById(R.id.dashboard_progress_bar);


        //Start Session
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        //Get all the data from Session
        String fullName = userDetails.get(SessionManager.KEY_FULL_NAME);
        String phoneNumber = userDetails.get(SessionManager.KEY_PHONE_NUMBER);

        navigationDrawer();

        //UserNameInitials.setText(fullName.charAt(0));
        UserDetailsText.setText("Welcome\n\n" + fullName + "\n" + phoneNumber);
    }

    //Navigation Drawer Functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                break;

            case R.id.nav_allQuiz:
                startActivity(new Intent(getApplicationContext(), AllQuizActivity.class));
                break;
        }
        return true;
    }

    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onStart() {
        super.onStart();
        String currentLocale = LocaleHelper.getLanguage(UserDashboard.this);
        if(currentLocale.equals("pa")){
            changeLocale.setText("ਪੰਜਾਬੀ"); }

        else if(currentLocale.equals("fr")){
            changeLocale.setText("French"); }

        else{
            changeLocale.setText("English");
        }

        changeLocale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] Language = {"ENGLISH", "ਪੰਜਾਬੀ", "French"};
                final int checkedItem;
                String currentLocale = LocaleHelper.getLanguage(UserDashboard.this);

                if (currentLocale.equals("pa")||currentLocale.equals("fr")) {
                    checkedItem = 1;
                }
                else {
                    checkedItem = 0;
                }




                final AlertDialog.Builder builder = new AlertDialog.Builder(UserDashboard.this);
                builder.setTitle(getString(R.string.select_a_language))
                        .setSingleChoiceItems(Language, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(UserDashboard.this,""+which,Toast.LENGTH_SHORT).show();
                                changeLocale.setText(Language[which]);
                                lang_selected= Language[which].equals("ENGLISH");
                                //if user select preferred language as English then
                                if(Language[which].equals("ENGLISH"))
                                {
                                    context = LocaleHelper.setLocale(UserDashboard.this, "en");
                                    changeLanguage("en");
                                }
                                //if user select preferred language as Punjabi then
                                if(Language[which].equals("ਪੰਜਾਬੀ"))
                                {
                                    context = LocaleHelper.setLocale(UserDashboard.this, "pa");
                                    changeLanguage("pa");
                                }

                                if(Language[which].equals("French"))
                                {
                                    context = LocaleHelper.setLocale(UserDashboard.this, "fr");
                                    changeLanguage("fr");
                                }



                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                            }
                        });
                builder.create().show();
            }
        });
    }
}