package itec.code2smile;

/**
 * Created by Laci on 11.04.2014.
 */
import android.content.SharedPreferences.Editor;


public class AppSettings {

    public static short KEY = 0;
    public static short KEY2 = 1;

    private String getString(short akey)    {
        return Singleton.prefs.getString("" + akey, "");
    }

    private String getString(String akey, String defaultValue)    {
        return Singleton.prefs.getString(akey, defaultValue);
    }
    private void setString(short akey, String avalue)    {
        Editor editor = Singleton.prefs.edit();
        editor.putString("" + akey, avalue);
        editor.commit();
    }
    private int getInt(short akey) {
        return Singleton.prefs.getInt("" + akey, 0);
    }
    private int getInt(short akey, int defValue)    {
        return Singleton.prefs.getInt("" + akey, defValue);
    }
    private void setInt(short akey, int avalue)    {
        Editor editor = Singleton.prefs.edit();
        editor.putInt("" + akey, avalue);
        editor.commit();
    }
    private boolean getBoolean(short akey) {
        return Singleton.prefs.getBoolean("" + akey, false);
    }
    private boolean getBoolean(short akey, boolean defValue)    {
        return Singleton.prefs.getBoolean("" + akey, defValue);
    }
    private void setBoolean(short akey, boolean avalue)    {
        Editor editor = Singleton.prefs.edit();
        editor.putBoolean("" + akey, avalue);
        editor.commit();
    }

    //custom methods (public)

    public void saveData(String str){
        this.setString(KEY, str);
    }

    public String getData(){
        return this.getString(KEY);
    }

    public void setState(boolean val){
        this.setBoolean(KEY2, val);
    }

    public boolean getState(){
        return this.getBoolean(KEY2);
    }

}

