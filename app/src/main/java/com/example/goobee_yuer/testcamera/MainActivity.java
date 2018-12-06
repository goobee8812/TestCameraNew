package com.example.goobee_yuer.testcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(MainActivity.this,MonitorService.class);
//        startService(intent);
        findViewById(R.id.show_record_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecorder();
            }
        });
        findViewById(R.id.hide_record_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideRecorder();
            }
        });
    }

    private void hideRecorder() {
        Intent intent = new Intent(MonitorService.ACTION_BACK_HOME);
        sendBroadcast(intent);
    }

    private void showRecorder() {
        Intent intent = new Intent(MonitorService.ACTION_SHOW_RECORDER);
        sendBroadcast(intent);
    }

}
