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
import java.util.Timer;
import java.util.TimerTask;

public class FrontPage extends AppCompatActivity {
    TextToSpeech t1,t2;
    private Button btntalk,volumebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        btntalk = findViewById(R.id.talk_btn);
        volumebtn = findViewById(R.id.volumentn);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        t2=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t2.setLanguage(Locale.UK);
                }
            }
        });
        btntalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Thank you.Happy shopping.This is the Front page.In the right upper corner ther is a button.If you clink on this you can control this by your voice command.";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                    }
                },20000);

                btntalk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }
                });

            }
        });

        volumebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Now You can control this app with voice command";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t2.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(getApplicationContext(),SpeechLoginActivity.class);
                startActivity(intent);
            }
        });





    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        if(t2 !=null){
            t2.stop();
            t2.shutdown();
        }
        super.onPause();
    }
}
