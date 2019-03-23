package com.sugar.cipher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CA extends AppCompatActivity {
    private Button okbtn;
    private Button backbtn;
    private RadioButton encipherbtn;
    private RadioButton decipherbtn;
    private TextView labelText;
    private EditText plainEdit;//明文输入
    private EditText cipherEdit;//密文输入
    private EditText keyEdit;//秘钥输入
    private String plaintext;//明文字符串
    private String ciphertext;//密文字符串
    private String keytext;//秘钥字符串
    private Encrypt encrypt;
    private Decrypt decrypt;
    private String cell;
    private Intent backIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        backIntent = new Intent(this, MainActivity.class);
        final Intent mainIntent = new Intent(this, MainActivity.class);//Intent 到MainActivity
        okbtn = (Button) findViewById(R.id.okbutton);
        backbtn = (Button) findViewById(R.id.backbutton);
        encipherbtn = (RadioButton) findViewById(R.id.encipherradioButton);
        decipherbtn = (RadioButton) findViewById(R.id.decipherradioButton);
        plainEdit = (EditText) findViewById(R.id.editPlaintext);
        cipherEdit = (EditText) findViewById(R.id.editCiphertext);
        labelText = (TextView) findViewById(R.id.labelView);
        keyEdit = (EditText) findViewById(R.id.editKeytext);
        cell = new String("01011011");
        encrypt = new Encrypt();
        decrypt = new Decrypt();
        plainEdit.setEnabled(false);//一开始，不能输入，当选择加密或解密之后才能在相应的里面输入。
        cipherEdit.setEnabled(false);
        keyEdit.setEnabled(false);
        SpannableString spannableString = new SpannableString("CA");
        //设置字体
        spannableString.setSpan(new AbsoluteSizeSpan(50), 0, "CA".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, "CA".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        labelText.setText(spannableString);
        encipherbtn.setOnClickListener(new View.OnClickListener() {//选择加密
            @Override
            public void onClick(View v) {
                plainEdit.setEnabled(true);//选择加密后,明文和秘钥可以输入
                cipherEdit.setEnabled(false);
                keyEdit.setEnabled(true);
                plainEdit.setText("");
                cipherEdit.setText("");
                keyEdit.setText("");
            }
        });
        decipherbtn.setOnClickListener(new View.OnClickListener() {//选择解密
            @Override
            public void onClick(View v) {
                plainEdit.setEnabled(false);//选择解密后,明文和秘钥可以输入
                cipherEdit.setEnabled(true);
                keyEdit.setEnabled(true);
                plainEdit.setText("");
                cipherEdit.setText("");
                keyEdit.setText("");
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plaintext = plainEdit.getText().toString();
                ciphertext = cipherEdit.getText().toString();
                keytext = keyEdit.getText().toString();

                if (encipherbtn.isChecked()) {//判断用户是否正确输入
                    if (plaintext.equals("")) {
                        Toast.makeText(CA.this, "请输入明文!", Toast.LENGTH_SHORT).show();
                    } else if (keytext.equals("")) {
                        Toast.makeText(CA.this, "请输入秘钥!", Toast.LENGTH_SHORT).show();
                    } else if (plaintext.length() < 8) {
                        Toast.makeText(CA.this, "明文长度不能小于8!", Toast.LENGTH_SHORT).show();
                    } else {//输入正确，开始加密：
                        ciphertext = encrypt.CA(plaintext, keytext, cell);
                        cipherEdit.setText(ciphertext);
                    }
                } else if (decipherbtn.isChecked()) {
                    if (ciphertext.equals("")) {
                        Toast.makeText(CA.this, "请输入密文!", Toast.LENGTH_SHORT).show();
                    } else if (keytext.equals("")) {
                        Toast.makeText(CA.this, "请输入秘钥!", Toast.LENGTH_SHORT).show();
                    } else if (ciphertext.length() < 8) {
                        Toast.makeText(CA.this, "密文长度不能小于8!", Toast.LENGTH_SHORT).show();
                    } else {//输入正确，开始解密:
                        plaintext = decrypt.CA(ciphertext, keytext, cell);
                        plainEdit.setText(plaintext);
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

    }

}
