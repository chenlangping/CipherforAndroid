package com.sugar.cipher;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class FileCA extends AppCompatActivity {

    static private int openfileDialogId = 0;
    private TextView plainFile;
    private TextView cipherFile;
    private RadioButton encipherbtn;
    private RadioButton decipherbtn;
    private Button okbtn;
    private Button choosePfile;
    private Button chooseCfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_c);
        encipherbtn = (RadioButton) findViewById(R.id.encipherradioButton);
        decipherbtn = (RadioButton) findViewById(R.id.decipherradioButton);
        okbtn = (Button) findViewById(R.id.okbutton);
        choosePfile = (Button) findViewById(R.id.choosePfile);
        chooseCfile = (Button) findViewById(R.id.chooseCfile);
        plainFile = (TextView) findViewById(R.id.editPlaintext);
        cipherFile = (TextView) findViewById(R.id.editCiphertext);
        choosePfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfileDialogId = 0;
                showDialog(openfileDialogId);
            }
        });

        chooseCfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfileDialogId = 1;
                showDialog(openfileDialogId);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == openfileDialogId) {
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标
            images.put("mp3", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put("jpg", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put("txt", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            plainFile.setText(filepath); // 把文件路径显示在标题上
                        }
                    },
                    ".mp3;",
                    images);
            return dialog;
        } else {
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标
            images.put("mp3", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put("jpg", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put("txt", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            cipherFile.setText(filepath); // 把文件路径显示在标题上
                        }
                    },
                    ".mp3;",
                    images);
            return dialog;
        }

    }
}