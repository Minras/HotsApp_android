package com.minras.android.hotsapp;

import android.app.Activity;
import android.app.Application;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.minras.android.hotsapp.manager.HeroManager;
import com.minras.android.hotsapp.manager.MessageManager;

import java.io.IOException;
import java.io.InputStream;


public class HotsAppApplication
        extends Application
        implements MessageManager.MessageListener {
    @Override
    public void onCreate() {
        super.onCreate();
        MessageManager.getInstance().subscribeMessageListener(this);

        String hJson = loadHeroesJsonString();
        HeroManager.getInstance().initialize(hJson);
    }

    private Activity mCurrentActivity = null;

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    /**
     * Activity can try to unregister itself from being a current activity
     * @param mCurrentActivity Activity object that tries to unregister itself
     */
    public void unsetCurrentActivity(Activity mCurrentActivity){
        if (getCurrentActivity().equals(mCurrentActivity)) {
            setCurrentActivity(null);
        }
    }

    @Override
    public void onMessageReceived(String status, String msg) {
        int bgColor;
        switch (status) {
            case MessageManager.STATUS_ERROR:
                bgColor = R.color.bgError;
                break;
            case MessageManager.STATUS_SUCCESS:
                bgColor = R.color.bgSuccess;
                break;
            case MessageManager.STATUS_WARNING:
                bgColor = R.color.bgWarning;
                break;
            case MessageManager.STATUS_NEUTRAL:
            default:
                bgColor = R.color.colorPrimary;
                break;
        }
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getCurrentActivity().findViewById(
                R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup snackView = (ViewGroup) snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(snackView.getContext(), bgColor));
        snackbar.show();
    }

    public String loadHeroesJsonString() {
        String json = null;
        try {
            InputStream is = getAssets().open(HeroManager.PATH_LOCAL_HERO_JSON);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
