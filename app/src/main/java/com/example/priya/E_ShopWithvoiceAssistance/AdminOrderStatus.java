package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.DialogInterface;
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
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.AdminOrderViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AdminOrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtUsername;
    MaterialSpinner spinner;
    FirebaseRecyclerAdapter<AdminRequest, AdminOrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

      loadOrders();

    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<AdminRequest, AdminOrderViewHolder> (
                AdminRequest.class,
                R.layout.adminorderlayout,
                AdminOrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(AdminOrderViewHolder viewHolder, AdminRequest model, final int position) {
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderDate.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));
                viewHolder.txtUserName.setText(model.getName());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderTransaction.setText(model.getaTransaction());
                viewHolder.txtOrderPayment.setText(Common.convertCodeToPayment(model.getPayment()));
                viewHolder.btndeliver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDialog(adapter.getRef(position).getKey(),adapter.getItem(position));

                    }
                });
                viewHolder.btntran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDialog2(adapter.getRef(position).getKey(),adapter.getItem(position));

                    }
                });
                viewHolder.btnpay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDialog1(adapter.getRef(position).getKey(),adapter.getItem(position));

                    }
                });

                viewHolder.btnrmv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder(adapter.getRef(position).getKey());

                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void showUpdateDialog2(String key, final AdminRequest item) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminOrderStatus.this);
        alertDialog.setTitle("Send a code");
        alertDialog.setMessage("Select a code");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.transaction_send,null);
        spinner = (MaterialSpinner)view.findViewById(R.id.tranSpinner);
        spinner.setItems("11323","35228","78833","239832","238333","2983233","1912233","2383834","129388234","2329283","667893","484284","0");
       // spinner.setItems("iwxern33","i1wern33","iwfern33","iwejrn33","iwelrn33","iw5ern33","iwelrn33","iw5ern33","iwe8rn33","iwer22n33","1iwern533","iwergn33","iwernk33",
               // "iwern3l3","iwernk33","iwern3l3","ivwern33","iwbern33","iwenrn33","z2xaq123","zx3aq123","zxaaq123","zxaq5123","zxaq1223","zxaq1213","zxawq1213","z2xaq123","zx3aq123","0");
        alertDialog.setView(view);

        final String lokalKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setaTransaction(String.valueOf(spinner.getSelectedIndex()));
                requests.child(lokalKey).setValue(item);
                String aTran = item.getaTransaction().toString();
                Log.d("Trannnn",aTran);
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

    private void showUpdateDialog1(String key, final AdminRequest item) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminOrderStatus.this);
        alertDialog.setTitle("Update Payment Status");
        alertDialog.setMessage("Please choose status");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_payment,null);
        spinner = (MaterialSpinner)view.findViewById(R.id.paymentSpinner);
        spinner.setItems("Cash In Delivery","Payment Pending In Bkash","Payment Completed In Bkash");
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


    private void deleteOrder(String key) {
        requests.child(key).removeValue();
        adapter.notifyDataSetChanged();

    }

    private void showUpdateDialog(String key, final AdminRequest item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminOrderStatus.this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please choose status");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout,null);
        spinner = (MaterialSpinner)view.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed","On the way","shipped");
        alertDialog.setView(view);

        final String lokalKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));
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
