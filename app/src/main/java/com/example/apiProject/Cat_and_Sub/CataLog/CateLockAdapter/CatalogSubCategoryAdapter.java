package com.example.apiProject.Cat_and_Sub.CataLog.CateLockAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiProject.Cat_and_Sub.CataLog.CataLogResponse.CatalogListResponse;
import com.example.apiProject.R;

import java.util.List;

public class CatalogSubCategoryAdapter extends RecyclerView.Adapter<CatalogSubCategoryAdapter.SubCatViewHolder>{
    List<CatalogListResponse.Category.SubCategory> subCategoryList;
    Context context;
    CatalogProductAdapter catalogProductAdapter;

    public CatalogSubCategoryAdapter(List<CatalogListResponse.Category.SubCategory> subCategoryList, Context context) {
        this.subCategoryList = subCategoryList;
        this.context = context;

        for (int i = 0; i < subCategoryList.size(); i++) {
            subCategoryList.get(i).setSelect(false);
        }
    }

    @NonNull
    @Override
    public SubCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.catalogsubcategory_itemfile,parent,false);
        return new SubCatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCatViewHolder holder, int position) {
        holder.txCatalogSubCategory.setText(subCategoryList.get(position).getSubcategoryName());

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        holder.rcvCatalogProduct.setLayoutManager(layoutManager);
        catalogProductAdapter = new CatalogProductAdapter(subCategoryList.get(position).getProductList(),context);
        holder.rcvCatalogProduct.setAdapter(catalogProductAdapter);


      /*  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        catalogProductAdapter = new CatalogProductAdapter((Context) subCategoryList.get(position).getProductList(), (List<CatalogListResponse.Category.SubCategory.Product>) context);
        holder.rcvCatalogProduct.setLayoutManager(layoutManager);
        holder.rcvCatalogProduct.setAdapter(catalogProductAdapter);*/



        if (subCategoryList.get(position).getSelect()){
            holder.rcvCatalogProduct.setVisibility(View.GONE);
        }else
            holder.rcvCatalogProduct.setVisibility(View.VISIBLE);


        holder.imgDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subCategoryList.get(position).getSelect() == false){
                    subCategoryList.get(position).setSelect(true);
                    notifyDataSetChanged();
                }
                else {
                    subCategoryList.get(position).setSelect(false);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class SubCatViewHolder extends RecyclerView.ViewHolder {
        TextView txCatalogSubCategory;
        ImageView imgDropDown;
        RecyclerView rcvCatalogProduct;
        public SubCatViewHolder(@NonNull View itemView) {
            super(itemView);

            txCatalogSubCategory=itemView.findViewById(R.id.txCatalogSubCategory);
            rcvCatalogProduct=itemView.findViewById(R.id.rcvCatalogProduct);
            imgDropDown=itemView.findViewById(R.id.imgDropDown);

        }
    }
}
