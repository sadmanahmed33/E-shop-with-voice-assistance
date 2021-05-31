package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.priya.finalprojectsdp.R;

public class AdminPage extends AppCompatActivity {
    public Button button1;
    public Button button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        button1 = findViewById(R.id.category);
        button2 = findViewById(R.id.user);
        button3 = findViewById(R.id.order);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this,AdminOrderStatus.class);
                startActivity(intent);
            }
        });


     button2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(AdminPage.this,RegisterDetails.class);
             startActivity(intent);
         }
     });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
