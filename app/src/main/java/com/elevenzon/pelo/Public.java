package com.elevenzon.pelo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;

import com.elevenzon.pelo.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import java.text.NumberFormat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Public {
    //public static final String URL= "http://gwtechsoft.com/api/";
    public static final String URL= "https://gwtbackend.onrender.com/api/";
//    public static final String SOCKET_URL = "wss://gwtechsoft.com/socketioconnect/";

//    public static final String URL = "http://10.0.2.2:8080/api/";
//    public static final String SOCKET_URL = "ws://10.0.2.2:5000/";

//    public static final String URL = "http://54.248.27.30/api/";

    public static final String URL_LOGIN = URL+"seller/signin";
    public static final String URL_LOGOUT = URL+"seller/signout";

    public static final String URL_REQUEST_TICKET = URL + "seller/newticket";
    public static final String URL_LOTTERY_NAME = URL + "admin/getlotterycategory";
    public static final String URL_GET_WIN_NUMBER = URL + "seller/getwiningnumber";
    public static final String URL_GET_TICKETs = URL + "seller/gettickets";
    public static final String URL_GET_DELETED_TICKETs = URL + "seller/getdeletedtickets";
    public static final String URL_GET_WIN_TICETs = URL + "seller/getwintickets";
    public static final String URL_GET_REPORTS = URL + "seller/getsalereports";
    public static final String URL_LOTTERY_TIME_CHECK = URL + "seller/lotterytimecheck";
    public static final String URL_GET_TICKET_NUMBERS = URL + "seller/getticketnumbers";
    public static final String URL_TICKET_DELET = URL + "seller/deleteticket/";
    public static final String URL_TICKET_DELET_FOREVER = URL + "seller/deleteticketforever/";
    public static final String URL_REPLAY_TICKET = URL + "seller/replayticket/";

    public static String lotteryCategoryName = "";
    public static String sellerName = "";
    public static String subAdminId = "";
    public static String companyName = "";
    public static String address = "";
    public static String phoneNumber = "";
    public static String ticketId = "";
    public static String jwToken = "";
    public static String sellerId = "";

    public static ArrayList ticket_data = new ArrayList();
    public static JSONArray respon_ticket_data = new JSONArray();
    public static JSONArray block_data = new JSONArray();
    public static JSONArray limit_data = new JSONArray();
    public static JSONObject responJsonObj;
    public static JSONArray responTicketsJsonArray;
    public static JSONArray responWinNumberJsonArray;
    public static JSONArray responTicketNumbersJsonArray;

    public static JSONArray lotteryData = new JSONArray();
    public static String fromDate;
    public static String toDate;

    public static boolean backendCheckFlag = true;

    public static View view ;

    public static String device_imei = "";

    public static Activity activity;

    public static SharedPreferences sharedPreferences;
    public static String selected_print = "sunmi";
    public static String ticket_view = "";
    public static long paid_amount = 0;
    public static int flag = 0;
    public static Bitmap reuseBitmap;
    public static TicketInfoCustomAdapterListView customAdapterListView;
    public static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    public static void addTicketItem(String gameCategory, String number, int amount) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gameCategory", gameCategory);
            jsonObject.put("number", number);
            jsonObject.put("amount", amount);

            ticket_data.add(jsonObject);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void calc_sum() {
        try {
            long sum = 0;

            // Iterate over the elements of the ticket_data Vector
            for (int i = 0; i < ticket_data.size(); i++) {
                // Get the JSONObject at the current index
                JSONObject jsonTicketObject = (JSONObject) ticket_data.get(i);

                // Get the amount value from the JSONObject and add it to the sum
                sum += jsonTicketObject.optInt("amount");
            }

            customAdapterListView = new TicketInfoCustomAdapterListView(VenteActivityAction.get_context);
            VenteActivityAction.listView.setAdapter(customAdapterListView);

            VenteActivityAction.total_paris.setText(numberFormat.format(ticket_data.size()));
            VenteActivityAction.valueur.setText(numberFormat.format(sum));

        } catch (Exception err) {System.out.println("print data error: " + err);}
    }

    public static Bitmap convertViewToBitmap(final View mView, Bitmap reuseBitmap) {
        @SuppressLint("Range") final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.MATCH_PARENT, View.MeasureSpec.UNSPECIFIED);
        @SuppressLint("Range") final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.UNSPECIFIED);
        mView.measure(widthMeasureSpec, heightMeasureSpec);

        int width = mView.getMeasuredWidth();
        int height = mView.getMeasuredHeight();

        if (reuseBitmap == null || reuseBitmap.getWidth() != width || reuseBitmap.getHeight() != height) {
            reuseBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas c = new Canvas(reuseBitmap);
        mView.layout(0, 0, width, height);
        mView.draw(c);

        return reuseBitmap;
    }

    public static Bitmap scaleImage(Bitmap bitmap1, Bitmap reuseBitmap) {
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        int newWidth = 384;
        float scaleWidth = (float) newWidth / (float) width;

        Matrix matrix;
        if (reuseBitmap != null) {
            matrix = new Matrix();
            matrix.reset();
        } else {
            matrix = new Matrix();
        }

        matrix.postScale(scaleWidth, 1.0F);

        return Bitmap.createBitmap(bitmap1, 0, 0, width, height, matrix, true);
    }

    public static void sendViewToPrinter(View view, Bitmap reuseBitmap) {
        Bitmap bitmap = convertViewToBitmap(view, reuseBitmap);
        sendImageToPrinter(scaleImage(bitmap, reuseBitmap));
    }

    public static void sendImageToPrinter(Bitmap bitmap) {
        SunmiPrintHelper.getInstance().setAlign(1);
        SunmiPrintHelper.getInstance().printBitmap(bitmap, 0);
        SunmiPrintHelper.getInstance().feedPaper();
    }

    public static void toLogin() {
        try {
            Intent intent = new Intent(Public.activity, LoginActivity.class);
            Public.activity.startActivity(intent);
        } catch (Exception ex) {
            System.out.println("login error: " + ex);
        }
    }


    public static byte[] compressData(String data) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
            gzipOutputStream.write(data.getBytes("UTF-8"));
            gzipOutputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decompressData(byte[] compressedData) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = gzipInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            gzipInputStream.close();
            return outputStream.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
