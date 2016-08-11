package eudes.api.sdk.dropbox.listener;

import java.io.File;

/**
 * Created Eudes on 09/08/2016.
 */
public interface IDownloadListener {

    void onDownloadComplete(File file);
    void onError(Exception e);


}
