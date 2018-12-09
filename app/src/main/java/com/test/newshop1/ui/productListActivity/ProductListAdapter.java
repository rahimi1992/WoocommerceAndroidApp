package com.test.newshop1.ui.productListActivity;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.databinding.ProductItemBinding;
import com.test.newshop1.ui.OnItemClickListener;

public class ProductListAdapter extends PagedListAdapter<Product, ProductListAdapter.CustomItemHolder> {

    private OnItemClickListener onItemClickListener;

    ProductListAdapter() {
        super(DIFF_CALLBACK);
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CustomItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ProductListAdapter.CustomItemHolder(ProductItemBinding.inflate(inflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomItemHolder holder, int position) {
        Product product = getItem(position);
        holder.binding.setProduct(product);
        holder.binding.setListener(onItemClickListener);

    }

    class CustomItemHolder extends RecyclerView.ViewHolder {

        final ProductItemBinding binding;

        CustomItemHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {

                @Override
                public boolean areItemsTheSame(Product oldConcert, Product newConcert) {
                    return oldConcert.getId().equals(newConcert.getId());
                }

                @Override
                public boolean areContentsTheSame(Product oldConcert,
                                                  @NonNull Product newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };

}
