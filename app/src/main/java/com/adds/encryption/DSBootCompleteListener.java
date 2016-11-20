package com.adds.encryption;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast Receiver to listen for boot complete intents.
 *
 * @author Rolbin
 */
public class DSBootCompleteListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(action) || Intent.ACTION_REBOOT.equals(action)) {
            //Start root detection service.
            Intent serviceIntent = new Intent(context, DSRootDetectionService.class);
            context.startService(serviceIntent);
        } else {
            //do nothing
        }
    }
}
