package com.sugar.cipher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CipherActivity extends AppCompatActivity {
    SpannableString spannableString = new SpannableString("Socket");
    private Button caesar;
    private Button autocipher;
    private Button keyword;
    private Button vigenere;
    private Button playfair;
    private Button permutation;
    private Button column;
    private Button CA;
    private Button DES;
    private Button back;
    private Button dh;
    private TextView labelText;
    private Intent serviceIntent;
    private Messenger rMessenger;
    private Messenger mMessenger;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj.toString().equals("fail")) ;
            Toast.makeText(CipherActivity.this, "连接失败，请重新连接!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);
        serviceIntent = new Intent(this, MySocketService.class);
        final Intent caesarIntent = new Intent(this, Caesar_socket.class);//Intent 到Caesar
        final Intent autocipherIntent = new Intent(this, Autocipher_socket.class);//Intent autocipher
        final Intent keywordIntent = new Intent(this, Keyword_socket.class);//Intent keyword
        final Intent vigenereIntent = new Intent(this, Vigenere_socket.class);//Intent vigenere
        final Intent playfairIntent = new Intent(this, Playfair_socket.class);//Intent playfair
        final Intent permutationIntent = new Intent(this, Permutation_socket.class);//Intent permutation
        final Intent columnIntent = new Intent(this, Column_socket.class);//Intent column
        final Intent CAIntent = new Intent(this, CA_socket.class);//Intent CA
        final Intent DESIntent = new Intent(this, DES_socket.class);//Intent DES
        final Intent DHIntent = new Intent(this, DH_socket.class);//Intent DH
        labelText = (TextView) findViewById(R.id.socketText);
        //设置字体
        spannableString.setSpan(new AbsoluteSizeSpan(50), 0, "Socket".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, "Socket".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        labelText.setText(spannableString);

        autocipher = (Button) findViewById(R.id.autokey1);
        autocipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(autocipherIntent);
            }
        });

        caesar = (Button) findViewById(R.id.caesar1);
        caesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(caesarIntent);
            }
        });

        keyword = (Button) findViewById(R.id.keyword1);
        keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(keywordIntent);
            }
        });

        vigenere = (Button) findViewById(R.id.vigenere1);
        vigenere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(vigenereIntent);
            }
        });

        playfair = (Button) findViewById(R.id.playfair1);
        playfair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playfairIntent);
            }
        });

        permutation = (Button) findViewById(R.id.permutation1);
        permutation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(permutationIntent);
            }
        });

        column = (Button) findViewById(R.id.columnpermutation1);
        column.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(columnIntent);
            }
        });

        CA = (Button) findViewById(R.id.ca1);
        CA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CAIntent);
            }
        });

        DES = (Button) findViewById(R.id.des1);
        DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DESIntent);
            }
        });

        dh = (Button) findViewById(R.id.DH);
        dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DHIntent);
            }
        });
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
                finish();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            stopService(serviceIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendMessage() throws RemoteException {
        Message msg = Message.obtain();
        msg.replyTo = mMessenger;

        rMessenger.send(msg);
    }

}
