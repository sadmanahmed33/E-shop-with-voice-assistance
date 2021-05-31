package com.example.priya.E_ShopWithvoiceAssistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


        import android.content.Intent;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Product;
import com.example.priya.finalprojectsdp.R;
import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class Database extends AppCompatActivity {

    public static final String fullname = "Name";
    public static final String iamge = "Image";


    ListView listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<Product> Productlist;
    ArrayList<String> list;
    ArrayAdapter<Product> adapter;


    Product product;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Products");

        // registration_class=new Registration_Class();
        listView = findViewById(R.id.list_view_id);
        Productlist = new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = Productlist.get(position);


                String no = reference.getKey();

                Intent intent = new Intent(getApplicationContext(),priorityData1.class);
                intent.putExtra(fullname, product.getName());



                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Productlist.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    product=ds.getValue(Product.class);
                    Productlist.add(product);
                   // list.add(registration_class.getId()+ "   " + registration_class.getName()+ "     " + registration_class.getEmail());
                }
                RegisterListClass adapter = new RegisterListClass(Database.this,Productlist);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
