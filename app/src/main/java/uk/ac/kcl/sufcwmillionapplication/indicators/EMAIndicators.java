package uk.ac.kcl.sufcwmillionapplication.indicators;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

/**
 * EMA (C1+C2+C3+....+CN)/N
 */
public class EMAIndicators extends TechnicalIndicators {

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        return new ArrayList<>();
    }

}
