package com.example.priya.E_ShopWithvoiceAssistance;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignupActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword, inputName, inputAddress, inputRepass, inputMobile,acnt_no;
    private Button btnSignIn, btnSignUp, btnResetPassword;

    private Button buttonForRegister;
    private EditText  acnt_id;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
//       FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Registration");



        buttonForRegister = findViewById(R.id.sign_up_button);
        btnSignIn = findViewById(R.id.sign_in_button);
        inputName = findViewById(R.id.name);
        inputAddress = findViewById(R.id.input_address);
        inputMobile = findViewById(R.id.input_mobile);
        inputPassword = findViewById(R.id.input_password);
        inputRepass = findViewById(R.id.input_reEnterPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

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

                        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                                R.style.AppTheme);

                        String address = inputAddress.getText().toString();
                        String name = inputName.getText().toString();
                        String mobile = inputMobile.getText().toString();
                        String pass = inputPassword.getText().toString();
                        String repass = inputRepass.getText().toString();

                        // TODO: Implement your own signup logic here.
                        if (dataSnapshot.child(inputMobile.getText().toString()).exists()) {
                            Toast.makeText(SignupActivity.this, "Exist", Toast.LENGTH_SHORT).show();
                        } else {


                            Intent intent = new Intent(getApplicationContext(),check.class);
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

    public void onSignupSuccess() {
        buttonForRegister.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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
            inputName.setError("Enter Valid Name");
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (address.isEmpty()) {
            inputAddress.setError("Enter Valid Address");
            valid = false;
        } else {
            inputAddress.setError(null);
        }


        if (mobile.isEmpty() || mobile.length()!=11) {
            inputMobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            inputMobile.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 6) {
            inputPassword.setError("Length must greater than 6");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        if (repass.isEmpty() || repass.length() < 6 || !(repass.equals(pass))) {
            inputRepass.setError("Password Do not match");
            valid = false;
        } else {
            inputRepass.setError(null);
        }

        return valid;
    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }



/*
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
*/
}


