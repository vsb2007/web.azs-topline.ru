package io.bgroup.topline.model;


public class MyConstant {
    private static String fileFolder = "";
    private static String filePrefix = "";

    public MyConstant() {
    }

    public static String getFileFolder() {
        return fileFolder;
    }

    public static void setFileFolder(String fileFolder) {
        MyConstant.fileFolder = fileFolder;
    }

    public static String getFilePrefix() {
        return filePrefix;
    }

    public static void setFilePrefix(String filePrefix) {
        MyConstant.filePrefix = filePrefix;
    }
}
