package uk.ac.kcl.sufcwmillionapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CacheBean;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;

public class CacheUtils {
    private final static String CACHE_NAME = "sufcache";

    public static boolean isCache(Context context,SearchBean searchBean) {
        CacheBean cacheBean = (CacheBean)getObject(context, searchBean.name);

        if (cacheBean != null){
            Log.d("cache"," cache bean +"+cacheBean);
            if ((searchBean.startDate.after(cacheBean.getStartDate()) || searchBean.startDate.equals(cacheBean.getStartDate())) &&
                    cacheBean.getStartDate().before(cacheBean.getEndDate()) && (cacheBean.getEndDate().after(searchBean.endDate) ||
                    cacheBean.getEndDate().equals(searchBean.endDate))) {
                return true;
            }
        }
        Log.d("cache","cache not exist");
        return false;
    }

    public static List<DailyQuote> getCacheDailyQuote(Context context, SearchBean searchBean) {
        CacheBean cacheBean = (CacheBean) getObject(context, searchBean.name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ((searchBean.startDate.after(cacheBean.getStartDate()) || searchBean.startDate.equals(cacheBean.getStartDate())) &&
                cacheBean.getStartDate().before(cacheBean.getEndDate()) && (cacheBean.getEndDate().after(searchBean.endDate) ||
                cacheBean.getEndDate().equals(searchBean.endDate))) {
            List<DailyQuote> cacheQuotes = cacheBean.getResults();
            List<DailyQuote> subCacheQuotes = new ArrayList<>();
            for (DailyQuote dailyQuote : cacheQuotes) {
                Date cacheDate = null;
                try {
                    cacheDate = sdf.parse(dailyQuote.date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (cacheDate != null) {
                    if ((cacheDate.after(cacheBean.getStartDate()) &&
                            cacheDate.before(cacheBean.getEndDate())) || cacheDate.equals(cacheBean.getStartDate())
                            || cacheDate.equals(cacheBean.getEndDate())) {
                        subCacheQuotes.add(dailyQuote);
                    }
                }

            }
            return subCacheQuotes;
        }

        return new ArrayList<DailyQuote>();
    }

    public static SymbolInfo getCacheSymbolInfo(Context context, SearchBean searchBean) {
        return new SymbolInfo();
    }

    public static void updateCache(Context context,SearchBean searchBean,List<DailyQuote> quotesCal, SymbolInfo symbolInfo) {
        CacheBean cacheBean = new CacheBean();
        cacheBean.setName(searchBean.name);
        cacheBean.setStartDate(searchBean.startDate);
        cacheBean.setEndDate(searchBean.endDate);
        cacheBean.setResults(quotesCal);
        saveObject(context,searchBean.name,cacheBean);
        Log.d("cache","update cache "+searchBean);
    }

    private static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;
            char hex_char1 = hexString.charAt(i);
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16;
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i);
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48);
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55;
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;
        }
        return retData;
    }

    public static Object getObject(Context context, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void saveObject(Context context, String key, Object obj) {
        try {
            SharedPreferences.Editor sharedata = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE).edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
