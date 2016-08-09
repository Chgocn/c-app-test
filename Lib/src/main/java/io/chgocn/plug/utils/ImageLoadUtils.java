package io.chgocn.plug.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import io.chgocn.plug.BaseApplication;
import io.chgocn.plug.R;

/**
 * Image Load Utils
 *
 * @author chgocn(chgocn@gmail.com)
 */
public class ImageLoadUtils {

    private static ImageLoadUtils _INSTANCE;

    public static ImageLoadUtils getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new ImageLoadUtils();
        }
        return _INSTANCE;
    }

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public static DisplayImageOptions OPTIONS = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_activity_default)
            .showImageForEmptyUri(R.drawable.ic_activity_default)
            .showImageOnFail(R.drawable.ic_activity_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    public void loadPhotoImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, OPTIONS);
    }

    public void loadPostImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, OPTIONS);
    }

    public void loadImage(String url, final ImageView imageView) {
        imageLoader.displayImage(url, imageView, OPTIONS, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
                if (view.getTag() == null || imageUri.equals(view.getTag())) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
                    ((ImageView) view).setImageBitmap(loadedImage);
                }
            }
        });
    }

    public void removeCache(String url, final ImageView imageView) {
        //移除文件
        if (url != null && !url.isEmpty()) {
            ImageLoadUtils.getInstance().imageLoader.getDiskCache().remove(url);
            //移除key
            ImageSize targetSize = ImageSizeUtils.defineTargetSizeForView(new ImageViewAware(imageView), new ImageSize(BaseApplication.S_WIDTH_PIX, BaseApplication.S_HEIGHT_PIX));
            String memoryCacheKey = MemoryCacheUtils.generateKey(url, targetSize);
            ImageLoadUtils.getInstance().imageLoader.getMemoryCache().remove(memoryCacheKey);
        } else {
            Log.i("ImageLoadUtils", "removeCache url is empty");
        }
    }

    public void loadRounded(String url, ImageView imageView, final DisplayImageOptions options) {
        imageLoader.displayImage(url, imageView, options);
    }

    public void loadRoundedByTag(String url, ImageView imageView, final DisplayImageOptions options) {
        imageLoader.displayImage(url, imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (view.getTag() == null || imageUri.equals(view.getTag())) {
                    imageLoader.displayImage(imageUri, (ImageView) view, options);
                }
            }
        });
    }

    public void loadImage(String url, final ImageView imageView, final DisplayImageOptions options) {
        imageLoader.displayImage(url, imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (view.getTag() == null || imageUri.equals(view.getTag())) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
                    ((ImageView) view).setImageBitmap(loadedImage);
                }
            }
        });
    }
}
