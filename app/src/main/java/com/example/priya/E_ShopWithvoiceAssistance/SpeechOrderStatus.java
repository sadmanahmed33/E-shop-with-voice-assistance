package com.example.priya.E_ShopWithvoiceAssistance;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priya.E_ShopWithvoiceAssistance.Common.Common;
import com.example.priya.E_ShopWithvoiceAssistance.Common.Request;
import com.example.priya.E_ShopWithvoiceAssistance.Interface.ItemClickListener;
import com.example.priya.E_ShopWithvoiceAssistance.ViewHolder.OrderViewHolder;
import com.example.priya.finalprojectsdp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SpeechOrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtUsername;
    TextToSpeech t1;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    RecyclerView talk_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        talk_btn = findViewById(R.id.listOrders);

        if(getIntent().getExtras() == null){
            loadOrders(Common.current_user.getMobile());
        }
        else {
            loadOrders(getIntent().getStringExtra("phone"));
        }



        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "All Order List are here which is confirmed by you ";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


            }
        });

    }



    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(final OrderViewHolder viewHolder, final Request model, final int position) {
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtUserName.setText(Common.current_user.getName());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderTransaction.setText(model.getaTransaction());
                viewHolder.txtOrderPayment.setText(convertCodeToPayment(model.getPayment()));

                final Request model1 = model;
                viewHolder.txtOrderPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = "user phone number"+model1.getPhone()+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+convertCodeToPayment(model.getPayment()),Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.txtOrderAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = "user phone number"+model1.getAddress()+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+model1.getAddress(),Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.txtOrderId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = "order id"+adapter.getRef(position).getKey()+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+adapter.getRef(position).getKey(),Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.txtUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = "user name"+model1.getName()+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+model1.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.txtOrderStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = "order status"+model1.getStatus()+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+model1.getStatus(),Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.txtOrderPayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = " "+convertCodeToPayment(model.getPayment())+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+(convertCodeToPayment(model.getPayment())),Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.txtOrderTransaction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toSpeak = "order phone number"+model1.getaTransaction()+"  ";
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),  ""+model1.getaTransaction(),Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {


                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToPayment(String payment) {
        if(payment.equals("11"))
            return "Payment Pending In Bkash";
        else if(payment.equals("12"))
            return "Payment Completed In Bkash";
        else if(payment.equals("21"))
            return "Payment Pending In Nexus Pay";
        else if(payment.equals("22"))
            return "Payment Completed In Nexus Pay";
        else if(payment.equals("31"))
            return "Payment Pending In Rocket";
        else if(payment.equals("32"))
            return "Payment Completed In Rocket";
        else
            return "Cash In Delivery";
    }


    private String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed.";
        else if(status.equals("1"))
            return "On the way.";
        else
            return "Shipped.";


    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

}
