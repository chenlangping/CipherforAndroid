package com.sugar.cipher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SocketConnect extends AppCompatActivity {
    private TextView labelText;
    private EditText ipText;
    private EditText portText;
    private Button connectbtn;
    private Button backbtn;
    private Intent serviceIntent;
    private boolean isBound;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        SpannableString spannableString = new SpannableString("SocketConnect");
        labelText = (TextView) findViewById(R.id.labelView);
        ipText = (EditText) findViewById(R.id.ipText);
        portText = (EditText) findViewById(R.id.portText);
        connectbtn = (Button) findViewById(R.id.connectButton);
        backbtn = (Button) findViewById(R.id.backbtn);
        serviceIntent = new Intent(this, MySocketService.class);
        //设置字体
        spannableString.setSpan(new AbsoluteSizeSpan(50), 0, "SocketConnect".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, "SocketConnect".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        labelText.setText(spannableString);

        connectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ipText.getText().toString().equals("")) {
                    Toast.makeText(SocketConnect.this, "请输入ip!", Toast.LENGTH_SHORT).show();
                } else if (portText.getText().toString().equals("")) {
                    Toast.makeText(SocketConnect.this, "请输入port!", Toast.LENGTH_SHORT).show();
                } else {
                    serviceIntent.putExtra("iptext", ipText.getText().toString());
                    serviceIntent.putExtra("porttext", portText.getText().toString());
                    startService(serviceIntent);
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void unBind() {
        if (isBound) {
            unbindService(sc);
            isBound = false;
        }
    }

    public void onDestroy() {
        unBind();
        super.onDestroy();
    }
}
