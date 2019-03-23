package com.sugar.cipher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DH_socket extends AppCompatActivity {
    private Button subbtn;
    private Button backbtn;
    private Button getDH;
    private Button getkey;
    private TextView recvText;
    private TextView labelText;
    private TextView plainText;
    private TextView cipherText;
    private TextView keyText;
    private TextView randomnum;
    private EditText plainEdit;//明文输入
    private EditText keyEdit;//秘钥输入
    private String plaintext;//明文字符串
    private String[] ciphertext;//密文字符串
    private String flag;
    private Messenger rMessenger;
    private Messenger mMessenger;
    private Intent bindIntent = null;
    private Random random;
    private int i = 0;//字符串数组存入时的下标
    private int n = 0;//字符串解密时的下标
    private String t;
    private String f;
    private SDH client;
    private String zhongjian;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            zhongjian = String.valueOf(msg.obj);
            Log.v("接受到数据-------------", zhongjian);
            recvText.setText(zhongjian);
        }
    };
    private ServiceConnection serConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            rMessenger = new Messenger(service);
            mMessenger = new Messenger(mHandler);
            try {
                sendMessage();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dh_socket);
        subbtn = (Button) findViewById(R.id.smbbtn);
        backbtn = (Button) findViewById(R.id.backbtn);
        getkey = (Button) findViewById(R.id.getbtn);
        getDH = (Button) findViewById(R.id.dhbtn);
        randomnum = (TextView) findViewById(R.id.dhtext);
        keyText = (TextView) findViewById(R.id.keytext);
        recvText = (TextView) findViewById(R.id.recvtext);
        ciphertext = new String[20];
        t = "23";
        f = "5";
        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomnum.getText().toString().equals("")) {
                    Toast.makeText(DH_socket.this, "请先生成随机数！", Toast.LENGTH_SHORT).show();
                } else {
                    if (bindIntent == null) {
                        //如果还没有绑定，则：
                        /*********   绑定socket服务   ***********/
                        bindIntent = new Intent(DH_socket.this, MySocketService.class);
                        bindService(bindIntent, serConn, Context.BIND_AUTO_CREATE);
                    }
                    //如果已经绑定了，则直接调用sendMessage发送消息
                    else {
                        try {
                            sendMessage();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = new Random();
                zhongjian = String.valueOf((random.nextInt(20) + 1));
                randomnum.setText(zhongjian);
            }
        });

        getkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomnum.getText().toString().equals("") || recvText.getText().toString().equals("")) {
                    Toast.makeText(DH_socket.this, "信息不完整!", Toast.LENGTH_SHORT).show();
                } else {
                    //在这里生成最后的秘钥
                    client.setK(recvText.getText().toString());
                    keyText.setText(client.K.toString());
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendMessage() throws RemoteException {
        Message msg = Message.obtain();
        msg.replyTo = mMessenger;

        client = new SDH(t, f, randomnum.getText().toString());
        msg.obj = client.A.toString();
        rMessenger.send(msg);
    }
}

