package com.test.newshop1.ui.ordersActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.R;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.List;

public class OrdersFragment extends Fragment {

    private OrdersViewModel mViewModel;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    private OrderRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.orders_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.container);
        adapter = new OrderRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this,factory).get(OrdersViewModel.class);
        mViewModel.getOrders().observe(this, adapter::setOrders);
    }

}
