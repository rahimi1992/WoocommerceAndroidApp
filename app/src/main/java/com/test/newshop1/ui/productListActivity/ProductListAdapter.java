package com.test.newshop1.ui.productListActivity;

import androidx.paging.PagedListAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.databinding.ProductItemBinding;
import com.test.newshop1.databinding.ProductItemGridBinding;
import com.test.newshop1.ui.OnItemClickListener;

import butterknife.ButterKnife;

public class ProductListAdapter extends PagedListAdapter<Product, ProductListAdapter.BaseViewHolder> {

    public static final int LINEAR_VIEW_TYPE = 1;
    public static final int GRID_VIEW_TYPE = 2;
    public static final int GRID_VIEW_TYPE_LARGE = 3;
    private int viewType;
    private OnItemClickListener onItemClickListener;

    ProductListAdapter(int viewType) {
        super(DIFF_CALLBACK);
        this.viewType = viewType;
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType){
            case LINEAR_VIEW_TYPE:
                return new LinearItemHolder(ProductItemBinding.inflate(inflater, viewGroup, false));
            case GRID_VIEW_TYPE:
            case GRID_VIEW_TYPE_LARGE:
                return new GridItemHolder(ProductItemGridBinding.inflate(inflater, viewGroup, false));
        }
        return new LinearItemHolder(ProductItemBinding.inflate(inflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Product product = getItem(position);
        holder.bind(product);

    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        private BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public abstract void bind(Product type);
    }

    class LinearItemHolder extends BaseViewHolder {

        private final ProductItemBinding binding;

        LinearItemHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(Product product) {
            binding.setProduct(product);
            binding.setListener(onItemClickListener);
        }
    }

    class GridItemHolder extends BaseViewHolder {

        private final ProductItemGridBinding binding;

        GridItemHolder(ProductItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(Product product) {
            binding.setProduct(product);
            binding.setListener(onItemClickListener);
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
