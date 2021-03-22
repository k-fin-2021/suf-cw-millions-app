package uk.ac.kcl.sufcwmillionapplication.indicators;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.utils.CommonUtils;

/**
 * Finance Indicators is an abstract class for the calculator of all technical indicators
 */
public abstract class TechnicalIndicators {

    /**
     * Calculate the technical indicator
     * @param dailyQuoteList The list of daily quote
     * @return The result series which needed to be displayed in the chart.
     */
    public abstract List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList);

    /**
     * get EMAIndex
     * @param term
     * @return
     */
    protected  static Double[] getEMAIndex(int term){
        return new Double[]{2D / (term + 1), (term - 1) * 1.0D / (term + 1)};
    }

}
