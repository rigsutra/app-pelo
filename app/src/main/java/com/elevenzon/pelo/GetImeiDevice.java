package com.elevenzon.pelo;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by binaryvi on 06/06/2016.
 */
public class GetImeiDevice {

    Context context;

    public GetImeiDevice(Context context){
        this.context = context;
    }

    public String getImeiNumber(){
        try {
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String id = tManager.getDeviceId();
            Public.device_imei = id;
            return  id;
        }catch (Exception ex) {
            String id = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
//            Toast.makeText(this.context, " IMEI does not exist.\n Get the device ID.", Toast.LENGTH_LONG).show();
            Public.device_imei = id;
            return id;
        }
    }
}
