package com.fsep.sova.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.activities.RegistrationConfirmPhoneActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionRegisterSendMobile;
import com.fsep.sova.network.events.registration.registration_send_mobile.RegisterSendingMobileIsSuccessEvent;
import com.fsep.sova.utils.PrefUtils;
import org.greenrobot.eventbus.Subscribe;

public class RegistrationPhoneFragment extends BaseLoadableFragment {

  @Bind(R.id.register_edit_mobile) EditText mPhoneField;
  @Bind(R.id.register_phone_number_input_layout) TextInputLayout mPhoneInputLayout;
  @BindString(R.string.phone_error_edit_text) String mPhoneError;
  private String mUserPhoneNumber;

  @OnClick(R.id.next_button) public void sendMobilePhone() {
    mUserPhoneNumber = mPhoneField.getText().toString().trim();
    boolean hasError = false;
    if (mUserPhoneNumber.length() == 0) {
      mPhoneInputLayout.setErrorEnabled(true);
      mPhoneInputLayout.setError(mPhoneError);
      hasError = true;
    } else {
      mPhoneInputLayout.setError(null);
      mPhoneInputLayout.setErrorEnabled(false);
    }
    if (!hasError) {
      sendPhone();
    }
  }

  private void sendPhone() {
    showProgressBar();
    Action action = new ActionRegisterSendMobile(mUserPhoneNumber);
    ServiceHelper.getInstance().startActionService(getActivity(), action);
  }

  @Subscribe public void onEvent(RegisterSendingMobileIsSuccessEvent event) {
    hideProgressBar();
    PrefUtils.saveUserPhone(App.context, mUserPhoneNumber);
    Intent intent = new Intent(getActivity(), RegistrationConfirmPhoneActivity.class);
    startActivity(intent);
    getActivity().finish();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup conatiner,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_registration_edit_phone, conatiner, false);
    ButterKnife.bind(this, view);
    return view;
  }
}
