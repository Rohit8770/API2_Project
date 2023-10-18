package com.example.apiProject.Cat_and_Sub.CataLog.CateLockAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apiProject.Cat_and_Sub.CataLog.CataLogResponse.CatalogListResponse;
import com.example.apiProject.R;

import java.util.List;

public class CatalogProductAdapter extends RecyclerView.Adapter<CatalogProductAdapter.CataViewHolder>{


    List<CatalogListResponse.Category.SubCategory.Product> productList;
    Context context;

    public CatalogProductAdapter(List<CatalogListResponse.Category.SubCategory.Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public CataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.catalogproduct_itemfile,parent,false);
        return new CataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CataViewHolder holder, int position) {
        holder.txCataName.setText(productList.get(position).getProductName());
        holder.txCataPrice.setText(productList.get(position).getProductPrice());
        holder.txCataDesc.setText(productList.get(position).getProductDesc());

        if (productList.get(position).getIsVeg().equals("1")){
            holder.imgProVeg.setImageResource(R.drawable.ic_veg);
        }else
            holder.imgProVeg.setImageResource(R.drawable.non_veg);

        Glide   .with(context)
                .load(productList.get(position).getProductImage())
                .placeholder(R.drawable.ic_imageholder)
                .into(holder.imgCataProduct);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CataViewHolder extends RecyclerView.ViewHolder {

        TextView txCataName,txCataPrice,txCataDesc;
        ImageView  imgCataProduct,imgProVeg;

        public CataViewHolder(@NonNull View itemView) {
            super(itemView);

            txCataName=itemView.findViewById(R.id.txCataName);
            txCataPrice=itemView.findViewById(R.id.txCataPrice);
            txCataDesc=itemView.findViewById(R.id.txCataDesc);
            imgCataProduct=itemView.findViewById(R.id.imgCataProduct);
            imgProVeg=itemView.findViewById(R.id.imgProVeg);
        }
    }

    private  void displayImage(Context context, ImageView imageView, String currentPhotoPath){
        Glide.with(context)
                .load(currentPhotoPath)
                .placeholder(R.drawable.ic_imageholder)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}
