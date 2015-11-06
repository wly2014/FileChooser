package com.wly.filechooser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        String filename = data.getStringExtra("filename");
        Log.i(":::",filename);
        Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();

    }

    public void start(View view) {
        Intent intent=new Intent(MainActivity.this,FileChooser.class);
        startActivityForResult(intent,1);
    }
}
