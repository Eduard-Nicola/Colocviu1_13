package ro.pub.cs.systems.eim.Colocviu1_13.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import ro.pub.cs.systems.eim.Colocviu1_13.general.Constants;

public class Colocviu1_13Service extends Service {
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String commands = intent.getStringExtra(Constants.COMMANDS);
        processingThread = new ProcessingThread(this, commands);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
