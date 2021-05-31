package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.database.database;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompleteTransaction extends AppCompatActivity {

    private Button button;
    private EditText amount,tran_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_transaction);
        final String price = getIntent().getStringExtra("Price2");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Requests");

        //      final String requId = Common.current_request.getRequestId();


        amount = findViewById(R.id.money_amount);
        tran_id = findViewById(R.id.tran_id);
        final String phoneno = Common.current_user.getMobile();
//        Log.d("Phone",price);

        button = findViewById(R.id.btncnfrmorder);

        amount = findViewById(R.id.money_amount);
        tran_id=findViewById(R.id.tran_id);
       // final String phoneno = Common.current_user.getMobile();
        Log.d("Phone",price);

        button = findViewById(R.id.btncnfrmorder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tran = tran_id.getText().toString();
                String amou = amount.getText().toString();
                if(tran.equals("")|amou.equals(""))
                {
                    Toast.makeText(CompleteTransaction.this, "Fields Are Empty!",
                            Toast.LENGTH_LONG).show();
                }
                else if (amou.equals(price) & tran.equals("55555") )
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("35228"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("78833"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("239832"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("2383834"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("238333"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }

                else if(amou.equals(price) & tran.equals("1912233"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals( "2983233"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("129388234"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("2329383"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("667893"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                    //finish();
                }
                else if(amou.equals(price) & tran.equals("484384"))
                {
                    Intent intent = new Intent(CompleteTransaction.this,Cart.class);
                    Toast.makeText(getApplicationContext(),"Payment Successfully Completed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    new database(getBaseContext()).cleanCart();
                    //set what would happen when positive button is clicked
                   // finish();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(CompleteTransaction.this)
                            .setMessage("Please Enter Valid Transaction Id and Price")
                            //set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(CompleteTransaction.this,CompleteTransaction.class);
                                    //set what would happen when positive button is clicked
                                    finish();
                                }
                            })

                            .show();
                }






            }
        });





    }
    }

