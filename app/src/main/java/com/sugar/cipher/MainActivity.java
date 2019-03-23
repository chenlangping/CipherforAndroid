package com.sugar.cipher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button caesar;
    private Button autocipher;
    private Button keyword;
    private Button vigenere;
    private Button playfair;
    private Button permutation;
    private Button column;
    private Button CA;
    private Button DES;
    private Button DH;
    private Button Connection;
    private Button back;
    private Button filebtn;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent caesarIntent = new Intent(this, Caesar.class);//Intent 到Caesar
        final Intent autocipherIntent = new Intent(this, Autocipher.class);//Intent autocipher
        final Intent keywordIntent = new Intent(this, Keyword.class);//Intent keyword
        final Intent vigenereIntent = new Intent(this, Vigenere.class);//Intent vigenere
        final Intent playfairIntent = new Intent(this, Playfair.class);//Intent playfair
        final Intent permutationIntent = new Intent(this, Permutation.class);//Intent permutation
        final Intent columnIntent = new Intent(this, Column.class);//Intent column
        final Intent CAIntent = new Intent(this, CA.class);//Intent CA
        final Intent DESIntent = new Intent(this, DES.class);//Intent DES
        final Intent file = new Intent(this, FileCA.class);//Intent DH
        final Intent ConnectionIntent = new Intent(this, SocketConnect.class);//Intent Connection
        caesar = (Button) findViewById(R.id.caesar);
        caesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(caesarIntent);
            }
        });

        autocipher = (Button) findViewById(R.id.autokey);
        autocipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(autocipherIntent);
            }
        });

        keyword = (Button) findViewById(R.id.keyword);
        keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(keywordIntent);
            }
        });

        vigenere = (Button) findViewById(R.id.vigenere);
        vigenere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(vigenereIntent);
            }
        });

        playfair = (Button) findViewById(R.id.playfair);
        playfair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playfairIntent);
            }
        });

        permutation = (Button) findViewById(R.id.permutation);
        permutation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(permutationIntent);
            }
        });

        column = (Button) findViewById(R.id.columnpermutation);
        column.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(columnIntent);
            }
        });

        CA = (Button) findViewById(R.id.ca);
        CA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CAIntent);
            }
        });

        DES = (Button) findViewById(R.id.des);
        DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DESIntent);
            }
        });


        Connection = (Button) findViewById(R.id.two);
        Connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ConnectionIntent);
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitApp();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
