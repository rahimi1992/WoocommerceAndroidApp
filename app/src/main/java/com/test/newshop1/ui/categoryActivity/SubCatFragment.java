package com.test.newshop1.ui.categoryActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SubCatFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_PARENT_ID = "parent-id";
    // TODO: Customize parameters
    private int parentId = 1;
    private OnCatItemClickListener mListener;
    private CategoryViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubCatFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SubCatFragment newInstance(int parentId) {
        SubCatFragment fragment = new SubCatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARENT_ID, parentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            parentId = getArguments().getInt(ARG_PARENT_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_cat_list, container, false);

        viewModel = CategoryActivity.obtainViewModel(getActivity());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            SubCatRecyclerViewAdapter adapter = new SubCatRecyclerViewAdapter(mListener);
            adapter.setViewType(SubCatRecyclerViewAdapter.NORMAL_VIEW_TYPE);
            recyclerView.setAdapter(adapter);
            recyclerView.setRotationY(180);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter.submitList(viewModel.getCategories(parentId));
        }

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCatItemClickListener) {
            mListener = (OnCatItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
