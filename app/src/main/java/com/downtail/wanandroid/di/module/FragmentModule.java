package com.downtail.wanandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.downtail.wanandroid.di.scope.ContextLife;
import com.downtail.wanandroid.di.scope.FragmentScope;

import androidx.fragment.app.Fragment;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public Fragment provideFragment() {
        return mFragment;
    }

    @FragmentScope
    @Provides
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @ContextLife("Fragment")
    @FragmentScope
    @Provides
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }
}
