package itec.code2smile;

/**
 * Created by Laci on 11.04.2014.
 */
import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

public class Singleton extends Application {

    private static Singleton mySingleton = null;
    public static SharedPreferences prefs;
    public static String prefName = "APP_PREFS";
    public static AppSettings mySettings;
    public static String m_szSaveFolder = "";
    public static ContextWrapper m_ContextWrp;
    public static String m_szWorkDir="";
    public static String TAG="MainActivity";
    public static String m_szPictDir="";

    public Singleton(){
        super();
        mySingleton = this;
        Log.w("Singleton","called");
    }

    public static Singleton getInstance(){
        if(mySingleton == null){
            mySingleton = new Singleton();
        }
        return mySingleton;
    }

    public void onCreate(){
        super.onCreate();
        Log.e("Singleton on create","called");
        prefs = getSharedPreferences(prefName,Context.MODE_PRIVATE);
        mySettings = new AppSettings();

        m_ContextWrp = new ContextWrapper(mySingleton);

        String state = Environment.getExternalStorageState();
        // get paths and other strings
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File f = Environment.getExternalStorageDirectory();
            m_szSaveFolder = f.getPath()+"/";
        } else {
            m_szSaveFolder = m_ContextWrp.getFilesDir().getAbsolutePath();
        }
        m_szWorkDir = m_szSaveFolder + "/trip_memories";

        File filesd = new File(m_szWorkDir);
        filesd.mkdirs();

        m_szPictDir = m_szWorkDir + "/pictures";
        File filesp = new File(m_szPictDir);
        filesp.mkdirs();

    }


}
