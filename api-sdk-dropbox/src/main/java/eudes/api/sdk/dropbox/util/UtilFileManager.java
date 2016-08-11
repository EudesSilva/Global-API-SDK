package eudes.api.sdk.dropbox.util;

import android.content.Context;

import eudes.api.sdk.dropbox.config.Const;

/**
 * Created Eudes on 09/08/2016.
 */
public class UtilFileManager {


    public static boolean isDatabasesExists(Context context) {
        return context.getDatabasePath(Const.CONST_NAME_DATABASE).exists();
    }

    public static String getPathDatabases(Context context) {
        return context.getDatabasePath(Const.CONST_NAME_DATABASE).toString();
    }

    public static String getPathDatabasesBackup(Context context) {
        return context.getDatabasePath(Const.CONST_NAME_DATABASE_BK).toString();
    }


}
