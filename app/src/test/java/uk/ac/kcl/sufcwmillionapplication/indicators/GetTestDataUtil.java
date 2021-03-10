package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.api.impl.YahooShareDaoImpl;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;

public class GetTestDataUtil {

    public static List<DailyQuote> getData(){
        List<DailyQuote> quotes = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            quotes = (new YahooShareDaoImpl()).getHistoryQuotes(new SearchBean(
                    "GBPUSD=X",sdf.parse("2021-01-20"),
                    sdf.parse("2021-03-05")));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return quotes;
    }
}
