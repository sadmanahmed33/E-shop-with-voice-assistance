package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.ProductViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference productList;
    String f;
    String blogId = "";
    LinearLayoutManager manager = new LinearLayoutManager(this);

    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    FirebaseRecyclerAdapter<Product, ProductViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();


    MaterialSearchBar materialSearchBar;
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
            Log.d("jjjjjjjj",blogId);

        }
        if(blogId != null && !blogId.isEmpty())
        {
            loadListProduct(blogId);
        }

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your product");
       /// materialSearchBar.setOnSearchActionListener(new );

       loadSuggest();

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if(suggestList.contains(text.toString()))
                {
                    startSearch(text);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(ProductList.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Alert message to be shown");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    prioritySearch();
                   // materialSearchBar.setLastSuggestions(null);

                }


                    Log.d("FFFFF", text.toString());
                }



            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

Log.d("PPPP",blogId+"0");

//Log.d("LLLLLLLLLLll",f);



    }

    private void prioritySearch() {
        searchAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("Rating").startAt(blogId+"1").endAt(blogId+"5")){
            @Override
            public int getItemCount() {
                flag = super.getItemCount();
                Log.d("NB", "getItemCount: " + super.getItemCount());

                return super.getItemCount();
            }

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                //flag = 1;
                Log.d("F", String.valueOf(flag));

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                flag=1;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // flag=2;
                        Intent productDetail = new Intent(ProductList.this, ProductDetail.class);
                        productDetail.putExtra("ProductId", searchAdapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            //return flag;
            //final float aFloat = 1;


        };
        recyclerView.setAdapter(searchAdapter);
    }


    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("Name").startAt(text.toString())
                .endAt(text.toString()+"\uf8ff")
        ) {
            @Override
            public int getItemCount() {
                flag = super.getItemCount();
                Log.d("NB", "getItemCount: " + super.getItemCount());

                return super.getItemCount();
            }

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                //flag = 1;
                Log.d("F", String.valueOf(flag));

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                flag=1;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // flag=2;
                        Intent productDetail = new Intent(ProductList.this, ProductDetail.class);
                        productDetail.putExtra("ProductId", searchAdapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            //return flag;
            //final float aFloat = 1;


        };
        recyclerView.setAdapter(searchAdapter);
    }





    private void loadSuggest() {
        productList.orderByChild("MenuId").equalTo(blogId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Product item = postSnapshot.getValue(Product.class);
                            suggestList.add(item.getName());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadListProduct(String blogId) {
        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("MenuId").equalTo(blogId)) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent productDetail = new Intent(ProductList.this,ProductDetail.class);
                        productDetail.putExtra("ProductId",adapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });




            }


        };

        Log.d("TAG",""+adapter.getItemCount());

        recyclerView.setAdapter(adapter);

    }
}
