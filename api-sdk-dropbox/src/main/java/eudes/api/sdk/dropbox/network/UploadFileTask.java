package eudes.api.sdk.dropbox.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import eudes.api.sdk.dropbox.config.Const;
import eudes.api.sdk.dropbox.listener.IUploadListener;
import eudes.api.sdk.dropbox.ui.UIProgressBar;

/**
 * Created Eudes on 09/08/2016.
 */
public class UploadFileTask extends AsyncTask<Void, Void, FileMetadata> {

    private final Context context;
    private final DbxClientV2 dbxClient;
    private final IUploadListener uploadListener;
    private ProgressDialog dialog;
    private Exception exception;
    private String pathFile;

    public UploadFileTask(Context context, DbxClientV2 dbxClient, String pathFile, IUploadListener uploadListener) {
        this.context = context;
        this.dbxClient = dbxClient;
        this.pathFile = pathFile;
        this.uploadListener = uploadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = UIProgressBar.show(context, Const.CONST_MSG_INFO_2 );
    }

    @Override
    protected FileMetadata doInBackground(Void... params) {
        try {
            File fileUpload = new File(pathFile);
            String nameInServer = fileUpload.getName();
            InputStream inputStream = new FileInputStream( fileUpload );
            return dbxClient.files().uploadBuilder("/" + nameInServer )
                                    .withMode(WriteMode.OVERWRITE)
                                    .uploadAndFinish(inputStream);
        } catch (DbxException | IOException e) {
            if( dialog.isShowing() ){
                dialog.dismiss();
            }
            e.printStackTrace();
            exception = e;
        }
        return null;
    }



    @Override
    protected void onPostExecute(FileMetadata fileMetadata) {
        super.onPostExecute(fileMetadata);
        if( dialog.isShowing() ){
            dialog.dismiss();
        }
        if (exception != null) {
            this.uploadListener.onUploadError(exception);
        } else if ( fileMetadata == null) {
            this.uploadListener.onUploadError(null);
        } else {
            this.uploadListener.onUploadComplete( fileMetadata );
        }
    }
}
