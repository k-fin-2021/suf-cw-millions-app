package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndicatorFactoryTest {

    @Test
    public void testGetBean(){
        TechnicalIndicators ema9 = IndicatorFactory.get(IndicatorNames.EMA_9);
        //TODO: If ema9's implementation is available, replace below assertion!!
        Assert.assertNull(ema9);
    }

    @Test
    public void testGetBeanNotExists(){
        Assert.assertThrows(IllegalArgumentException.class,()->{
            IndicatorFactory.get("KCL");
        });
    }

}