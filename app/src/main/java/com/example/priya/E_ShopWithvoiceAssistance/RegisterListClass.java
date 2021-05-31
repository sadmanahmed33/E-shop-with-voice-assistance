package com.example.priya.E_ShopWithvoiceAssistance;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.finalprojectsdp.R;

import java.util.List;

public class RegisterListClass extends ArrayAdapter<Product>{
    private Activity context;
    private List<Product> registrationClassList;

    public RegisterListClass(Activity context, List<Product> registrationClassList){
        super(context, R.layout.activity_listing_adapter, registrationClassList);
        this.context = context;
        this.registrationClassList = registrationClassList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_register_list_class,null,true);

        TextView textViewAccountNo = listViewItem.findViewById(R.id.user_info_number);
        TextView textViewEmail = listViewItem.findViewById(R.id.user_info_name);

        Product registration_class = registrationClassList.get(position);

        // textViewAccountNo.setText(registration_class.getAccountNo());
        textViewEmail.setText(registration_class.getName());

        return listViewItem;

    }
}
