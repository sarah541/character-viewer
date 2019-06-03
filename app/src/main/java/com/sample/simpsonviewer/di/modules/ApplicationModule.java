package com.sample.simpsonviewer.di.modules;

import android.content.Context;

import com.sample.simpsonviewer.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;


@Module(includes = {ActivityBindingModule.class, AndroidInjectionModule.class})
public class ApplicationModule {

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
}
