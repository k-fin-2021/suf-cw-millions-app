package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class MACDIndicatorsTest {
    private MACDIndicators macdIndicators;

    @Test
    public void testMACD_calculate() {
        List<DailyQuote> DailyQuote_test = GetTestDataUtil.getData();

        macdIndicators = new MACDIndicators();
        List<CalculateResult> result = macdIndicators.calculate(DailyQuote_test);
        for(int i = 0; i < result.size(); i++){
            result.get(i).data = decimal(result.get(i).data);
        }
        System.out.println(result);
        double[] theoretical_result = new double[]{0.00,
                0.000193368,
                0.000829193,
                0.000988677,
                0.001028893,
                0.001547729,
                0.001361191,
                0.001642984,
                0.001627101,
                0.001445186,
                0.001131193,
                0.00079045,
                0.000730422,
                0.001174201,
                0.00156419,
                0.002428585,
                0.003194688,
                0.003580213,
                0.004330466,
                0.005213856,
                0.0056338,
                0.00572503,
                0.00653776,
                0.007608077,
                0.00867967,
                0.009811845,
                0.010810104,
                0.010351256,
                0.00958965,
                0.008540566,
                0.007824405,
                0.006950862,
                0.005924879
        };
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,theoretical_result[i], 0.0001);
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
