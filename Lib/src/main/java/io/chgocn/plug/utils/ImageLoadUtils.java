package io.chgocn.plug.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
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

    private static ImageLoadUtils _instance;

    public static ImageLoadUtils getInstance() {
        if (_instance == null) {
            _instance = new ImageLoadUtils();
        }
        return _instance;
    }

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_activity_default)
            .showImageForEmptyUri(R.drawable.ic_activity_default)
            .showImageOnFail(R.drawable.ic_activity_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

//    public static DisplayImageOptions optionsAvatar = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.ic_exception_blank)
//            .showImageForEmptyUri(R.drawable.ic_exception_blank)
//            .showImageOnFail(R.drawable.ic_exception_error)
//            .cacheInMemory(true)
//            .cacheOnDisk(true)
//            .considerExifParams(true)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .imageScaleType(ImageScaleType.EXACTLY)
//            .build();

//    public static DisplayImageOptions optionsRounded = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.ic_exception_blank)
//            .showImageForEmptyUri(R.drawable.ic_exception_error)
//            .showImageOnFail(R.drawable.ic_exception_error)
//            .cacheInMemory(true)
//            .cacheOnDisk(true)
//            .considerExifParams(true)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .imageScaleType(ImageScaleType.EXACTLY)
//            .displayer(new RoundedBitmapDisplayer(360))
//            .build();

    public void loadPhotoImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, options);
    }

    public void loadPostImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, options);
    }

//    public void loadAvatar(String url, ImageView imageView) {
//        imageLoader.displayImage(url, imageView, optionsAvatar);
//    }

//    public void loadAvatar(Uri uri, ImageView imageView) {
//        loadAvatar(uri.toString(), imageView);
//    }

//    public void loadRounded(String url, ImageView imageView) {
//        imageLoader.displayImage(url, imageView, optionsRounded);
//    }

    public void loadImage(String url, final ImageView imageView) {
        imageLoader.displayImage(url, imageView, options, new SimpleImageLoadingListener() {
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

//    public void loadRoundedByTag(String url, ImageView imageView) {
//        imageLoader.displayImage(url, imageView, optionsRounded, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                if (view.getTag() == null || imageUri.equals(view.getTag())) {
//                    imageLoader.displayImage(imageUri, (ImageView) view, optionsRounded);
//                }
//            }
//        });
//    }


//    /**
//     * 从内存卡中异步加载本地图片
//     *
//     * @param uri
//     * @param imageView
//     */
//    public void displayFromSDCard(String uri, ImageView imageView) {
//        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
//        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
//    }
//
//    /**
//     * 从assets文件夹中异步加载图片
//     *
//     * @param imageName
//     *            图片名称，带后缀的，例如：1.png
//     * @param imageView
//     */
//    public void dispalyFromAssets(String imageName, ImageView imageView) {
//        // String imageUri = "assets://image.png"; // from assets
//        ImageLoader.getInstance().displayImage("assets://" + imageName, imageView);
//    }
//
//    /**
//     * 从drawable中异步加载本地图片
//     *
//     * @param imageId
//     * @param imageView
//     */
//    public void displayFromDrawable(int imageId, ImageView imageView) {
//        // String imageUri = "drawable://" + R.drawable.image; // from drawables
//        // (only images, non-9patch)
//        ImageLoader.getInstance().displayImage("drawable://" + imageId, imageView);
//    }
//
//    /**
//     * 从内容提提供者中抓取图片
//     */
//    public void displayFromContent(String uri, ImageView imageView) {
//        // String imageUri = "content://media/external/audio/albumart/13"; //
//        // from content provider
//        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
//    }

    public static DisplayImageOptions optionsBoyAvatar = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_avatar_boy_default)
            .showImageForEmptyUri(R.drawable.ic_avatar_boy_default)
            .showImageOnFail(R.drawable.ic_avatar_boy_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(new RoundedBitmapDisplayer(360))
            .build();

    public static DisplayImageOptions optionsGirlAvatar = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_avatar_girl_default)
            .showImageForEmptyUri(R.drawable.ic_avatar_girl_default)
            .showImageOnFail(R.drawable.ic_avatar_girl_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(new RoundedBitmapDisplayer(360))
            .build();

    public static DisplayImageOptions optionsGroupAvatar = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_group_default)
            .showImageForEmptyUri(R.drawable.ic_group_default)
            .showImageOnFail(R.drawable.ic_group_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(new RoundedBitmapDisplayer(360))
            .build();

    public static DisplayImageOptions optionsGroupActivity = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_activity_default)
            .showImageForEmptyUri(R.drawable.ic_activity_default)
            .showImageOnFail(R.drawable.ic_activity_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(new RoundedBitmapDisplayer(360))
            .build();

    public void removeCache(String url, final ImageView imageView) {
        //移除文件
        if (url != null && !url.isEmpty()) {
            ImageLoadUtils.getInstance().imageLoader.getDiskCache().remove(url);
            //移除key
            ImageSize targetSize = ImageSizeUtils.defineTargetSizeForView(new ImageViewAware(imageView), new ImageSize(BaseApplication.sWidthPix, BaseApplication.sHeightPix));
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
