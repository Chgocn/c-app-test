package io.chgocn.plug.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

/**
 * Base Fragment offers
 * umeng analytics
 * home optional selected
 * add fragment state.
 *
 * @author chgocn(chgocn@gmail.com)
 */
public abstract class BaseDeterminFragment extends android.support.v4.app.Fragment {

    //fragment state
    private boolean fragmentResume=false;
    private boolean fragmentVisible=false;
    private boolean fragmentOnCreated=false;

    /**
     * Is this fragment usable from the UI-thread
     *
     * @return true if usable, false otherwise
     */
    protected boolean isUsable() {
        return getActivity() != null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isUsable())
            return false;

        switch (item.getItemId()) {
            case (android.R.id.home):
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(),container,false);
        // & fuck shareSDK，not support api callback tag.
        if (!fragmentResume && fragmentVisible) {//
            onFragmentCreateView(view);
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()){
            fragmentResume=true;
            fragmentVisible=false;
            fragmentOnCreated=true;
            onFragmentVisibleAndResumed();
        } else if (isVisibleToUser){
            fragmentResume=false;
            fragmentVisible=true;
            fragmentOnCreated=true;
            onFragmentCreated();
        } else if(!isVisibleToUser && fragmentOnCreated){// only when you go out of fragment screen
            fragmentVisible=false;
            fragmentResume=false;
        }
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected abstract int getContentView();

    /**
     * you must Initialize variables because this method only be called when first time fragment is created.
     * @param rootView:root view of fragment xml.
     */
    protected void onFragmentCreateView(View rootView){

    }

    /**
     * do something only at fragment screen is resumed.
     */
    protected void onFragmentCreated() {

    }

    /**
     * do something only at fragment onCreated.
     */
    protected void onFragmentVisibleAndResumed() {

    }

    // [+] umeng analytics
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }
    // [-] umeng analytics

}
