package com.downtail.wanandroid.di.component;

import android.content.Context;

import com.downtail.wanandroid.di.module.FragmentModule;
import com.downtail.wanandroid.di.scope.ContextLife;
import com.downtail.wanandroid.di.scope.FragmentScope;
import com.downtail.wanandroid.ui.home.HomeFragment;
import com.downtail.wanandroid.ui.mine.fragment.CollectFragment;
import com.downtail.wanandroid.ui.project.fragment.ArticleFragment;
import com.downtail.wanandroid.ui.project.fragment.ProjectFragment;
import com.downtail.wanandroid.ui.service.ServiceFragment;
import com.downtail.wanandroid.ui.system.fragment.NavigationFragment;
import com.downtail.wanandroid.ui.system.fragment.SystemFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Fragment")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    void inject(HomeFragment fragment);

    void inject(SystemFragment fragment);

    void inject(NavigationFragment fragment);

    void inject(ProjectFragment fragment);

    void inject(ArticleFragment fragment);

    void inject(ServiceFragment fragment);

    void inject(CollectFragment fragment);
}
