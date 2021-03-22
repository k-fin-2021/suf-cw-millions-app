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
             double[] theoretical_result = new double[]{0.00,
                     3.86735E-05,
                     0.000196777,
                     0.000355157,
                     0.000489904,
                     0.000701469,
                     0.000833414,
                     0.000995328,
                     0.001121682,
                     0.001186383,
                     0.001175345,
                     0.001098366,
                     0.001024777,
                     0.001054662,
                     0.001156568,
                     0.001410971,
                     0.001767714,
                     0.002130214,
                     0.002570265,
                     0.003098983,
                     0.003605946,
                     0.004029763,
                     0.004531362,
                     0.005146705,
                     0.005853298,
                     0.006645008,
                     0.007478027,
                     0.008052673,
                     0.008360068,
                     0.008396168,
                     0.008281815,
                     0.008015624,
                     0.007597475
                     };
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,theoretical_result[i], 0.0001);
        }
    }

    public double decimal(double value){
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

}

