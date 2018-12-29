package com.test.newshop1.utilities;

import java.text.DecimalFormat;

public class PersianTextUtil {

    private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};


    public static String toPer(String text, boolean comma) {
        if (text == null || text.length() == 0) {
            return " ";
        }

//        DecimalFormat formatter = new DecimalFormat("#,###,###");

        if (comma) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            text = formatter.format(Integer.valueOf(text));
        }
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out += persianNumbers[number];
            } else if (c == ',') {
                out += '،';
            } else {
                out += c;
            }

        }
        return out;
    }

    public static String toPer(int num, boolean comma){
        return toPer(String.valueOf(num), comma);
    }
    public static String toPer(String text){return toPer(text, true);}
    public static String toPer(int num){return toPer(String.valueOf(num), true);}
    public static String toPer(long num){return toPer(String.valueOf(num), false);}
    public static String toPer(Double num){return toPer(String.valueOf(Math.round(num)), true);}

}
