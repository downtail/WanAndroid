package com.downtail.wanandroid.core;


import com.downtail.wanandroid.core.db.DatabaseHelper;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.HttpHelper;
import com.downtail.wanandroid.core.preference.PreferenceHelper;
import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.BannerResponse;
import com.downtail.wanandroid.entity.response.CategoryResponse;
import com.downtail.wanandroid.entity.response.NavigationResponse;
import com.downtail.wanandroid.entity.response.Paging;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.RecordResponse;
import com.downtail.wanandroid.entity.response.ServiceResponse;
import com.downtail.wanandroid.entity.response.SystemResponse;
import com.downtail.wanandroid.entity.response.UserEntity;
import com.downtail.wanandroid.entity.response.WebsiteResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public class DataManager implements HttpHelper, DatabaseHelper, PreferenceHelper {

    private HttpHelper httpHelper;
    private DatabaseHelper databaseHelper;
    private PreferenceHelper preferenceHelper;

    public DataManager(HttpHelper httpHelper, DatabaseHelper databaseHelper, PreferenceHelper preferenceHelper) {
        this.httpHelper = httpHelper;
        this.databaseHelper = databaseHelper;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public Observable<ResponseBody> downloadLatestApk(String url) {
        return httpHelper.downloadLatestApk(url);
    }

    @Override
    public boolean getLoginStatus() {
        return preferenceHelper.getLoginStatus();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        preferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public Observable<Boolean> setUserEntity(UserEntity entity) {
        return preferenceHelper.setUserEntity(entity);
    }

    @Override
    public Observable<UserEntity> getUserEntity() {
        return preferenceHelper.getUserEntity();
    }

    @Override
    public Observable<BaseResponse<List<BannerResponse>>> getBannerData() {
        return httpHelper.getBannerData();
    }

    @Override
    public Observable<BaseResponse<List<ArticleResponse>>> getAdvancedArticleData() {
        return httpHelper.getAdvancedArticleData();
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getHomeArticleData(int page) {
        return httpHelper.getHomeArticleData(page);
    }

    @Override
    public Observable<BaseResponse<List<SystemResponse>>> getSystemData() {
        return httpHelper.getSystemData();
    }

    @Override
    public Observable<BaseResponse<List<NavigationResponse>>> getNavigationData() {
        return httpHelper.getNavigationData();
    }

    @Override
    public Observable<BaseResponse<List<CategoryResponse>>> getProjectCategoryData() {
        return httpHelper.getProjectCategoryData();
    }

    @Override
    public Observable<BaseResponse<List<ServiceResponse>>> getServiceColumnData() {
        return httpHelper.getServiceColumnData();
    }

    @Override
    public Observable<BaseResponse<UserEntity>> login(String username, String password) {
        return httpHelper.login(username, password);
    }

    @Override
    public Observable<BaseResponse<UserEntity>> register(String username, String password, String rePassword) {
        return httpHelper.register(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<String>> logout() {
        return httpHelper.logout();
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getProjectArticleData(int page, int cid) {
        return httpHelper.getProjectArticleData(page, cid);
    }

    @Override
    public Observable<BaseResponse<String>> confirmArticleCollect(int id) {
        return httpHelper.confirmArticleCollect(id);
    }

    @Override
    public Observable<BaseResponse<String>> cancelArticleCollect(int id) {
        return httpHelper.cancelArticleCollect(id);
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getCollectArticleData(int page) {
        return httpHelper.getCollectArticleData(page);
    }

    @Override
    public Observable<BaseResponse<WebsiteResponse>> collectWebsite(String name, String link) {
        return httpHelper.collectWebsite(name, link);
    }

    @Override
    public Observable<BaseResponse<List<WebsiteResponse>>> getCollectWebsiteData() {
        return httpHelper.getCollectWebsiteData();
    }

    @Override
    public Observable<BaseResponse<WebsiteResponse>> modifyWebsite(int id, String name, String link) {
        return httpHelper.modifyWebsite(id, name, link);
    }

    @Override
    public Observable<BaseResponse<String>> cancelWebsiteCollect(int id) {
        return httpHelper.cancelWebsiteCollect(id);
    }

    @Override
    public Observable<BaseResponse<Paging<RankResponse>>> getRankListData(int page) {
        return httpHelper.getRankListData(page);
    }

    @Override
    public Observable<BaseResponse<RankResponse>> getRankData() {
        return httpHelper.getRankData();
    }

    @Override
    public Observable<BaseResponse<Paging<RecordResponse>>> getRecordData(int page) {
        return httpHelper.getRecordData(page);
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleData(int id, int page) {
        return httpHelper.getClientArticleData(id, page);
    }

    @Override
    public Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleByKeyword(int id, int page, String keyword) {
        return httpHelper.getClientArticleByKeyword(id, page, keyword);
    }

    @Override
    public Observable<List<Author>> getAuthorList() {
        return databaseHelper.getAuthorList();
    }

    @Override
    public void saveAuthorList(List<Author> data) {
        databaseHelper.saveAuthorList(data);
    }
}
