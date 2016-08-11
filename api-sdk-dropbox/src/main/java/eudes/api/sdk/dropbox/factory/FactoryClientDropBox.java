package eudes.api.sdk.dropbox.factory;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import eudes.api.sdk.dropbox.config.Const;


/**
 * Created Eudes on 09/08/2016.
 */
public class FactoryClientDropBox {


    private static DbxClientV2 factoryDbxClient;

    private FactoryClientDropBox() { }


    public static void configureClient(String accessToken) {
        if (factoryDbxClient == null) {
            DbxRequestConfig config = DbxRequestConfig.newBuilder(Const.CONST_APP_NAME).build();
            factoryDbxClient = new DbxClientV2(config, accessToken);
        }
    }



    public static DbxClientV2 getClient() {
        if (factoryDbxClient == null) {
            throw new IllegalStateException( Const.CONST_MSG_ERRO_1 );
        }
        return factoryDbxClient;
    }
}
