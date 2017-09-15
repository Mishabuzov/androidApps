package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fsep.sova.R;
import com.fsep.sova.adapters.UsersAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionFindUsers;
import com.fsep.sova.network.events.find_users.FindingUsersErrorEvent;
import com.fsep.sova.network.events.find_users.FindingUsersIsEmptyEvent;
import com.fsep.sova.network.events.find_users.FindingUsersIsSuccessEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsersFragment extends BaseRecyclerViewFragment implements SearchView.OnQueryTextListener {

    public static final String EXTRA_CHECKED_USER_ID = "com.fsep.sova.extra_checked_user_id";

    @BindDimen(R.dimen.icons_size_small) int mIconSize;
    @Bind(R.id.user_toolbar) Toolbar mToolbar;
    private static final int USERS_PER_PAGE = 20;
    private UsersAdapter mAdapter;
    private long mCheckedUserId;

    @OnClick(R.id.back_btn)
    public void onBackBtn() {
        getActivity().finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSwipeRefreshLayout().setEnabled(false);
        if(getArguments() != null) {
            mCheckedUserId = getArguments().getLong(EXTRA_CHECKED_USER_ID, -1);
        }
        mAdapter = new UsersAdapter(this, getActivity(), mCheckedUserId);
        getRecyclerView().setAdapter(mAdapter);
        load();
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_user, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
    }

    /*private Drawable resizeImage(int resId)
    {
        // load the origial Bitmap
        Bitmap BitmapOrg = BitmapFactory.decodeResource(getResources(), resId);
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();

        // calculate the scale
        int scaleWidth = mIconSize;
        int scaleHeight = mIconSize;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0,width, height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        searchAction(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        searchAction(newText);
        return false;
    }

    private void searchAction(String query) {
        Action searchAction = new ActionFindUsers.Builder()
                .find(query).build();
        ServiceHelper.getInstance().startActionService(getActivity(), searchAction);
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    public void load() {
        Action action = new ActionFindUsers.Builder()
                .find("")
                .count(USERS_PER_PAGE)
                .from(0)
                .build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Override
    public void onRefresh() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FindingUsersIsSuccessEvent event) {
        mAdapter.setUserInfos(event.getUsers());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FindingUsersIsEmptyEvent event) {
        mAdapter.showEmptyDataView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FindingUsersErrorEvent event) {
        Toast.makeText(getActivity(), R.string.content_get_users_error, Toast.LENGTH_LONG).show();
    }
}
