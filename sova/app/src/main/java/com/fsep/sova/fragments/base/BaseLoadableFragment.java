package com.fsep.sova.fragments.base;

import android.database.ContentObserver;
import android.net.Uri;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fsep.sova.R;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.events.ExpiredTokenEvent;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseLoadableFragment extends BaseFragment {

  protected boolean loadingDataInProcess;

  private MaterialDialog mDialog;

  protected Map<ContentObserver, Uri> contentObservers = new HashMap<>();

  @CallSuper @Override public void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
    for (Map.Entry<ContentObserver, Uri> entry : contentObservers.entrySet()) {
      getActivity().getContentResolver()
          .registerContentObserver(entry.getValue(), true, entry.getKey());
    }
  }

  @CallSuper @Override public void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
    for (Map.Entry<ContentObserver, Uri> entry : contentObservers.entrySet()) {
      getActivity().getContentResolver().unregisterContentObserver(entry.getKey());
    }
    hideProgressBar();
  }

  protected void showProgressBar() {
    loadingDataInProcess = true;
    if (mDialog == null) {
      mDialog = new MaterialDialog.Builder(getContext()).content(R.string.content_loading)
          .progress(true, 0)
          .cancelable(false)
          .show();
    } else {
      mDialog.show();
    }
  }

  protected void hideProgressBar() {
    loadingDataInProcess = false;
    if (mDialog != null) mDialog.dismiss();
  }

  protected void startAction(@NonNull Action action) {
    ServiceHelper.getInstance().startActionService(getActivity(), checkNotNull(action));
  }

  @Subscribe public void onEvent(ExpiredTokenEvent event) {
    // TODO возврат на окно авторизации
    //        startActivity(new Intent(getActivity(), LoginActivity.class));
    //        PrefUtils.clearPreferences(getActivity());
    //        getActivity().finish();
  }
}
