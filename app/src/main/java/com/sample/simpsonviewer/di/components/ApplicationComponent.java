package com.sample.simpsonviewer.di.components;

import com.sample.simpsonviewer.MyApplication;
import com.sample.simpsonviewer.di.modules.ApplicationModule;
import com.sample.simpsonviewer.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(MyApplication application);
}
