package com.example.priya.E_ShopWithvoiceAssistance;

import com.google.firebase.database.FirebaseDatabase;

public class setpermission extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
