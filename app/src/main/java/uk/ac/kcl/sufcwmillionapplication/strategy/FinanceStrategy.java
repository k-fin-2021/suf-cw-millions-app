package uk.ac.kcl.sufcwmillionapplication.strategy;

import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public interface FinanceStrategy {

    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList);

}
