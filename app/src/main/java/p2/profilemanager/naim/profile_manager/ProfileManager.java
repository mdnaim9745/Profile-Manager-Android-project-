package p2.profilemanager.naim.profile_manager;


import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class ProfileManager{

    AudioManager audioManager;
    public static boolean home=false, namaz =false, office=false ;
    Context context;
    public  ProfileManager(Context context)
    {
        this.context = context;
        audioManager =(AudioManager)context.getSystemService((Context.AUDIO_SERVICE));
    }
    public void home()
    {
        if(home==false)
        {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            int getstreamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            audioManager.setStreamVolume(AudioManager.STREAM_RING,getstreamMaxVolume,AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
            Settings.System.putInt(context.getContentResolver(),"vibrate_when_ringing",0);
            home=true;
            namaz=false;
            office= false;
            Toast.makeText(context,"HOME MODE ON",Toast.LENGTH_LONG).show();
            Log.d("Home Mode On","");
        }
    }

    public void namaz()
    {
        if(namaz==false)
        {
            if(audioManager.getRingerMode()!= AudioManager.RINGER_MODE_VIBRATE)
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
            home=false;
            namaz=true;
            office=false;
            Toast.makeText(context,"NAMAZ MODE ON",Toast.LENGTH_LONG).show();
            Log.d("Namaz Mode On","");
        }
    }
    public void office()
    {
        if(office==false)
        {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            int getStreamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            getStreamMaxVolume=getStreamMaxVolume/2;
            audioManager.setStreamVolume(AudioManager.STREAM_RING, getStreamMaxVolume, AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
            Settings.System.putInt(context.getContentResolver() , "vibrate_when_ringing",1);
            home=false;
            namaz=false;
            office=true;
            Toast.makeText(context,"OFFICE MODE ON",Toast.LENGTH_LONG).show();
            Log.d("Office Mode On","");
        }
    }



}
