package com.grabuszynski.android.niceapp;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    private SensorManager sManager;
    private Accelerometer accData;
    protected boolean state = true;

    private static int sleepTime = 25;
    private static double mistake = 0.03;

    private double accXS, accYS, accZS, G;
    private double accX=0, accY=0, accZ=0, lAccX = 0, lAccY = 0, lAccZ = 0;
    private double angle;
    private double vX=0, vY=0, vZ=0, lVX = 0, lVY = 0, lVZ = 0, vD;
    private double Y, YS; //TO DELETE
    private int thrust1 = 170, thrust2 = 170, thrust3 = 170, thrust4 = 170;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accData = new Accelerometer(sManager);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void changeText(View view) {
        //Handler handler = new Handler();
        state = true;

        lAccX = accData.getX();
        lAccY = accData.getY();
        lAccZ = accData.getZ();
        accXS = accData.getX();
        accYS = accData.getY();
        G = accData.getZ();
        accZS = (float)(G - 9.8145);

        //angle = countAngle(accYS, accXS, accZS);
        //YS = Math.cos(angle)*(-(G*Math.sin(angle)));
        YS = accYS;

        Thread th = new Thread( new Runnable() {
            @Override
            public void run() {
                while(state){

                    accX = accData.getX();
                    accY = accData.getY();
                    accZ = accData.getZ();

                    angle = countAngle(accY - accYS, accX - accXS, accZ - accZS);

                    Y = Math.cos(angle)*(accY-(G*Math.sin(angle)));

                    if(Y > YS + mistake || Y < -YS-mistake ){
                        vY += ((lAccY+Y)-YS)/(2000/sleepTime);
                    }

                    lAccY = Y;

                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*SeekBar p1 = (SeekBar)findViewById(R.id.engine1);
                            p1.setMax(255);
                            p1.setProgress(thrust1);

                            SeekBar p2 = (SeekBar)findViewById(R.id.engine2);
                            p2.setMax(255);
                            p2.setProgress(thrust2);

                            SeekBar p3 = (SeekBar)findViewById(R.id.engine3);
                            p3.setMax(255);
                            p3.setProgress(thrust3);

                            SeekBar p4 = (SeekBar)findViewById(R.id.engine4);
                            p4.setMax(255);
                            p4.setProgress(thrust4);//*/

                            TextView tVX = (TextView)findViewById(R.id.xLabel);
                            tVX.setText(Float.toString((float)accX));
                            TextView tVY = (TextView)findViewById(R.id.yLabel);
                            tVY.setText(Float.toString((float)accY));
                            TextView tVZ = (TextView)findViewById(R.id.zLabel);
                            tVZ.setText(Float.toString((float)accZ));

                            TextView tVVX = (TextView)findViewById(R.id.xSpeed);
                            tVVX.setText("a="+Float.toString((float)angle));

                            TextView tVVY = (TextView)findViewById(R.id.ySpeed);
                            tVVY.setText("speed="+Float.toString((float)vY));

                            TextView tVVZ = (TextView)findViewById(R.id.zSpeed);
                            tVVZ.setText("Y="+Float.toString((float)Y));

                            TextView tVSS = (TextView)findViewById(R.id.zRoad);
                            tVSS.setText("YS="+Float.toString((float)YS));
                        }
                    });
                }
            }
        });
        th.start();

        //readAcc = new ReadAcc(tVX, tVY, tVZ, sManager);
        //readAcc.setState(true);

        //readAcc.start();
    }

    public void stopThread(View view) {
        this.state = false;
        accY = 0;
        accX = 0;
        accZ = 0;
        vX = 0;
        vY = 0;
        vZ = 0;
    }

    private double countAngle(double X, double Y, double Z){
        return Math.atan(X / (Math.sqrt((Y * Y) + (Z * Z))));//*(180/Math.PI);
    }
//
//    public void updateLabels(float x, float y, float z) {
//
//        textViewX.setText(Float.toString(x));
//        textViewY.setText(Float.toString(y));
//        textViewZ.setText(Float.toString(z));
//    }
}
