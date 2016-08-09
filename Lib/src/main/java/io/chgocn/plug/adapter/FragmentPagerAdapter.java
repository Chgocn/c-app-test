package io.chgocn.plug.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.chgocn.plug.fragment.FragmentProvider;

/**
 * Pager adapter that provides the current fragment
 *
 * @author chgocn (chgocn@gmail.com)
 */
public abstract class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter implements FragmentProvider {

    private final FragmentActivity activity;

    private Fragment selected;

    /**
     * @param activity
     */
    public FragmentPagerAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());

        this.activity = activity;
    }

    /**
     * @param fragment
     */
    public FragmentPagerAdapter(Fragment fragment) {
        super(fragment.getChildFragmentManager());

        this.activity = fragment.getActivity();
    }

    @Override
    public Fragment getSelected() {
        return selected;
    }

    @Override
    public void setPrimaryItem(final ViewGroup container, final int position, final Object object) {
        super.setPrimaryItem(container, position, object);

        boolean changed = false;
        if (object instanceof Fragment) {
            changed = object != selected;
            selected = (Fragment) object;
        } else {
            changed = object != null;
            selected = null;
        }

        if (changed){
            activity.invalidateOptionsMenu();
        }
    }

    // [+] save status
    private List<WeakReference<Fragment>> list = new ArrayList<>();

    public List<WeakReference<Fragment>> getFragments() {
        for (int i = list.size() - 1; i >= 0; --i) {
            if (null == list.get(i).get()) {
                list.remove(i);
            }
        }
        return list;
    }

    protected void saveFragment(Fragment fragment) {
        if (fragment == null || list.contains(new WeakReference<>(fragment))){
            return;
        }

//        for (WeakReference<Fragment> item : list) {
//            if (item.get() == fragment){
//                return;
//            }
//        }

        list.add(new WeakReference<>(fragment));
    }
    // [-] save status

}

