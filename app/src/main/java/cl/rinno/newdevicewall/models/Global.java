package cl.rinno.newdevicewall.models;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by chinodoge on 13-03-2017.
 */

public class Global {

    public static String dirHome = Environment.getExternalStorageDirectory().getAbsolutePath()+"/dataset/";
    public static String newJson = "Objects2.json";
    private static String dirResource = "r/";
    public static String dirImages = dirHome+dirResource+"i/";
    public static String dirJson = dirHome+dirResource+newJson;
    public static String jsonData2 = "";

    public static void makeDirectories() {
        File mainFolder = new File(dirHome);
        File resourceFolder = new File(dirHome + dirResource);
        File imagesFolder = new File(dirImages);
        if (mainFolder.mkdir()) {
            Log.d("mkDir", "SourceApp Created");
        } else {
            Log.d("mkDir", "SourceApp NOT Created");
        }
        if (resourceFolder.mkdir()) {
            Log.d("mkDir", "Resource Created");
        } else {
            Log.d("mkDir", "Resource NOT Created");
        }
        if (imagesFolder.mkdir()) {
            Log.d("mkDir", "IMAGES Created");
        } else {
            Log.d("mkDir", "IMAGES NOT Created");
        }

    }
}
