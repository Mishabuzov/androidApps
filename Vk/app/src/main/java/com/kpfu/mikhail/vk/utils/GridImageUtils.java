package com.kpfu.mikhail.vk.utils;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.attachments.Attachment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.kpfu.mikhail.vk.content.attachments.AttachmentType.PHOTO;
import static com.kpfu.mikhail.vk.content.attachments.AttachmentType.VIDEO;

public class GridImageUtils {

    private Context mContext;
    private GridLayout mMosaicLayout;
    private List<Attachment> mAttachments;
//    private List<String> mPhotoUrls = new ArrayList<>();

    private View myView;
    private RelativeLayout galleryLayout;
    private RelativeLayout.LayoutParams relativeParams;
    private boolean mIsMoreThan3Views;

    public GridImageUtils(@NonNull Context context,
                          @NonNull GridLayout mosaicLayout,
                          @NonNull List<Attachment> attachments) {
        mContext = context;
        mMosaicLayout = mosaicLayout;
        mAttachments = new ArrayList<>();
        filterAttachments(attachments);
    }

    private void filterAttachments(List<Attachment> attachments) {
        for (Attachment attachment : attachments) {
            if (attachment.getType() == PHOTO || attachment.getType() == VIDEO) {
                mAttachments.add(attachment);
            }
        }
        if(mAttachments.size()!=0){
            mMosaicLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setParamsAndPicture(int width,
                                    int height,
                                    LayoutParams params,
                                    int contentNum) {
        params.width = width;
        params.height = height;
        RelativeLayout attachment;
        if(mAttachments.size()!=0) {
            attachment = createImageView(mAttachments.get(contentNum), params);
        } else {
            attachment = createImageView(null, params);
        }
        mMosaicLayout.addView(attachment, params);
    }

    public void fillGridLayoutByMosaicAlgorithm(int mosaicParentPadding, int mosaicImageMargins) {
      /*  int mosaicParentPadding = (int) mContext.getResources().getDimension(R.dimen.mosaic_layout_parent_padding_size);
        int mosaicImageMargins = (int) mContext.getResources().getDimension(R.dimen.mosaic_layout_image_margins);
       */
      Point size = new Point();
        int screenWidth = (size.x - 2 * mosaicParentPadding);
        int imageWidth = screenWidth / 3;
        int imageHeight = screenWidth / 4;

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
        myView = inflater.inflate(R.layout.gallery_image_item, null);
        galleryLayout = (RelativeLayout) myView.findViewById(R.id.gallery_layout);
        relativeParams = new RelativeLayout.LayoutParams(params);
        ImageView mosaicElement = (ImageView) myView.findViewById(R.id.gallery_image);
        setImageParams(mosaicElement, relativeParams, getContentImageUrl(attachment));
        setMosaicCase(attachment);
        return galleryLayout;
    }

    private String getContentImageUrl(Attachment attachment) {
        if(attachment == null){
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

    private void setMosaicCase(Attachment attachment) {
       /* if (attachment.isVideo()) {
            ImageView videoMark = (ImageView) myView.findViewById(R.id.video_mark);
            videoMark.setVisibility(View.VISIBLE);
        }*/
        if (mIsMoreThan3Views) {
            ImageView shadow = (ImageView) myView.findViewById(R.id.shadow);
            shadow.setLayoutParams(relativeParams);
            shadow.setVisibility(View.VISIBLE);

            TextView tvQuantityOfImage = (TextView) myView.findViewById(R.id.tv_images_amount);
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
            ImageView shadow = (ImageView) myView.findViewById(R.id.shadow);
            shadow.setVisibility(View.VISIBLE);
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
    private void setImageParams(ImageView mosaicElement, ViewGroup.LayoutParams params, String url) {
        mosaicElement.setLayoutParams(params);
        mosaicElement.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(url!=null) {
            Picasso.with(mosaicElement.getContext()).load(url).into(mosaicElement);
        } else {
            Picasso.with(mosaicElement.getContext()).load(android.R.color.transparent).into(mosaicElement);
        }
    }

}
