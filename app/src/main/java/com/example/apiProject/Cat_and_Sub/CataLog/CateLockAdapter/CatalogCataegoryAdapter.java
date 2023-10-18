package com.example.apiProject.Cat_and_Sub.CataLog.CateLockAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiProject.Cat_and_Sub.CataLog.CataLogResponse.CatalogListResponse;
import com.example.apiProject.Cat_and_Sub.CataLog.CetaLockActivity;
import com.example.apiProject.R;

import java.util.List;

public class CatalogCataegoryAdapter extends RecyclerView.Adapter<CatalogCataegoryAdapter.CategoryViewHolder>{

    Context context;
    List<CatalogListResponse.Category> categoryList;
    CatalogSubCategoryAdapter catalogSubCategoryAdapter;

    public CatalogCataegoryAdapter(Context context, List<CatalogListResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.calalogcategory_itemfile,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.txCatalogCategory.setText(categoryList.get(position).getCategoryName());

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        holder.rcvCatalogCategory.setLayoutManager(layoutManager);
        catalogSubCategoryAdapter=new CatalogSubCategoryAdapter(categoryList.get(position).getSubCategoryList(),context);
        holder.rcvCatalogCategory.setAdapter(catalogSubCategoryAdapter);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView txCatalogCategory;
        RecyclerView rcvCatalogCategory;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txCatalogCategory=itemView.findViewById(R.id.txCatalogCategory);
            rcvCatalogCategory=itemView.findViewById(R.id.rcvCatalogCategory);
            }
        }

}




