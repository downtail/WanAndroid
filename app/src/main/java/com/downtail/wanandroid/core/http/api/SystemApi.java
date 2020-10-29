package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.entity.response.NavigationResponse;
import com.downtail.wanandroid.entity.response.SystemResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface SystemApi {

    @GET(Constants.SYSTEM_COLUMN)
    Observable<BaseResponse<List<SystemResponse>>> getSystemData();

    @GET(Constants.NAVIGATION_COLUMN)
    Observable<BaseResponse<List<NavigationResponse>>> getNavigationData();
}
