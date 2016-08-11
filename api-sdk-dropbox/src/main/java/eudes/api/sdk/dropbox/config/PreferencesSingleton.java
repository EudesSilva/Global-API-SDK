package eudes.api.sdk.dropbox.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import eudes.api.sdk.dropbox.initialize.AppInitialize;

/**
 * Created Eudes on 09/08/2016.
 */
public class PreferencesSingleton {

 	private static final String MyPREFERENCES = "pref_configs";
    private static SharedPreferences mPreferences;
    private static PreferencesSingleton mInstance;
    private static Editor mEditor;


	
    public static PreferencesSingleton getInstance() {
        if (mInstance == null) {
            Context context = AppInitialize.globalContext;
            mInstance = new PreferencesSingleton();
            mPreferences = context.getSharedPreferences( MyPREFERENCES, Context.MODE_PRIVATE );
            mEditor = mPreferences.edit();
        }
        return mInstance;
    }
	

	
	public void writeString( String key, String value ){
		mEditor.putString( key, value ); 
		mEditor.commit();	 
	}
	
	public void writeBoolean( String key, boolean value ){
		mEditor.putBoolean(key, value);
		mEditor.commit();	  
	}
	

	
	public String readString(String key){
	    String valuePref =  mPreferences.getString( key, null);
		return valuePref;
	}

	public boolean readBoolean(String key){
	    boolean valuePref =  mPreferences.getBoolean(key, false);
		return valuePref;
	}		
	

}
