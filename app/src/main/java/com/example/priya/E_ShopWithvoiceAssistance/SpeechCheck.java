package com.example.priya.E_ShopWithvoiceAssistance;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.priya.finalprojectsdp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SpeechCheck extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "PhoneAuthActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private ViewGroup mPhoneNumberViews;
    private ViewGroup mSignedInViews;

    private TextView mStatusText;
    private TextView mDetailText;

    private EditText mPhoneNumberField;
    private EditText mVerificationField;

    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;
    private LinearLayout buttontalk;
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_check);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        buttontalk = findViewById(R.id.container1);
        mPhoneNumberField = findViewById(R.id.field_phone_number);
        mVerificationField = findViewById(R.id.field_verfication_code);

        mStartButton = findViewById(R.id.button_start_verification);
        mVerifyButton = findViewById(R.id.button_verify_phone);
        mResendButton = findViewById(R.id.button_resend);
        mDetailText = findViewById(R.id.detail);


        // Assign click listeners
        //mStartButton.setOnClickListener(new);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "start verification";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                mStartButton.setOnClickListener(this);
            }
        });
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "verify";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                mVerifyButton.setOnClickListener(this);
            }
        });
        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Resend code";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                mResendButton.setOnClickListener(this);
            }
        });
        //mResendButton.setOnClickListener(this);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        buttontalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "We send u a verification code";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


            }
        });
        mPhoneNumberField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Enter the mobile number";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                //updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
            mStartButton.setEnabled(false);
        }
        mStartButton.setEnabled(true);
        // [END_EXCLUDE]
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+880"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+880"+ phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String name = getIntent().getStringExtra("name");
                            String address = getIntent().getStringExtra("address");
                            String pass = getIntent().getStringExtra("pass");
                            String repass = getIntent().getStringExtra("repass");
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = database.getReference("Registration");

                            Registration_Class reg = new Registration_Class(name, address, pass,
                                    repass,mPhoneNumberField.getText().toString());
                            ref.child(mPhoneNumberField.getText().toString()).setValue(reg);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(SpeechCheck.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            // [START_EXCLUDE]

                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI

                            // [END_EXCLUDE]
                        }
                    }
                });
    }








    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                enableViews(mStartButton, mPhoneNumberField);
                disableViews(mVerifyButton, mResendButton, mVerificationField);
                mDetailText.setText(null);
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                enableViews(mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                //  disableViews(mStartButton);
                // mDetailText.setText(R.string.status_code_sent);
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                        mVerificationField);
                mDetailText.setText(R.string.status_verification_failed);
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                        mVerificationField);
                mDetailText.setText(R.string.status_verification_succeeded);


                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mVerificationField.setText(cred.getSmsCode());
                    } else {
                        mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                mDetailText.setText(R.string.status_sign_in_failed);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            // Signed out
            mPhoneNumberViews.setVisibility(View.VISIBLE);
            mSignedInViews.setVisibility(View.GONE);

            mStatusText.setText(R.string.signed_out);
        } else {
            // Signed in
            mPhoneNumberViews.setVisibility(View.GONE);
            mSignedInViews.setVisibility(View.VISIBLE);

            enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            mVerificationField.setText(null);

            mStatusText.setText(R.string.signed_in);
            mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        }
    }
    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }
    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_verification:
                if (!validatePhoneNumber()) {
                    return;
                }

                startPhoneNumberVerification(mPhoneNumberField.getText().toString());
                break;
            case R.id.button_verify_phone:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
        }
    }
}
