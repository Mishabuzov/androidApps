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
import com.fsep.sova.R;
import com.fsep.sova.activities.RegistrationUserActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionRegisterConfirmMobileCode;
import com.fsep.sova.network.events.registration.registration_confirm_code.RegisterConfirmingMobileCodeIsSuccessEvent;
import org.greenrobot.eventbus.Subscribe;

public class RegistrationConfirmPhoneFragment extends BaseLoadableFragment {

  @BindString(R.string.phone_code_error_edit_text) String mCodeError;

  @Bind(R.id.register_code) EditText mCodeField;
  @Bind(R.id.register_code_input_layout) TextInputLayout mPhoneCodeLayout;

  @OnClick(R.id.confirm_button) public void confirmMobilePhone() {
    String code = mCodeField.getText().toString().trim();
    boolean hasError = false;
    if (code.length() == 0) {
      mPhoneCodeLayout.setErrorEnabled(true);
      mPhoneCodeLayout.setError(mCodeError);
      hasError = true;
    } else {
      mPhoneCodeLayout.setError(null);
      mPhoneCodeLayout.setErrorEnabled(false);
    }
    if (!hasError) {
      confirmCode(code);
    }
  }

  private void confirmCode(String code) {
    showProgressBar();
    Action action = new ActionRegisterConfirmMobileCode(code);
    ServiceHelper.getInstance().startActionService(getActivity(), action);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup conatiner,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_registration_confirm_phone, conatiner, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Subscribe public void onEvent(RegisterConfirmingMobileCodeIsSuccessEvent event) {
    Intent intent = new Intent(getActivity(), RegistrationUserActivity.class);
    startActivity(intent);
    getActivity().finish();
  }
}
