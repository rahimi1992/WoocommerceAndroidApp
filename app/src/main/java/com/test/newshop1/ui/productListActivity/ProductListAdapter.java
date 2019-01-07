package com.test.newshop1.ui.productListActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.product.SimpleProduct;
import com.test.newshop1.databinding.ProductItemBinding;
import com.test.newshop1.databinding.ProductItemGridBinding;
import com.test.newshop1.databinding.ProductItemLargeBinding;
import com.test.newshop1.databinding.ProductItemSmallBinding;
import com.test.newshop1.ui.OnItemClickListener;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class ProductListAdapter extends PagedListAdapter<SimpleProduct, ProductListAdapter.BaseViewHolder> {

    public static final int LINEAR_VIEW_TYPE = 1;
    public static final int GRID_VIEW_TYPE = 2;
    public static final int GRID_VIEW_TYPE_LARGE = 3;
    public static final int SMALL_VIEW_TYPE = 4;

    private int viewType;
    private OnItemClickListener onItemClickListener;

    public ProductListAdapter(int viewType) {
        super(DIFF_CALLBACK);
        this.viewType = viewType;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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
                return new GridItemHolder(ProductItemGridBinding.inflate(inflater, viewGroup, false));
            case GRID_VIEW_TYPE_LARGE:
                return new GridItemHolder(ProductItemGridBinding.inflate(inflater, viewGroup, false));
                //return new LargeItemHolder(ProductItemLargeBinding.inflate(inflater, viewGroup, false));
            case SMALL_VIEW_TYPE:
                return new SmallItemHolder(ProductItemSmallBinding.inflate(inflater, viewGroup, false));
        }
        return new LinearItemHolder(ProductItemBinding.inflate(inflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        SimpleProduct product = getItem(position);
        holder.bind(product);

    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        private BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void bind(SimpleProduct type);
    }

    class LinearItemHolder extends BaseViewHolder {

        private final ProductItemBinding binding;

        LinearItemHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(onItemClickListener);

        }

        @Override
        public void bind(SimpleProduct product) {
            binding.setProduct(product);
        }
    }

    class GridItemHolder extends BaseViewHolder {

        private final ProductItemGridBinding binding;

        GridItemHolder(ProductItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(onItemClickListener);

        }

        @Override
        public void bind(SimpleProduct product) {
            binding.setProduct(product);
        }
    }

    class LargeItemHolder extends BaseViewHolder {

        private final ProductItemLargeBinding binding;

        LargeItemHolder(ProductItemLargeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(onItemClickListener);

        }

        @Override
        public void bind(SimpleProduct product) {
            binding.setProduct(product);
        }
    }

    class SmallItemHolder extends BaseViewHolder {

        private final ProductItemSmallBinding binding;

        SmallItemHolder(ProductItemSmallBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(onItemClickListener);

        }

        @Override
        public void bind(SimpleProduct product) {
            binding.setProduct(product);
        }
    }


    private static DiffUtil.ItemCallback<SimpleProduct> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<SimpleProduct>() {

                @Override
                public boolean areItemsTheSame(SimpleProduct oldConcert, SimpleProduct newConcert) {
                    return oldConcert.getId().equals(newConcert.getId());
                }

                @Override
                public boolean areContentsTheSame(SimpleProduct oldConcert,
                                                  @NonNull SimpleProduct newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };

}
