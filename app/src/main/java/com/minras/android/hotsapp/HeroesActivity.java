package com.minras.android.hotsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class HeroesActivity extends AppCompatActivity {
    protected HotsAppApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (HotsAppApplication)getApplicationContext();

        setContentView(R.layout.activity_heroes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnTestMsg = (Button)findViewById(R.id.viewBtnTestMsg);
        btnTestMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MessageManager.getInstance().sendMessage(MessageManager.STATUS_WARNING, "xxx");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        application.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        application.unsetCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        application.unsetCurrentActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_heroes, menu);
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

    public String loadHeroesData() {
        String json;
        try {
            InputStream is = getAssets().open("json/heroes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
