package com.verifone.threadhandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create_thread_handler();

        mButton = (Button) findViewById(R.id.c_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(110);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "handler.post:" + Thread.currentThread().getName());
                    }
                });
            }
        });
    }

    private void create_thread_handler() {
        mHandlerThread = new HandlerThread("myHandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d("TAG", "sendmessage:" + Thread.currentThread().getName());
                Log.d("TAG", String.valueOf(msg.what));
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
