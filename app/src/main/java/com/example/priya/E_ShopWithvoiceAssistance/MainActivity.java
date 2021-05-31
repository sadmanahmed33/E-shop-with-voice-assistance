package com.example.priya.E_ShopWithvoiceAssistance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Blog;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.finalprojectsdp.R;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.AdminMainViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference catagory;
    TextView textFullname;
    RecyclerView recycleMenu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Blog,AdminMainViewHolder> adapter;
    private ImageButton mSelectImage;

    private EditText mPostTittle;
    private EditText mPostDesc;
    private Button mSubmitBtn;


    private Uri mImageUri = null ;
    private  static final  int GALLERY_REQUEST = 1 ;

    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    String  photoLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();
        catagory = database.getReference("Blog");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postIntent = new Intent(MainActivity.this,PostActivity.class);
                startActivity(postIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        textFullname =(TextView)headerView.findViewById(R.id.textFullName);
        textFullname.setText("Tahmina");
        recycleMenu =(RecyclerView)findViewById(R.id.recycler_menu);
        recycleMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleMenu.setLayoutManager(layoutManager);
        loadMneu();
    }

    private void loadMneu() {
        adapter  = new FirebaseRecyclerAdapter<Blog, AdminMainViewHolder>(Blog.class,R.layout.menu_item,AdminMainViewHolder.class,catagory) {
            @Override
            protected void populateViewHolder(AdminMainViewHolder viewHolder, Blog model, int position) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Blog clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isOnClick) {
                        //
                    }
                });
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isOnClick) {
                        Intent foodList = new Intent(MainActivity.this,admin_product_list.class);
                        Toast.makeText(MainActivity.this,""+clickitem.getName(),Toast.LENGTH_SHORT).show();
                        foodList.putExtra("BlogId",adapter.getRef(position).getKey());
                        startActivity(foodList);

                    }
                });
            }
        };
        recycleMenu.setAdapter(adapter);
    }

    @Override
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_orders) {
            Intent cartIntent = new Intent(MainActivity.this,AdminOrderStatus.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_user) {
            Intent orderIntent = new Intent(MainActivity.this,RegisterDetails.class);
            startActivity(orderIntent);


        } else if (id == R.id.nav_log_out) {

            Intent signIn = new Intent(MainActivity.this,LoginActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    private void deleteCatagory(String key) {
        catagory.child(key).removeValue();
        Toast.makeText(this,"Item Deleted !!!",Toast.LENGTH_SHORT).show();
    }

    private void showUpdateDialog(final String key, final Blog item) {
            setContentView(R.layout.activity_post);

            mStorage = FirebaseStorage.getInstance().getReference();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
            mSelectImage =(ImageButton) findViewById(R.id.image_select);
            mPostTittle =(EditText)findViewById(R.id.tittle_field);
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




                    if(!TextUtils.isEmpty(title_val) && mImageUri !=null)
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
                                        catagory.child(key).setValue(item);
                                        mProgress.dismiss();
                                        startActivity(new Intent(MainActivity.this,MainActivity.class));
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



        protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == GALLERY_REQUEST  || resultCode == RESULT_OK )
            {

                //mImageUri = data.getData();
                mImageUri = data.getData();
                mSelectImage.setImageURI(mImageUri);

            }
        }



    }

