package io.chgocn.plug.utils;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Utilities for working with the {@link View} class
 *
 * @author Kevin Sawicki <kevinsawicki@gmail.com>
 * @author chgocn
 */
public final class ViewUtils {

    /**
     * Set visibility of given view to be gone or visible
     * <p/>
     * This method has no effect if the view visibility is currently invisible
     *
     * @param view
     * @param gone
     * @return view
     */
    public static <V extends View> V setGone(final V view, final boolean gone) {
        if (view != null){
            if (gone) {
                if (GONE != view.getVisibility()){
                    view.setVisibility(GONE);
                }
            } else {
                if (VISIBLE != view.getVisibility()){
                    view.setVisibility(VISIBLE);
                }
            }
        }
        return view;
    }

    /**
     * Set visibility of given view to be invisible or visible
     * <p/>
     * This method has no effect if the view visibility is currently gone
     *
     * @param view
     * @param invisible
     * @return view
     */
    public static <V extends View> V setInvisible(final V view, final boolean invisible) {
        if (view != null){
            if (invisible) {
                if (INVISIBLE != view.getVisibility()){
                    view.setVisibility(INVISIBLE);
                }
            } else {
                if (VISIBLE != view.getVisibility()){
                    view.setVisibility(VISIBLE);
                }
            }
        }
        return view;
    }

    public static void increaseHitRectBy(int amount, View delegate) {
        increaseHitRectBy(amount, amount, amount, amount, delegate);
    }

    public static void increaseHitRectBy(final int top, final int left, final int bottom, final int right, final View delegate) {
        final View parent = (View)delegate.getParent();
        if(parent != null && delegate.getContext() != null) {
            parent.post(new Runnable() {
                public void run() {
                    float densityDpi = (float)delegate.getContext().getResources().getDisplayMetrics().densityDpi;
                    Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= ViewUtils.transformToDensityPixel(top, densityDpi);
                    r.left -= ViewUtils.transformToDensityPixel(left, densityDpi);
                    r.bottom += ViewUtils.transformToDensityPixel(bottom, densityDpi);
                    r.right += ViewUtils.transformToDensityPixel(right, densityDpi);
                    parent.setTouchDelegate(new TouchDelegate(r, delegate));
                }
            });
        }

    }

    public static int transformToDensityPixel(int regularPixel, DisplayMetrics displayMetrics) {
        return transformToDensityPixel(regularPixel, (float)displayMetrics.densityDpi);
    }

    public static int transformToDensityPixel(int regularPixel, float densityDpi) {
        return (int)((float)regularPixel * densityDpi);
    }

    private ViewUtils() {
    }
}
