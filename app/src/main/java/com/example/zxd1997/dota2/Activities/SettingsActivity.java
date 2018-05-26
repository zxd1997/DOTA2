
package com.example.zxd1997.dota2.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Update;

public class SettingsActivity extends AppCompatActivity {
    final static int FINISHED = 8;
    ProgressDialog pd;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FINISHED: {
                    Update.readFromJson();
                    pd.dismiss();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String[] data = {"Update"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_expandable_list_item_1, data);
        ListView listView = findViewById(R.id.settings);
        listView.addHeaderView(new ViewStub(this));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: {
                        Toast.makeText(SettingsActivity.this, "Update", Toast.LENGTH_LONG).show();
                        Update.updatezip(handler);
                        pd = new ProgressDialog(SettingsActivity.this);
                        pd.setTitle("Update");
                        pd.setMessage("Updating");
                        pd.show();
                        break;
                    }
                }
            }
        });
    }
}
