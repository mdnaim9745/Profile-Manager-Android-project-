package p2.profilemanager.naim.profile_manager;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class EventManager implements SensorEventListener {

    Context context;
    ProfileManager profileManager=null;
    SensorManager sensorManager;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Sensor proximity;
    private Sensor light;
    private Sensor accelerometer;
    private boolean hasObject=false,hasLight=false,shake=false,fold=false;
    private static float lastX=0,lastY=0,lastZ=0;

    public  EventManager(Context context)
    {
        this.context = context;
        profileManager = new ProfileManager(this.context);
        letsDefine();
    }
    public void letsDefine()
    {
        sensorManager =(SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void startEventManager()
    {

        sensorManager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,light,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor  sensor = event.sensor;

        if(sensor.getType()==Sensor.TYPE_PROXIMITY)
        {

            if (event.values[0]==0)
            {
                hasObject =true;

                Log.d("namz", " hasObject =true;");
            }
            else
            {
                hasObject=false;
            }
        }
        if(sensor.getType()==Sensor.TYPE_LIGHT)
        {

            if(event.values[0]==0)
            {
                hasLight=false;
            }
            else
            {
                hasLight=true;
                Log.d("namz", "hasLight=true;");
            }
        }
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            float x,y,z,initialX,initialY,initialZ;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            initialX = lastX;
            initialY = lastY;
            initialZ = lastZ;
            if((x>initialX+3 || x<initialX-3) || (y>initialY+3 || y<initialY-3) || (z>initialZ+3 || z<initialZ-3)){
                lastX=x;
                lastY=y;
                lastZ=z;

                shake=true;
            }
            else
            {

                shake=false;
            }
            if(z<-9) {
                fold = true;
                Log.d("namz", "fold = true;");
            }
            else {
                fold = false;

            }


            if(fold == false && hasObject == false && hasLight==true && shake==false)
            {

                profileManager.home();
            }
            else if(fold == true && hasObject == true  && shake==false)
            {

                profileManager.namaz();
            }
            else if(shake == true && hasObject == true && hasLight==true && fold==false)
            {
                profileManager.office();
            }

        }
    }

    public void stopEventManager()
    {
        sensorManager.unregisterListener(this);
    }
}

