package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.database.database;
import com.example.priya.finalprojectsdp.R;

public class PaymentActivity extends AppCompatActivity {
    private Button buttonbkash,buttoncash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        buttonbkash = findViewById(R.id.bkash);
        buttoncash = findViewById(R.id.cash);
        final String price = getIntent().getStringExtra("Price1");
        buttoncash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(PaymentActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(PaymentActivity.this,Home.class);
                                startActivity(intent);
                                new database(getBaseContext()).cleanCart();
                                finish();
                            }
                        })
                        //set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

            }
        });
        buttonbkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(PaymentActivity.this)
                        .setTitle("Are you sure to Confirm Order?")
                        .setMessage("Please confirm your payment to 01679357283 this number.User your phone number as a reference.We will send you a transaction Id.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String Bkash = "Bkash";
                                Intent intent = new Intent(PaymentActivity.this,CompleteTransaction.class);
                                intent.putExtra("Price2",price);
                                intent.putExtra("Bkash",Bkash);
                                startActivity(intent);
                                finish();
                            }
                        })
                        //set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Nothing Happened", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

            }
        });


        //Log.d("Price",price);

    }
}
