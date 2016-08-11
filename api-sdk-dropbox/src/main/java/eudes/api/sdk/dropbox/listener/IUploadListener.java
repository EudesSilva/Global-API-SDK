package eudes.api.sdk.dropbox.listener;

import com.dropbox.core.v2.files.FileMetadata;

/**
 * Created Eudes on 09/08/2016.
 */
public interface IUploadListener {

    void onUploadComplete(FileMetadata fileMetadata);
    void onUploadError(Exception e);

}
