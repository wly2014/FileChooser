package com.wly.filechooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileChooser extends Activity {

    private List<Map<String, Object>> fileList;
    private FileAdapter adapter;
    private ListView listView;
    private Toolbar toolbar;

    /**
     * currentDirection:当前文件夹
     */
    private String currentDirection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);

        currentDirection = Environment.getExternalStorageDirectory().getAbsolutePath();
        listView = (ListView) super.findViewById(R.id.file_list);

        init();
    }

    public void init() {
        Log.i("currentDirection::", currentDirection);

        fileList = getFileList(currentDirection);
        toolbar = (Toolbar) findViewById(R.id.toolbar_file_choose);
        toolbar.setTitle(currentDirection);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileBack();
            }
        });

        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser.this.finish();
            }
        });
        listView.setOnItemClickListener(new OnItemClickListenerImpl());
        adapter = new FileAdapter(this, fileList, currentDirection);
        listView.setAdapter(adapter);

    }

    private void fileBack() {
        File file = new File(currentDirection);

        if (file.getParent().equals("/")) {
            Toast.makeText(FileChooser.this, "已到顶目录了", Toast.LENGTH_SHORT).show();
        } else {
            currentDirection = file.getParent();
            updateItems();
        }
    }

    private List<Map<String, Object>> getFileList(String currentDirection) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        File file = new File(currentDirection);

        File[] files = file.listFiles();
        int length;
        if (files == null) {
            length = 0;
        } else {
            length = files.length;
        }

        for (int i = 0; i < length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("filename", files[i].getName());
            list.add(map);
        }

        return list;
    }

    public void updateItems() {
        adapter.notifyDataSetChanged();
        init();
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            /**
             * 特别注意此处，路径要为绝对的路径，而fileList.get(position).get("filename").toString()
             * 的到的仅是被点击的目录名
             */
            String childFile = currentDirection + "/" + fileList.get(position).get("filename").toString();
            /**
             * 如果file是目录：点击进入子目录；
             * 否则：判断是不.xls或.xlsx格式的excel文件。
             */
            if (new File(childFile).isDirectory()) {
                currentDirection = childFile;
                updateItems();
            } else {
//                if (childFile.endsWith(".xls") || childFile.endsWith(".xlsx")) {

                    Intent intentForResult = new Intent();
                    intentForResult.putExtra("filename", childFile);
                    FileChooser.this.setResult(1, intentForResult);
                    FileChooser.this.finish();

//                } else {
//                    Toast.makeText(FileChooser.this, "格式错误", Toast.LENGTH_SHORT).show();
//                }
            }
        }
    }
}
