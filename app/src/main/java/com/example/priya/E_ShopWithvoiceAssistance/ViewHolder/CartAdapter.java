package com.example.priya.E_ShopWithvoiceAssistance.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Order;
import com.example.priya.finalprojectsdp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class CartAdapter  extends  RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;
    public  CartAdapter(List<Order> listData,Context context )
    {
        this.listData = listData;
        this.context = context;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,viewGroup,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(i).getQuantity(), Color.RED);
        cartViewHolder.img_cart_count.setImageDrawable(drawable);
        Locale locale = new Locale("bn","BD");
        NumberFormat fnt;
        fnt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(i).getPrice()))*(Integer.parseInt(listData.get(i).getQuantity()));
        cartViewHolder.txt_price.setText(fnt.format(price));
        cartViewHolder.txt_cart_name.setText(listData.get(i).getProductName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

  public Order getItem(int position)
  {
      return listData.get(position);
  }
  public void removeItem(int position)
  {
      listData.remove(position);
      notifyItemRemoved(position);
  }
  public void restoreItem(Order item,int position)
  {
      listData.add(position,item);
      notifyItemInserted(position);

  }
}
