package com.example.priya.E_ShopWithvoiceAssistance.Common;

import com.example.priya.E_ShopWithvoiceAssistance.Registration_Class;

import java.util.Calendar;
import java.util.Locale;

public class Common {
    public static Registration_Class current_user;
    public static Request current_request;




    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static final int PICK_IMAGE_REQUEST = 1;

    public static String convertCodeToPayment(String payment) {
        if(payment.equals("11"))
            return "Payment Pending In Bkash";
        else if(payment.equals("12"))
            return "Payment Completed In Bkash";
        else if(payment.equals("21"))
            return "Payment Pending In Bkash";
        else
            return "Cash In Delivery";
    }

    public static String convertCodeToPayment1(String payment) {
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


    public static String convertCodeToTransaction(String payment) {
        if(payment.equals("0"))
            return "0";
        else if(payment.equals("1"))
            return "11323";
        else if(payment.equals("2"))
            return "35228";
        else if(payment.equals("3"))
            return "788332";
        else if(payment.equals("4"))
            return "239832";
        else if(payment.equals("5"))
            return "238333";
        else if(payment.equals("6"))
            return "2983233";
        else if(payment.equals("7"))
            return "1912233";
        else if(payment.equals("8"))
            return "2383834";
        else if(payment.equals("9"))
            return "129388234";
        else if(payment.equals("10"))
            return "2329383";
        else if(payment.equals("11"))
            return "667893";
        else
            return "zxjakhw1";

    }


    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed.";
        else if(status.equals("1"))
            return "On the way.";
        else
            return "Shipped.";


    }
public static String getDate (long time)
{
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    calendar.setTimeInMillis(time);
    StringBuilder date = new StringBuilder(
            android.text.format.DateFormat.format("dd-MM-yyyy HH:mm"
            ,calendar)
            .toString());
    return date.toString();
}
}
