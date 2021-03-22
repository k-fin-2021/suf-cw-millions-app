package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;


public class EMAindicatorsTest {
    private EMAIndicators emaIndicators;

    @Test
    public void testEMA_calculate1() {
        List<DailyQuote> dailyquote = new ArrayList<DailyQuote>();
        DailyQuote dq1 = DailyQuote.createByPKDailyQuote("2020-01-01");
        dq1.open = 2.0;
        dq1.high = 3.0;
        dq1.low = 1.0;
        dq1.close = 1.0;
        dq1.adjclose = 2.0;
        dq1.volume = 0.0;
        dailyquote.add(dq1);

        DailyQuote dq2 = DailyQuote.createByPKDailyQuote("2020-01-02");
        dq2.open = 3.0;
        dq2.high = 5.0;
        dq2.low = 2.0;
        dq2.close = 2.0;
        dq2.adjclose = 4.0;
        dq2.volume = 0.0;
        dailyquote.add(dq2);

        DailyQuote dq3 = DailyQuote.createByPKDailyQuote("2020-01-03");
        dq3.open = 3.0;
        dq3.high = 3.0;
        dq3.low = 1.0;
        dq3.close = 3.0;
        dq3.adjclose = 1.0;
        dq3.volume = 0.0;
        dailyquote.add(dq3);

        DailyQuote dq4 = DailyQuote.createByPKDailyQuote("2020-01-04");
        dq4.open = 2.0;
        dq4.close = 4.0;
        dq4.high = 5.0;
        dq4.low = 1.0;
        dq4.adjclose = 4.0;
        dq4.volume = 0.0;
        dailyquote.add(dq4);

        emaIndicators = new EMAIndicators(5);
        List<CalculateResult> result = emaIndicators.calculate(dailyquote);
        double[] TestResult = new double[]{1.0,1.333,1.888,2.592};
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,TestResult[i], 0.001);
        }
    }
    @Test
    public void testEMA_calculate2() {
        List<DailyQuote> dailyquote = GetTestDataUtil.getData();
        emaIndicators = new EMAIndicators(26);
        List<CalculateResult> result = emaIndicators.calculate(dailyquote);
        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i).date+", "+result.get(i).data);
        }
        double[] expect_result = new double[]{1.364424,
                1.364603,
                1.365221,
                1.365488,
                1.365666,
                1.366295,
                1.366343,
                1.366799,
                1.367019,
                1.367082,
                1.366997,
                1.366843,
                1.366900,
                1.367416,
                1.367946,
                1.368972,
                1.370030,
                1.370845,
                1.372053,
                1.373492,
                1.374627,
                1.375516,
                1.377089,
                1.379017,
                1.381099,
                1.383390,
                1.385718,
                1.386837,
                1.387608,
                1.388004,
                1.388559,
                1.388866,
                1.388906
        };
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,expect_result[i], 0.001);
        }
    }

}
