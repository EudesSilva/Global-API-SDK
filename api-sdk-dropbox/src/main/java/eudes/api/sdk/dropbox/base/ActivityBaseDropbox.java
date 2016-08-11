package eudes.api.sdk.dropbox.base;

import android.support.v7.app.AppCompatActivity;

import com.dropbox.core.android.Auth;

import eudes.api.sdk.dropbox.config.Const;
import eudes.api.sdk.dropbox.config.PreferencesSingleton;
import eudes.api.sdk.dropbox.factory.FactoryClientDropBox;


/**
 * Created Eudes on 09/08/2016.
 */
public abstract class ActivityBaseDropbox extends AppCompatActivity{


    @Override
    protected void onResume() {
        super.onResume();
        retrieveAccessToken();
    }

   private void retrieveAccessToken(){
       String accessToken = PreferencesSingleton.getInstance()
                                                .readString(Const.CONST_ACCESS_TOKEN);
       if (accessToken == null || accessToken.isEmpty() ) {
           accessToken = Auth.getOAuth2Token();
           if (accessToken != null) {
               PreferencesSingleton.getInstance()
                                   .writeString(Const.CONST_ACCESS_TOKEN, accessToken);
               initializeClientDropBox(accessToken);
           }

       } else {
           initializeClientDropBox(accessToken);
       }
   }

    private void initializeClientDropBox(String accessToken) {
        FactoryClientDropBox.configureClient(accessToken);
        showResultLogin();
    }



    protected void startOAuth2Authentication( String appKEY ) {
        if (!existsToken()) {
            Auth.startOAuth2Authentication(getApplicationContext(), appKEY);
        }
    }



    protected abstract void showResultLogin();



    private boolean existsToken() {
        String accessToken = PreferencesSingleton
                             .getInstance().readString(Const.CONST_ACCESS_TOKEN);
        return accessToken != null && !accessToken.isEmpty();
    }



}
