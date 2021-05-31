package com.example.priya.E_ShopWithvoiceAssistance.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.finalprojectsdp.R;

public class AdminProductViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener

{
    public TextView product_name,product_desc,product_price,product_dicount;
    public ImageView product_image;
    private ItemClickListener itemClickListener;
    public AdminProductViewHolder(@NonNull View itemView) {
        super(itemView);
        product_name = (TextView)itemView.findViewById(R.id.product_name);
        //product_desc = (TextView)itemView.findViewById(R.id.edit_description);
        //product_price = (TextView)itemView.findViewById(R.id.edit_price);
        //product_dicount = (TextView)itemView.findViewById(R.id.edit_discount);
        product_image = (ImageView)itemView.findViewById(R.id.product_image);
        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the Option");


        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}
