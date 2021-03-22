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
        System.out.println(result);
             double[] theoretical_result = new double[]{0.00,
                     3.8673504273534226E-5,
                     1.967773209633528E-4,
                     3.551573104387398E-4,
                     4.899043664614696E-4,
                     7.014693293685521E-4,
                     8.334136903770239E-4,
                     9.95327739098994E-4,
                     0.0011216823878472224,
                     0.0011863830780727812,
                     0.001175345107222501,
                     0.0010983661595665314,
                     0.0010247772541098665,
                     0.0010546619194251958,
                     0.0011565676052319798,
                     0.0014109710283751798,
                     0.0017677143444114797,
                     0.0021302141503220665,
                     0.0025702645545897193,
                     0.003098982743918218,
                     0.0036059462661088724,
                     0.0040297630287795,
                     0.0045313623385995,
                     0.0051467053612437585,
                     0.005853298358006742,
                     0.006645007720807561,
                     0.007478026890093048,
                     0.008052672792141299,
                     0.008360068148253335,
                     0.00839616763776417,
                     0.008281815117675374,
                     0.008015624398622363,
                     0.007597475291932782
                     };
        for(int i = 0; i < result.size(); i++){
            Assert.assertEquals(result.get(i).data,theoretical_result[i], 0.0001);
        }
    }

    private static class TestIndicators extends TechnicalIndicators{

        @Override
        public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
            return null;
        }
    }
}

