package com.sugar.cipher;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MySocketService extends Service {
    private Socket socket = new Socket();
    private Intent cipherIntent;
    private String ip;
    private int port;
    private String recv = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Messenger cMessenger = null;
    private Message message = null;
    private android.os.Handler mHandler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("recieve-----------", (String) msg.obj);
            cMessenger = msg.replyTo;
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                Log.v("send to computer-----", (String) msg.obj);
                if (socket.isConnected())
                    if (!socket.isClosed())
                        out.println((String) msg.obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private Messenger mMessenger = new Messenger(mHandler);
    public MySocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        ip = intent.getStringExtra("iptext");
        port = Integer.parseInt(intent.getStringExtra("porttext").toString());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                 /*   if (socket!=null)
                        if (!socket.isClosed())
                            socket.close();*/
                    socket.connect(new InetSocketAddress(ip, port), 5000);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        if (!socket.isClosed()) {
                            if (socket.isConnected()) {
                                recv = in.readLine();
                                if (recv != null) {
                                    message = Message.obtain();//这里要注意，因为以后会多次使用message,所以不能只是单纯的new。否则以后可能会报错
                                    message.obj = recv;
                                    Log.v("准备向Activity发送数据------", recv);
                                    recv = null;
                                    cMessenger.send(message);
                                    Log.v("向Activity发送数据---------", (String) message.obj);
                                }
                            }
                        }
                        //     }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        cipherIntent = new Intent(this, CipherActivity.class);
        cipherIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(cipherIntent);

        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
