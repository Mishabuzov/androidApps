package com.kpfu.mikhail.gif.screen.base.feed;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.Gif;
import com.kpfu.mikhail.gif.content.GifInfo;
import com.kpfu.mikhail.gif.screen.auth.AuthActivity;
import com.kpfu.mikhail.gif.screen.base.feed.GifAdapter.RecyclerCallback;
import com.kpfu.mikhail.gif.screen.full_screen.FullScreenActivity;
import com.kpfu.mikhail.gif.screen.general.LoadingDialog;
import com.kpfu.mikhail.gif.screen.general.LoadingView;
import com.kpfu.mikhail.gif.utils.AttachmentsHelper;
import com.kpfu.mikhail.gif.widget.EmptyRecyclerView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public abstract class BaseRecyclerGifFragment extends Fragment
        implements RecyclerCallback, BaseGifView {

    public static final int PICK_CONTACT_REQUEST = 1;

    @BindDrawable(R.drawable.open_drawer_icon) Drawable mDrawIcon;

    @BindString(R.string.auth_dialog_title) String mAuthDialogTitleMsg;

    @BindString(R.string.auth_dialog_descr) String mAuthDialogDescrMsg;

    @BindString(R.string.auth_dialog_positive) String mAuthDialogYesAnsMsg;

    @BindString(R.string.auth_dialog_negative) String mAuthDialogNoAnsMsg;

    @BindString(R.string.auth_dialog_neutral) String mAuthDialogNeutralAnsMsg;

    @BindString(R.string.toast_error) String mErrMsgPrescription;

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty) View mEmptyView;

    @BindView(R.id.toolbar) Toolbar mToolbar;

//    @BindView(R.id.btn_floating_action) FloatingActionButton mFab;

    private GifAdapter mGifAdapter;

    private LoadingView mLoadingView;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private int mAdapterPosition;

    private BaseGifPresenter mPresenter;

    private AttachmentsHelper mAttachmentsHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler_gif, container, false);
        ButterKnife.bind(this, view);
        initFragmentElements();
        mPresenter.loadGifs();
        mAttachmentsHelper = new AttachmentsHelper(this);
//        initFab();
        return view;
    }

/*    private void initFab(){
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video*//**//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_CONTACT_REQUEST);*//*
                mAttachmentsHelper.showAddVideoDialog();
            }
        });
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
//        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                mAttachmentsHelper.processMediaResult(requestCode, data);





               /* Uri selectedImageUri = data.getData();

                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();


                // MEDIA GALLERY

                String selectedImagePath = getPath(selectedImageUri);
                if (filemanagerstring != null) {
                    String[] cmd = {"-i"
                            , filemanagerstring
                            , "Image.gif"};
                    conversion(cmd);

                }*/
            }
//        }
    }

    /*public void conversion(String[] cmd) {
        FFmpeg ffmpeg = FFmpeg.getInstance(getActivity());

        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Logger.d("Start");
                }

                @Override
                public void onProgress(String message) {
                    Logger.d("Progress");
                }

                @Override
                public void onFailure(String message) {
                    Logger.e(String.format("%1$s -- %2$s", "error!", message));
                }

                @Override
                public void onSuccess(String message) {
                    Logger.d("Done!");
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getActivity(), "Finish", Toast.LENGTH_LONG).show();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            e.printStackTrace();
        }
    }*/

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        String res = null;
        if (cursor != null && cursor.moveToFirst()) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.syncState();
        }
    }

    private void initFragmentElements() {
        mLoadingView = LoadingDialog.view(getActivity()
                .getSupportFragmentManager());
        installGifAdapter();
        setupActionBar();
        mPresenter = initPresenter();
    }

    public abstract BaseGifPresenter initPresenter();

    private void setupActionBar() {
        mToolbar.setBackgroundResource(R.color.toolbar_color);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(setToolbarTitle());
        }
        setActionBarDrawerToggle();
    }

    private void setActionBarDrawerToggle() {
        BaseCallback callback = (BaseCallback) getActivity();
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), callback.getDrawer(), mToolbar, R.string.app_name, R.string.app_name);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        mActionBarDrawerToggle.setHomeAsUpIndicator(mDrawIcon);
        final boolean[] isDrawerOpened = {false};
        mActionBarDrawerToggle.setToolbarNavigationClickListener(v -> {
            if (isDrawerOpened[0]) {
                callback.closeDrawer();
                isDrawerOpened[0] = false;
            } else {
                callback.openDrawer();
                isDrawerOpened[0] = true;
            }
        });
    }

    protected abstract int setToolbarTitle();

    private void installGifAdapter() {
        mGifAdapter = new GifAdapter(this);
        mGifAdapter.attachToRecyclerView(mRecyclerView);
        setupFeedRecyclerView();
    }

    private void setupFeedRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mGifAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
    }

    @Override
    public void showAuthorizeDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(mAuthDialogTitleMsg);
        alertDialog.setMessage(mAuthDialogDescrMsg);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mAuthDialogNoAnsMsg,
                (dialog, id) -> alertDialog.closeOptionsMenu());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mAuthDialogYesAnsMsg,
                (dialog, id) -> startActivity(new Intent(getActivity(), AuthActivity.class)));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, mAuthDialogNeutralAnsMsg,
                (dialog, id) -> alertDialog.closeOptionsMenu());
        alertDialog.show();
    }

    @Override
    public void onLikeButtonClicked(String likeStatus, String id, int adapterPosition) {
        mAdapterPosition = adapterPosition;
        mPresenter.likeRequest(id, likeStatus);
    }

    @Override
    public void onFavoriteButtonClicked(boolean isFavorite, String gifId, int adapterPosition) {
        mAdapterPosition = adapterPosition;
        if (isFavorite) {
            mPresenter.addToFavoritesRequest(gifId);
        } else {
            mPresenter.deleteFromFavoritesRequest(gifId);
        }
    }

    @Override
    public void startFullScreenActivity(Gif gif) {
        FullScreenActivity.startFullScreenActivity(getActivity(), gif);
    }

    @Override
    public void getGifInfo(String gifId, int adapterPosition) {
        mAdapterPosition = adapterPosition;
        mPresenter.loadGifInfo(gifId);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return getActivity().getLayoutInflater();
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void showErrorLoadingFeed() {
        mGifAdapter.clear();
    }

    @Override
    public void showDefaultToastError() {
        Toast.makeText(getContext(), mErrMsgPrescription, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGifList(List<Gif> gifs) {
        mGifAdapter.changeDataSet(gifs);
    }

    @Override
    public void onGifInfoLoaded(GifInfo gifInfo) {
        mGifAdapter.updateGif(gifInfo, mAdapterPosition);
    }

    @Override
    public void onErrorLikeRequest() {
        mGifAdapter.getPreviousLikeState(mAdapterPosition);
        showDefaultToastError();
    }

    @Override
    public void onErrorFavoritesRequest() {
        mGifAdapter.getPreviousFavoriteState(mAdapterPosition);
        showDefaultToastError();
    }

    protected GifAdapter getGifAdapter() {
        return mGifAdapter;
    }

    protected int getAdapterPosition() {
        return mAdapterPosition;
    }


    public interface BaseCallback {

        DrawerLayout getDrawer();

        void closeDrawer();

        void openDrawer();

    }
}
