package com.example.priya.E_ShopWithvoiceAssistance;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText account_no, pass;
    private Button loginButton, SignUp;
    private TextView attemptText;

    long l_login;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Registration");

        account_no = findViewById(R.id.student_account_no_id);
        pass = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);

        SignUp = findViewById(R.id.btn_signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!validate()) {
                        onLoginFailed();
                        return;
                    }

                    loginButton.setEnabled(false);

                    String mobile = account_no.getText().toString();
                    String password = pass.getText().toString();

                    // TODO: Implement your own authentication logic here.
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if((account_no.getText().toString().equals("01679357283"))&&(pass.getText().toString().equals("tahmina123")))
                            {
                                Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_SHORT).show();
                                //Intent intent= new Intent(LoginActivity.this,Database.class);
                                Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);

                            }

                            if(dataSnapshot.child(account_no.getText().toString()).exists())
                            {
                                Registration_Class log = dataSnapshot.child(account_no.getText().toString()).getValue(Registration_Class.class);
                                log.setMobile(account_no.getText().toString());

                                if(log.getPassword().equals(pass.getText().toString()))
                                {
                                    Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_SHORT).show();
                                    //Intent intent= new Intent(LoginActivity.this,Database.class);
                                    Intent intent= new Intent(LoginActivity.this,Home.class);
                                    Common.current_user= log;

                                    startActivity(intent);
                                }

                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Wrong",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Doesn't Exist!",Toast.LENGTH_SHORT).show();

                            }





                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

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

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = account_no.getText().toString();
        String password = pass.getText().toString();

        if (mobile.isEmpty()||mobile.length()!=11) {
            account_no.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            account_no.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            pass.setError("Password Length Must be greater than or equal to6");
            valid = false;
        } else {
            pass.setError(null);
        }

        return valid;
    }

}

