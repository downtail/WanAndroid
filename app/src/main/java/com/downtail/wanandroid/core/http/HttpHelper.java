package com.downtail.wanandroid.core.http;

import com.downtail.wanandroid.model.BannerResponse;

import java.util.List;

import io.reactivex.Observable;

public interface HttpHelper {

    Observable<BaseResponse<List<BannerResponse>>> getBannerData();
}
