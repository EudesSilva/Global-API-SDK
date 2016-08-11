package eudes.api.sdk.dropbox.initialize;

import android.app.Application;
import android.content.Context;

/**
 * Created Eudes on 09/08/2016.
 */
public class AppInitialize extends Application {

    public static Context globalContext;
    public static Application appInitialize;


    @Override
    public void onCreate() {
        super.onCreate();
            globalContext = this;
            appInitialize = this;
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
