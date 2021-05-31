package com.example.priya.E_ShopWithvoiceAssistance;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class SpeechSignUpActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword, inputName, inputAddress, inputRepass, inputMobile,acnt_no;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private LinearLayout talkbutton;
    private Button buttonForRegister;
    private EditText  acnt_id;
    TextToSpeech t1;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_sign_up);
//       FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Registration");
        talkbutton = findViewById(R.id.talk_btn);



        buttonForRegister = findViewById(R.id.sign_up_button);
        btnSignIn = findViewById(R.id.sign_in_button);
        inputName = findViewById(R.id.name);
        inputAddress = findViewById(R.id.input_address);
        inputMobile = findViewById(R.id.input_mobile);
        inputPassword = findViewById(R.id.input_password);
        inputRepass = findViewById(R.id.input_reEnterPassword);



        checkPermission();

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final SpeechRecognizer mSpeechRecognizer1 = SpeechRecognizer.createSpeechRecognizer(this);
        //final SpeechRecognizer mSpeechRecognizer2 = SpeechRecognizer.createSpeechRecognizer(this);
        final SpeechRecognizer mSpeechRecognizer3 = SpeechRecognizer.createSpeechRecognizer(this);
        final SpeechRecognizer mSpeechRecognizer4 = SpeechRecognizer.createSpeechRecognizer(this);
        final SpeechRecognizer mSpeechRecognizer5 = SpeechRecognizer.createSpeechRecognizer(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Login Page link";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                btnSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SpeechSignUpActivity.this,SpeechLoginActivity.class);
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
                String toSpeak = "Signup Page.";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


            }
        });

        inputName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Enter Your Name";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                inputName.setOnClickListener(new View.OnClickListener() {
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
                                    inputName.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        inputName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    //_emailText.setText("");
                                    //_emailText.setText("");
                                    String repass = inputName.getText().toString();
                                    repass=repass.replace(" ","");
                                    repass.toLowerCase();
                                    inputName.setText(repass);


                                }
                            }
                        });


                    }
                });
            }
        });

        inputPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Enter Your Password";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                inputPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent mSpeechRecognizerIntent4 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        mSpeechRecognizerIntent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        mSpeechRecognizerIntent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.UK);
                        mSpeechRecognizer4.setRecognitionListener(new RecognitionListener() {
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
                                    inputPassword.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        findViewById(R.id.input_password).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer4.startListening(mSpeechRecognizerIntent4);
                                    // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    //_emailText.setText("");
                                    //_emailText.setText("");
                                    String repass = inputPassword.getText().toString();
                                    repass=repass.replace(" ","");
                                    repass.toLowerCase();
                                    inputPassword.setText(repass);
                                    //replace(" ","");
                                    // email = email.replace(" ","");
                                    //email = email.toLowerCase();
                                }
                            }
                        });
                    }
                });
            }
        });
        inputMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Enter Your Phone Number";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                inputMobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent mSpeechRecognizerIntent3= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        mSpeechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        mSpeechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.UK);
                        mSpeechRecognizer3.setRecognitionListener(new RecognitionListener() {
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
                                    inputMobile.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        findViewById(R.id.input_mobile).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer3.startListening(mSpeechRecognizerIntent3);
                                    // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    //_emailText.setText("");
                                    //inputMobile.setText("");
                                    String mobile = inputMobile.getText().toString();
                                    mobile=mobile.replace(" ","");
                                    inputMobile.setText(mobile);
                                    //mobile=mobile.replace(" ","");
                                }
                            }
                        });

                    }
                });
            }
        });

        inputRepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Re enter password";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                inputRepass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent mSpeechRecognizerIntent5 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        mSpeechRecognizerIntent5.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        mSpeechRecognizerIntent5.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.UK);
                        mSpeechRecognizer5.setRecognitionListener(new RecognitionListener() {
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
                                    inputRepass.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        findViewById(R.id.input_reEnterPassword).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer5.startListening(mSpeechRecognizerIntent5);
                                    // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                    //_emailText.setText("");
                                    String repass = inputRepass.getText().toString();
                                    repass=repass.replace(" ","");
                                    repass.toLowerCase();
                                    inputRepass.setText(repass);
                                    //replace(" ","");
                                    // email = email.replace(" ","");
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
        inputAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Please Enter Your Adrress";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                inputAddress.setOnClickListener(new View.OnClickListener() {
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
                                    inputAddress.setText(matches.get(0));
                            }

                            @Override
                            public void onPartialResults(Bundle bundle) {

                            }

                            @Override
                            public void onEvent(int i, Bundle bundle) {

                            }
                        });
                        findViewById(R.id.input_address).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    mSpeechRecognizer1.startListening(mSpeechRecognizerIntent1);
                                    //_emailText.setText("");
                                    String repass = inputAddress.getText().toString();
                                    repass=repass.replace(" ","");
                                    repass.toLowerCase();
                                    inputAddress.setText(repass);
                                }
                            }
                        });

                    }
                });
            }
        });

        buttonForRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Register Button";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                buttonForRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (!validate()) {
                                    onSignupFailed();
                                    return;
                                }

                                buttonForRegister.setEnabled(false);

                                final ProgressDialog progressDialog = new ProgressDialog(SpeechSignUpActivity.this,
                                        R.style.AppTheme);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Creating Account...");
                                progressDialog.show();

                                String address = inputAddress.getText().toString();
                                String name = inputName.getText().toString();
                                String mobile = inputMobile.getText().toString();
                                //  mobile = mobile.replace(" ","");
                                Log.d("MMMob",mobile);
                                String pass = inputPassword.getText().toString();
                                String repass = inputRepass.getText().toString();

                                // TODO: Implement your own signup logic here.
                                if (dataSnapshot.child(inputMobile.getText().toString()).exists()) {
                                    Toast.makeText(getBaseContext(), "Phone number Exists", Toast.LENGTH_LONG).show();
                                    String toSpeak = "Phone number Exists";
                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                } else {
                                    Intent intent = new Intent(getApplicationContext(),SpeechCheck.class);
                                    intent.putExtra("mobile",mobile);
                                    intent.putExtra("name",name);
                                    intent.putExtra("address",address);
                                    intent.putExtra("pass",pass);
                                    intent.putExtra("repass",repass);


                                    startActivity(intent);

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
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }



    public void onSignupSuccess() {
        buttonForRegister.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        String toSpeak = "Failed to sign up";
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        buttonForRegister.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String address = inputAddress.getText().toString();
        String name = inputName.getText().toString();
        String mobile = inputMobile.getText().toString();
        String pass = inputPassword.getText().toString();
        String repass = inputRepass.getText().toString();

        if (name.isEmpty()) {
            inputName.setError("Name Field is empty");
            String toSpeak = "Name Field is empty";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (address.isEmpty()) {
            inputAddress.setError("Enter Valid Address");
            String toSpeak = "Enter Valid Address";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            valid = false;
        } else {
            inputAddress.setError(null);
        }


        if (mobile.isEmpty() || mobile.length()!=11) {
            inputMobile.setError("Enter Valid Mobile Number");
            String toSpeak = "Enter Valid Mobile Number";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        } else {
            inputMobile.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 6) {
            inputPassword.setError("Length must greater than 6");
            String toSpeak = "Length must greater than 6";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        if (repass.isEmpty() || repass.length() < 6 || !(repass.equals(pass))) {
            inputRepass.setError("Password not matched");
            String toSpeak = "Password not matched";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        } else {
            inputRepass.setError(null);
        }

        if((name.isEmpty())&&(address.isEmpty())&&mobile.isEmpty()&&pass.isEmpty()&&repass.isEmpty())
        {
            inputRepass.setError("Sign Up Failed");
            String toSpeak = "Failed to sign up";
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            valid = false;
        }
        else {
            inputName.setError(null);
            inputAddress.setError(null);
            inputMobile.setError(null);
            inputPassword.setError(null);
            inputRepass.setError(null);
        }

        return valid;
    }




/*
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
*/

}


