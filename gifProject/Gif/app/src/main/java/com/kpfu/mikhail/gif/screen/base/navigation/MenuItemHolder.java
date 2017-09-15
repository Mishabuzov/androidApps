package com.kpfu.mikhail.gif.screen.base.navigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.MenuItem;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

class MenuItemHolder extends RecyclerView.ViewHolder {

    @BindColor(R.color.colorMenuItemSelectedLayout) int mSelectedLayoutColor;

    @BindColor(R.color.colorMenuItemSelectedText) int mSelectedTextColor;

    @BindColor(R.color.colorMenuItemUnselectedText) int mUnselectedTextColor;

    @BindColor(R.color.colorMenuItemUnselectedLayout) int mUnselectedLayoutColor;

    @BindView(R.id.menu_item_name) TextView mMenuTv;

    @BindView(R.id.menu_layout) RelativeLayout mMenuLayout;

    private MenuAdapter mMenuAdapter;

    private MenuAdapter.MenuCallback mMenuCallback;


    MenuItemHolder(@NonNull View itemView, @NonNull MenuAdapter menuAdapter,
                   @NonNull MenuAdapter.MenuCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mMenuAdapter = menuAdapter;
        mMenuCallback = callback;
    }

    public void bind(@NonNull MenuItem item) {
        mMenuTv.setText(item.getItemName());
        selectFirstItem(item.getItemName());
        mMenuLayout.setOnClickListener(view -> {
            unselectMenuItems();
            selectMenuItem();
            mMenuCallback.switchScreen(item);
            mMenuAdapter.setCurrentMenuItem(item);
        });
    }

    private void selectMenuItem() {
        mMenuTv.setTextColor(mSelectedTextColor);
        mMenuLayout.setBackgroundColor(mSelectedLayoutColor);
        saveSelectedValues();
    }

    private void selectFirstItem(int itemName) {
        if (itemName == MenuItem.FEED.getItemName()) {
            selectMenuItem();
        }
    }

    private void unselectMenuItems() {
        mMenuAdapter.getSelectedMenuLayout().setBackgroundColor(mUnselectedLayoutColor);
        mMenuAdapter.getSelectedMenuTv().setTextColor(mUnselectedTextColor);
    }

    private void saveSelectedValues() {
        RelativeLayout selectedLayout = mMenuLayout;
        TextView selectedTv = mMenuTv;
        mMenuAdapter.saveSelectedValues(selectedTv, selectedLayout);
    }
}
