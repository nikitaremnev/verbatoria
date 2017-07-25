package com.verbatoria.utils;

import android.os.Environment;

import java.io.File;

/**
 * Утилиты для работы с файловой системой приложения
 *
 * @author nikitaremnev
 */
public class FileUtils {

    public final static String APPLICATION_FILES_DIRECTORY = "Verbatoria";
    public final static String JSON_FILE_EXTENSION = ".json";

    public static void createApplicationDirectory() {
        File applicationDirectory = getApplicationDirectory();
        if (!applicationDirectory.exists()) {
            applicationDirectory.mkdirs();
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static File getApplicationDirectory() {
        return new File(Environment.getExternalStorageDirectory(), APPLICATION_FILES_DIRECTORY);
    }
}
