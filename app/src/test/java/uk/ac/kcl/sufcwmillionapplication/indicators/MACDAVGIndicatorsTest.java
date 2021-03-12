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
        List<DailyQuote> DailyQuote_test = GetTestDataUtil.getData();

        macdavgIndicators = new MACDAVGIndicators();
        List<CalculateResult> result = macdavgIndicators.calculate(DailyQuote_test);
        for(int i = 0; i < result.size(); i++){
            result.get(i).data = decimal(result.get(i).data);
        }
        System.out.println(result);
             double[] theoretical_result = new double[]{0.01062, 0.01092, 0.0109, 0.01075, 0.01059, 0.01021, 0.00981, 0.00904};
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,theoretical_result[i], 0.001);
        }
    }

    public double decimal(double value){
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }


    private static class TestIndicators extends TechnicalIndicators{

        @Override
        public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
            return null;
        }
    }
}

