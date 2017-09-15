package com.fsep.sova.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fsep.sova.R;
import com.karumi.dexter.PermissionToken;

import static com.fsep.sova.utils.AndroidUtils.checkNotNull;

public final class PermissionsUtils {

  private PermissionsUtils() {
  }

  public static void showPermissionRationale(@NonNull final PermissionToken token,
                                             @NonNull Context context, @StringRes int title, @StringRes int content) {
    checkNotNull(token);
    checkNotNull(context);
    new MaterialDialog.Builder(context).title(title)
        .content(content)
        .positiveText(R.string.content_dialog_button_ok)
        .negativeText(R.string.content_dialog_button_cancel)
        .onPositive((dialog, which) -> {
          dialog.dismiss();
          token.continuePermissionRequest();
        })
        .onNegative((dialog, which) -> {
          dialog.dismiss();
          token.cancelPermissionRequest();
        })
        .dismissListener(dialog -> token.cancelPermissionRequest())
        .show();
  }
}
