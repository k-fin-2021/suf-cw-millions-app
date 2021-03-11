package uk.ac.kcl.sufcwmillionapplication;


import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.indicators.EMAIndicators;
import uk.ac.kcl.sufcwmillionapplication.indicators.GetTestDataUtil;
import uk.ac.kcl.sufcwmillionapplication.indicators.MACDIndicators;
import uk.ac.kcl.sufcwmillionapplication.indicators.SMAIndicators;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StrategyUnitTest {

    static List<DailyQuote> originData;

    @BeforeClass
    public static void setup() {
        originData = GetTestDataUtil.getData();
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

    @Test
    public void test_SMA_strategy(){
        System.out.println("=========== SMA =============");
        SMAIndicators smaStrategy = new SMAIndicators();
        List<CalculateResult> results = smaStrategy.calculate(originData);
        for (CalculateResult result:results){
            System.out.println(result.toString());
        }
    }
}