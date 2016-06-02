package io.chgocn.plug.fragment;

import android.support.v4.app.Fragment;

/**
 * Provides a fragment
 * @author chgocn(chgocn@gmail.com)
 */
public interface FragmentProvider {

    /**
     * Get selected fragment
     *
     * @return fragment
     */
    Fragment getSelected();
}
