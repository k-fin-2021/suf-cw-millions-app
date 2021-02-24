package uk.ac.kcl.sufcwmillionapplication.api;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.api.impl.YahooShareDaoImpl;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;

import static org.junit.Assert.*;

public class YahooShareDaoImplTest {

    @Test
    public void getInfoOfSymbol() {
        ShareDao yahooShareDao = new YahooShareDaoImpl();
        SymbolInfo symbolInfo = yahooShareDao.getInfoOfSymbol("HSBC");
        Assert.assertNotNull(symbolInfo);
        Assert.assertEquals("HSBC Holdings plc", symbolInfo.getLongName());
        Assert.assertEquals("HSBC Holdings, plc.", symbolInfo.getShortName());
        Assert.assertEquals("HSBC",symbolInfo.getSymbol());
    }

    @Test
    public void getHistoryQuotes() throws Exception {
        ShareDao yahooShareDao = new YahooShareDaoImpl();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DailyQuote> quotes = yahooShareDao.getHistoryQuotes("0005.HK",
                sdf.parse("2020-12-01"),sdf.parse("2021-01-05"));
        Assert.assertNotNull(quotes);
        Assert.assertEquals(23, quotes.size());
    }
}
