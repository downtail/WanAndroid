package com.downtail.wanandroid.di.component;

import android.content.Context;

import com.downtail.wanandroid.di.module.FragmentModule;
import com.downtail.wanandroid.di.scope.ContextLife;
import com.downtail.wanandroid.di.scope.FragmentScope;
import com.downtail.wanandroid.ui.home.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Fragment")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    void inject(HomeFragment fragment);
}
