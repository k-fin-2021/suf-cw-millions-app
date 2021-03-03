package uk.ac.kcl.sufcwmillionapplication;


import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.indicators.EMAIndicators;
import uk.ac.kcl.sufcwmillionapplication.indicators.MACDIndicators;
import uk.ac.kcl.sufcwmillionapplication.utils.DailyQuoteUtils;
import uk.ac.kcl.sufcwmillionapplication.utils.NetworkUtils;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StrategyUnitTest {

    static ArrayList<DailyQuote> originData;

    @BeforeClass
    public static void setup() {
        String findQuotedateData = "2020-01-01";
        String mydata = NetworkUtils.fetchUrl(DailyQuoteUtils.findQuoteUrl(findQuotedateData));
        originData = DailyQuoteUtils.makeFromCSV(mydata);
        System.out.println("========  "+DailyQuoteUtils.findQuoteUrl(findQuotedateData)+"  =======");
        if (originData!=null && originData.size()>0){
            System.out.println("=====origin data init success, total "+originData.size());
        }else {
            System.out.println("=====origin data init fail");
        }
    }


    @Test
    public void test_EMA_strategy(){
        System.out.println("=========== EMA =============");
        EMAIndicators emaStrategy = new EMAIndicators(12);
        List<CalculateResult> results = emaStrategy.calculate(originData);
        for (CalculateResult result:results){
            System.out.println(result.toString());
        }
    }

    @Test
    public void test_MACD_strategy(){
        System.out.println("=========== MACD =============");
        MACDIndicators macdStrategy = new MACDIndicators();
        List<CalculateResult> results = macdStrategy.calculate(originData);
        for (CalculateResult result:results){
            System.out.println(result.toString());
        }
    }
}