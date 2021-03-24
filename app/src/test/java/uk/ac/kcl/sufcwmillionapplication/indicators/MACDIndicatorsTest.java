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
                0.0002,
                0.0008,
                0.0010,
                0.0010,
                0.0015,
                0.0014,
                0.0016,
                0.0016,
                0.0014,
                0.0011,
                0.0008,
                0.0007,
                0.0011,
                0.0016,
                0.0024,
                0.0032,
                0.0036,
                0.0043,
                0.0052,
                0.0056,
                0.0057,
                0.0065,
                0.0076,
                0.0087,
                0.0098,
                0.0108,
                0.0104,
                0.0096,
                0.0085,
                0.0078,
                0.0070,
                0.0059
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
