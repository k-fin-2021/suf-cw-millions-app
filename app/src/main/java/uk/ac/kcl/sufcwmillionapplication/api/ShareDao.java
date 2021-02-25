package uk.ac.kcl.sufcwmillionapplication.api;

import java.util.Date;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;

public interface ShareDao {

    SymbolInfo getInfoOfSymbol(String symbol);

    List<DailyQuote> getHistoryQuotes(String symbol, Date startDate, Date endDate);

}
