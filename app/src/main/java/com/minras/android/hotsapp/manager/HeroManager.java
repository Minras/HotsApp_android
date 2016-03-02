package com.minras.android.hotsapp.manager;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import com.minras.android.hotsapp.R;

public class HeroManager {
    public static final String PATH_LOCAL_HERO_JSON = "json/heroes.json";

    private HashMap<String, JSONObject> heroes = new HashMap<>();
    private ArrayList<String> heroesNames = new ArrayList<>();

    // singleton code
    protected HeroManager() {
        initNormalizedNameMap();
    }
    private static HeroManager instance = null;
    public static HeroManager getInstance() {
        if (instance == null) {
            instance = new HeroManager();
        }
        return instance;
    }

    // code for working with normalized names
    private static HashMap<String, String> normalizedNamesMap;

    public static HashMap<String, String> getNormalizedNamesMap() {
        return normalizedNamesMap;
    }

    private void initNormalizedNameMap() {
        normalizedNamesMap = new HashMap<>();
        normalizedNamesMap.put("Anub'arak", "Anubarak");
        normalizedNamesMap.put("Butcher", "The_Butcher");
        normalizedNamesMap.put("E.T.C.", "Elite_Tauren_Chieftain");
        normalizedNamesMap.put("Kael'thas", "Kaelthas");
        normalizedNamesMap.put("Li Li", "Li_Li");
        normalizedNamesMap.put("Li-Ming", "Li_Ming");
        normalizedNamesMap.put("The Lost Vikings", "The_Lost_Vikings");
        normalizedNamesMap.put("Lt. Morales", "Morales");
        normalizedNamesMap.put("Rehgar", "Reghar");
        normalizedNamesMap.put("Rexxar", "Rexar");
        normalizedNamesMap.put("Sgt. Hammer", "Sergeant_Hammer");
    }

    public String normalizeName(String name) {
        if (getNormalizedNamesMap().containsKey(name)) {
            name = getNormalizedNamesMap().get(name);
        }
        return name.toLowerCase();
    }

    /**
     * Initialize hero data using loaded Json string
     * @param hJson Json string representing all heroes data
     */
    public void initialize(String hJson) {
        try {
            JSONArray rawHeroes = new JSONArray(hJson);
            int j = 0;
            for (int i = 0; i < rawHeroes.length(); i++) {
                JSONObject hero = rawHeroes.getJSONObject(i);
                String name = normalizeName(hero.getString("name"));
                heroes.put(name, hero);
                j++;
            }
            heroesNames.addAll(heroes.keySet());
            Collections.sort(heroesNames);
        } catch (JSONException e) {
            MessageManager.getInstance()
                    .sendMessage(MessageManager.STATUS_ERROR, e.getMessage());
            e.printStackTrace();
        }
    }

    public HashMap<String, JSONObject> getHeroes() {
        return heroes;
    }
    public Integer getHeroesPortrait(int idx, Context context) {
        String resName = String.format("hero_portrait_%s", heroesNames.get(idx));
        // return R.drawable.hero_portrait_chen;

        Resources resources = context.getResources();
        return resources.getIdentifier(resName, "drawable", context.getPackageName());
    }

    public JSONObject getHero(String name) {
        return heroes.get(name);
    }

}
