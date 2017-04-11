package cl.rinno.newdevicewall.models;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

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
    public static int mBandera = 0;

    public static Producto producto;
    public static Producto accesorio;
    public static ArrayList<Producto> allDevices = new ArrayList<>();
    public static ArrayList<Producto> allProducts = new ArrayList<>();
    public static ArrayList<Producto> allAccessories = new ArrayList<>();
    public static ArrayList<Provider> providersDevices = new ArrayList<>();
    public static ArrayList<Producto> planesDeVozCCList = new ArrayList<>();
    public static ArrayList<Producto> planesDeVozIlimitado = new ArrayList<>();
    public static ArrayList<Producto> planesSmartFunSimple = new ArrayList<>();
    public static ArrayList<Producto> planesControlFunSimple = new ArrayList<>();
    public static ArrayList<Producto> miPrimerPlanMultimedia = new ArrayList<>();
    public static ArrayList<Producto> miPrimerPlanVoz = new ArrayList<>();
    public static ArrayList<Producto> allPlans = new ArrayList<>();

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

    public static String[][] getBackgroundColorsCard() {
        String[][] colorSet = new String[6][3];

        colorSet[0][0] = "#0154a0";
        colorSet[0][1] = "#FFFFFF";
        colorSet[0][2] = "#ff6700";

        colorSet[1][0] = "#37b08d";
        colorSet[1][1] = "#ffffff";
        colorSet[1][2] = "#0154a0";

        colorSet[2][0] = "#ff6702";
        colorSet[2][1] = "#FFFFFF";
        colorSet[2][2] = "#0154a0";

        colorSet[3][0] = "#ffae05";
        colorSet[3][1] = "#0154a0";
        colorSet[3][2] = "#ffffff";

        colorSet[4][0] = "#478abf";
        colorSet[4][1] = "#ffffff";
        colorSet[4][2] = "#f5a623";

        colorSet[5][0] = "#5c086f";
        colorSet[5][1] = "#ffffff";
        colorSet[5][2] = "#f5a623";

        return colorSet;
    }

    public static Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.cl");
            int val           = p.waitFor();
            return (val == 0);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
