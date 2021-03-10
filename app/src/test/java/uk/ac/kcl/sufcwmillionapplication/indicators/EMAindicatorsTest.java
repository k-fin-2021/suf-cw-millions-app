package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;


public class EMAindicatorsTest {
    private EMAIndicators emaIndicators;

    @Test
    public void testEMA_calculate() {
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
        dq4.high = 5.0;
        dq4.low = 1.0;
        dq4.close = 4.0;
        dq4.adjclose = 4.0;
        dq4.volume = 0.0;
        dailyquote.add(dq4);

        emaIndicators = new EMAIndicators(3);
        List<CalculateResult> result = emaIndicators.calculate(dailyquote);
        double[] TestResult = new double[]{2.00,2.50,3.25};
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,TestResult[i], 0.001);
        }
    }


    private static class TestIndicators extends TechnicalIndicators{

        @Override
        public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
            return null;
        }
    }
}