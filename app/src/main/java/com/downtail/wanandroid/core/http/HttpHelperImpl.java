package com.downtail.wanandroid.core.http;

import com.downtail.wanandroid.core.http.api.HomeApi;
import com.downtail.wanandroid.core.http.api.NavigationApi;
import com.downtail.wanandroid.core.http.api.ProjectApi;
import com.downtail.wanandroid.core.http.api.SetupApi;
import com.downtail.wanandroid.core.http.api.SystemApi;
import com.downtail.wanandroid.model.BannerResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HttpHelperImpl implements HttpHelper {

    private HomeApi homeApi;
    private SystemApi systemApi;
    private NavigationApi navigationApi;
    private ProjectApi projectApi;
    private SetupApi setupApi;

    @Inject
    public HttpHelperImpl(HomeApi homeApi, SystemApi systemApi, NavigationApi navigationApi, ProjectApi projectApi, SetupApi setupApi) {
        this.homeApi = homeApi;
        this.systemApi = systemApi;
        this.navigationApi = navigationApi;
        this.projectApi = projectApi;
        this.setupApi = setupApi;
    }

    @Override
    public Observable<BaseResponse<List<BannerResponse>>> getBannerData() {
        return homeApi.getBannerData();
    }
}
