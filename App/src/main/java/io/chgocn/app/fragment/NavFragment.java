package io.chgocn.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.chgocn.app.R;
import io.chgocn.plug.fragment.BaseFragment;

public class NavFragment extends BaseFragment  {

    private RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_nav;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

    }

}
