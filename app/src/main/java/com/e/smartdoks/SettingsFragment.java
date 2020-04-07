package com.e.smartdoks;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

//import com.e.smartdoks.utils.AppState;

public class SettingsFragment extends Fragment{
    View v;
//    private Object EditText;
//    private TextInputEditText EditTextYourName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadLocale();
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        SwitchCompat mySwitch = v.findViewById(R.id.nightmodeswitch);
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            mySwitch.setChecked(false);
        } else {
            mySwitch.setChecked(true);
        }

//        EditTextYourName = v.findViewById(R.id.yourname);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restart();
                    Log.d("Message", "Light Mode On");
                    SharedPreferences prefs = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restart();
                    Log.d("Message", "Light Mode Off");
                    SharedPreferences prefs = getActivity().getSharedPreferences("pref", MODE_PRIVATE);

                }

            }
        });

        // change action bar title, if you don't change it will be according to your systems default

//        ActionBar  actionBar =getActivity().getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));

//        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));
        Button changeLang = v.findViewById(R.id.changeMyLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });

        return v;

    }
    private  void  restart() {
        Intent intent = getActivity().getIntent();
        startActivity(intent);
    }
    private  void  recreate() {
        Intent intent = getActivity().getIntent();
        getActivity().getApplicationContext().startActivity(intent);

    }
    private void showChangeLanguageDialog() {
        //array of language to display
        final String[] listItems = {"Chinese - 中文", "Japanese - 日本語", "English (US)","Korean - 한국어", "Filipino (Phil)", "Spanish -Español"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //Chinese
                    setLocale("zh");
                    recreate();
                }
                if (i == 1) {
                    //Japanese
                    setLocale("ja");
                    recreate();

                }
                else if (i == 2) {
                    //English (US)
                    setLocale("en_US");
                    recreate();
                }
                else if (i == 3) {
                    //Korean
                    setLocale("ko");
                    recreate();
                }
                else if (i == 4) {
                    //Philippines
                    setLocale("pl");
                    recreate();
                }
                else if (i == 5) {
                    //Chinese
                    setLocale("es");
                    recreate();
                }
                //dismiss alert dialog when language selected
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
//        Configuration config = new Configuration();
        Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);

//        getActivity().getBaseContext().getResources().updateConfiguration(config,getBaseContext.getResources.getDisplayMetrics());
        //noinspection deprecation
        getActivity().getBaseContext().getResources().updateConfiguration(config,getActivity().getResources().getDisplayMetrics());
        //noinspection deprecation
        getActivity().getResources().updateConfiguration(config,getActivity().getResources().getDisplayMetrics());
 //        save data to shared preferences
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("Settings",  MODE_PRIVATE).edit();
        editor.putString("My Lang", lang);
        editor.apply();
    }

    // load language saved in shared Preferences
    public void loadLocale() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        getActivity().getLifecycle();
    }

//    private boolean confirmInput() {
//        String yourname = String.valueOf(EditTextYourName.getText()).toString();
//
//    }

}