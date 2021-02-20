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
        quotes = new ArrayList<>();
        DailyQuote dq = DailyQuote.createByPKDailyQuote("2021-01-01");
        dq.close = 1.0;
        quotes.add(dq);
        dq = DailyQuote.createByPKDailyQuote("2021-01-02");
        dq.close = 2.0;
        quotes.add(dq);
        dq = DailyQuote.createByPKDailyQuote("2021-01-03");
        dq.close = 3.0;
        quotes.add(dq);
        testIndicators = new TestIndicators();
    }

    @Test
    public void testExtractAllClosingPrice() {
        List<Double> res = new ArrayList<>();
        res.add(1.0);
        res.add(2.0);
        res.add(3.0);
        Assert.assertEquals(res, testIndicators.extractAllClosingPrice(quotes));
    }

    @Test
    public void testExtractAllDate() {
        List<String> res = new ArrayList<>();
        res.add("2021-01-01");
        res.add("2021-01-02");
        res.add("2021-01-03");
        Assert.assertEquals(res, testIndicators.extractAllDate(quotes));
    }

    @Test
    public void testExtractLastClosingPrice() {
        List<Double> res = new ArrayList<>();
        res.add(2.0);
        res.add(3.0);
        Assert.assertEquals(res, testIndicators.extractLastClosingPrice(quotes,2));
    }

    @Test
    public void testExtractLastDate() {
        List<String> res = new ArrayList<>();
        res.add("2021-01-02");
        res.add("2021-01-03");
        Assert.assertEquals(res, testIndicators.extractLastDate(quotes,2));
    }

    @Test
    public void testGenerateResult() {
        List<String> date = new ArrayList<>();
        date.add("2021-01-01");
        date.add("2021-01-02");
        List<Double> price = new ArrayList<>();
        price.add(1.0);
        price.add(2.0);
        List<CalculateResult> result = new ArrayList<>();
        CalculateResult calculateResult = new CalculateResult();
        calculateResult.data = 1.0;
        calculateResult.date = "2021-01-01";
        result.add(calculateResult);
        calculateResult = new CalculateResult();
        calculateResult.data = 2.0;
        calculateResult.date = "2021-01-02";
        result.add(calculateResult);
        Assert.assertEquals(result,testIndicators.generateResult(date,price));
    }


    private static class TestIndicators extends TechnicalIndicators{

        @Override
        public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
            return null;
        }
    }
}