package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Order;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Request;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.CartAdapter;
import com.example.priya.E_ShopWithvoiceAssistance.database.database;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Cart extends AppCompatActivity implements NavigationView.OnCreateContextMenuListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database1;
    DatabaseReference requests;

    TextView txtTotalPrice;
    TextToSpeech t1;
    Button dlt_cart;
    Button btnPlace;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database1 = FirebaseDatabase.getInstance();
        requests = database1.getReference("Requests");

        //dlt_cart = findViewById(R.id.cart_delete);

        String phone = getIntent().getStringExtra("phone");

        RelativeLayout talk_btn;




        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
       // ItemTouchHelper.SimpleCallback itemTouchHelper = new RecycleInterfaceHelper(0,ItemTouchHelper.LEFT,this);
        //new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();

              //  anotherpage();

            }
        });






        loadListFood();

    }


    private void anotherpage()
    {
        String price;
        price = txtTotalPrice.getText().toString();
        Intent intent =new Intent(this,PaymentActivity.class);
        intent.putExtra("Price1",price);
        intent.putExtra("phone", Common.current_user.getMobile());
        new database(getBaseContext()).cleanCart();

        startActivity(intent);


    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Enter Your Address: ");
        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.current_user.getMobile(),
                        Common.current_user.getName(),
                        editAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

               // new database(getBaseContext()).cleanCart();
                String price = txtTotalPrice.getText().toString();

                Toast.makeText(Cart.this,"Thank you for your order request",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Cart.this,PaymentActivity.class);
                intent.putExtra("Price1",price);
                intent.putExtra("phone",Common.current_user.getMobile());
               // new database(getBaseContext()).cleanCart();

                startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void loadListFood() {

        cart = new database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("bn","BD");
        NumberFormat fnt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fnt.format(total));
        String price;
        price = txtTotalPrice.getText().toString();
        price = price.replace(",","");
        price = price.replace(".00৳","");
        txtTotalPrice.setText(price);
        Log.d("prive",price);




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
        if(item.getTitle().equals(Common.DELETE))
        {
            //deleteCatagory(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }
    private void deleteCatagory(String key) {
        requests.child(key).removeValue();
        Toast.makeText(this,"Item Deleted !!!",Toast.LENGTH_SHORT).show();
    }

}
