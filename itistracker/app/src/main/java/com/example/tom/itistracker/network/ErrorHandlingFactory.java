package com.example.tom.itistracker.network;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class ErrorHandlingFactory extends CallAdapter.Factory {

    @NonNull
    private static final List<Class<? extends Throwable>> mNetworkExceptions =
            Arrays.asList(UnknownHostException.class, SocketTimeoutException.class,
                    ConnectException.class);

    @NonNull
    private final RxJava2CallAdapterFactory mOriginalFactory;

    private ErrorHandlingFactory() {
        mOriginalFactory = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new ErrorHandlingFactory();
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull final Type returnType,
                                 @NonNull final Annotation[] annotations,
                                 @NonNull final Retrofit retrofit) {
        return new RxCallAdapterWrapper<>(retrofit, mOriginalFactory.get(returnType, annotations, retrofit));
    }

    private static final class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {

        private final Retrofit mRetrofit;

        private final CallAdapter<R, ?> mWrappedCallAdapter;

        RxCallAdapterWrapper(@NonNull final Retrofit retrofit,
                             final CallAdapter<R, ?> wrapped) {
            mRetrofit = retrofit;
            mWrappedCallAdapter = wrapped;
        }

        @Override
        public Type responseType() {
            return mWrappedCallAdapter.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object adapt(@NonNull final Call<R> call) {
            Object result = mWrappedCallAdapter.adapt(call);

            if (result instanceof Single) {
                return ((Single) result).onErrorResumeNext(throwable ->
                        Single.error(getRetrofitException((Throwable) throwable)));
            }

            if (result instanceof Observable) {
                return ((Observable) result).onErrorResumeNext(
                        throwable -> {
                            return Observable.error(getRetrofitException((Throwable) throwable));
                        });
            }

            if (result instanceof Completable) {
                return ((Completable) result).onErrorResumeNext(throwable
                        -> Completable.error(getRetrofitException(throwable)));
            }

            return result;
        }

        private boolean isNetworkError(Throwable throwable) {
            return mNetworkExceptions.contains(throwable.getClass());
        }

        private RetrofitException getRetrofitException(final Throwable throwable) {
            // Non-200 http error
            if (throwable instanceof HttpException) {
                final HttpException httpException = (HttpException) throwable;
                return RetrofitException.requestError(httpException, mRetrofit);
            }

            // A network error
            if (isNetworkError(throwable)) {
                return RetrofitException.networkError((IOException) throwable);
            }

            // An unknown error
            return RetrofitException.unexpectedError(throwable);
        }
    }
}
