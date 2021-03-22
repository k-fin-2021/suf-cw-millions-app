package uk.ac.kcl.sufcwmillionapplication.api;

import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;

public interface ShareDao {

    SymbolInfo getInfoOfSymbol(SearchBean searchBean);

    List<DailyQuote> getHistoryQuotes(SearchBean searchBean);

}
