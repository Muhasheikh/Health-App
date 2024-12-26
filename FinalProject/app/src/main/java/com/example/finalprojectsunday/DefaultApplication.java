package com.example.finalprojectsunday;

import android.app.Application;
import android.content.Context;

import com.example.finalprojectsunday.other.LocaleHelper;


public class DefaultApplication extends Application {

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.setDefaultLanguage(base, "de"));
    }

}
