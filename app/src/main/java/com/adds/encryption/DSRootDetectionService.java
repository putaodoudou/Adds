package com.adds.encryption;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Service for detecting rooted device on boot
 * and if rooted, keyStore file will be deleted.
 *
 * @author cs94758
 */
public class DSRootDetectionService extends IntentService {

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     */
    public DSRootDetectionService() {
        super("workerThread");
        setIntentRedelivery(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //checks whether device is rooted
        if (DSRootDetectionHelper.isRooted()) {
            DSCryptographyHelper.deleteKeyStore(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

