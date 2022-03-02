package com.epsilonpros.gocongomagasin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.epsilonpros.gocongomagasin.services.ServiceBackground;
import com.epsilonpros.gocongomagasin.utils.Utils;

public class ReceiverBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Utils.isServiceRunning(context, ServiceBackground.class)){
            context.startService(new Intent(context, ServiceBackground.class));
        }
    }
}
