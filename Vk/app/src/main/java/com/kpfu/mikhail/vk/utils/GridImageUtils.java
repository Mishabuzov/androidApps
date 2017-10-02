package com.kpfu.mikhail.vk.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.attachments.Attachment;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.kpfu.mikhail.vk.content.attachments.AttachmentType.PHOTO;

public class GridImageUtils {

    private Context mContext;
    private GridLayout mMosaicLayout;
    private List<Attachment> mAttachments;
//    private List<String> mPhotoUrls = new ArrayList<>();

    private boolean mIsMoreThan3Views;

    public GridImageUtils(@NonNull Context context,
                          @NonNull GridLayout mosaicLayout,
                          @NonNull List<Attachment> attachments) {
        mContext = context;
        mMosaicLayout = mosaicLayout;
        mAttachments = attachments;
//        filterAttachments(attachments);
    }

   /* private void filterAttachments(List<Attachment> attachments) {
        for (Attachment attachment : attachments) {
            if (attachment.getType() == PHOTO || attachment.getType() == VIDEO) {
                mAttachments.add(attachment);
            }
        }
        if (mAttachments.size() != 0) {
            mMosaicLayout.setVisibility(VISIBLE);
        }
    }*/

    public void setParamsAndPicture(int width,
                                    int height,
                                    LayoutParams params,
                                    int contentNum) {
        params.width = width;
        params.height = height;
        RelativeLayout attachment;
        if (mAttachments.size() != 0) {
            attachment = createImageView(mAttachments.get(contentNum), params);
        } else {
            attachment = createImageView(null, params);
        }
        mMosaicLayout.addView(attachment, params);
    }

    public void fillGridLayoutByMosaicAlgorithm() {
        int mosaicParentPadding = (int) mContext.getResources().getDimension(R.dimen.mosaic_layout_parent_padding_size);
        int mosaicImageMargins = (int) mContext.getResources().getDimension(R.dimen.mosaic_layout_image_margins);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
     /*   int width = size.x;
        int height = size.y;
        Point size = new Point();*/
//        int screenWidth = (size.x - 2 * mosaicParentPadding);
        int screenWidth = size.x;
        /*if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            screenWidth = size.x;
        } else {
            screenWidth = size.y;
        }*/
        int imageWidth = screenWidth / 3;
        int imageHeight = screenWidth / 3;
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageHeight = screenWidth / 5;
        }

        int i = 0;
        int rows = 0;
        int columns = 0;

        LayoutParams params;
        switch (mAttachments.size()) {
            case 0:
            case 1:
                //1
                params = new LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns, 2));
                params.setGravity(Gravity.CENTER_HORIZONTAL);
                params.setMargins(0, mosaicImageMargins, 0, 0);
                setParamsAndPicture(screenWidth, imageHeight * 2, params, i);
                break;
            case 2:
                //1
                params = new LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns));
                params.setMargins(0, (int) mosaicImageMargins, (int) mosaicImageMargins, 0);
                setParamsAndPicture(screenWidth / 2, imageHeight * 2, params, i);
                //2
                i++;
                params = new LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns + 1));
                params.setMargins(0, (int) mosaicImageMargins, 0, 0);
                setParamsAndPicture(screenWidth / 2, imageHeight * 2, params, i);
                break;
            default:
                //1
                params = new LayoutParams(GridLayout.spec(rows, 2), GridLayout.spec(columns));
                params.setMargins(0, (int) mosaicImageMargins, (int) mosaicImageMargins, 0);
                setParamsAndPicture(imageWidth * 2, imageHeight * 2 + (int) mosaicImageMargins, params, i);
                //2
                i++;
                params = new LayoutParams(GridLayout.spec(rows), GridLayout.spec(columns + 1));
                params.setMargins(0, (int) mosaicImageMargins, 0, 0);
                setParamsAndPicture(imageWidth, imageHeight, params, i);
                //blurring
                if (mAttachments.size() > 3) {
                    setMoreThan3ViewsFlag(true);
                }
                //3
                i++;
                params = new LayoutParams(GridLayout.spec(rows + 1), GridLayout.spec(columns + 1));
                params.setMargins(0, mosaicImageMargins, 0, 0);
                setParamsAndPicture(imageWidth, imageHeight, params, i);
        }
    }

    private RelativeLayout createImageView(Attachment attachment, LayoutParams params) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mosaicView = inflater.inflate(R.layout.gallery_image_item, null);
        RelativeLayout galleryLayout = (RelativeLayout) mosaicView.findViewById(R.id.gallery_layout);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(params);
        ImageView mosaicElement = (ImageView) mosaicView.findViewById(R.id.gallery_image);
        LinearLayout imagePrBarLayout = (LinearLayout) mosaicView.findViewById(R.id.pr_bar_layout);
        TextView imageLoadingErrorTv = (TextView) mosaicView.findViewById(R.id.error_image_tv);
//        mProgressBar = (ProgressBar) myView.findViewById(R.id.pr_bar);
        shadowImage(mosaicView, relativeParams);
        setImageParams(mosaicElement, relativeParams, getContentImageUrl(attachment),
                imagePrBarLayout, mIsMoreThan3Views, mBlurringLayout, imageLoadingErrorTv);
        return galleryLayout;
    }

    private String getContentImageUrl(Attachment attachment) {
        if (attachment == null) {
            return null;
        } else if (attachment.getType() == PHOTO) {
            return attachment.getPhoto().getMediumQualityPhoto();
        } else {
            return attachment.getVideo().getThumbnail();
        }
    }

    private void setMoreThan3ViewsFlag(boolean isMoreThan3Views) {
        mIsMoreThan3Views = isMoreThan3Views;
    }

    private RelativeLayout mBlurringLayout;

    private void shadowImage(View mosaicView, RelativeLayout.LayoutParams relativeParams) {
       /* if (attachment.isVideo()) {
            ImageView videoMark = (ImageView) myView.findViewById(R.id.video_mark);
            videoMark.setVisibility(View.VISIBLE);
        }*/
        if (mIsMoreThan3Views) {
            ImageView shadow = (ImageView) mosaicView.findViewById(R.id.shadow);
            shadow.setLayoutParams(relativeParams);
            mBlurringLayout = (RelativeLayout) mosaicView.findViewById(R.id.blurring_layout);
            TextView tvQuantityOfImage = (TextView) mosaicView.findViewById(R.id.tv_images_amount);
            tvQuantityOfImage.setText("+" + String.valueOf(mAttachments.size() - 2));
//            setGoToGalleryListener(galleryLayout);
        }
       /* else {
            setShowContentListener(galleryLayout, attachment);
        }*/
    }

 /*   private void setCaseForGalleryFragment(Content content) {
        setShowContentListener(galleryLayout, content);
        if (content.isVideo()) {
            ImageView ivPlay = (ImageView) myView.findViewById(R.id.iv_play);
            ivPlay.setVisibility(View.VISIBLE);
            ImageView mShadow = (ImageView) myView.findViewById(R.id.mShadow);
            mShadow.setVisibility(View.VISIBLE);
        }
    }*/

    /*private void setGoToGalleryListener(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getActivity(), GalleryActivity.class);
                intent.putParcelableArrayListExtra(Constants.MEDIA_CONTENT, (ArrayList<Content>) mAttachments);
                mContext.startActivity(intent);
            }
        });
    }
*/
    private void setImageParams(ImageView mosaicElement,
                                ViewGroup.LayoutParams params,
                                String url,
                                @NonNull LinearLayout progressBarLayout,
                                boolean isMoreThan3Views,
                                @Nullable RelativeLayout blurringLayout,
                                @NonNull TextView imageLoadingErrorTv) {
        mosaicElement.setLayoutParams(params);
        mosaicElement.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (url != null) {
//            Picasso.with(mMosaicElement.getContext()).load(url).resize(width, height).into(mMosaicElement);
            Picasso.with(mosaicElement.getContext())
                    .load(url)
                    .into(mosaicElement,
                            new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBarLayout.setVisibility(GONE);
                                    mosaicElement.setVisibility(VISIBLE);
                                    if (isMoreThan3Views && blurringLayout != null) {
                                        blurringLayout.setVisibility(VISIBLE);
                                    }
                                }

                                @Override
                                public void onError() {
                                    imageLoadingErrorTv.setText(R.string.error_loading_image);
                                }
                            });
        } else {
            Picasso.with(mosaicElement.getContext()).load(android.R.color.transparent).into(mosaicElement);
        }
    }

}
