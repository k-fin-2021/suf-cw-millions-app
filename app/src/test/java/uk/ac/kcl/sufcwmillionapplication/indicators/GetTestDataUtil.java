package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.utils.CommonUtils;

public class GetTestDataUtil {

    private static List<DailyQuote> quotes;

    public static List<DailyQuote> getData(){
        if(quotes != null) {
            return quotes;
        }
        URL path = GetTestDataUtil.class.getClassLoader().getResource("GBPUSD.csv");
        File inputFile = new File(path.getPath());
        quotes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String content = reader.readLine();
            while (content != null){
                if(CommonUtils.isEmptyString(content) || content.startsWith("Date")){
                    content = reader.readLine();
                    continue;
                }
                String columns[] = content.split(",");
                DailyQuote dq = DailyQuote.createDailyQuote();
                dq.date = columns[0];
                dq.open = Double.parseDouble(columns[1]);
                dq.high = Double.parseDouble(columns[2]);
                dq.low  = Double.parseDouble(columns[3]);
                dq.close = Double.parseDouble(columns[4]);
                dq.adjclose = Double.parseDouble(columns[5]);
                dq.volume = Integer.parseInt(columns[6]);
                quotes.add(dq);
                content = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotes;
    }
}
