package com.example.owner.project_final.firebase;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.example.owner.project_final.R;

public class ImageLoaderHelper {

    public static void setProfileImage(Context context, Uri imageUri, ImageView imageView, String imageUpdate) {
        if (null == imageUri || "".equals(imageUri.toString())) {
            int placeHolderResId = 0;
            //[오투잡]넣고싶은 디폴트 이미지 현재는 0 null 을 넣음;
            Glide.with(context)
                    .load(imageUri)
                    .signature(new StringSignature(imageUpdate))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeHolderResId)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imageUri)
                    .signature(new StringSignature(imageUpdate))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
