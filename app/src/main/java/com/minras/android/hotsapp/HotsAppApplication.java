package com.minras.android.hotsapp;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;


public class HotsAppApplication
        extends Application
        implements MessageManager.MessageListener {
    @Override
    public void onCreate() {
        super.onCreate();
        MessageManager.getInstance().subscribeMessageListener(this);
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
}
