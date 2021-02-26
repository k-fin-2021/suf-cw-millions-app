package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Test;

public class IndicatorFactoryTest {

    @Test
    public void testGetBean(){
        TechnicalIndicators ema9 = IndicatorFactory.get(IndicatorNames.EMA_9);
        Assert.assertNotNull(ema9);
        Assert.assertTrue(ema9 instanceof EMAIndicators);


        TechnicalIndicators reGetEma = IndicatorFactory.get(IndicatorNames.EMA_9);
        Assert.assertNotNull(ema9);

        TechnicalIndicators macd = IndicatorFactory.get(IndicatorNames.MACD_12_26);
        Assert.assertNotNull(macd);
        Assert.assertTrue(macd instanceof MACDIndicators);

        TechnicalIndicators macdAvg = IndicatorFactory.get(IndicatorNames.MACD_AVG);
        Assert.assertNotNull(macdAvg);
        Assert.assertTrue(macdAvg instanceof MACDAVGIndicators);

        TechnicalIndicators sma = IndicatorFactory.get(IndicatorNames.SMA_26);
        Assert.assertNotNull(sma);
        Assert.assertTrue(sma instanceof SMAIndicators);
    }

    @Test
    public void testGetBeanNotExists(){
        Assert.assertThrows(IllegalArgumentException.class,()->{
            IndicatorFactory.get("KCL");
        });
    }

}