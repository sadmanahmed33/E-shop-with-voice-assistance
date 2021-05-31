package com.example.priya.E_ShopWithvoiceAssistance;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Order;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Rating;
import com.example.priya.E_ShopWithvoiceAssistance.database.database;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

public class ProductDetail extends AppCompatActivity implements RatingDialogListener {
    TextView product_name,product_price,product_description;
    ImageView product_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart,btnRating;
    ElegantNumberButton numberButton;
    String productId ="";

    FirebaseDatabase database;
    DatabaseReference products;
    DatabaseReference ratings;
    RatingBar ratingBar;

    Product currentProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        database = FirebaseDatabase.getInstance();
        products = database.getReference("Products");
        ratings = database.getReference("Rating");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);
        btnRating = (FloatingActionButton) findViewById(R.id.btn_rating);
        ratingBar = (RatingBar)  findViewById(R.id.ratingBar);
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new database(getBaseContext()).addToCart(new Order(
                    productId,
                    currentProduct.getName(),
                    numberButton.getNumber(),
                    currentProduct.getPrice(),
                    currentProduct.getDiscount()


                ));
                Toast.makeText(ProductDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });
        product_description = (TextView) findViewById(R.id.food_description);
        product_name = (TextView)findViewById(R.id.food_name);
        product_price = (TextView) findViewById(R.id.food_price);
        product_image = (ImageView) findViewById(R.id.img_food);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        if(getIntent() != null)
            productId = getIntent().getStringExtra("ProductId");

        if(!productId.isEmpty())
        {

            getDetailProduct(productId);
            getRatingProduct(productId);
        }

    }

    private void getRatingProduct(String productId) {
        Query productRating = ratings.orderByChild("productId").equalTo(productId);
        productRating.addListenerForSingleValueEvent(new ValueEventListener() {
            int count = 0 ,sum =0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Rating rating = dataSnapshot1.getValue(Rating.class);
                    sum+=Integer.parseInt(rating.getRateValue());
                    count++;
                   // break;
                }
                if(count !=0) {
                    float average = sum / count;
                    ratingBar.setRating(average);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Good","Very Good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate This Product")
                .setDescription("Pleae select some start and give your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.colorPrimaryDark)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(ProductDetail.this)
                .show();

    }

    private void getDetailProduct(String productId) {
        products.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentProduct= dataSnapshot.getValue(Product.class);

                Picasso.with(getBaseContext()).load(currentProduct.getImage())
                            .into(product_image);
                collapsingToolbarLayout.setTitle(currentProduct.getName());
                product_price.setText(currentProduct.getPrice());
                product_name.setText(currentProduct.getName());
                product_description.setText(currentProduct.getDescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        final Rating rating = new Rating(Common.current_user.getMobile(),
                productId,
                String.valueOf(i),
                s
                );
        ratings.child(Common.current_user.getMobile()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.current_user.getMobile()).exists())
                {
                    ratings.child(Common.current_user.getMobile()).removeValue();
                    ratings.child(Common.current_user.getMobile()).setValue(rating);
                }
                else
                {
                    ratings.child(Common.current_user.getMobile()).setValue(rating);
                }
                Toast.makeText(ProductDetail.this,"Thank your for submit rating!!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
