package com.fsep.sova.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fsep.sova.R;
import com.fsep.sova.activities.NotesActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.models.FileType;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionGetTicketForFileUploading;
import com.fsep.sova.network.actions.ActionRegisterCheckUser;
import com.fsep.sova.network.actions.ActionRegisterFinish;
import com.fsep.sova.network.actions.ActionUploadImageFile;
import com.fsep.sova.network.events.get_ticket.GettingTicketErrorEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadSuccessEvent;
import com.fsep.sova.network.events.registration.registration_check_user.RegisterCheckUserIsSuccess;
import com.fsep.sova.network.events.registration.registration_user.FinishRegisterIsSuccessEvent;
import com.fsep.sova.utils.FileUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationUserFragment extends BaseLoadableFragment {

  public static final int GALLERY = 0;

  private UserInfo mUser;
  @BindString(R.string.name_error_edit_text) String mNameError;
  @BindString(R.string.surname_error_edit_text) String mSurnameError;
  @BindString(R.string.login_error_edit_text) String mLoginError;
  @BindString(R.string.password_error_edit_text) String mPasswordError;

  @Bind(R.id.add_user_image) ImageView avatarImageView;
  @Bind(R.id.avatar_loading_progress_layout) View avatarLoadingProcessLayout;
  @Bind(R.id.avatar_loading_progress) View avatarLoadingProgressBar;
  @Bind(R.id.avatar_loading_error) View avatarLoadingError;
  @Bind(R.id.register_edit_name) EditText mNameField;
  @Bind(R.id.register_edit_surname) EditText mSurnameField;
  @Bind(R.id.register_edit_login) EditText mLoginField;
  @Bind(R.id.register_edit_password) EditText mPasswordField;
  @Bind(R.id.register_name) TextInputLayout mNameInputLayout;
  @Bind(R.id.register_surname) TextInputLayout mSurnameInputLayout;
  @Bind(R.id.register_login) TextInputLayout mLoginInputLayout;
  @Bind(R.id.register_password) TextInputLayout mPasswordInputLayout;

  @OnClick(R.id.finish_button) public void onClickFinishButton() {

    String name = mNameField.getText().toString().trim();
    String surname = mSurnameField.getText().toString().trim();
    String login = mLoginField.getText().toString().trim();
    String password = mPasswordField.getText().toString().trim();
    boolean hasError = false;
    if (name.length() == 0) {
      mNameInputLayout.setErrorEnabled(true);
      mNameInputLayout.setError(mNameError);
      hasError = true;
    } else {
      mNameInputLayout.setError(null);
      mNameInputLayout.setErrorEnabled(false);
    }
    if (surname.length() != 0) {
      mSurnameInputLayout.setError(null);
      mSurnameInputLayout.setErrorEnabled(false);
    }
    if (login.length() == 0) {
      mLoginInputLayout.setError(mLoginError);
      mLoginInputLayout.setErrorEnabled(true);
      hasError = true;
    } else {
      mLoginInputLayout.setError(null);
      mLoginInputLayout.setErrorEnabled(false);
    }
    if (password.length() == 0) {
      mPasswordInputLayout.setError(mPasswordError);
      mPasswordInputLayout.setErrorEnabled(true);
      hasError = true;
    } else {
      mPasswordInputLayout.setError(null);
      mPasswordInputLayout.setErrorEnabled(false);
    }
    if (!hasError) {
      mUser = new UserInfo();
      mUser.setFirstName(mNameField.getText().toString());
      mUser.setLastName(mSurnameField.getText().toString());
      mUser.setNickName(mLoginField.getText().toString());
      checkUser();
    }
  }

  @OnClick(R.id.add_user_image) void onAddUserButtonClick() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      Dexter.checkPermission(new PermissionListener() {
        @Override public void onPermissionGranted(PermissionGrantedResponse response) {
          openGallery();
        }

        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
            PermissionToken token) {/* ... */}
      }, Manifest.permission.READ_EXTERNAL_STORAGE);
    } else {
      openGallery();
    }
  }

  private String avatarFilePath;

  private void openGallery() {
    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(galleryIntent, GALLERY);
  }

  private void checkUser() {
    showProgressBar();
    Action action = new ActionRegisterCheckUser(mUser.getNickName());
    ServiceHelper.getInstance().startActionService(getActivity(), action);
  }

  @Subscribe public void onCheckRegisterSuccessEvent(RegisterCheckUserIsSuccess event) {
    finishRegistration();
  }

  private void finishRegistration() {
    Action action = new ActionRegisterFinish(mUser);
    ServiceHelper.getInstance().startActionService(getActivity(), action);
  }

  @Subscribe public void finishRegister(FinishRegisterIsSuccessEvent event) {
    hideProgressBar();
    Intent intent = new Intent(getActivity(), NotesActivity.class);
    startActivity(intent);
    getActivity().finish();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup conatiner,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_registration_user_fields, conatiner, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == GALLERY) {
        checkNotNull(data);
        addImageToImageView(data.getData());
      }
    }
  }

  private void addImageToImageView(@NonNull Uri uri) {
    checkNotNull(uri);
    avatarFilePath = FileUtils.getPath(getContext(), uri);
    avatarLoadingProcessLayout.setVisibility(View.VISIBLE);
    Glide.with(getContext()).load(uri).into(avatarImageView);
    getTicketForUploading();
  }

  private void uploadFile(@NonNull String path, @NonNull String ticket) {
    checkNotNull(path);
    checkNotNull(ticket);
    startAction(new ActionUploadImageFile(ticket, path, FileType.IMAGE));
  }

  private void getTicketForUploading() {
    startAction(new ActionGetTicketForFileUploading(
        ActionGetTicketForFileUploading.Reason.TO_AVATAR_UPLOAD.toString()));
  }

  @Subscribe void onEvent(GettingTicketIsSuccessEvent event) {
    uploadFile(avatarFilePath, event.getTicket());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) void onEvent(GettingTicketErrorEvent event) {
    avatarLoadingProgressBar.setVisibility(View.GONE);
    avatarLoadingError.setVisibility(View.VISIBLE);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) void onEvent(ImageFileUploadSuccessEvent event) {
    avatarLoadingProcessLayout.setVisibility(View.GONE);
    Picasso.with(getActivity()).load(event.getPhoto().getOriginalUrl()).into(avatarImageView);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) void onEvent(ImageFileUploadIsErrorEvent event) {
    avatarLoadingProgressBar.setVisibility(View.GONE);
    avatarLoadingError.setVisibility(View.VISIBLE);
  }
}
