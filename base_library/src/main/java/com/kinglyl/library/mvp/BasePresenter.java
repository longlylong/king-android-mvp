package com.kinglyl.library.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kinglyl.library.activity.BaseActivity;
import com.kinglyl.library.http.ErrorUtils;
import com.kinglyl.library.http.HttpConstant;
import com.kinglyl.library.http.HttpProtocolFactory;
import com.kinglyl.library.http.bean.BaseResult;
import com.kinglyl.library.thread.AsyncExecutor;
import com.kinglyl.library.thread.MainThreadExecutor;
import com.kinglyl.library.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BasePresenter<V extends BaseView> {

    protected BaseActivity mContext;
    protected V mView;

    private Map<String, Call> httpCall = new HashMap<>();

    void attachView(BaseActivity context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    void detachView() {
        this.mView = null;
        for (String key : httpCall.keySet()) {
            Call call = httpCall.get(key);
            if (call != null) {
                call.cancel();
            }
        }
        httpCall.clear();
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

    void onCleared() {
    }

    void onSaveInstanceState(Bundle outState) {
    }

    public <P> P getProtocol(Class<P> apiClass) {
        return HttpProtocolFactory.getProtocol(HttpConstant.getRootApiUrl(), apiClass);
    }

    protected <T> void postJson(HttpListener<T> listener) {
        postJson(mView, listener);
    }

    private <T> void postJson(BaseView mView, HttpListener<T> listener) {
        if (mView != null) {
            MainThreadExecutor.getInstance().execute(mView::showLoading);
        }

        AsyncExecutor.getInstance().execute(() -> {
            Call<BaseResult<T>> call = listener.getApi();
            String url = call.request().url().toString();

            try {
                Call oldCall = httpCall.get(url);
                if (oldCall != null) {
                    oldCall.cancel();
                }
                httpCall.put(url, call);

                Response<BaseResult<T>> execute = call.execute();
                BaseResult<T> body = execute.body();

                httpCall.remove(url);

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
                httpCall.remove(url);
                if ("Socket closed".equals(e.getMessage())) {
                    LogUtil.e("已取消请求:" + url);
                    return;
                }
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
