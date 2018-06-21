package com.example.zxd1997.dota2.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.debug.hv.ViewServer;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.Update;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private final static int FINISHED = 8;
    private ProgressDialog pd;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case FINISHED: {
                    Update.readFromJson();
                    pd.dismiss();
                    break;
                }
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Update.setDensity(this, getApplication());
        ViewServer.get(this).addWindow(this);
        MyApplication.add(this);
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String[] data = {"Update"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_expandable_list_item_1, data);
        ListView listView = findViewById(R.id.settings);
        listView.addHeaderView(new ViewStub(this));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: {
                        Toast.makeText(SettingsActivity.this, "Update", Toast.LENGTH_LONG).show();
                        Update.update_zip(handler);
                        pd = new ProgressDialog(SettingsActivity.this);
                        pd.setTitle("Update");
                        pd.setMessage("Updating");
                        pd.setCanceledOnTouchOutside(false);
                        pd.show();
                        break;
                    }
                }
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ViewServer.get(this).setFocusedWindow(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ViewServer.get(this).removeWindow(this);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
