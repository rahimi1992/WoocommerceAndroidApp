package com.test.newshop1.ui.ordersActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.newshop1.R;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.paymentActivity.PaymentActivity;
import com.test.newshop1.utilities.InjectorUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersFragment extends Fragment {
    private static final String TAG = "OrdersFragment";

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

        adapter.setOnPaymentListener(this::onPaymentButtonClicked);
    }

    private void onPaymentButtonClicked(Order order){

        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PaymentActivity.ORDER_ID, String.valueOf(order.getId()));
        startActivity(intent);
        Log.d(TAG, "onPaymentButtonClicked: " + order.getId());
        //Toast.makeText(getActivity(), "Not Implemented", Toast.LENGTH_SHORT).show();
    }
}
