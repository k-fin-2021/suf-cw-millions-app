package uk.ac.kcl.sufcwmillionapplication.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DailyQuote implements Serializable {

    private DailyQuote() {

    }

    public static DailyQuote createDailyQuote() {
        DailyQuote result = new DailyQuote();
        return result;
    }

    public static DailyQuote createDailyQuote(String date) {
        DailyQuote result = new DailyQuote();
        result.date = date;
        return result;
    }

    public String date = ""; /* primary */

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

