package uk.ac.kcl.sufcwmillionapplication;




import android.util.Log;

import org.junit.Test;


import java.util.ArrayList;

import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.strategy.EMAStrategy;
import uk.ac.kcl.sufcwmillionapplication.utils.DailyQuoteUtils;
import uk.ac.kcl.sufcwmillionapplication.utils.NetworkUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void parse_from_url() {
        String findQuotedateData = "2020-01-01";
        String mydata = NetworkUtils.fetchUrl(DailyQuoteUtils.findQuoteUrl(findQuotedateData));
        ArrayList<DailyQuote> arrayList = DailyQuoteUtils.makeFromCSV(mydata);

        System.out.println("========  "+DailyQuoteUtils.findQuoteUrl(findQuotedateData)+"  =======");
        for (int i=0;i<arrayList.size();i++){
            System.out.println(arrayList.get(i).toString());
        }
    }


}