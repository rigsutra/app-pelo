package com.elevenzon.pelo;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Response;

public class ApiClient {
    public static JSONObject jsonObject;
    public static void login(String imei, String password, LoginActivity loginActivity) {
        try {
            jsonObject = new JSONObject();
            jsonObject.put("imei", imei);
            jsonObject.put("password", password);
            byte[] compressData =  Public.compressData(jsonObject.toString());
            AndroidNetworking.post(Public.URL_LOGIN)
                    .addHeaders("Content-Encoding", "gzip")
                    .addByteBody(compressData)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsOkHttpResponse(new OkHttpResponseListener() {
                        @Override
                        public void onResponse(Response responseData) {
                            try {
                                String depressData = Public.decompressData(responseData.body().bytes());
                                JSONObject response = new JSONObject(depressData);
                                if(response.get("success").toString().equals("true")) {
                                    Public.jwToken = response.getString("token");
                                    Public.sellerName = response.getString("userName");
                                    Public.companyName = response.getString("companyName");
                                    Public.phoneNumber = response.getString("phoneNumber");
                                    Public.address = response.getString("address");
                                    Public.sellerId = response.getString("sellerId");
                                    Public.subAdminId = response.getString("subAdminId");
                                    loginActivity.progress.setVisibility(View.GONE);
                                    loginActivity.login.setEnabled(true);
                                    Intent intent = new Intent(Public.activity.getApplicationContext(), VenteRapport.class);
                                    Public.activity.startActivity(intent);

                                } else if(response.get("success").toString().equals("false")) {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.get("message").toString(), Toast.LENGTH_LONG).show();
                                    loginActivity.progress.setVisibility(View.GONE);
                                    loginActivity.login.setEnabled(true);
                                }
                            }catch(Exception ex) {
                                System.out.println(ex);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Public.activity.getApplicationContext(), " Database connect failed! \n NetWork Error!\nPlease try again.", Toast.LENGTH_LONG).show();
                            loginActivity.progress.setVisibility(View.GONE);
                            loginActivity.login.setEnabled(true);
                        }
                    });
        }catch (Exception ex) {
            System.out.println("Exception ==>" + ex.getMessage());
        }
    }

    public static void logout() {
        try {
            AndroidNetworking.post(Public.URL_LOGOUT)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                Toast.makeText(Public.activity.getApplicationContext(), response.get("message").toString(), Toast.LENGTH_LONG).show();
                            }catch(Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                        }
                    });
        }catch (Exception ex) {
            System.out.println("Exception ==>" + ex.getMessage());
        }
    }

    public static void ticketDelete(String id, int index) {
        try {
            AndroidNetworking.delete(Public.URL_TICKET_DELET + id)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.get("success").toString().equals("true")) {
                                    Public.responTicketsJsonArray.remove(index);
                                    TicketCustomAdapterListView customAdapterListView = new TicketCustomAdapterListView(Public.activity.getApplicationContext());
                                    TicketView.winTicket_container.setAdapter(customAdapterListView);
                                }
                                Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            }catch(Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            try {
                                Toast.makeText(Public.activity.getApplicationContext(), "Network error! try again!", Toast.LENGTH_SHORT).show();
                            } catch (Exception ex) {}
                        }
                    });
        }catch (Exception ex) {
            System.out.println("Exception ==>" + ex.getMessage());
        }
    }

    public static void ticketDeleteForever(String id, int index) {
        try {
            AndroidNetworking.delete(Public.URL_TICKET_DELET_FOREVER + id)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.get("success").toString().equals("true")) {
                                    Public.responTicketsJsonArray.remove(index);
                                    TicketCustomAdapterListView customAdapterListView = new TicketCustomAdapterListView(Public.activity.getApplicationContext());
                                    TicketView.winTicket_container.setAdapter(customAdapterListView);
                                }
                                Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            }catch(Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            try {
                                Toast.makeText(Public.activity.getApplicationContext(), "Network error! try again!", Toast.LENGTH_SHORT).show();
                            } catch (Exception ex) {}
                        }
                    });
        }catch (Exception ex) {
            System.out.println("Exception ==>" + ex.getMessage());
        }
    }

    public static void getLotteryCategory(final ApiResponseListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Make the API call here
                    try {
                        AndroidNetworking.get(Public.URL_LOTTERY_NAME)
                                .addHeaders("Content-Type", "application/json")
                                .addHeaders("Authorization", "Bearer " + Public.jwToken)
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // do anything with response
                                        try {
                                            if(response.get("success").toString().equals("true")) {
                                                Public.lotteryData = (JSONArray) response.get("data");
                                                listener.onSuccess((JSONArray) response.get("data"));
                                            } else if(response.get("success").toString().equals("false")) {
                                                Toast.makeText(Public.activity.getApplicationContext(), response.get("message").toString(), Toast.LENGTH_LONG).show();
                                                try {
                                                    if(response.getString("message").equals("Unauthorized!")) {
                                                        Public.toLogin();
                                                    }
                                                } catch (Exception ex) {}

                                            }
                                        }catch(Exception ex) {
                                            System.out.println(response);
                                        }
                                    }
                                    @Override
                                    public void onError(ANError error) {
                                        System.out.println("error: " + error);
                                        // handle error
                                        Toast.makeText(Public.activity.getApplicationContext(), " Database connect failed! \n NetWork Error!\nPlease try again.", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }catch (Exception ex) {
                        System.out.println("Exception ==>" + ex.getMessage());
                    }
//                    listener.onSuccess(response);
                } catch (Throwable throwable) {
                    listener.onFailure(throwable);
                }
            }
        }).start();
    }

    public static void requestTicket(JSONObject newTicket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] compressData =  Public.compressData(newTicket.toString());
                    AndroidNetworking.post(Public.URL_REQUEST_TICKET)
                            .addHeaders("Content-Encoding", "gzip")
                            .addHeaders("Authorization", "Bearer " + Public.jwToken)
                            .addByteBody(compressData)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsOkHttpResponse(new OkHttpResponseListener() {
                                @Override
                                public void onResponse(Response responseData) {
                                    try {
                                        String depressData = Public.decompressData(responseData.body().bytes());
                                        JSONObject response = new JSONObject(depressData);
                                        if(response.getString("success").equals("true")) {
//
                                            Public.respon_ticket_data = response.getJSONArray("numbers");

                                            Public.block_data = response.getJSONArray("block_data");
                                            Public.limit_data = response.getJSONArray("limit_data");

                                            if(Public.respon_ticket_data.length() > 0) {
                                                Public.ticketId = response.getString("ticketId");
                                                Toast.makeText(Public.activity.getApplicationContext(), "Ticket info saved to DB! Please print this ticket!", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(Public.activity.getApplicationContext(), "You cant create ticket!", Toast.LENGTH_LONG).show();
                                            }

                                            Intent intent = new Intent(Public.activity, TicketPrint.class);
                                            Public.activity.startActivity(intent);
                                            FiniDialog.spinner.setVisibility(View.GONE);
                                            FiniDialog.bt_ok.setEnabled(true);
                                        } else {
                                            Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                            Public.backendCheckFlag = true;
                                            FiniDialog.spinner.setVisibility(View.GONE);
                                            FiniDialog.bt_ok.setEnabled(true);
                                            try {
                                                if(response.getString("message").equals("Unauthorized!")) {
                                                    Public.toLogin();
                                                }
                                            } catch (Exception ex) {}
                                        }
                                    }catch(Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                                @Override
                                public void onError(ANError anError) {
                                }
                            });
                } catch (Exception err) {
                    System.out.println(err);
                }
            }
        }).start();

    }

    public static void getWinNumber(String lottery, String fromDate, String toDate) {
        try {
            SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
            AndroidNetworking.post(Public.URL_GET_WIN_NUMBER)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addBodyParameter("lotteryCategoryName", lottery)
                    .addBodyParameter("fromDate", format_date.format(new Date(fromDate)))
                    .addBodyParameter("toDate", format_date.format(new Date(toDate)))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            try {
                                if(response.getString("success").equals("true")) {
                                    Public.responWinNumberJsonArray = response.getJSONArray("data");
                                    if(Public.responWinNumberJsonArray.length() > 0) {
                                        Intent intent = new Intent(Public.activity, WinNumberView.class);
                                        Public.activity.startActivity(intent);
                                    } else {
                                        Toast.makeText(Public.activity.getApplicationContext(), "No data found!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            } catch (Exception err) {
                                System.out.println(err);
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            System.out.println(error);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void getTicket(String lottery, String fromDate, String toDate) {
        try {
            SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
            AndroidNetworking.get(Public.URL_GET_TICKETs)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addQueryParameter("lotteryCategoryName", lottery)
                    .addQueryParameter("fromDate", format_date.format(new Date(fromDate)))
                    .addQueryParameter("toDate", format_date.format(new Date(toDate)))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            try {
                                if(response.getString("success").equals("true")) {
                                    Public.responTicketsJsonArray = response.getJSONArray("data");
                                    if(Public.responTicketsJsonArray.length() > 0) {
                                        Public.ticket_view = "sold ticket";
                                        Intent intent = new Intent(Public.activity, TicketView.class);
                                        Public.activity.startActivity(intent);
                                    } else {
                                        Toast.makeText(Public.activity.getApplicationContext(), "No data found!", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            } catch (Exception err) {
                                System.out.println(err);
                            }
                        }

                        @Override
                        public void onError(ANError error) {

                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            System.out.println(error);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void getDeletedTicket(String lottery, String fromDate, String toDate) {
        try {
            SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
            AndroidNetworking.get(Public.URL_GET_DELETED_TICKETs)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addQueryParameter("lotteryCategoryName", lottery)
                    .addQueryParameter("fromDate", format_date.format(new Date(fromDate)))
                    .addQueryParameter("toDate", format_date.format(new Date(toDate)))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            try {
                                if(response.getString("success").equals("true")) {
                                    Public.responTicketsJsonArray = response.getJSONArray("data");
                                    if(Public.responTicketsJsonArray.length() > 0) {
                                        Public.ticket_view = "deleted ticket";
                                        Intent intent = new Intent(Public.activity, TicketView.class);
                                        Public.activity.startActivity(intent);
                                    } else {
                                        Toast.makeText(Public.activity.getApplicationContext(), "No data found!", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            } catch (Exception err) {
                                System.out.println(err);
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            System.out.println(error);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void getWinTicket(String lottery, String fromDate, String toDate) {
        try {
            SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
            AndroidNetworking.get(Public.URL_GET_WIN_TICETs)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addQueryParameter("lotteryCategoryName", lottery)
                    .addQueryParameter("fromDate", format_date.format(new Date(fromDate)))
                    .addQueryParameter("toDate", format_date.format(new Date(toDate)))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            try {
                                if(response.getString("success").equals("true")) {
                                    Public.responTicketsJsonArray = response.getJSONArray("data");
                                    if(Public.responTicketsJsonArray.length() > 0) {
                                        Public.ticket_view = "win ticket";
                                        Intent intent = new Intent(Public.activity, TicketView.class);
                                        Public.activity.startActivity(intent);
                                    } else {
                                        Toast.makeText(Public.activity.getApplicationContext(), "No data found!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            } catch (Exception err) {
                                System.out.println(err);
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            System.out.println(error);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void getReports(String lottery, String fromDate, String toDate) {
        try {
            SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
            AndroidNetworking.get(Public.URL_GET_REPORTS)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addQueryParameter("lotteryCategoryName", lottery)
                    .addQueryParameter("fromDate", format_date.format(new Date(fromDate)))
                    .addQueryParameter("toDate", format_date.format(new Date(toDate)))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            try {
                                if(response.getString("success").equals("true")) {
                                    Public.responJsonObj = response.getJSONObject("data");
                                    Intent intent = new Intent(Public.activity, ReportsData.class);
                                    Public.activity.startActivity(intent);
                                } else {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            } catch (Exception err) {
                                System.out.println(err);
                            }
                        }

                        @Override
                        public void onError(ANError error) {

                            RapportActivity.progress.setVisibility(View.GONE);
                            RapportActivity.search.setEnabled(true);
                            System.out.println(error);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void getTicketNumbers(String id, final ApiResponseListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        AndroidNetworking.get(Public.URL_GET_TICKET_NUMBERS)
                                .addHeaders("Content-Type", "application/json")
                                .addHeaders("Authorization", "Bearer " + Public.jwToken)
                                .addQueryParameter("id", id)
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try {
                                            if(response.getString("success").equals("true")) {
                                                Public.responTicketNumbersJsonArray = response.getJSONArray("data");

                                                listener.onSuccess((JSONArray) response.get("data"));
                                            } else {
                                                Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                                try {
                                                    if(response.getString("message").equals("Unauthorized!")) {
                                                        Public.toLogin();
                                                    }
                                                } catch (Exception ex) {}
                                            }
                                        } catch (Exception err) {
                                            System.out.println(err);
                                        }
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        System.out.println(error);
                                    }
                                });
                    } catch (Exception err) {
                        System.out.println(err);
                    }
                } catch (Throwable throwable) {
                    listener.onFailure(throwable);
                }
            }
        }).start();

    }

    public static void lotteryTimeCheck(String lotteryId) {
        try {
            AndroidNetworking.get(Public.URL_LOTTERY_TIME_CHECK)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addQueryParameter("lotId", lotteryId)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("success").equals("true") && response.getString("data").equals("true")) {
                                    LotteryCategoryPage.progress_layout.setVisibility(View.GONE);
                                    Intent intent = new Intent(Public.activity, VenteActivityAction.class);
                                    Public.activity.startActivity(intent);
                                } else {
                                    LotteryCategoryPage.progress_layout.setVisibility(View.GONE);
                                    Toast.makeText(Public.activity.getApplicationContext(), "Lottery time check failed!", Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            } catch (Exception err) {
                                LotteryCategoryPage.progress_layout.setVisibility(View.GONE);
                                System.out.println(err);
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            LotteryCategoryPage.progress_layout.setVisibility(View.GONE);
                            System.out.println(error);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void replayTicket(JSONObject replayTicketInfo) {
        try {
            byte[] compressData =  Public.compressData(replayTicketInfo.toString());
            AndroidNetworking.post(Public.URL_REPLAY_TICKET)
                    .addHeaders("Content-Encoding", "gzip")
                    .addHeaders("Authorization", "Bearer " + Public.jwToken)
                    .addByteBody(compressData)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsOkHttpResponse(new OkHttpResponseListener() {
                        @Override
                        public void onResponse(Response responseData) {
                            ReplayTicket.progress.setVisibility(View.GONE);
                            ReplayTicket.search.setEnabled(true);
                            try {
                                String depressData = Public.decompressData(responseData.body().bytes());
                                JSONObject response = new JSONObject(depressData);

                                if(response.getString("success").equals("true")) {
                                    JSONArray jsonArray= response.getJSONArray("numbers");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            if(jsonObject1.getBoolean("bonus")) continue;

                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("gameCategory", jsonObject1.getString("gameCategory"));
                                            jsonObject.put("number", jsonObject1.getString("number"));
                                            jsonObject.put("amount", jsonObject1.getInt("amount"));

                                            Public.ticket_data.add(jsonObject);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    Intent intent = new Intent(Public.activity.getApplicationContext(), VenteActivityAction.class);
                                    Public.activity.startActivity(intent);
                                } else {
                                    Toast.makeText(Public.activity.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                    try {
                                        if(response.getString("message").equals("Unauthorized!")) {
                                            Public.toLogin();
                                        }
                                    } catch (Exception ex) {}
                                }
                            }catch(Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            ReplayTicket.progress.setVisibility(View.GONE);
                            ReplayTicket.search.setEnabled(true);
                        }
                    });
        } catch (Exception err) {
            System.out.println(err);
        }
    }
}
