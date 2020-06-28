package com.downtail.wanandroid.core.http;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class RxUtil {

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<BaseResponse<T>, T> transformer() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(BaseResponse<T> tBaseResponse) throws Exception {
                        if (tBaseResponse.getErrorCode() == 0) {
                            T data = tBaseResponse.getData();
                            if (data == null) {
                                return (T) "";
                            } else {
                                return tBaseResponse.getData();
                            }
                        }
                        throw new ServerException(tBaseResponse.getErrorCode(), tBaseResponse.getErrorMsg());
                    }
                });
            }
        };
    }
}
