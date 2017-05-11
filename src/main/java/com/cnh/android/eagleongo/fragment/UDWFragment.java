package com.cnh.android.eagleongo.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cnh.android.eagleongo.R;
import com.cnh.android.eagleongo.model.SingleUdwRecyclerViewAdapter;
import com.cnh.android.eagleongo.view.RecyclerItemTouchHelperCallback;
import com.cnh.android.eagleongo.view.SingleUdwViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by f10210c on 5/11/2017.
 */
public class UDWFragment extends Fragment {
    @BindView(R.id.recyclerview_mainscreen)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_udw_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SingleUdwRecyclerViewAdapter adapter = (SingleUdwRecyclerViewAdapter)mRecyclerView.getAdapter();

        int id = item.getItemId();
        if (id == R.id.action_show_pfudw) {
            adapter.setData(getPfUdws(getContext()));
            return true;
        }

        if (id == R.id.action_show_agudw) {
            adapter.setData(getAgUdws(getContext()));
            return true;
        }

        if (id == R.id.action_show_ymudw) {
            adapter.setData(getYmUdws(getContext()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_udw, container, false);
        ButterKnife.bind(this, view);

        // Init UDW list with PF UDWs as default
        SingleUdwRecyclerViewAdapter adapter = new SingleUdwRecyclerViewAdapter(getContext());
        adapter.setData(getPfUdws(getContext()));

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                OrientationHelper.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(
                new SingleUdwRecyclerViewAdapter.SpaceItemDecoration(2)); //TODO: move 2px to dimen
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new RecyclerItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        } else {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        }
    }

    // TODO: so ugly :(  will build a factory for UDWs.
    private List<SingleUdwViewHolder.UdwItem> getPfUdws(Context context) {
        List<SingleUdwViewHolder.UdwItem> data = new ArrayList<>();
        int i = 0;

        SingleUdwViewHolder.UdwItem item = new SingleUdwViewHolder.UdwItem(context, i++,
                "TotalFuelUsedUDW",
                "com.cnh.pf.pfudwservice",
                "com.cnh.pf.pfudwservice.widget.TotalFuelUsedUDW");
        data.add(item);

        // Need set el.param as JSON string
//        item = new SingleUdwViewHolder.UdwItem(context, i++,
//                "GroundSpeedUDW",
//                "com.cnh.pf.pfudwservice",
//                "com.cnh.pf.pfudwservice.widget.GroundSpeedUDW");
//        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "FuelUsedUDW",
                "com.cnh.pf.pfudwservice",
                "com.cnh.pf.pfudwservice.widget.FuelUsedUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "AverageWorkingRateUDW",
                "com.cnh.pf.pfudwservice",
                "com.cnh.pf.pfudwservice.widget.AverageWorkingRateUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "TimeInWorkUDW",
                "com.cnh.pf.pfudwservice",
                "com.cnh.pf.pfudwservice.widget.TimeInWorkUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "TimeInTaskUDW",
                "com.cnh.pf.pfudwservice",
                "com.cnh.pf.pfudwservice.widget.TimeInTaskUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "OverlapControlUDW",
                "com.cnh.pf.rscudwservice",
                "com.cnh.pf.rscudwservice.widget.OverlapControlUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "BoundaryControlUDW",
                "com.cnh.pf.rscudwservice",
                "com.cnh.pf.rscudwservice.widget.BoundaryControlUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "OverlapControlUDW",
                "com.cnh.pf.rscudwservice",
                "com.cnh.pf.rscudwservice.widget.OverlapControlUDW");
        data.add(item);

        return data;
    }

    private List<SingleUdwViewHolder.UdwItem> getAgUdws(Context context) {
        List<SingleUdwViewHolder.UdwItem> data = new ArrayList<>();
        int i = 0;

        SingleUdwViewHolder.UdwItem item = new SingleUdwViewHolder.UdwItem(context, i++,
                "CrossTrackErrorStatusUDW",
                "com.cnh.pf.agudwservice",
                "com.cnh.pf.agudwservice.widget.CrossTrackErrorStatusUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "RowGuideOffsetUDW",
                "com.cnh.pf.agudwservice",
                "com.cnh.pf.agudwservice.widget.RowGuideOffsetUDW");
        data.add(item);

        return data;
    }

    private List<SingleUdwViewHolder.UdwItem> getYmUdws(Context context) {
        List<SingleUdwViewHolder.UdwItem> data = new ArrayList<>();
        int i = 0;

        SingleUdwViewHolder.UdwItem item = new SingleUdwViewHolder.UdwItem(context, i++,
                "AverageYieldUDW",
                "com.cnh.pf.ymudwservice",
                "com.cnh.pf.ymudwservice.widget.AverageYieldUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "AverageYieldCounterUDW",
                "com.cnh.pf.ymudwservice",
                "com.cnh.pf.ymudwservice.widget.AverageYieldCounterUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "AverageMoistureUDW",
                "com.cnh.pf.ymudwservice",
                "com.cnh.pf.ymudwservice.widget.AverageMoistureUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "AverageFlowUDW",
                "com.cnh.pf.ymudwservice",
                "com.cnh.pf.ymudwservice.widget.AverageFlowUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "CropTemperatureUDW",
                "com.cnh.pf.ymudwservice",
                "com.cnh.pf.ymudwservice.widget.CropTemperatureUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "AverageFlowCounterUDW",
                "com.cnh.pf.ymudwservice",
                "com.cnh.pf.ymudwservice.widget.AverageFlowCounterUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "CrossTrackErrorStatusUDW",
                "com.cnh.pf.agudwservice",
                "com.cnh.pf.agudwservice.widget.CrossTrackErrorStatusUDW");
        data.add(item);

        item = new SingleUdwViewHolder.UdwItem(context, i++,
                "RowGuideOffsetUDW",
                "com.cnh.pf.agudwservice",
                "com.cnh.pf.agudwservice.widget.RowGuideOffsetUDW");
        data.add(item);

        return data;
    }
}
