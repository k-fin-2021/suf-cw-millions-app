package uk.ac.kcl.sufcwmillionapplication.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DailyQuote implements Serializable {
    static ArrayList<DailyQuote> DailyQuote_allInstances = new ArrayList<DailyQuote>();

    DailyQuote() {
        DailyQuote_allInstances.add(this);
    }

    // TODO: There is a code quality concern for this class...
    public static DailyQuote createDailyQuote() {
        DailyQuote result = new DailyQuote();
        return result;
    }

    public String date = ""; /* primary */
    public static Map<String, DailyQuote> DailyQuote_index = new HashMap<String, DailyQuote>();

    public static DailyQuote createByPKDailyQuote(String datex) {
        DailyQuote result = new DailyQuote();
        DailyQuote.DailyQuote_index.put(datex, result);
        result.date = datex;
        return result;
    }

    public double open = 0;
    public double high = 0;
    public double low = 0;
    public double close = 0;
    public double adjclose = 0;
    public double volume = 0;

    @Override
    public String toString() {
        return "DailyQuote{" +
                "date='" + date + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", adjclose=" + adjclose +
                ", volume=" + volume +
                '}';
    }
}

