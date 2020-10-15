/*
 *  * EaseMob CONFIDENTIAL
 * __________________
 * Copyright (C) 2017 EaseMob Technologies. All rights reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */
package com.king.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HomeKeyReceiver extends BroadcastReceiver {

    private HomeKeyEvent homeKeyEvent;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (homeKeyEvent != null) {
            homeKeyEvent.onHomeKey();
        }

//        HomeKeyReceiver homeKeyReceiver = new HomeKeyReceiver();
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        registerReceiver(homeKeyReceiver, intentFilter);
    }

    public void setHomeKeyEvent(HomeKeyEvent homeKeyEvent) {
        this.homeKeyEvent = homeKeyEvent;
    }

    public interface HomeKeyEvent {
        void onHomeKey();
    }
}
