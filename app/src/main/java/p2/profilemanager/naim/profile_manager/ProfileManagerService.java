package p2.profilemanager.naim.profile_manager;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ProfileManagerService extends Service {

    EventManager eventManager=null;

    @Override
    public  IBinder onBind(Intent intent)
    {
        return  null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        eventManager = new EventManager(this);
        eventManager.startEventManager();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventManager.stopEventManager();
        Toast.makeText(this,"Destroyed",Toast.LENGTH_LONG).show();
    }
}
