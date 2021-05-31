package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.priya.E_ShopWithvoiceAssistance.Common.AdminRequest;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Request;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.OrderViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtUsername;
    MaterialSpinner spinner;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String tid = getIntent().getStringExtra("tid");
       // Log.d("TransactionId",tid);

        Intent intent = new Intent(getBaseContext(), CompleteTransaction.class);
       // intent.putExtra("phone",request.getPhone());
        intent.putExtra("tid",tid);
        String Bkash = getIntent().getStringExtra("Bkash");
        String CashInDelivery = getIntent().getStringExtra("CashInDelivery");

        if(getIntent().getExtras() == null){
            loadOrders(Common.current_user.getMobile());
        }
        else {
            loadOrders(getIntent().getStringExtra("phone"));
        }

    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(final OrderViewHolder viewHolder, Request model, final int position) {
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderDate.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtUserName.setText(Common.current_user.getName());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderTransaction.setText(Common.convertCodeToTransaction(model.getaTransaction()));
                viewHolder.txtOrderPayment.setText(Common.convertCodeToPayment(model.getPayment()));

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //Intent intent = new Intent(getApplicationContext(),CompleteTransaction.class);
                        //intent.putExtra("price",viewHolder.)

                    }
                });
                viewHolder.btndlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder(adapter.getRef(position).getKey());

                    }
                });
                viewHolder.btnpay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDialog1(adapter.getRef(position).getKey(),adapter.getItem(position));

                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
        adapter.notifyDataSetChanged();

    }

    private void showUpdateDialog1(String key, final Request item) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Update Payment Status");
        alertDialog.setMessage("Please choose status");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_payment,null);
        spinner = (MaterialSpinner)view.findViewById(R.id.paymentSpinner);
        spinner.setItems("Cash In Delivery","Bkash");
        alertDialog.setView(view);

        final String lokalKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setPayment(String.valueOf(spinner.getSelectedIndex()));
                requests.child(lokalKey).setValue(item);
                adapter.notifyDataSetChanged();


            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }



}
