package com.grabuszynski.android.niceapp;

import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.lang.Thread;
import java.lang.InterruptedException;

/**
 * Created by Piotr on 2015-01-27.
 */
public class ReadAcc implements Runnable{

    private boolean state = false;
    private SensorManager sManager;
    Accelerometer accData;
    TextView tVX, tVY, tVZ;

    public ReadAcc(TextView textViewX, TextView textViewY, TextView textViewZ, SensorManager sManager) {
        super();
        this.sManager = sManager;
        tVX = textViewX;
        tVY = textViewY;
        tVZ = textViewZ;
        this.accData = new Accelerometer(sManager);
    }

    public void run(){
        while(state == true){

            tVX.setText(Float.toString(accData.getX()));
            //activity.updateLabels(accData.getX(), accData.getY(), accData.getZ());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
