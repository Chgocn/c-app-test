package io.chgocn.app.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.ViewGroup;

import io.chgocn.app.R;
import io.chgocn.app.fragment.NavFragment;
import io.chgocn.plug.adapter.FragmentPagerAdapter;

/**
 * Created by chgocn(chgocn@gmail.com).
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    private Class[] fragmentArray = new Class[]{Fragment.class, NavFragment.class, Fragment.class};
    private int[] titleArray = new int[]{R.string.test1, R.string.test2, R.string.test3};

    /**
     * Create pager adapter
     *
     * @param activity
     */
    public HomeFragmentAdapter(AppCompatActivity activity) {
        super(activity);

        resources = activity.getResources();
    }

    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return fragmentArray.length;
    }

    @Override
    public Fragment getItem(int position) {
        try{
            return (Fragment)fragmentArray[position].newInstance();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        //return position < fragmentArray.length ? fragmentArray[position] : null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position < titleArray.length ? resources.getString(titleArray[position]) : null;
    }
}
