package com.test.newshop1.ui.detailActivity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.databinding.ProductItemGridFixBinding;
import com.test.newshop1.ui.OnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NormalProductListAdapter extends RecyclerView.Adapter<NormalProductListAdapter.ProductHolder> {

    private List<Product> products;
    private OnItemClickListener listener;

    NormalProductListAdapter() {
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ProductHolder(ProductItemGridBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.binding.setProduct(product);
        holder.binding.setListener(listener);

    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    void setProducts(List<Product> products) {
        if (products != null && !products.isEmpty()) {
            this.products = products;
            notifyDataSetChanged();
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        ProductItemGridBinding binding;

        ProductHolder(@NonNull ProductItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
