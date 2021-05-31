package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.Register_View_Holder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterDetails extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtUsername;
    FirebaseRecyclerAdapter<Registration_Class, Register_View_Holder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Registration");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        loadOrders();


    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Registration_Class, Register_View_Holder>(
                Registration_Class.class,
                R.layout.register_sattus,
                Register_View_Holder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(Register_View_Holder viewHolder, final Registration_Class model, int position) {

                viewHolder.txtRegisterPhone.setText(model.getMobile());
                viewHolder.txtRegisterDate.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));
                viewHolder.txtRegisterName.setText(model.getName());
                viewHolder.txtRegisterPassword.setText(model.getPassword());
                viewHolder.txtRegisterOrder.setText("Order Details");
                viewHolder.txtRegisterOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),UserOrderAdmin.class);
                        intent.putExtra("phone",model.getMobile());
                        Toast.makeText(RegisterDetails.this,""+model.getMobile(),Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });



            }
        };
        recyclerView.setAdapter(adapter);
    }


}
