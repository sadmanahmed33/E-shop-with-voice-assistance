package com.example.priya.E_ShopWithvoiceAssistance.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.priya.finalprojectsdp.R;

public class AdminOrderViewHolder extends RecyclerView.ViewHolder{

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress,txtOrderTransaction,txtOrderPayment,txtUserName,txtOrderDate;
    LinearLayout talk_btn;
    public Button btnpay,btntran,btnrmv,btndeliver;
    public AdminOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.order_address);
        txtUserName = (TextView) itemView.findViewById(R.id.order_name);
        txtOrderId = (TextView) itemView.findViewById(R.id.order_id);
        txtOrderDate = (TextView)itemView.findViewById(R.id.order_date);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.order_phone);
        txtOrderTransaction = (TextView) itemView.findViewById(R.id.order_Transaction);
        txtOrderPayment= (TextView) itemView.findViewById(R.id.order_Payment);
        talk_btn= itemView.findViewById(R.id.talk_btn);

        btnpay = itemView.findViewById(R.id.payment);
        btntran = itemView.findViewById(R.id.tran);
        btnrmv = itemView.findViewById(R.id.remove);
        btndeliver = itemView.findViewById(R.id.deliver);
        //itemView.setOnClickListener(this);
        //itemView.setOnCreateContextMenuListener(this);
    }



}
