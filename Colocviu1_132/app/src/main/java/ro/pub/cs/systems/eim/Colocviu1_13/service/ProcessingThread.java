package ro.pub.cs.systems.eim.Colocviu1_13.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

import ro.pub.cs.systems.eim.Colocviu1_13.general.Constants;

public class ProcessingThread extends Thread {
    private Context context = null;

    private String commands;

    public ProcessingThread(Context context, String commands) {
        this.context = context;
        this.commands = commands;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started!");

        sleep();
        sendMessage();

        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_BROADCAST);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + commands);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
