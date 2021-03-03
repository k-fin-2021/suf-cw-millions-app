package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class MACDAVGIndicatorsTest {
    private MACDAVGIndicators macdavgIndicators;

    @Test
    public void testMACDAVG_calculate() {
        ArrayList<DailyQuote> DailyQuote_test = new ArrayList<DailyQuote>();
        DailyQuote a = DailyQuote.createByPKDailyQuote("2020-01-01");
        a.open = 2.0;
        a.high = 3.0;
        a.low = 1.0;
        a.close = 2.0;
        a.adjclose = 2.0;
        a.volume = 0.0;
        DailyQuote_test.add(a);

        DailyQuote b = DailyQuote.createByPKDailyQuote("2020-01-02");
        b.open = 3.0;
        b.high = 5.0;
        b.low = 2.0;
        b.close = 4.0;
        b.adjclose = 4.0;
        b.volume = 0.0;
        DailyQuote_test.add(b);

        DailyQuote c = DailyQuote.createByPKDailyQuote("2020-01-03");
        c.open = 3.0;
        c.high = 3.0;
        c.low = 1.0;
        c.close = 1.0;
        c.adjclose = 1.0;
        c.volume = 0.0;
        DailyQuote_test.add(c);

        DailyQuote d = DailyQuote.createByPKDailyQuote("2020-01-04");
        d.open = 2.0;
        d.high = 5.0;
        d.low = 1.0;
        d.close = 4.0;
        d.adjclose = 4.0;
        d.volume = 0.0;
        DailyQuote_test.add(d);

        macdavgIndicators = new MACDAVGIndicators();
        List<CalculateResult> result = macdavgIndicators.calculate(DailyQuote_test);
        for(int i = 0; i < result.size(); i++){
            result.get(i).data = decimal(result.get(i).data);
        }
        double[] theoretical_result = new double[]{0.0000, 0.0319,0.0342, 0.0656};
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,theoretical_result[i], 0.001);
            System.out.println(result.get(i).data);
        }
    }

    public double decimal(double value){
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }


    private static class TestIndicators extends TechnicalIndicators{

        @Override
        public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
            return null;
        }
    }
}

