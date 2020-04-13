package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.Colocviu1_13.general.Constants;
import ro.pub.cs.systems.eim.Colocviu1_13.service.Colocviu1_13Service;

public class Colocviu1_13MainActivity extends AppCompatActivity {

    private Button buttonNorth;
    private Button buttonSouth;
    private Button buttonEast;
    private Button buttonWest;
    private TextView textView;
    private Button buttonActivity;

    private Integer numberOfClicks = 0;

    private Integer serviceStatus = Constants.SERVICE_STOPPED;
    private IntentFilter intentFilter = new IntentFilter();

    private class ButtonClickListener implements View.OnClickListener {
        private String name;

        ButtonClickListener(String name) {
            this.name = name;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.buttonActivity:
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_13SecondaryActivity.class);
                    String commands = textView.getText().toString();
                    intent.putExtra(Constants.COMMANDS, commands);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    textView.setText("");
                    numberOfClicks = 0;
                    break;
                default:
                    numberOfClicks++;
                    String text = textView.getText().toString();

                    if (text.isEmpty()) {
                        textView.setText(name);
                    } else {
                        textView.setText(textView.getText() + "," + name);
                    }

                    if (numberOfClicks == Constants.CLICKS_THRESHOLD && serviceStatus != Constants.SERVICE_STARTED) {
                        Intent intentService = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                        intentService.putExtra(Constants.COMMANDS, textView.getText().toString());
                        getApplicationContext().startService(intentService);
                        serviceStatus = Constants.SERVICE_STARTED;
                    }

                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_main);

        buttonNorth = findViewById(R.id.buttonNorth);
        buttonSouth = findViewById(R.id.buttonSouth);
        buttonEast = findViewById(R.id.buttonEast);
        buttonWest = findViewById(R.id.buttonWest);
        textView = findViewById(R.id.textView);
        buttonActivity = findViewById(R.id.buttonActivity);

        buttonNorth.setOnClickListener(new ButtonClickListener("North"));
        buttonSouth.setOnClickListener(new ButtonClickListener("South"));
        buttonEast.setOnClickListener(new ButtonClickListener("East"));
        buttonWest.setOnClickListener(new ButtonClickListener("West"));
        buttonActivity.setOnClickListener(new ButtonClickListener(""));

        intentFilter.addAction(Constants.ACTION_BROADCAST);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Constants.RESULT_REGISTER) {
                Toast.makeText(this, "Button pressed was Register", Toast.LENGTH_LONG).show();
            }
            if (resultCode == Constants.RESULT_CANCEL) {
                Toast.makeText(this, "Button pressed was Cancel", Toast.LENGTH_LONG).show();
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA);
            Log.d(Constants.BROADCAST_TAG, "Broadcast received: " + message);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Constants.NUMBER_OF_CLICKS, numberOfClicks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.NUMBER_OF_CLICKS)) {
            Integer s = savedInstanceState.getInt(Constants.NUMBER_OF_CLICKS);
            Toast.makeText(this, "Number of button clicks is " + s.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_13Service.class);
        serviceStatus = Constants.SERVICE_STOPPED;
        stopService(intent);
        super.onDestroy();
    }
}
