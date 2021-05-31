package com.example.priya.E_ShopWithvoiceAssistance;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechLoginActivity extends AppCompatActivity {

    private EditText account_no, pass;
    private Button loginButton, SignUp;
    private LinearLayout talkbutton;
    private TextView attemptText;
    TextToSpeech t1;

    long l_login;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_login);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Registration");


        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final SpeechRecognizer mSpeechRecognizer1 = SpeechRecognizer.createSpeechRecognizer(this);

        account_no = findViewById(R.id.account_no_id);
        pass = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        checkPermission();

        SignUp = findViewById(R.id.btn_signup);
        talkbutton = findViewById(R.id.talk_btn);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Signup Page Link";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                SignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SpeechLoginActivity.this,SpeechSignUpActivity.class);
                        startActivity(intent);
                    }
                });

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


        talkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Login Page";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


            }
        });

        account_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Enter Your Phone Number";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                account_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.UK);
                        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                            @Override
                            public void onReadyForSpeech(Bundle bundle) {

                            }

                            @Override
                            public void onBeginningOfSpeech() {

                            }

                            @Override
                            public void onRmsChanged(float v) {

                            }

                            @Override
                            public void onBufferReceived(byte[] bytes) {

                            }

                            @Override
                            public void onEndOfSpeech() {

                            }

                            @Override
                            public void onError(int i) {

                            }

                            @Override
                            public void onResults(Bundle bundle) {
                                //getting all the matches
                                ArrayList<String> matches = bundle
                                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                                //displaying the first match
                                if (matches != null)
                                    account_no.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        account_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    //_emailText.setText("");
                                    String mobile = account_no.getText().toString();

                                    mobile = mobile.replace(" ","");
                                    account_no.setText(mobile);
                                    //account_no = account_no.toLowerCase();
                                    //String temp_email = email;
                                    //replace(" ","");
                                    // _emailText = _emailText.replace(" ","");
                                    //email = email.toLowerCase();

                                    // _emailText.setTextAppearance();
                                    //_emailText.setHint("Listening...");
                                }
                            }
                        });
                    }
                });
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Enter Your Password";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent mSpeechRecognizerIntent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        mSpeechRecognizerIntent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        mSpeechRecognizerIntent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.UK);
                        mSpeechRecognizer1.setRecognitionListener(new RecognitionListener() {
                            @Override
                            public void onReadyForSpeech(Bundle bundle) {

                            }

                            @Override
                            public void onBeginningOfSpeech() {

                            }

                            @Override
                            public void onRmsChanged(float v) {

                            }

                            @Override
                            public void onBufferReceived(byte[] bytes) {

                            }

                            @Override
                            public void onEndOfSpeech() {

                            }

                            @Override
                            public void onError(int i) {

                            }

                            @Override
                            public void onResults(Bundle bundle) {
                                //getting all the matches
                                ArrayList<String> matches = bundle
                                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                                //displaying the first match
                                if (matches != null)
                                    pass.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        findViewById(R.id.password).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer1.startListening(mSpeechRecognizerIntent1);
                                    // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    //_emailText.setText("");
                                    String password = pass.getText().toString();
                                    password=password.replace(" ","");
                                    password=password.toLowerCase();
                                    pass.setText(password);
                                    //replace(" ","");
                                    // email = email.replace(" ","");
                                    //email = email.toLowerCase();
                                    pass.addTextChangedListener(new TextWatcher() {

                                        public void onTextChanged(CharSequence s, int start,int before, int count)
                                        {
                                            // TODO Auto-generated method stub
                                            if(pass.getText().toString().equals("email"))     //size as per your requirement
                                            {
                                                mSpeechRecognizer1.stopListening();
                                                account_no.requestFocus();
                                            }



                                        }
                                        public void beforeTextChanged(CharSequence s, int start,
                                                                      int count, int after) {
                                            // TODO Auto-generated method stub

                                        }

                                        public void afterTextChanged(Editable s) {
                                            // TODO Auto-generated method stub
                                        }

                                    });

                                    // _emailText.setTextAppearance();
                                    //_emailText.setHint("Listening...");
                                }
                            }
                        });
                    }
                });
            }
        });







        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Login Button";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!validate()) {
                            onLoginFailed();
                            return;
                        }

                        loginButton.setEnabled(false);

                        final ProgressDialog progressDialog = new ProgressDialog(SpeechLoginActivity.this,
                                R.style.AppTheme);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();

                        String mobile = account_no.getText().toString();
                        String password = pass.getText().toString();

                        // TODO: Implement your own authentication logic here.
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if((account_no.getText().toString().equals("01679357283"))&&(pass.getText().toString().equals("tahmina123")))
                                {
                                    Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_SHORT).show();
                                    //Intent intent= new Intent(SpeechLoginActivity.this,Database.class);
                                    Intent intent= new Intent(SpeechLoginActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }

                                if(dataSnapshot.child(account_no.getText().toString()).exists())
                                {
                                    Registration_Class log = dataSnapshot.child(account_no.getText().toString()).getValue(Registration_Class.class);
                                    log.setMobile(account_no.getText().toString());

                                    if(log.getPassword().equals(pass.getText().toString()))
                                    {
                                        Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_SHORT).show();
                                        //Intent intent= new Intent(SpeechLoginActivity.this,Database.class);
                                        Intent intent= new Intent(SpeechLoginActivity.this,SpeechHome.class);
                                        Common.current_user= log;

                                        startActivity(intent);
                                    }

                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Wrong Password",Toast.LENGTH_SHORT).show();
                                        String toSpeak = "Wrong Password";
                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                    }

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Doesn't Exist!",Toast.LENGTH_SHORT).show();
                                    String toSpeak = "Doesn't Exist";
                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                }





                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });



            }
        });




    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        String toSpeak = "Login Failed";
       // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
       // t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = account_no.getText().toString();
        String password = pass.getText().toString();

        if (mobile.isEmpty()||mobile.length()!=11) {
            account_no.setError("Enter Valid Mobile Number");
            String toSpeak = "Enter Valid Mobile Number";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        } else {
            account_no.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            pass.setError("Password Length Must be greater than or equal to 6");
            String toSpeak = "Password Length Must be greater than or equal to 6";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        } else {
            pass.setError(null);
        }

        if ((password.isEmpty() || password.length() < 6)&&(mobile.isEmpty()||mobile.length()!=11)) {
            pass.setError("Password Length Must be greater than or equal to 6");
            String toSpeak = "Login Failed";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        }
        else
        {
            pass.setError(null);
            account_no.setError(null);
        }


        return valid;
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

}

