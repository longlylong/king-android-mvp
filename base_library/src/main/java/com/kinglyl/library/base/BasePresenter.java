package com.kinglyl.library.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kinglyl.library.http.ErrorUtils;
import com.kinglyl.library.http.HttpConstant;
import com.kinglyl.library.http.HttpProtocolFactory;
import com.kinglyl.library.http.bean.BaseResult;
import com.kinglyl.library.thread.AsyncExecutor;
import com.kinglyl.library.thread.MainThreadExecutor;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BasePresenter<V extends BaseView> {

    protected Context mContext;

    protected V mView;

    void onCleared() {
    }

    void attachView(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    void detachView() {
        this.mView = null;
    }

    public boolean isAttachView() {
        return this.mView != null;
    }

    void onCreatePresenter(@Nullable Bundle savedState) {
    }

    void onDestroyPresenter() {
        this.mContext = null;
        detachView();
    }

    void onSaveInstanceState(Bundle outState) {
    }

    public <P> P getProtocol(Class<P> apiClass) {
        return HttpProtocolFactory.getProtocol(HttpConstant.getRootApiUrl(), apiClass);
    }

    protected <T> void postJson(HttpListener<T> listener) {
        postJson(mView, listener);
    }

    protected <T> void postJson(BaseView mView, HttpListener<T> listener) {
        if (mView != null) {
            MainThreadExecutor.getInstance().execute(mView::showLoading);
        }

        AsyncExecutor.getInstance().execute(() -> {
            try {
                Call<BaseResult<T>> call = listener.getApi();
                Response<BaseResult<T>> execute = call.execute();
                BaseResult<T> body = execute.body();

                MainThreadExecutor.getInstance().execute(() -> {
                    if (body != null) {
                        if (body.ok()) {
                            listener.onHttpSuccess(body.data);

                        } else {
                            listener.onHttpError(body.code, body.message);
                            if (mView != null) {
                                mView.onHttpError(body.code, body.message);
                            }
                        }

                    } else {
                        listener.onHttpError(-1, ErrorUtils.getErrorMsg());
                        if (mView != null) {
                            mView.onHttpError(-1, ErrorUtils.getErrorMsg());
                        }
                    }
                });

            } catch (Exception e) {
                if (mView != null) {
                    MainThreadExecutor.getInstance().execute(() -> {
                        mView.onHttpError(-1, ErrorUtils.getErrorMsg());
                    });
                }
            }
        });
    }

    public abstract static class HttpListener<T> {
        public abstract Call<BaseResult<T>> getApi();

        public abstract void onHttpSuccess(T t);

        void onHttpError(int code, String message) {
        }
    }
}
