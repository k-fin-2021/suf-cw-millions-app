package uk.ac.kcl.sufcwmillionapplication.indicators;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class EMAIndicators extends TechnicalIndicators {

    static public int term = 0;
    public EMAIndicators(int x){
        term = x;
    }
    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        return new ArrayList<>();
    }

}
