package com.example.tom.itistracker.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.util.SparseArrayCompat;

import com.example.tom.itistracker.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RetrofitException extends RuntimeException {

    public static final int NETWORK_ERROR_CODE = 1;

    public static final int UNEXPECTED_ERROR_CODE = 0;

    public static final int HTTP_EXCEPTION_CODE = 400;

    public static final int SERVER_EXCEPTION_CODE = 500;

    @NonNull
    private static final SparseArrayCompat<Integer> mDefaultErrorMessages;

    static {
        mDefaultErrorMessages = new SparseArrayCompat<>();
        mDefaultErrorMessages.put(HTTP_EXCEPTION_CODE, R.string.login_unsuccess_default_error);
        mDefaultErrorMessages.put(SERVER_EXCEPTION_CODE, R.string.unidentified_problems_message);
        mDefaultErrorMessages.put(UNEXPECTED_ERROR_CODE, R.string.unidentified_problems_message);
        mDefaultErrorMessages.put(NETWORK_ERROR_CODE, R.string.network_error_message);
    }

    @NonNull
    private final Type mType;

    private final int mCode;

    @StringRes
    private final int mToastMessage;

    @NonNull
    private final Throwable mException;

    @Nullable
    private final Response mResponse;

    @Nullable
    private final Retrofit mRetrofit;

    private RetrofitException(@NonNull final Type type,
                              final int code,
                              @StringRes final int message,
                              @NonNull final Throwable exception,
                              @Nullable final Response response,
                              @Nullable final Retrofit retrofit) {
        super(getOriginalExceptionMessage(exception, code), exception);
        mType = type;
        mCode = code;
        mToastMessage = message;
        mException = exception;
        mResponse = response;
        mRetrofit = retrofit;
    }

    static RetrofitException requestError(@NonNull final HttpException requestException,
                                          @NonNull final Retrofit retrofit) {
        final int errorCode = requestException.code();
        if (errorCode >= HTTP_EXCEPTION_CODE && errorCode < SERVER_EXCEPTION_CODE) {
            return new RetrofitException(
                    Type.HTTP, errorCode, mDefaultErrorMessages.get(HTTP_EXCEPTION_CODE), requestException,
                    requestException.response(), retrofit);
        } else if (errorCode >= SERVER_EXCEPTION_CODE) {
            return new RetrofitException(
                    Type.SERVER, errorCode, mDefaultErrorMessages.get(SERVER_EXCEPTION_CODE), requestException,
                    requestException.response(), retrofit);
        } else {
            return new RetrofitException(
                    Type.UNEXPECTED, errorCode, mDefaultErrorMessages.get(UNEXPECTED_ERROR_CODE),
                    requestException, requestException.response(), retrofit);
        }
    }

    static RetrofitException networkError(@NonNull final IOException exception) {
        return new RetrofitException(Type.NETWORK, NETWORK_ERROR_CODE, mDefaultErrorMessages.get(NETWORK_ERROR_CODE),
                exception, null, null);
    }

    static RetrofitException unexpectedError(@NonNull final Throwable exception) {
        return new RetrofitException(Type.UNEXPECTED, UNEXPECTED_ERROR_CODE, mDefaultErrorMessages.get(UNEXPECTED_ERROR_CODE),
                exception, null, null);
    }

    private static String getOriginalExceptionMessage(@NonNull final Throwable exception, final int code) {
        return String.format(Locale.getDefault(), "%1$s %2$d:\n%3$s",
                "Exception", code, exception.getMessage());
    }

    @NonNull
    public Type getType() {
        return mType;
    }

    public int getCode() {
        return mCode;
    }

    public int getToastMessage() {
        return mToastMessage;
    }

    @NonNull
    public String getOriginalExceptionMessage() {
        return getOriginalExceptionMessage(mException, mCode);
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        throw new IOException("Not defined");
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        throw new IOException("Not defined");
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    @NonNull
    public <T> T getErrorBodyAs(@NonNull final Class<T> type) throws IOException {
        if (mResponse == null || mResponse.errorBody() == null || mRetrofit == null) {
            throw new IOException();
        }
        Converter<ResponseBody, T> converter = mRetrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(mResponse.errorBody());
    }

    public enum Type {
        NETWORK,
        HTTP,
        SERVER,
        UNEXPECTED
    }

}
