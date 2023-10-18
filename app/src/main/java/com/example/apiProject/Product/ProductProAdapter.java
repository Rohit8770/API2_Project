package com.example.apiProject.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apiProject.Cat_and_Sub.Product.ResponseProduct.ProductCategoryListResponse;
import com.example.apiProject.R;

import java.util.ArrayList;
import java.util.List;

public class ProductProAdapter extends RecyclerView.Adapter<ProductProAdapter.ProductViewHolder> {

    List<ProductCategoryListResponse.Product> productListResponse,searchList;
    Context context;

    ItemClick itemClick;
    public interface ItemClick{
        void deleteClick(ProductCategoryListResponse.Product productListResponse);
        void editClick(ProductCategoryListResponse.Product productListResponse);
    }

    public void setUpInterface(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
    public void Search(CharSequence charSequence, TextView textView, RecyclerView recyclerView){

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()){
            searchList = productListResponse;
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            int flag = 0;
            List<ProductCategoryListResponse.Product> filterList = new ArrayList<>();
            for (ProductCategoryListResponse.Product single : productListResponse){
                if (single.getProductName().toLowerCase().contains(charString.toLowerCase())){
                    filterList.add(single);
                    flag = 1;
                }
            }
            if (flag == 1){
                searchList = filterList;
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }
            else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        }
        notifyDataSetChanged();
    }


    public ProductProAdapter(List<ProductCategoryListResponse.Product> productListResponse, Context context) {
        this.productListResponse = productListResponse;
        this.searchList =productListResponse;
        this.context = context;
    }

    public void setData(List<ProductCategoryListResponse.Product> productListResponse) {
        this.productListResponse = productListResponse;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.tvProductName.setText(searchList.get(position).getProductName());
        holder.tvProductPrice.setText(searchList.get(position).getProductPrice());
        holder.tvProductDescription.setText(searchList.get(position).getProductDesc());
        if (searchList.get(position).getIsVeg().equals("1")){
            holder.ivIsVeg.setBackgroundResource(R.drawable.check_box_24);
        }
        else {
            holder.ivIsVeg.setImageResource(R.drawable.non_veg);
        }
        Glide
                .with(context)
                .load(searchList.get(position).getProductImage())
                .placeholder(R.drawable.ic_imageholder)
                .into(holder.ivProduct);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.deleteClick(searchList.get(position));
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.editClick(searchList.get(position));
            }
        });
       /* holder.tvProductName.setText(searchList.get(position).getProductName());
        holder.tvProductPrice.setText(searchList.get(position).getProductPrice());
        holder.tvProductDescription.setText(searchList.get(position).getProductDesc());
        if (searchList.get(position).getIsVeg().equals("1")){
            holder.ivIsVeg.setBackgroundResource(R.drawable.ic_non_veg_symbol);
        }
        else {
            holder.ivIsVeg.setImageResource(R.drawable.ic_veg_symbol);
        }
        Glide
                .with(context)
                .load(searchList.get(position).getProduct_image())
                .placeholder(R.drawable.ic_imageholder)
                .into(holder.ivProduct);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.deleteClick(searchList.get(position));
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.editClick(searchList.get(position));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct,ivDelete,ivEdit,ivIsVeg;
        TextView tvProductName,tvProductPrice,tvProductDescription;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivIsVeg = itemView.findViewById(R.id.ivIsVeg);
        }
    }
}
