package eudes.api.sdk.dropbox;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.text.DateFormat;

import eudes.api.sdk.dropbox.base.ActivityBaseDropbox;
import eudes.api.sdk.dropbox.config.Const;
import eudes.api.sdk.dropbox.factory.FactoryClientDropBox;
import eudes.api.sdk.dropbox.listener.IAccountListener;
import eudes.api.sdk.dropbox.listener.IDownloadListener;
import eudes.api.sdk.dropbox.listener.IUploadListener;
import eudes.api.sdk.dropbox.network.AccountTask;
import eudes.api.sdk.dropbox.network.DownloadFileTask;
import eudes.api.sdk.dropbox.network.UploadFileTask;
import eudes.api.sdk.dropbox.util.UtilFileManager;



/**
 * Created Eudes on 09/08/2016.
 */
public class ActivityDropBox extends ActivityBaseDropbox {

    private String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button SignInButton = (Button) findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startOAuth2Authentication( getString(R.string.APP_KEY ) );
            }
        });

        Button btUp = (Button) findViewById(R.id.btUp);
        btUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        Button btDow = (Button) findViewById(R.id.btDow);
        btDow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });

    }


    private void upload(){
        String pathDatabse = UtilFileManager.getPathDatabases(ActivityDropBox.this);
        new UploadFileTask(ActivityDropBox.this, FactoryClientDropBox.getClient(),
                                                 pathDatabse, new IUploadListener() {
            @Override
            public void onUploadComplete(FileMetadata result) {
                String message = result.getName() + " size " +
                                 result.getSize() + " modified " +
                                 DateFormat.getDateTimeInstance()
                                 .format( result.getClientModified() );

                Log.i( TAG, "Messagem upload file. "+ message);
            }

            @Override
            public void onUploadError(Exception e) {
                Log.e( TAG, "Failed to upload file.", e);
            }
        }).execute();
    }

    private void download(){
        new DownloadFileTask(ActivityDropBox.this, FactoryClientDropBox.getClient(), new IDownloadListener() {
            @Override
            public void onDownloadComplete(File result) {
                String message = " Name " + result.getName() + " Path " +
                        result.getPath() + " modified " +
                        DateFormat.getDateTimeInstance()
                                .format( result.lastModified()  );
                Log.i( TAG, "Messagem Download file. "+ message);
            }

            @Override
            public void onError(Exception e) {
                Log.e( TAG, "Failed to download file.", e);
            }
        }).execute();
    }

    @Override
    protected void showResultLogin() {
        new AccountTask(ActivityDropBox.this, FactoryClientDropBox.getClient(), new IAccountListener() {
            @Override
            public void onLoginSuccess(FullAccount fullAccount) {
                ((TextView) findViewById(R.id.email_text)).setText( fullAccount.getEmail() );
                ((TextView) findViewById(R.id.name_text)).setText( fullAccount.getName().getDisplayName() );
                ((TextView) findViewById(R.id.type_text)).setText( fullAccount.getAccountType().name() );

                Log.i( TAG,"::Login Sucesso!!! \n" +fullAccount.getEmail() +"\n"
                        + fullAccount.getName().getDisplayName() +"\n"
                        + fullAccount.getAccountType().name() );
            }

            @Override
            public void onLoginError(Exception e) {
                Log.e( TAG, Const.CONST_MSG_ERRO_2 , e);
            }
        }).execute();
    }








}