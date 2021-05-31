package com.example.priya.E_ShopWithvoiceAssistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.priya.finalprojectsdp.R;

public class bbbb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbbb);


        Bundle bundle =getIntent().getExtras();

        String data22= bundle.getString("Price2");
        TextView textview1 =(TextView) findViewById(R.id.textView4);
        textview1.setText(data22);

        String data33= bundle.getString("address");
        TextView textview2 =(TextView) findViewById(R.id.textView5);
        textview2.setText(data33);

        String data44 =bundle.getString("spin");
        TextView textview3 =(TextView) findViewById(R.id.textView6);
        textview3.setText(data44);


        String data66 =bundle.getString("PHONE");
        TextView textview4 =(TextView) findViewById(R.id.textView7);
        textview4.setText(data66);

        String data77 =bundle.getString("TRAN");
        TextView textview5 =(TextView) findViewById(R.id.textView8);
        textview5.setText(data77);

    }
}
