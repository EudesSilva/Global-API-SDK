package eudes.api.sdk.dropbox.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import eudes.api.sdk.dropbox.config.Const;
import eudes.api.sdk.dropbox.listener.IAccountListener;
import eudes.api.sdk.dropbox.ui.UIProgressBar;


/**
 * Created Eudes on 09/08/2016.
 */
public class AccountTask extends AsyncTask<Void, Void, FullAccount> {

    private Context context;
    private final DbxClientV2 dbxClientV2;
    private final IAccountListener accountListener;
    private ProgressDialog dialog;
    private Exception exception;

    public AccountTask(Context context, DbxClientV2 dbxClient, IAccountListener accountListener) {
        dbxClientV2 = dbxClient;
        this.context = context;
        this.accountListener = accountListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = UIProgressBar.show( context, Const.CONST_MSG_INFO_3 );
    }

    @Override
    protected FullAccount doInBackground(Void... params) {
        try {
            return dbxClientV2.users().getCurrentAccount();
        } catch (DbxException e) {
            dialog.dismiss();
            e.printStackTrace();
            exception = e;
        }
        return null;
    }



    @Override
    protected void onPostExecute(FullAccount account) {
        super.onPostExecute(account);
        if( dialog.isShowing() ){
            dialog.dismiss();
        }

        if (exception != null) {
            this.accountListener.onLoginError(exception);
        } else {
            this.accountListener.onLoginSuccess(account);
        }
    }




}
