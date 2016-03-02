package com.minras.android.hotsapp.heroes;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.minras.android.hotsapp.HotsAppApplication;
import com.minras.android.hotsapp.R;
import com.minras.android.hotsapp.manager.HeroManager;
import com.minras.android.hotsapp.manager.MessageManager;

import org.json.JSONException;

public class HeroesActivity extends AppCompatActivity {
    protected HotsAppApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (HotsAppApplication)getApplicationContext();

        setContentView(R.layout.activity_heroes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridview = (GridView) findViewById(R.id.heroesGridView);
        gridview.setColumnWidth(92 + 2 * gridview.getHorizontalSpacing());
        gridview.setAdapter(new HeroListAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String name;
                try {
                    name = HeroManager.getInstance().getHero(position).getString("name");
                } catch (JSONException e) {
                    // e.printStackTrace();
                    name = "Error defining the hero name!";
                }
                MessageManager.getInstance().sendMessage(MessageManager.STATUS_WARNING, name);
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
}
