package com.sample.simpsonviewer.di.modules;

import com.sample.simpsonviewer.MainActivity;
import com.sample.simpsonviewer.di.scope.PerActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector
    public abstract MainActivity provideMainActivity();
}
