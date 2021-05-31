package com.example.priya.E_ShopWithvoiceAssistance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.AdminProductViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class admin_product_list extends AppCompatActivity implements NavigationView.OnCreateContextMenuListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
   // FirebaseDatabase database;
    DatabaseReference catagory;
    DatabaseReference productList;
    private ImageButton mSelectImage;

    private EditText mPostTittle;
    private EditText mPostDesc,mPrice,mDiscount;
    private Button mSubmitBtn;


    private Uri mImageUri = null ;
    private  static final  int GALLERY_REQUEST = 1 ;

    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    //private ImageButton mSelectImage;


    String  photoLink;

    String blogId = "";
    LinearLayoutManager manager = new LinearLayoutManager(this);

    FirebaseRecyclerAdapter<Product, AdminProductViewHolder> adapter;

    FirebaseRecyclerAdapter<Product, AdminProductViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    //database = FirebaseDatabase.getInstance();
    //catagory = database.getReference("Blog");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // recyclerView = findViewById(R.layout.a)
        setContentView(R.layout.activity_admin_product_list);
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Products");
        //textFullname =(TextView)headerView.findViewById(R.id.textFullName);
        //textFullname.setText(Common.current_user.getName());

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postIntent = new Intent(admin_product_list.this,add_new_product.class);
                startActivity(postIntent);
            }
        });

        //  recyclerView.setLayoutManager(manager);
        //  recyclerView.setHasFixedSize(true);

        if(getIntent()!=null)
        {
            blogId = getIntent().getStringExtra("BlogId");

        }
        if(blogId != null && !blogId.isEmpty())
        {
            loadListproduct(blogId);
        }

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your product");

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
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });




    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the MainActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else if(item.getTitle().equals(Common.DELETE))
        {
            deleteCatagory(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }
    private void showUpdateDialog(final String key, final Product item) {
        setContentView(R.layout.add_new_product_item);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mSelectImage =(ImageButton) findViewById(R.id.image_select);
        mPostTittle =(EditText)findViewById(R.id.edit_name);
        mPostDesc =(EditText)findViewById(R.id.edit_description);
        mDiscount =(EditText)findViewById(R.id.edit_discount);
        mPrice =(EditText)findViewById(R.id.edit_price);
        mSubmitBtn =(Button)findViewById(R.id.submit_button);
        mProgress = new ProgressDialog(this);


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setMessage("Updating....");


                final String title_val = mPostTittle.getText().toString().trim();
                final String title_val1 = mPostDesc.getText().toString().trim();
                final String title_val2 = mPrice.getText().toString().trim();
                final String title_val3 = mDiscount.getText().toString().trim();


                if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(title_val1) &&!TextUtils.isEmpty(title_val2)&&!TextUtils.isEmpty(title_val3))
                {
                    mProgress.show();

                    final StorageReference filepath = mStorage.child("Blog_Image").child(mImageUri.getLastPathSegment());


                    filepath.putFile(mImageUri).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setImage(uri.toString());
                                    photoLink=  uri.toString();
                                    DatabaseReference newPost = mDatabase.push();
                                    item.setName(mPostTittle.getText().toString());
                                    item.setName(mPostDesc.getText().toString());
                                    item.setName(mPrice.getText().toString());
                                    item.setName(mDiscount.getText().toString());
                                    catagory.child(key).setValue(item);
                                    mProgress.dismiss();
                                    startActivity(new Intent(admin_product_list.this,admin_product_list.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Sadman", "onFailure: " + e.toString());
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Log.d("Sadman", "onCanceled:");
                                }
                            });







                        }
                    });
                }

            }
        });


    }

    private void deleteCatagory(String key) {
        productList.child(key).removeValue();
        Toast.makeText(this,"Item Deleted !!!",Toast.LENGTH_SHORT).show();
    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Product, AdminProductViewHolder>(
                Product.class,
                R.layout.product_list,
                AdminProductViewHolder.class,
                productList.orderByChild("Name").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(AdminProductViewHolder viewHolder, Product model, int position) {
                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent productDetail = new Intent(admin_product_list.this,ProductDetail.class);
                        productDetail.putExtra("ProductId",searchAdapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });


            }

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

    private void loadListproduct(String blogId) {
        adapter = new FirebaseRecyclerAdapter<Product, AdminProductViewHolder>(   Product.class,
                R.layout.product_list,
                AdminProductViewHolder.class,
                productList.orderByChild("MenuId").equalTo(blogId)) {
            @Override
            protected void populateViewHolder(AdminProductViewHolder viewHolder, Product model, int position) {
                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent productDetail = new Intent(admin_product_list.this,ProductDetail.class);
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
