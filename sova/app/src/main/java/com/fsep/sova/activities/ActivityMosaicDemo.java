package com.fsep.sova.activities;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.GridLayout.Spec;
import android.widget.TextView;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;

import java.util.Random;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

//This activity demonstrate mosaic, because server still doesn't work
public class ActivityMosaicDemo extends BaseActivity {

    @BindDimen(R.dimen.mosaic_layout_parent_padding_size) int mMosaicParentPadding;
    @Bind(R.id.grid_for_images) GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_details);
        ButterKnife.bind(this);
        fillGridLayoutByMosaicAlgorithm();
    }

    private int mAmountOfPictures = 55;
    private boolean mIsRectangle = false;

    private int getRandomColor() {
        Random random = new Random();
        int colors[] = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED,
                Color.CYAN};
        int pos = random.nextInt(colors.length);
        return colors[pos];
    }

    private boolean isUnBeautifulCase(int i, int r, int z, int c, int normalScreenSize) {
        if (i == mAmountOfPictures - 2 && r == z && c == 2) {
            mIsRectangle = true;
            setParamsAndPicture(normalScreenSize * 2, GridLayout.spec(r), GridLayout.spec(c, 2), getRandomColor());
            setParamsAndPicture(normalScreenSize * 2, GridLayout.spec(r + 1), GridLayout.spec(c, 2), getRandomColor());
            return true;
        } else if (i == mAmountOfPictures - 1 && r == z && c == 2) {
            setParamsAndPicture(normalScreenSize * 2, GridLayout.spec(r, 2), GridLayout.spec(c, 2), getRandomColor());
            return true;
        } else if (i == mAmountOfPictures - 1 && ((r == z + 1 && c == 2) || (r == z + 4 && c == 0))) {
            mIsRectangle = true;
            setParamsAndPicture(normalScreenSize * 2, GridLayout.spec(r), GridLayout.spec(c, 2), getRandomColor());
            return true;
        } else if (i == mAmountOfPictures - 1 && (r == z + 2 || r == z + 5 || r == z + 3 || r==z&&r!=0) && c < 3) {
            mIsRectangle = true;
            setParamsAndPicture(normalScreenSize * (4-c), GridLayout.spec(r), GridLayout.spec(c, 4 - c), getRandomColor());
            return true;
        } else if (i == mAmountOfPictures - 1 && r==0 && c==0){
            setParamsAndPicture(normalScreenSize * 3, GridLayout.spec(r, 3), GridLayout.spec(c, 3), getRandomColor());
            return true;
        }
        return false;
    }

    private int normalRectangleHeight;

    private void fillGridLayoutByMosaicAlgorithm() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x - mMosaicParentPadding*2;
        int normalScreenSize = (int) (screenWidth * 0.25);
        normalRectangleHeight = normalScreenSize;

        int i = 0;
        int rows = 0;
        int columns = 0;
        int z = 0;
        while (i < mAmountOfPictures) {
            if (((i == mAmountOfPictures - 2) || (i == mAmountOfPictures - 1)) && isUnBeautifulCase(i, rows, z, columns, normalScreenSize)) {
                break;
            }
            if ((rows == z && columns == 0) || (rows == 3 + z && columns == 2)) {
                setParamsAndPicture(normalScreenSize * 2, GridLayout.spec(rows, 2), GridLayout.spec(columns, 2), getRandomColor());
                columns += 2;
                i++;
            } else if ((rows == 1 + z && columns == 0) || (rows == 4 + z && columns == 2)) {
                columns += 2;
            } else {
                setParamsAndPicture(normalScreenSize, GridLayout.spec(rows), GridLayout.spec(columns), getRandomColor());
                columns++;
                i++;
            }
            if (columns == 4) {
                columns = 0;
                if (rows == 5 + z) {
                    z += 6;
                }
                rows++;
            }
        }
    }

    private TextView getColorTv(int color, LayoutParams params) {
        TextView mosaicElement = new TextView(this);
        mosaicElement.setLayoutParams(params);
        mosaicElement.setGravity(Gravity.CENTER);
        mosaicElement.setBackgroundColor(color);
        mosaicElement.setText("Picture");
        return mosaicElement;
    }

    private void setParamsAndPicture(int size, Spec row, Spec col, int color) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(row, col);
        params.width = size;
        if (mIsRectangle) {
            params.height = normalRectangleHeight;
        } else {
            params.height = size;
        }
        gridLayout.addView(getColorTv(color, params), params);
    }
}
