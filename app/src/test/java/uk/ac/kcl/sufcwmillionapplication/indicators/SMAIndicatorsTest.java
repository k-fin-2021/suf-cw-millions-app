package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class SMAIndicatorsTest {
    private SMAIndicators smaIndicators;

    @Test
    public void testSMACalculate() {
        List<DailyQuote> DailyQuote_test = GetTestDataUtil.getData();
        smaIndicators = new SMAIndicators();
        List<CalculateResult> result = smaIndicators.calculate(DailyQuote_test);
        for(int i = 0; i < result.size(); i++){
            result.get(i).data = decimal(result.get(i).data);
            System.out.println(result.get(i).data);
        }
        double[] theoretical_result = new double[]{1.3792,
                1.3811,
                1.3824,
                1.3833,
                1.3843,
                1.3853,
                1.386,
                1.3869};
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,theoretical_result[i], 0.001);
        }
    }

    public double decimal(double value){
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }


}