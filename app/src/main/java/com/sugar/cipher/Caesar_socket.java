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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Caesar_socket extends AppCompatActivity {
    private static final String[] choose = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private Button subbtn;
    private Button backbtn;
    private Button decipherbtn;
    private TextView recvText;
    private EditText plainEdit;//明文输入
    private EditText keyEdit;//秘钥输入
    private String plaintext;//明文字符串
    private String[] ciphertext;//密文字符串
    private String flag;
    private String keytext;
    private Messenger rMessenger;
    private Messenger mMessenger;
    private Intent backIntent;
    private Intent bindIntent = null;
    private Encrypt encrypt;
    private Decrypt decrypt;
    private Spinner spinner;
    private ArrayAdapter adapter;
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
        setContentView(R.layout.activity_caesar_socket);
        recvText = (TextView) findViewById(R.id.recvText);
        plainEdit = (EditText) findViewById(R.id.plain_socketText);
        keytext = "a";
        subbtn = (Button) findViewById(R.id.smbbtn);
        backbtn = (Button) findViewById(R.id.backbtn);
        decipherbtn = (Button) findViewById(R.id.debtn);
        spinner = (Spinner) findViewById(R.id.spinner2);
        backIntent = new Intent(this, CipherActivity.class);
        ciphertext = new String[20];
        decrypt = new Decrypt();
        encrypt = new Encrypt();
        recvText.setMovementMethod(ScrollingMovementMethod.getInstance());
        //将可选内容ArrayAdapter连接起来
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choose);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                keytext = choose[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plainEdit.getText().toString().equals("")) {
                    Toast.makeText(Caesar_socket.this, "请输入明文！", Toast.LENGTH_SHORT).show();
                } else {
                    if (bindIntent == null) {
                        //如果还没有绑定，则：
                        /*********   绑定socket服务   ***********/
                        bindIntent = new Intent(Caesar_socket.this, MySocketService.class);
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
                    Toast.makeText(Caesar_socket.this, "无消息，请等待...", Toast.LENGTH_SHORT).show();
                } else {
                    plaintext = decrypt.Caesar(ciphertext[n], keytext);
                    n++;
                    flag = String.valueOf(n);
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
        msg.obj = encrypt.Caesar(plainEdit.getText().toString(), keytext);
        rMessenger.send(msg);
    }
}


