package uk.ac.kcl.sufcwmillionapplication.strategy;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class EMAStrategy implements FinanceStrategy{

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        return new ArrayList<>();
    }

}
