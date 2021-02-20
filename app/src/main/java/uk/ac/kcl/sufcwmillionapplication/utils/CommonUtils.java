package uk.ac.kcl.sufcwmillionapplication.utils;

/**
 * Common Utils
 */
public class CommonUtils {

    /**
     * Check String is empty or not.
     * @param str The string needs to be checked
     * @return true if empty
     */
    public static boolean isEmptyString(String str){
        return (str == null || str.isEmpty());
    }

    public static boolean isStringEquals(String str1, String str2){
        if(str1 == str2){
            return true;
        }
        if (str1 == null || str2 == null){
            return false;
        }
        return str1.equals(str2);
    }

}
