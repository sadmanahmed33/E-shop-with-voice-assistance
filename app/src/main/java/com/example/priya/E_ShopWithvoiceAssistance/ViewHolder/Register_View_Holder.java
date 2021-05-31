package com.example.priya.E_ShopWithvoiceAssistance.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.finalprojectsdp.R;

public class Register_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtRegisterPhone,txtRegisterName,txtRegisterPassword,txtRegisterOrder,txtRegisterDate;
    LinearLayout talk_btn;
    private ItemClickListener itemClickListener;
    public Register_View_Holder(@NonNull View itemView) {
        super(itemView);
        txtRegisterPhone = (TextView) itemView.findViewById(R.id.register_num);
        txtRegisterName = (TextView) itemView.findViewById(R.id.register_name);
        txtRegisterPassword = (TextView) itemView.findViewById(R.id.register_pass);
        txtRegisterOrder = (TextView) itemView.findViewById(R.id.register_order);
        txtRegisterDate = (TextView)itemView.findViewById(R.id.register_date);
        talk_btn= itemView.findViewById(R.id.talk_btn);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
