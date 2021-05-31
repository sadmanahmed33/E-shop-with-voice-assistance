package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Blog;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.MainViewHolder;
import com.example.priya.E_ShopWithvoiceAssistance.database.database;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import service.PaymentService;
import service.ReceiveMessage;
import service.TransactionService;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference catagory;
    TextView textFullname;
    RecyclerView recycleMenu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Blog,MainViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();
        catagory = database.getReference("Blog");
        final String phone = Common.current_user.getMobile();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                cartIntent.putExtra("phone",Common.current_user.getMobile());
                startActivity(cartIntent);
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
        textFullname.setText(Common.current_user.getName());

        recycleMenu =(RecyclerView)findViewById(R.id.recycler_menu);
        recycleMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleMenu.setLayoutManager(layoutManager);
        loadMneu();

        Intent service = new Intent(Home.this, ReceiveMessage.class);
        startService(service);


        Intent service1 = new Intent(Home.this, PaymentService.class);
        startService(service1);


        Intent service2 = new Intent(Home.this, TransactionService.class);
        startService(service2);


    }

    private void loadMneu() {
        adapter  = new FirebaseRecyclerAdapter<Blog, MainViewHolder>(Blog.class,R.layout.menu_item,MainViewHolder.class,catagory) {
            @Override
            protected void populateViewHolder(MainViewHolder viewHolder, Blog model, int position) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Blog clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isOnClick) {
                        Intent foodList = new Intent(Home.this,ProductList.class);
                        Toast.makeText(Home.this,""+clickitem.getName(),Toast.LENGTH_SHORT).show();
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
        // automatically handle clicks on the Home/Up button, so long
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

        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this,Cart.class);
            cartIntent.putExtra("phone",Common.current_user.getMobile());
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);


        } else if (id == R.id.nav_log_out) {

            Intent signIn = new Intent(Home.this,LoginActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            new database(getBaseContext()).cleanCart();
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
