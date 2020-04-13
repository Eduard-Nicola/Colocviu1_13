package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.pub.cs.systems.eim.Colocviu1_13.general.Constants;

public class Colocviu1_13SecondaryActivity extends AppCompatActivity {

    private TextView textViewComm;
    private Button buttonRegister;
    private Button buttonCancel;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonRegister:
                    setResult(Constants.RESULT_REGISTER, null);
                    break;
                case R.id.buttonCancel:
                    setResult(Constants.RESULT_CANCEL, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_secondary);

        textViewComm = findViewById(R.id.textViewComm);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.COMMANDS)) {
            String commands = intent.getStringExtra(Constants.COMMANDS);
            textViewComm.setText(commands);
        }

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonRegister.setOnClickListener(new ButtonClickListener());
        buttonCancel.setOnClickListener(new ButtonClickListener());
    }
}
