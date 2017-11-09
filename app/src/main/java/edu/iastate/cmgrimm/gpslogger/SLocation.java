package edu.iastate.cmgrimm.gpslogger;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

import static java.util.jar.Pack200.Packer.ERROR;

/**
 * Created by Chase on 11/9/2017.
 */

public class SLocation {

    //TODO identify internal storage location

    private String sdLocation;
    private String sdRemainingStorage;
    private String internalRemainingStorage;

    public SLocation() {

       this.sdLocation = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"";
       this.sdRemainingStorage = getAvailableExternalMemorySize();
       this.internalRemainingStorage = getAvailableInternalMemorySize();
    }

    public String getStorageLocation() {
        //TODO differentiate between KB and MB suffix

        int sd = Integer.parseInt(sdRemainingStorage.replaceAll("[^\\d.]", ""));
        if (sd < 100) {
            return sdLocation;
        } else {
            return sdLocation;
        }
    }

    //is there an sd card?
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public String getSdLocation() {
        return sdLocation;
    }

    public String getSdRemainingStorage() {
        return sdRemainingStorage;
    }

    public String getInternalRemainingStorage() {
        return internalRemainingStorage;
    }
}
