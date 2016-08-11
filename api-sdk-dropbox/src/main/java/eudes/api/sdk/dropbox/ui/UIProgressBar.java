package eudes.api.sdk.dropbox.ui;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created Eudes on 09/08/2016.
 */
public class UIProgressBar {


    public static ProgressDialog show(Context context, String msgText){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage( msgText );
        dialog.show();
        return dialog;
    }
}
