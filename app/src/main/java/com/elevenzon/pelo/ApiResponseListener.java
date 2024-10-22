package com.elevenzon.pelo;

import org.json.JSONArray;

public interface ApiResponseListener {
    void onSuccess(JSONArray response);
    void onFailure(Throwable throwable);
}
