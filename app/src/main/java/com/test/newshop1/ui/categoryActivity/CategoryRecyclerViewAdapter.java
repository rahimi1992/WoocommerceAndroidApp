package com.test.newshop1.ui.categoryActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.databinding.CategoryItemGridBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryImageViewHolder> {

    private List<Category> mCategories;

    private OnCatItemClickListener listener;



    CategoryRecyclerViewAdapter() {
        mCategories = new ArrayList<>();
    }
    void setOnItemClickListener(OnCatItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CategoryImageViewHolder(CategoryItemGridBinding.inflate(inflater, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryImageViewHolder holder, int position) {

        Category categoryItem = mCategories.get(position);
        holder.binding.setCategory(categoryItem);
        holder.binding.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return ((mCategories != null) && (mCategories.size() != 0) ? mCategories.size() : 0);
    }


    void loadNewData(List<Category> newCategory, boolean reset){

        if (newCategory !=null && newCategory.size()!=0){
            if (reset){
                mCategories = newCategory;
            }else {
                mCategories.addAll(newCategory);
            }

        }
        notifyDataSetChanged();
    }

//    public Category getCategory(int position){
//        return ((mCategories != null) && (mCategories.size() != 0) ? mCategories.get(position) : null);
//    }


    class CategoryImageViewHolder extends RecyclerView.ViewHolder {

        final CategoryItemGridBinding binding;

        CategoryImageViewHolder(CategoryItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
