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
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Playfair_socket extends AppCompatActivity {
    char replace = 'x'; // 填充字符
    private Button subbtn;
    private Button backbtn;
    private Button decipherbtn;
    private TextView recvText;
    private EditText plainEdit;//明文输入
    private EditText keyEdit;//秘钥输入
    private String plaintext;//明文字符串
    private String[] ciphertext;//密文字符串
    private char[] plain;//因为playfair算法加解密返回的是char类型，这个当做中转
    private String flag;
    private Messenger rMessenger;
    private Messenger mMessenger;
    private Intent backIntent;
    private Intent bindIntent = null;
    private PlayfairAlgorithm playfairAlgorithm;
    private int i = 0;//字符串数组存入时的下标
    private int n = 0;//字符串解密时的下标
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ciphertext[i] = (String) msg.obj;
            i++;
            flag = String.valueOf(i);
            recvText.append("密文 " + flag + ": " + (String) msg.obj + "\n");
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
        setContentView(R.layout.activity_playfair_socket);
        recvText = (TextView) findViewById(R.id.recvText);
        plainEdit = (EditText) findViewById(R.id.plain_socketText);
        keyEdit = (EditText) findViewById(R.id.key_socketText);
        subbtn = (Button) findViewById(R.id.smbbtn);
        backbtn = (Button) findViewById(R.id.backbtn);
        decipherbtn = (Button) findViewById(R.id.debtn);
        backIntent = new Intent(this, CipherActivity.class);
        ciphertext = new String[20];
        playfairAlgorithm = new PlayfairAlgorithm();
        recvText.setMovementMethod(ScrollingMovementMethod.getInstance());
        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plainEdit.getText().toString().equals("")) {
                    Toast.makeText(Playfair_socket.this, "请输入明文！", Toast.LENGTH_SHORT).show();
                } else if (keyEdit.getText().toString().equals("")) {
                    Toast.makeText(Playfair_socket.this, "请输入秘钥", Toast.LENGTH_SHORT).show();
                } else {
                    if (bindIntent == null) {
                        //如果还没有绑定，则：
                        /*********   绑定socket服务   ***********/
                        bindIntent = new Intent(Playfair_socket.this, MySocketService.class);
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

        decipherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n == i) {
                    Toast.makeText(Playfair_socket.this, "无消息，请等待...", Toast.LENGTH_SHORT).show();
                } else {
                    char[] plaintext1 = playfairAlgorithm.decode(ciphertext[n].toCharArray(), keyEdit.getText().toString().replaceAll(" ", "").toCharArray());
                    n++;
                    flag = String.valueOf(n);
                    plaintext = new String(plaintext1);
                    recvText.append("明文 " + flag + ": " + plaintext + "\n");
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
        /***  在这里进行加密  ***/
        plain = playfairAlgorithm.encode(plainEdit.getText().toString().replaceAll(" ", "").toCharArray(), keyEdit.getText().toString().replaceAll(" ", "").toCharArray(), replace);
        msg.obj = new String(plain);
        rMessenger.send(msg);
    }
}


