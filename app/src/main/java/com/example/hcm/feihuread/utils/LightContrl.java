package com.example.hcm.feihuread.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

/**
 * Created by hcm on 2018/4/27.
 */

public class LightContrl {
    ContentResolver  contentResolver;
    private Context context;
    private Window window;
    public LightContrl(Context context,ContentResolver contentResolver,Window window)
    {
        this.context=context;
        this.contentResolver=contentResolver;
        this.window=window;
    }

    public LightContrl() {

    }

    // 获取系统屏幕亮度
    public int getScreenBrightness() {
        int value = 0;
        ContentResolver cr = contentResolver;
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }

    // 获取app亮度
    public void changeAppBrightness(int brightness) {

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        window.setAttributes(lp);
    }



}
