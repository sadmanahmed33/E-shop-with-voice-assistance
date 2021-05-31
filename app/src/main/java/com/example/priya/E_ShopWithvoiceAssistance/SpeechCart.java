package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Order;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.CartAdapter;
import com.example.priya.E_ShopWithvoiceAssistance.database.database;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SpeechCart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database1;
    DatabaseReference requests;

    TextView txtTotalPrice;
    TextToSpeech t1;
    Button btnPlace;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database1 = FirebaseDatabase.getInstance();
        requests = database1.getReference("Requests");

        String phone = getIntent().getStringExtra("phone");

        RelativeLayout talk_btn,talk;



        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        talk = findViewById(R.id.talk);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);
        talk_btn = findViewById(R.id.talk_btn);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSpeak = "Place Your Order";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                btnPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialog();

                    }
                });



                //  anotherpage();

            }
        });

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        talk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "List of carted products";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

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
       /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(SpeechCart.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Enter Your Address: ");
        final EditText editAddress = new EditText(SpeechCart.this);
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
                        */

                // new database(getBaseContext()).cleanCart();
                String price;
                price = txtTotalPrice.getText().toString();

                Toast.makeText(SpeechCart.this,"Thank you for your order request",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(SpeechCart.this,PaymentActivity.class);
                intent.putExtra("Price1",price);
                intent.putExtra("phone",Common.current_user.getMobile());
                // new database(getBaseContext()).cleanCart();

                startActivity(intent);
                finish();
            }
       /* });
        /*alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }*/
    private void loadListFood() {

        cart = new database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "HHHHHH";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("bn","BD");
        NumberFormat fnt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fnt.format(total));

        final int finalTotal = total;
        txtTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = finalTotal +"";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            }
        });




    }
}
