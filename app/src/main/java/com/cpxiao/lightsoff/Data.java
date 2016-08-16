package com.cpxiao.lightsoff;

/**
 * Created by cpxiao on 5/16/16.
 * Data
 */
public class Data {


    private static final String[] LEVEL_1 = {"4", "4", "4", "1111100110011111"};

    private static final String[][] LEVEL_ALL = {
            LEVEL_1
    };

    public static String[] getData(int level) {
        if (level > 0 && level <= LEVEL_ALL.length) {
            return LEVEL_ALL[level];
        }
        return null;
    }

    public static int getX(String[] data) {
        if (data == null || data.length != 4) {
            return 0;
        }
        return Integer.valueOf(data[0]);
    }

    public static int getY(String[] data) {
        if (data == null || data.length != 4) {
            return 0;
        }
        return Integer.valueOf(data[1]);
    }

    public static int getMoves(String[] data) {
        if (data == null || data.length != 4) {
            return 0;
        }
        return Integer.valueOf(data[2]);
    }

    public static String getStore(String[] data) {
        if (data == null || data.length != 4) {
            return null;
        }
        return data[3];
    }

}
