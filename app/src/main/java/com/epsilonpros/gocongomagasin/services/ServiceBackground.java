package com.epsilonpros.gocongomagasin.services;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.epsilonpros.gocongomagasin.modeles.Produit;
import com.epsilonpros.gocongomagasin.sqlite.BddManager;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ServiceBackground extends Service {

    Timer timer;
    Gson gson;
    SQLiteDatabase database;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        gson = new Gson();

        sendAgents();
        return START_STICKY;
    }
    private void sendAgents(){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        },0,TimeUnit.HOURS.toMillis(1));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.purge();
        timer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
