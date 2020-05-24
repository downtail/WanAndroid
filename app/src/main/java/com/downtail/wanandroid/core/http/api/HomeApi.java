package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.model.BannerResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HomeApi {

    @GET(Constants.BANNER_URL)
    Observable<BaseResponse<List<BannerResponse>>> getBannerData();
}
