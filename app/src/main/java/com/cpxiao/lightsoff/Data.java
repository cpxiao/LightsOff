package com.cpxiao.lightsoff;

/**
 * Data
 *
 * @author cpxiao on 2016/5/16.
 */
public class Data {


    private static final String[] LEVEL_4_1 = {"4", "4", "4", "1111100110011111"};
    private static final String[] LEVEL_4_2 = {"4", "4", "4", "0110111111110110"};
    private static final String[] LEVEL_4_3 = {"4", "4", "4", "1000010000100001"};
    private static final String[] LEVEL_4_4 = {"4", "4", "4", "0001001001001000"};
    private static final String[] LEVEL_4_5 = {"4", "4", "4", "1001011001101001"};
    private static final String[] LEVEL_4_6 = {"4", "4", "4", "1111111111111111"};
    private static final String[] LEVEL_5_1 = {"5", "5", "1", "0000000100011100010000000"};
    private static final String[] LEVEL_5_2 = {"5", "5", "4", "1101110001000001000111011"};
    private static final String[] LEVEL_5_3 = {"5", "5", "3", "0100011000001100010100011"};
    private static final String[] LEVEL_5_4 = {"5", "5", "3", "0100011000001000001100010"};
    private static final String[] LEVEL_5_5 = {"5", "5", "3", "1000100110101010110011001"};
    private static final String[] LEVEL_5_6 = {"5", "5", "0", "0010000100110110010000100"};


    private static final String[][] LEVEL_ALL = {
            LEVEL_4_1, LEVEL_4_2, LEVEL_4_3, LEVEL_4_4, LEVEL_4_5, LEVEL_4_6,
            LEVEL_5_1, LEVEL_5_2, LEVEL_5_3, LEVEL_5_4, LEVEL_5_5, LEVEL_5_6
    };

    public static String[] getData(int level) {
        if (level > 0 && level <= LEVEL_ALL.length) {
            return LEVEL_ALL[level - 1];
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
