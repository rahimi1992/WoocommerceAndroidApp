package com.test.newshop1.utilities;

public class ImageUtil {

    public static final int SMALL_SIZE = 11;
    public static final int MEDIUM_SIZE = 22;

    public static String getThumb(String src, int size){
        //Log.d(TAG, "Thumb: " + src);
        // Log.d(TAG, "Thumb: "+src.replaceFirst(".jpg", "-100x100.jpg"));
        switch (size) {
            case SMALL_SIZE:
                return src.replaceFirst(".jpg", "-100x100.jpg");
            case MEDIUM_SIZE:
                return src.replaceFirst(".jpg", "-300x225.jpg");
        }
        return src;
    }

}
