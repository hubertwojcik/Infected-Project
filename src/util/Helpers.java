package util;

public class Helpers {

    public static double roundToTwoDecimals(double value){
        return Math.round(value * 100) / 100.0;
    }

    public static boolean isStringNumberic(String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
