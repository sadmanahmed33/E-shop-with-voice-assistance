package com.example.priya.E_ShopWithvoiceAssistance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.ProductViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpeechProductList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference productList;
    String blogId = "";
    TextToSpeech t1;
    LinearLayoutManager manager = new LinearLayoutManager(this);

    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    FirebaseRecyclerAdapter<Product, ProductViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();

    EditText materialSearchBar;
    Integer flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // recyclerView = findViewById(R.layout.a)
        setContentView(R.layout.activity_speech_product_list);
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Products");
        //textFullname =(TextView)headerView.findViewById(R.id.textFullName);
        //textFullname.setText(Common.current_user.getName());

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //  recyclerView.setLayoutManager(manager);
        //  recyclerView.setHasFixedSize(true);

        if(getIntent()!=null)
        {
            blogId = getIntent().getStringExtra("BlogId");

        }
        if(blogId != null && !blogId.isEmpty())
        {
            loadListProduct(blogId);
        }

        materialSearchBar = (EditText) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your product");
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        checkPermission();

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        materialSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "search a product";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
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
                            materialSearchBar.setText(matches.get(0));
                    }

                    @Override
                    public void onPartialResults(Bundle bundle) {

                    }

                    @Override
                    public void onEvent(int i, Bundle bundle) {

                    }
                });


                materialSearchBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        {
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                            // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                            //_emailText.setText("");
                            materialSearchBar.setText("");

                            materialSearchBar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String name = materialSearchBar.getText().toString();
                                    name=name.replace(" ", "");
                                    //name=name.toLowerCase();
                                    materialSearchBar.setText(name);
                                    materialSearchBar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startSearch(materialSearchBar.getText());
                                        }
                                    });

                                }
                            });

                        }
                    }
                    });








            }
        });


        /// materialSearchBar.setOnSearchActionListener(new );

        loadSuggest();





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

    private void prioritySearch() {
        searchAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("Rating").limitToLast(5)
        ) {
            @Override
            public int getItemCount() {
                flag = super.getItemCount();
                Log.d("NB", "getItemCount: " + super.getItemCount());

                return super.getItemCount();
            }

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {

                //flag = 1;
                Log.d("F", String.valueOf(flag));

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                flag=1;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        String toSpeak = local.getName()+"";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        t1.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD,null);
                        // flag=2;



                        Intent productDetail = new Intent(SpeechProductList.this, SpeechProductDetail.class);
                        productDetail.putExtra("ProductId", searchAdapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            //return flag;
            //final float aFloat = 1;


        };
        recyclerView.setAdapter(searchAdapter);
    }


    private void startSearch(CharSequence text) {
        String toSpeak = "product found";
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        searchAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("Name").startAt(text.toString().toLowerCase()).endAt(text.toString().toLowerCase()+"\uf8ff")
        ) {
            @Override
            public int getItemCount() {
                flag = super.getItemCount();
                Log.d("NB", "getItemCount: " + super.getItemCount());

                return super.getItemCount();
            }

            @Override
            protected void populateViewHolder(final ProductViewHolder viewHolder, Product model, int position) {
                //flag = 1;
                Log.d("F", String.valueOf(flag));

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                flag=1;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // flag=2;
                        String toSpeak = local.getName()+"";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isOnClick) {
                                Intent productDetail = new Intent(SpeechProductList.this, SpeechProductDetail.class);
                                productDetail.putExtra("ProductId", searchAdapter.getRef(position).getKey());
                                startActivity(productDetail);

                            }
                        });

                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            //return flag;
            //final float aFloat = 1;


        };
        recyclerView.setAdapter(searchAdapter);
    }





    private void loadSuggest() {
        productList.orderByChild("MenuId").equalTo(blogId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Product item = postSnapshot.getValue(Product.class);
                            suggestList.add(item.getName());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadListProduct(String blogId) {
        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.product_list,
                ProductViewHolder.class,
                productList.orderByChild("MenuId").equalTo(blogId)) {
            @Override
            protected void populateViewHolder(final ProductViewHolder viewHolder, Product model, int position) {

                viewHolder.product_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.product_image);
                final Product local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        String toSpeak = local.getName()+"";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isOnClick) {
                                Intent productDetail = new Intent(SpeechProductList.this,SpeechProductDetail.class);
                                productDetail.putExtra("ProductId",adapter.getRef(position).getKey());
                                startActivity(productDetail);
                            }
                        });

                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                    }
                });




            }


        };

        Log.d("TAG",""+adapter.getItemCount());

        recyclerView.setAdapter(adapter);

    }
}
