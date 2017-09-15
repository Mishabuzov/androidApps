package com.fsep.sova.network;

import com.fsep.sova.App;
import com.fsep.sova.utils.PrefUtils;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationInterceptor implements Interceptor {
  @Override public Response intercept(Chain chain) throws IOException {
    if (PrefUtils.isSignedIn(App.context)) {
      Request original = chain.request();
      Request request = original.newBuilder()
          .header("Auth-Token", checkNotNull(PrefUtils.getAuthToken(App.context)))
          .method(original.method(), original.body())
          .build();
      return chain.proceed(request);
    } else if (PrefUtils.hasTempAuthToken(App.context)) {
      Request original = chain.request();
      Request request = original.newBuilder()
          .header("TempAuth-Token", checkNotNull(PrefUtils.getTempAuthToken(App.context)))
          .method(original.method(), original.body())
          .build();
      return chain.proceed(request);
    }
    return chain.proceed(chain.request());
  }
}
