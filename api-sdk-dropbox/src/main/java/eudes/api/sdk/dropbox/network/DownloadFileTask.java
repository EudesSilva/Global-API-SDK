package eudes.api.sdk.dropbox.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import eudes.api.sdk.dropbox.config.Const;
import eudes.api.sdk.dropbox.listener.IDownloadListener;
import eudes.api.sdk.dropbox.ui.UIProgressBar;
import eudes.api.sdk.dropbox.util.UtilFileManager;


/**
 * Created Eudes on 09/08/2016.
 */
public class DownloadFileTask extends AsyncTask<Void, Void, File> {

    private final Context context;
    private final DbxClientV2 dbxClientV2;
    private final IDownloadListener downloadListener;
    private ProgressDialog dialog;
    private Exception exception;


    public DownloadFileTask(Context context, DbxClientV2 dbxClient, IDownloadListener downloadListener) {
        this.context = context;
        dbxClientV2 = dbxClient;
        this.downloadListener = downloadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = UIProgressBar.show(context,  Const.CONST_MSG_INFO_1 );
    }

    @Override
    protected File doInBackground(Void... params) {
        try {
            String pathBackup = UtilFileManager.getPathDatabasesBackup(context);
            File file = new File( pathBackup );
            OutputStream outputStream = new FileOutputStream(file);
            dbxClientV2.files().download( "/" + Const.CONST_NAME_DATABASE )
                               .download(outputStream);
            return file;
        } catch (DbxException | IOException e) {
            dialog.dismiss();
            exception = e;
        }
        return null;
    }


    @Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);
        if( dialog.isShowing() ){
            dialog.dismiss();
        }
        if (exception != null) {
            this.downloadListener.onError(exception);
        } else {
            this.downloadListener.onDownloadComplete(result);
        }
    }


}
