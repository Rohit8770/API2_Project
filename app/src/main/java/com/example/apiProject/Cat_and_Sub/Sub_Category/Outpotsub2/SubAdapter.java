package com.example.apiProject.Cat_and_Sub.Sub_Category.Outpotsub2;





import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiProject.Cat_and_Sub.Product.ResponseProduct.ProductCategoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCatagoryListResponse;
import com.example.apiProject.R;

import java.util.ArrayList;
import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubViewHolder> {

    Context context;
    List<SubCatagoryListResponse.SubCategory> subCatagories;
    List<SubCatagoryListResponse.SubCategory> SearchList;

    SubCatagoryClick subCatagoryClick;

    public void updateDate(List<SubCatagoryListResponse.SubCategory> newSubCategoryList){
        subCatagories.clear();
        subCatagories.addAll(newSubCategoryList);
        notifyDataSetChanged();
    }

    public interface SubCatagoryClick{
        void SubDeleteCategory(SubCatagoryListResponse.SubCategory subCategory);
        void SubEditClick(SubCatagoryListResponse.SubCategory subCategory);
    }
    public void SetUpInterface(SubAdapter.SubCatagoryClick subCatagoryClick){
        this.subCatagoryClick = subCatagoryClick;
    }

    public SubAdapter(Context context, List<SubCatagoryListResponse.SubCategory> subCatagories) {
        this.context = context;
        this.subCatagories = subCatagories;
        this.SearchList = subCatagories;
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.sub_item,parent,false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubCatagoryListResponse.SubCategory subCategory=SearchList.get(position);
        holder.Textview1.setText(subCategory.getSubCategoryId());
        holder.Textview2.setText(subCategory.getSubcategoryName());
        holder.Textview3.setText(subCategory.getCategoryId());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCatagoryClick.SubDeleteCategory(subCategory);

            }
        });
        holder.btnEdit11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCatagoryClick.SubEditClick(subCatagories.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return SearchList.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {

        TextView  Textview1,Textview2,Textview3;
        ImageView btnEdit11,btnDelete;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            Textview1=itemView.findViewById(R.id.Textview1);
            Textview2=itemView.findViewById(R.id.Textview2);
            Textview3=itemView.findViewById(R.id.Textview3);
            btnEdit11=itemView.findViewById(R.id.btnEdit11);
            btnDelete=itemView.findViewById(R.id.btnDelete);

        }
    }
    public void Search(CharSequence charSequence, RecyclerView rcv) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                SearchList = subCatagories;
                rcv.setVisibility(View.VISIBLE);
            } else {
                int flag = 0;
                List<SubCatagoryListResponse.SubCategory> filterlist = new ArrayList<>();
                for (SubCatagoryListResponse.SubCategory Row : subCatagories) {
                    if (Row.getSubcategoryName().toString().toLowerCase().contains(charString.toLowerCase())) {
                        filterlist.add(Row);
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    SearchList = filterlist;
                    rcv.setVisibility(View.VISIBLE);
                } else {
                    rcv.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCatagoryListResponse;
import com.example.apiProject.R;

import java.util.ArrayList;
import java.util.List;

public class SubAdapter extends  RecyclerView.Adapter<SubAdapter.RecSubViewHolder>{

    List<SubCatagoryListResponse.SubCategory> categorySubListResponseList;

    Context context;

    List<SubCatagoryListResponse.SubCategory> subsearchList;


    SubCategoryClick subCategoryClick;


    public void updateData(List<SubCatagoryListResponse.SubCategory> newSubCategoryList) {
        categorySubListResponseList.clear();
        categorySubListResponseList.addAll(newSubCategoryList);
        notifyDataSetChanged();

    }


    public interface SubCategoryClick{
        void DeleteClick(SubCatagoryListResponse.SubCategory subCategory);

        void EditClick(SubCatagoryListResponse.SubCategory  subCategory);

    }


    public void SetUpInterface(SubAdapter.SubCategoryClick subCategoryClick){
        this.subCategoryClick = subCategoryClick;
    }


    public SubAdapter(List<SubCatagoryListResponse.SubCategory> categorySubListResponseList, Context context*/
/* List<CategorySubListResponse.SubCategory> subsearchList*//*) {
        this.categorySubListResponseList = categorySubListResponseList;
        this.context = context;
        this.subsearchList = categorySubListResponseList;
    }

    @NonNull
    @Override
    public RecSubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.sub_item,parent,false);
        return new RecSubViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull RecSubViewHolder holder, int position) {
        SubCatagoryListResponse.SubCategory subcategory = subsearchList.get(position);
        holder.itemsubdata.setText(subcategory.getSubcategoryName());
        holder.itemsubcategory_id.setText(subcategory.getSubCategoryId());
        holder.itemcategory_id.setText(subcategory.getCategoryId());

        holder.subeditimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SubAddActivity.class);
                intent.putExtra("category_id",subcategory.getCategoryId());
                intent.putExtra("subcategoryId",subcategory.getSubCategoryId());
                intent.putExtra("subcategoryName",subcategory.getSubcategoryName());
                view.getContext().startActivity(intent);
            }
        });


        holder.subdeleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoryClick.DeleteClick(subcategory);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subsearchList.size();
    }

    public class RecSubViewHolder extends RecyclerView.ViewHolder{
        TextView itemsubdata ,itemcategory_id,itemsubcategory_id;

        ImageView subeditimg,subdeleteimg;

        //Button subeditbtn, subdeletebtn;
        public RecSubViewHolder(@NonNull View itemView) {
            super(itemView);
            subeditimg  = itemView.findViewById(R.id.btnEdit11);
            subdeleteimg = itemView.findViewById(R.id.btnDelete);
            itemsubdata  = itemView.findViewById(R.id.Textview1);
            itemcategory_id  = itemView.findViewById(R.id.Textview2);
            itemsubcategory_id = itemView.findViewById(R.id.Textview3);



        }
    }



    public void Search(CharSequence charSequence,  RecyclerView rcvData) {
        try{
            String charString=charSequence.toString().toLowerCase().trim();
            if(charString.isEmpty()){
                subsearchList=categorySubListResponseList;
                rcvData.setVisibility(View.VISIBLE);
            }else{
                int flag=0;
                List<SubCatagoryListResponse.SubCategory> filtersublist = new ArrayList<>();
                for(SubCatagoryListResponse.SubCategory Row:categorySubListResponseList){
                    if(Row.getSubcategoryName().toString().toLowerCase().contains(charString.toLowerCase())){
                        filtersublist.add(Row);
                        //filterlist.add(Row);
                        flag=1;
                    }
                }
                if (flag == 1) {
                    subsearchList =filtersublist;
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








