package com.epsilonpros.gocongomagasin.utils;

import android.content.Context;

import com.android.volley.Response;
import com.epsilonpros.gocongomagasin.interfaces.GeneralInterface;

import java.util.ArrayList;

public class Engine {

    public static void getResponse (Context context, String url, ArrayList<String[]> list, Response.Listener<String> listener, Response.ErrorListener errorListener){
        new Thread(()->{
            Utils.sendOnServer(context, url, Engine.class.getSimpleName(), list,listener,  errorListener );
        }).start();
    }

}
