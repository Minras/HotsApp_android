package com.minras.android.hotsapp.heroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minras.android.hotsapp.R;
import com.minras.android.hotsapp.manager.HeroManager;

import org.json.JSONException;

public class HeroesListPortraitAdapter extends BaseAdapter {
    private Context mContext;

    public HeroesListPortraitAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return HeroManager.getInstance().getHeroes().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            gridItemView = inflater.inflate(R.layout.hero_list_portrait, null);

            TextView textView = (TextView) gridItemView.findViewById(R.id.grid_item_label);
            try {
                textView.setText(HeroManager.getInstance().getHero(position).getString("name"));
            } catch (JSONException e) {
                textView.setText("N/A");
            }

            ImageView imageView = (ImageView) gridItemView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(HeroManager.getInstance().getHeroesPortrait(position, mContext));
        } else {
            gridItemView = convertView;
        }

        return gridItemView;
    }
}