package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.priya.finalprojectsdp.R;

public class aaaa extends AppCompatActivity {

    EditText editText1;
    Button b1;
    String data_1;
    String toaddress;
    String data_2;
    String sendingSpinnerValue;
    String PhoneNumber;
    String TransectionNumber;

    EditText editTextPhoneNumber;
    EditText editTextTransection;


    TextView spinnerdata;

    Spinner sp; //define spinner
    String names [] = {"Bkash","Cash on Delivary"}; //make string array
    ArrayAdapter <String> adapter; //define array adapter of string type

    String record = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaaa);

        //here start the spinner code
        sp =(Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        spinnerdata = (TextView)findViewById(R.id.spinnerValueIsHere);

        //set adapter to spinner
        sp.setAdapter(adapter);

        //set spinner method
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //use possition value
                switch (position)
                {
                    case 0:
                        record = "Bkash";
                        break;

                    case  1:
                        record = "Cash on Delivary ";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        Bundle bundle =getIntent().getExtras();

        data_1 = bundle.getString("Price1"); //here is the price

        TextView textview1 =(TextView) findViewById(R.id.textView2);
        textview1.setText(data_1);



        //here is the address


        b1 =(Button)findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextpageData();
            }
        });






    } //here ends the oncreate method or say main method



    //here defines the calling function
    private void nextpageData()
    {

        Intent intent =new Intent(this,bbbb.class);
        editText1=(EditText)findViewById(R.id.address);
        toaddress = editText1.getText().toString();  //it is the address value in string
        TextView textview11 =(TextView) findViewById(R.id.textView2);
        data_2 = textview11.getText().toString();  //it is the price value in string

        //sending spinner value
        TextView textview55 =(TextView) findViewById(R.id.spinnerValueIsHere);
        sendingSpinnerValue=textview55.getText().toString();   //it is the payment(spinner) value in string
        //ending spinner value

        //sending phone no
        editTextPhoneNumber = (EditText) findViewById(R.id.phone_no);
        PhoneNumber =editTextPhoneNumber.getText().toString();
        //ending

        //sending transection no
        editTextTransection =(EditText) findViewById(R.id.transection);
        TransectionNumber =editTextTransection.getText().toString();


        //ending

        intent.putExtra("Price2",data_2);
        intent.putExtra("address",toaddress);
        intent.putExtra("spin",sendingSpinnerValue);
        intent.putExtra("PHONE",PhoneNumber);
        intent.putExtra("TRAN",TransectionNumber);


        startActivity(intent);




    }

    public void displayResult(View view)
    {
        spinnerdata.setTextSize(18);
        spinnerdata.setText(record);

    }


} // here is the last line
