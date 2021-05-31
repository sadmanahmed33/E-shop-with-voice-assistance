package com.example.priya.E_ShopWithvoiceAssistance.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.finalprojectsdp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener
{
    public TextView txt_cart_name,txt_price;
    public ImageView img_cart_count;
    public RelativeLayout view_background,view_foreground;

    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name)
    {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        img_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_count);
  //      view_background = (RelativeLayout)itemView.findViewById(R.id.view_background);
//        view_foreground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);


    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}