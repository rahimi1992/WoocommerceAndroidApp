package com.test.newshop1.ui.categoryActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.databinding.CategoryItemSimpleBinding;
import com.test.newshop1.databinding.CategoryItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class SubCatRecyclerViewAdapter extends RecyclerView.Adapter<SubCatRecyclerViewAdapter.BaseViewHolder> {

    public static final int NORMAL_VIEW_TYPE = 1;
    public static final int SIMPLE_VIEW_TYPE = 2;
    private int viewType = NORMAL_VIEW_TYPE;
    private List<Category> categories;
    private final OnCatItemClickListener mListener;

    public SubCatRecyclerViewAdapter(OnCatItemClickListener listener) {
        mListener = listener;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public void submitList(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case NORMAL_VIEW_TYPE:
                return new CategoryViewHolder(CategoryItemBinding.inflate(inflater, parent, false));
            case SIMPLE_VIEW_TYPE:
                return new CategorySimpleViewHolder(CategoryItemSimpleBinding.inflate(inflater, parent, false));

        }
        return new CategoryViewHolder(CategoryItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        holder.bind(categories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public void selectItem(int position) {
        categories.get(position).setSelected(true);
    }

    public void unSelectItem(int position) {
        categories.get(position).setSelected(false);
    }


    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        private BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void bind(Category category, int position);
    }

    public class CategoryViewHolder extends BaseViewHolder {
        private final CategoryItemBinding binding;

        CategoryViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(mListener);
        }

        @Override
        public void bind(Category category, int position) {
            binding.setCategory(category);
        }
    }

    public class CategorySimpleViewHolder extends BaseViewHolder {
        private final CategoryItemSimpleBinding binding;

        CategorySimpleViewHolder(CategoryItemSimpleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(mListener);
        }

        @Override
        public void bind(Category category, int position) {
            binding.setCategory(category);
            binding.setPosition(position);
        }
    }
}
