package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.ProductViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class priorityData1 extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference productList;
    String blogId = "";
    LinearLayoutManager manager = new LinearLayoutManager(this);

    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    FirebaseRecyclerAdapter<Product, ProductViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    Integer flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // recyclerView = findViewById(R.layout.a)
        setContentView(R.layout.activity_product_list);
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Products");
        //textFullname =(TextView)headerView.findViewById(R.id.textFullName);
        //textFullname.setText(Common.current_user.getName());

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //  recyclerView.setLayoutManager(manager);
        //  recyclerView.setHasFixedSize(true);

        if(getIntent()!=null)
        {
            blogId = getIntent().getStringExtra("BlogId");

        }
        if(blogId != null && !blogId.isEmpty())
        {
            loadListProduct(blogId);
        }

        /// materialSearchBar.setOnSearchActionListener(new );







    }





    private void loadListProduct(String blogId) {
        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("MenuId").equalTo(blogId)) {
            @Override
            public int getItemCount() {
                Log.d("NB", "getItemCount: " + super.getItemCount());
                flag = super.getItemCount();
                flag = 0;
                return flag;
            }

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent productDetail = new Intent(priorityData1.this,ProductDetail.class);
                        productDetail.putExtra("ProductId",adapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });




            }


        };

        Log.d("TAG",""+adapter.getItemCount());

        recyclerView.setAdapter(adapter);

    }

}

