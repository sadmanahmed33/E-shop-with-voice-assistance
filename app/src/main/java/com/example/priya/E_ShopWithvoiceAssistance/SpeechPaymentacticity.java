package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.priya.finalprojectsdp.R;

import java.util.Locale;

public class SpeechPaymentacticity extends AppCompatActivity {
    private Button buttonbkash,buttoncash;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        buttonbkash = findViewById(R.id.bkash);
        buttoncash = findViewById(R.id.cash);

        final String price = getIntent().getStringExtra("Price1");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        buttoncash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak="Cash In Delivaery";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                buttonbkash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),SpeechProductList.class);
                        startActivity(intent);

                    }
                });


            }
        });
        buttonbkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSpeak="Please confirm your payment to 01679357283 this Bkash number.User your phone number as a reference.We will send youa a transaction Id.";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                buttonbkash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),SpeechCompleteTransaction.class);
                        startActivity(intent);

                    }
                });

            }
        });


        //Log.d("Price",price);

    }
}
