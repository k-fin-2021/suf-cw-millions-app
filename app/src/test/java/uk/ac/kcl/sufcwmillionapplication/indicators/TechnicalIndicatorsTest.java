package uk.ac.kcl.sufcwmillionapplication.indicators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

import static org.junit.Assert.*;


public class TechnicalIndicatorsTest {

    private List<DailyQuote> quotes;
    private TestIndicators testIndicators;

    @Before
    public void prepareData(){
        quotes = GetTestDataUtil.getData();
        testIndicators = new TestIndicators();
    }

    @Test
    public void test(){
        Assert.assertNotNull(testIndicators.calculate(quotes));
    }



    private static class TestIndicators extends TechnicalIndicators{

        @Override
        public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
            return new ArrayList<>();
        }
    }
}