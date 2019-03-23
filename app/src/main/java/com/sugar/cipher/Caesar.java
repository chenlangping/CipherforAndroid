package com.sugar.cipher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Caesar extends AppCompatActivity {
    private static final String[] choose = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private Button okbtn;
    private Button backbtn;
    private Spinner spinner;
    private ArrayAdapter adapter;
    private RadioButton encipherbtn;
    private RadioButton decipherbtn;
    private TextView labelText;
    private EditText plainEdit;//明文输入
    private EditText cipherEdit;//密文输入
    private String plaintext;//明文字符串
    private String ciphertext;//密文字符串
    private String keytext;//秘钥字符串
    private TextView plainText;
    private TextView cipherText;
    private TextView keyText;
    private Encrypt encrypt;
    private Decrypt decrypt;
    private Intent backIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar);
        backIntent = new Intent(this, MainActivity.class);
        final Intent mainIntent = new Intent(this, MainActivity.class);//Intent 到MainActivity
        SpannableString spannableString = new SpannableString("Caesar");
        plainText = (TextView) findViewById(R.id.plaintextView);
        cipherText = (TextView) findViewById(R.id.ciphertextView);
        keyText = (TextView) findViewById(R.id.keytextView);
        labelText = (TextView) findViewById(R.id.labelView);
        okbtn = (Button) findViewById(R.id.okbutton);
        backbtn = (Button) findViewById(R.id.backbutton);
        spinner = (Spinner) findViewById(R.id.spinner);
        encipherbtn = (RadioButton) findViewById(R.id.encipherradioButton);
        decipherbtn = (RadioButton) findViewById(R.id.decipherradioButton);
        plainEdit = (EditText) findViewById(R.id.editPlaintext);
        cipherEdit = (EditText) findViewById(R.id.editCiphertext);
        plainEdit.setEnabled(false);//一开始，不能输入，当选择加密或解密之后才能在相应的里面输入。
        cipherEdit.setEnabled(false);
        encrypt = new Encrypt();
        decrypt = new Decrypt();
        //设置字体
        spannableString.setSpan(new AbsoluteSizeSpan(50), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        labelText.setText(spannableString);

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

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

        encipherbtn.setOnClickListener(new View.OnClickListener() {//选择加密
            @Override
            public void onClick(View v) {
                plainEdit.setEnabled(true);//选择加密后,明文和秘钥可以输入
                cipherEdit.setEnabled(false);
                //       keyEdit.setEnabled(true);
                plainEdit.setText("");
                cipherEdit.setText("");
                //       keyEdit.setText("");
            }
        });
        decipherbtn.setOnClickListener(new View.OnClickListener() {//选择解密
            @Override
            public void onClick(View v) {
                plainEdit.setEnabled(false);//选择解密后,明文和秘钥可以输入
                cipherEdit.setEnabled(true);
                //      keyEdit.setEnabled(true);
                plainEdit.setText("");
                cipherEdit.setText("");
                //       keyEdit.setText("");
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plaintext = plainEdit.getText().toString();
                ciphertext = cipherEdit.getText().toString();
                if (encipherbtn.isChecked()) {//判断用户是否正确输入
                    if (plaintext.equals("")) {
                        Toast.makeText(Caesar.this, "请输入明文!", Toast.LENGTH_SHORT).show();
                    } else if (keytext.equals("")) {
                        Toast.makeText(Caesar.this, "请输入秘钥!", Toast.LENGTH_SHORT).show();
                    } else {//输入正确，开始加密：
                        ciphertext = encrypt.Caesar(plaintext, keytext);
                        cipherEdit.setText(ciphertext);
                    }
                } else if (decipherbtn.isChecked()) {
                    if (ciphertext.equals("")) {
                        Toast.makeText(Caesar.this, "请输入密文!", Toast.LENGTH_SHORT).show();
                    } else if (keytext.equals("")) {
                        Toast.makeText(Caesar.this, "请输入秘钥!", Toast.LENGTH_SHORT).show();
                    } else {//输入正确，开始解密:
                        plaintext = decrypt.Caesar(ciphertext, keytext);
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
