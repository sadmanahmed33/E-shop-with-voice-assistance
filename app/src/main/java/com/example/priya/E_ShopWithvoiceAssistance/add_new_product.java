package com.example.priya.E_ShopWithvoiceAssistance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.finalprojectsdp.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//import com.google.android.gms.tasks.Task;

public class add_new_product extends AppCompatActivity {

    private ImageButton mSelectImage;

    private EditText mPostTittle;
    private EditText mPostDesc,mPrice,mDiscount;
    private Button mSubmitBtn;


    private Uri mImageUri = null ;
    private  static final  int GALLERY_REQUEST = 1 ;

    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    String  photoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Products");




        mSelectImage =(ImageButton) findViewById(R.id.image_select);
        mPostTittle =(EditText)findViewById(R.id.edit_name);
        mPostDesc =(EditText)findViewById(R.id.edit_description);
        mPrice =(EditText)findViewById(R.id.edit_price);
        mDiscount = (EditText) findViewById(R.id.edit_discount);

        mSubmitBtn =(Button)findViewById(R.id.submit_button);
        mProgress = new ProgressDialog(this);


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Common.PICK_IMAGE_REQUEST);
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpostactivity();

            }
        });


    }


    private void startpostactivity() {
        mProgress.setMessage("Adding a New Product");

        final String title_val = mPostTittle.getText().toString().trim();
        final String title_val1 = mPostDesc.getText().toString().trim();
        final String title_val2 = mPrice.getText().toString().trim();
        final String title_val3 = mDiscount.getText().toString().trim();




        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(title_val1) &&!TextUtils.isEmpty(title_val2)&&!TextUtils.isEmpty(title_val3) && mImageUri !=null)
        {
            mProgress.show();

            final StorageReference filepath = mStorage.child("Blog_Image").child(mImageUri.getLastPathSegment());


            filepath.putFile(mImageUri).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            photoLink= uri.toString();
                            DatabaseReference newPost = mDatabase.push();
                            newPost.child("MenuId").setValue("01");
                            newPost.child("Name").setValue(title_val);
                            newPost.child("Description").setValue(title_val1);
                            newPost.child("Price").setValue(title_val2);
                            newPost.child("Discount").setValue(title_val3);
                            newPost.child("Image").setValue(photoLink);
                            mProgress.dismiss();
                            startActivity(new Intent(add_new_product.this,admin_product_list.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Sadman", "onFailure: " + e.toString());
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Log.d("Sadman", "onCanceled:");
                        }
                    });







                }
            });
        }
    }


    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Common.PICK_IMAGE_REQUEST  || resultCode == RESULT_OK )
        {

            //mImageUri = data.getData();
            mImageUri = data.getData();
            mSelectImage.setImageURI(mImageUri);

        }
    }








}
