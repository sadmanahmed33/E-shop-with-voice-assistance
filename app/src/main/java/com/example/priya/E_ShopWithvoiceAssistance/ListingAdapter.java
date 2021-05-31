package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.finalprojectsdp.R;

import java.util.ArrayList;

public class ListingAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Product> products;
    //Button button;

    public ListingAdapter(Context con, ArrayList<Product> products)
    {
        context=con;
        layoutInflater = LayoutInflater.from(context);
        this.products=products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_listing_adapter, null, false);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView.findViewById(R.id.product_image);
            holder.fullname = (TextView) convertView.findViewById(R.id.product_name);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product=products.get(position);

        holder.fullname.setText(product.getName());
        holder.imageView.setImageDrawable(Drawable.createFromPath(product.getImage()));

        return convertView;
    }

    public class ViewHolder {
        TextView fullname;
        ImageView imageView;

    }
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}