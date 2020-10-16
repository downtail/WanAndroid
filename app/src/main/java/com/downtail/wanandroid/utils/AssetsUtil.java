package com.downtail.wanandroid.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * WanAndroid
 * Created by downtail on 2020/10/13
 */
public class AssetsUtil {

    public static String getAssetsAuthorData(Context context) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            AssetManager assetManager = context.getAssets();
            inputStreamReader = new InputStreamReader(assetManager.open("author.json"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }
}
