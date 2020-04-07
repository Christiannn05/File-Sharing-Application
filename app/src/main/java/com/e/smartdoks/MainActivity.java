package com.e.smartdoks;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.e.smartdoks.utils.AppState.isLoggedIn;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawer;
    Button shareFiles;
    Dialog mydialog;
    Dialog mydialog2;
    Dialog mydialog3;
    Dialog mydialog4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mydialog = new Dialog(this);
            mydialog2 = new Dialog(this);
            mydialog3 = new Dialog(this);
            mydialog4 = new Dialog(this);
            shareFiles = new Button(this);

        setContentView(R.layout.activity_main);


            shareFiles = new Button(this);
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SendFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_send);
            }
        }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedbackFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.nav_browse:
                            selectedFragment = new BrowseFragment();
                            break;
                        case R.id.nav_send:
                            selectedFragment = new SendFragment();
                            break;

                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
    public void ShowPopUp (View v) {
        TextView txtclose;
        mydialog.setContentView(R.layout.popup);
        txtclose = (TextView) mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }
    public void ShowPopUp2 (View v) {
        TextView txtclose2;
        mydialog2.setContentView(R.layout.popup2);
        txtclose2 = (TextView) mydialog2.findViewById(R.id.txtclose2);
        txtclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog2.dismiss();
            }
        });
        mydialog2.show();
    }
    public void ShowPopUp3 (View v) {
        TextView txtclose3;
        mydialog3.setContentView(R.layout.popup3);
        txtclose3 = (TextView) mydialog3.findViewById(R.id.txtclose3);
        txtclose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog3.dismiss();
            }
        });
        mydialog3.show();
    }
    public void ShowPopUp4 (View v) {
        TextView txtclose4;
        mydialog4.setContentView(R.layout.popup4);
        txtclose4 = (TextView) mydialog4.findViewById(R.id.txtclose4);
        txtclose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog4.dismiss();
            }
        });
        Button button = mydialog4.findViewById(R.id.feedbackSubmit);
        final RatingBar ratingBar;
        final EditText message =mydialog4.findViewById(R.id.feedbackMessage);
        ratingBar = mydialog4.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(MainActivity.this, "Stars" + v,Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main","Button clicked");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"smartdokskiosk@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback from Android File Transfer App (SmartDoks)");
                i.putExtra(Intent.EXTRA_TEXT,  "\n\nRating: " +ratingBar.getRating() +"\n\nMessage: " + message.getText());
                try{
                    startActivity(Intent.createChooser(i,"Please select Email"));
                } catch (android.content.ActivityNotFoundException e){
                    Toast.makeText(MainActivity.this,"There are no Email Clients", Toast.LENGTH_SHORT);
                }
            }
        });
        mydialog4.show();
    }

}
