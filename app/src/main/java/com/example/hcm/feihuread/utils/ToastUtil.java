package com.example.hcm.feihuread.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hcm on 2018/4/2.
 */

public class ToastUtil {

    private static Context context = null;
    private static Toast toast = null;


    public static void getShortToast(Context context, int retId){
        if (toast == null) {
            toast = Toast.makeText(context, retId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(retId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }


        toast.show();
    }


    public static void getShortToastByString(Context context, String hint){
        if (toast == null) {
            toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
        } else {
            toast.setText(hint);
            toast.setDuration(Toast.LENGTH_SHORT);
        }


        toast.show();
    }


    public static void getLongToast(Context context, int retId){
        if (toast == null) {
            toast = Toast.makeText(context, retId, Toast.LENGTH_LONG);
        } else {
            toast.setText(retId);
            toast.setDuration(Toast.LENGTH_LONG);
        }


        toast.show();
    }


    public static void getLongToastByString(Context context, String hint){
        if (toast == null) {
            toast = Toast.makeText(context, hint, Toast.LENGTH_LONG);
        } else {
            toast.setText(hint);
            toast.setDuration(Toast.LENGTH_LONG);
        }


        toast.show();
    }
}