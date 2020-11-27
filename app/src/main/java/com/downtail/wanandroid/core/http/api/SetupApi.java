package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.entity.response.UserEntity;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.RecordResponse;
import com.downtail.wanandroid.entity.response.WebsiteResponse;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.local.Paging;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SetupApi {

    @FormUrlEncoded
    @POST(Constants.LOGIN)
    Observable<BaseResponse<UserEntity>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.REGISTER)
    Observable<BaseResponse<UserEntity>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String rePassword);

    @GET(Constants.LOGOUT)
    Observable<BaseResponse<String>> logout();

    @POST(Constants.COLLECT_INNER)
    Observable<BaseResponse<String>> confirmArticleCollect(@Path("id") int id);

    @POST(Constants.CANCEL_INNER)
    Observable<BaseResponse<String>> cancelArticleCollect(@Path("id") int id);

    @GET(Constants.INNER_COLLECT)
    Observable<BaseResponse<Paging<ArticleResponse>>> getCollectArticleData(@Path("page") int page);

    @FormUrlEncoded
    @POST(Constants.COLLECT_WEBSITE)
    Observable<BaseResponse<WebsiteResponse>> collectWebsite(@Field("name") String name, @Field("link") String link);

    @GET(Constants.OUTER_COLLECT)
    Observable<BaseResponse<List<WebsiteResponse>>> getCollectWebsiteData();

    @FormUrlEncoded
    @POST(Constants.MODIFY_WEBSITE)
    Observable<BaseResponse<WebsiteResponse>> modifyWebsite(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    @FormUrlEncoded
    @POST(Constants.CANCEL_WEBSITE)
    Observable<BaseResponse<String>> cancelWebsiteCollect(@Field("id") int id);

    @GET(Constants.RANK_LIST)
    Observable<BaseResponse<Paging<RankResponse>>> getRankListData(@Path("page") int page);

    @GET(Constants.RANK)
    Observable<BaseResponse<RankResponse>> getRankData();

    @GET(Constants.RECORD_LIST)
    Observable<BaseResponse<Paging<RecordResponse>>> getRecordData(@Path("page") int page);
}
