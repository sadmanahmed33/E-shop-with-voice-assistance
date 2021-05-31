package com.example.priya.E_ShopWithvoiceAssistance.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.finalprojectsdp.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress,txtOrderTransaction,txtOrderPayment,txtUserName,txtOrderDate;
    LinearLayout talk_btn;
    public Button btnpay,btndlt;
    private ItemClickListener itemClickListener;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.order_address);
        txtUserName = (TextView) itemView.findViewById(R.id.order_name);
        txtOrderDate = (TextView)itemView.findViewById(R.id.order_date);
        txtOrderId = (TextView) itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.order_phone);
        txtOrderTransaction = (TextView) itemView.findViewById(R.id.order_Transaction);
        txtOrderPayment= (TextView) itemView.findViewById(R.id.order_Payment);
        talk_btn= itemView.findViewById(R.id.talk_btn);
        btnpay = itemView.findViewById(R.id.payment);
        btndlt = itemView.findViewById(R.id.cancel);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(ItemClickListener cannot_view_order) {
    }
}
