package com.grabuszynski.android.niceapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Piotr on 2015-01-27.
 */
public class Accelerometer implements SensorEventListener{

    private float x, y, z;
    private Sensor s;

    public Accelerometer(SensorManager sManager){
        //Wybieramy Akcelerometr
        s = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
