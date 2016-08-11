package eudes.api.sdk.dropbox.listener;

import com.dropbox.core.v2.users.FullAccount;

/**
 * Created Eudes on 09/08/2016.
 */
public interface IAccountListener {

    void onLoginSuccess(FullAccount fullAccount);
    void onLoginError(Exception e);

}
