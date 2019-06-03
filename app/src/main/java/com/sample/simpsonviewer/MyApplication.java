package com.sample.simpsonviewer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.sample.simpsonviewer.di.components.ApplicationComponent;
import com.sample.simpsonviewer.di.components.DaggerApplicationComponent;
import com.sample.simpsonviewer.di.modules.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    private static ApplicationComponent applicationComponent;
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent.inject(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
