package com.example.apiProject.Cat_and_Sub.Category.Output;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CatagoryListResponse;
import com.example.apiProject.R;


import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataViewHolder>{

    Context context;
    List<CatagoryListResponse.Category> categoryList;
    List<CatagoryListResponse.Category > searchList;
    CategoryClick categoryClick;

    public CategoryAdapter(Context context, List<CatagoryListResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.searchList = new ArrayList<>(categoryList);


            }
    public interface CategoryClick{
        void EditClick(CatagoryListResponse.Category category);
        void DeleteClick(CatagoryListResponse.Category category);
    }

    public  void SetUpInterFace(CategoryClick categoryClick){
        this.categoryClick=categoryClick;
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.category_item,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CatagoryListResponse.Category category=searchList.get(position);
        holder.catText.setText(category.getCategoryName());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
             categoryClick.EditClick(categoryList.get(position));
              /*  Intent intent = new Intent(view.getContext(), AddActivity.class);
                intent.putExtra("category_id", category.getCategoryId());
                intent.putExtra("category_name", category.getCategoryName());
                view.getContext().startActivity(intent);*/

            }
        });

         holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.DeleteClick(category);
            }
        });
    }
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView catText;
        ImageView btnEdit,btnDelete;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            catText=itemView.findViewById(R.id.catText);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try{
            String charString=charSequence.toString().toLowerCase().trim();
            if(charString.isEmpty()){
                searchList=categoryList;
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            }else{
                int flag=0;
                List<CatagoryListResponse.Category> filterList=new ArrayList<>();
                for(CatagoryListResponse.Category Row:categoryList){
                    if(Row.getCategoryName().toString().toLowerCase().contains(charString.toLowerCase())){
                        filterList.add(Row);
                        flag=1;
                    }
                }
                if (flag == 1) {
                    searchList=filterList;
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    categoryListRecyclerView.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}






/*
    public void Search(CharSequence charSequence,  RecyclerView rcvData) {
        try{
            String charString=charSequence.toString().toLowerCase().trim();
            if(charString.isEmpty()){
                searchList=categoryList;
                rcvData.setVisibility(View.VISIBLE);
            }else{
                int flag=0;
                List<CatagoryListResponse.Category> filterlist=new ArrayList<>();
                for(CatagoryListResponse.Category Row:categoryList){
                    if(Row.getCategoryName().toString().toLowerCase().contains(charString.toLowerCase())){
                        filterlist.add(Row);
                        flag=1;
                    }
                }
                if (flag == 1) {
                    searchList=filterlist;
                    rcvData.setVisibility(View.VISIBLE);
                }
                else{
                    rcvData.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}*/
