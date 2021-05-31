package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.priya.finalprojectsdp.R;

public class bkashPayment extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash_payment);
        button = findViewById(R.id.bkashok);
        final String price = getIntent().getStringExtra("Price2");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bkashPayment.this,CompleteTransaction.class);
                intent.putExtra("Price3",price);
                startActivity(intent);
            }
        });
    }
}
