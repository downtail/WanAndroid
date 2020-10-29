package com.downtail.wanandroid.core.http;

import com.downtail.wanandroid.core.http.api.HomeApi;
import com.downtail.wanandroid.core.http.api.ProjectApi;
import com.downtail.wanandroid.core.http.api.ServiceApi;
import com.downtail.wanandroid.core.http.api.SetupApi;
import com.downtail.wanandroid.core.http.api.SystemApi;
import com.downtail.wanandroid.entity.response.BannerResponse;
import com.downtail.wanandroid.entity.response.UserEntity;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.RecordResponse;
import com.downtail.wanandroid.entity.response.WebsiteResponse;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.CategoryResponse;
import com.downtail.wanandroid.entity.response.Paging;
import com.downtail.wanandroid.entity.response.ServiceResponse;
import com.downtail.wanandroid.entity.response.NavigationResponse;
import com.downtail.wanandroid.entity.response.SystemResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public class HttpHelperImpl implements HttpHelper {

    private HomeApi homeApi;
    private SystemApi systemApi;
    private ServiceApi serviceApi;
    private ProjectApi projectApi;
    private SetupApi setupApi;

    @Inject
    public HttpHelperImpl(HomeApi homeApi, SystemApi systemApi, ServiceApi serviceApi, ProjectApi projectApi, SetupApi setupApi) {
        this.homeApi = homeApi;
        this.systemApi = systemApi;
        this.serviceApi = serviceApi;
        this.projectApi = projectApi;
        this.setupApi = setupApi;
    }

    @Override
    public Observable<ResponseBody> downloadLatestApk(String url) {
        return homeApi.downloadLatestApk(url);
    }

    @Override
    public Observable<BaseResponse<List<BannerResponse>>> getBannerData() {
        return homeApi.getBannerData();
    }

    @Override
    public Observable<BaseResponse<List<ArticleResponse>>> getAdvancedArticleData() {
        return homeApi.getAdvancedArticleData();
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getHomeArticleData(int page) {
        return homeApi.getHomeArticleData(page);
    }

    @Override
    public Observable<BaseResponse<List<SystemResponse>>> getSystemData() {
        return systemApi.getSystemData();
    }

    @Override
    public Observable<BaseResponse<List<NavigationResponse>>> getNavigationData() {
        return systemApi.getNavigationData();
    }

    @Override
    public Observable<BaseResponse<List<CategoryResponse>>> getProjectCategoryData() {
        return projectApi.getProjectCategoryData();
    }

    @Override
    public Observable<BaseResponse<List<ServiceResponse>>> getServiceColumnData() {
        return serviceApi.getServiceColumnData();
    }

    @Override
    public Observable<BaseResponse<UserEntity>> login(String username, String password) {
        return setupApi.login(username, password);
    }

    @Override
    public Observable<BaseResponse<UserEntity>> register(String username, String password, String rePassword) {
        return setupApi.register(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<String>> logout() {
        return setupApi.logout();
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getProjectArticleData(int page, int cid) {
        return projectApi.getProjectArticleData(page, cid);
    }

    @Override
    public Observable<BaseResponse<String>> confirmArticleCollect(int id) {
        return setupApi.confirmArticleCollect(id);
    }

    @Override
    public Observable<BaseResponse<String>> cancelArticleCollect(int id) {
        return setupApi.cancelArticleCollect(id);
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getCollectArticleData(int page) {
        return setupApi.getCollectArticleData(page);
    }

    @Override
    public Observable<BaseResponse<WebsiteResponse>> collectWebsite(String name, String link) {
        return setupApi.collectWebsite(name, link);
    }

    @Override
    public Observable<BaseResponse<List<WebsiteResponse>>> getCollectWebsiteData() {
        return setupApi.getCollectWebsiteData();
    }

    @Override
    public Observable<BaseResponse<WebsiteResponse>> modifyWebsite(int id, String name, String link) {
        return setupApi.modifyWebsite(id, name, link);
    }

    @Override
    public Observable<BaseResponse<String>> cancelWebsiteCollect(int id) {
        return setupApi.cancelWebsiteCollect(id);
    }

    @Override
    public Observable<BaseResponse<Paging<RankResponse>>> getRankListData(int page) {
        return setupApi.getRankListData(page);
    }

    @Override
    public Observable<BaseResponse<RankResponse>> getRankData() {
        return setupApi.getRankData();
    }

    @Override
    public Observable<BaseResponse<Paging<RecordResponse>>> getRecordData(int page) {
        return setupApi.getRecordData(page);
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleData(int id, int page) {
        return serviceApi.getClientArticleData(id, page);
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleByKeyword(int id, int page, String keyword) {
        return serviceApi.getClientArticleByKeyword(id, page, keyword);
    }
}
