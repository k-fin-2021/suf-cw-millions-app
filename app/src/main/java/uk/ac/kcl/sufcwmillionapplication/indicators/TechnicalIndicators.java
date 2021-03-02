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

    protected static final String ERROR_TAG = "TechnicalIndicators";

    /**
     * Calculate the technical indicator
     * @param dailyQuoteList The list of daily quote
     * @return The result series which needed to be displayed in the chart.
     */
    public abstract List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList);

    /**
     * Get all the closing price in the daily quote series
     * @param dailyQuoteList The list of daily quote
     * @return A series of closing price
     */
    protected List<Double> extractAllClosingPrice(List<DailyQuote> dailyQuoteList){
        return extractLastClosingPrice(dailyQuoteList,-1);
    }

    /**
     * Get all the date in the daily quote series
     * @param dailyQuoteList The list of daily quote
     * @return A series of dates
     */
    protected List<String> extractAllDate(List<DailyQuote> dailyQuoteList){
        return extractLastDate(dailyQuoteList,-1);
    }

    /**
     * Get a sub sequence of the closing price in the daily quote series
     * @param dailyQuoteList The list of daily quote
     * @param lastNumber Last X items we need to reference
     * @return A sequence of closing prices of lastNumber elements in the list
     */
    protected List<Double> extractLastClosingPrice(List<DailyQuote> dailyQuoteList,int lastNumber){
        List<Double> result = new ArrayList<>();
        if(dailyQuoteList == null || dailyQuoteList.isEmpty()){
            return result;
        }

        int size = dailyQuoteList.size();
        double price = 0.0;

        if(lastNumber < 0 || lastNumber > size){
            lastNumber = dailyQuoteList.size();
        }
        for (int index = size - lastNumber; index < size ; index ++){
            price = dailyQuoteList.get(index).close;
            if(price < 0){
                Log.w(ERROR_TAG, "Closing price lower than 0: " + dailyQuoteList.get(index) );
            }
            result.add(price);
        }
        return result;
    }

    /**
     * Get a sub sequence of the date in the daily quote series
     * @param dailyQuoteList The list of daily quote
     * @param lastNumber Last X items we need to reference
     * @return A sequence of dates of lastNumber elements in the list
     */
    protected List<String> extractLastDate(List<DailyQuote> dailyQuoteList,int lastNumber){
        List<String> result = new ArrayList<>();
        if(dailyQuoteList == null || dailyQuoteList.isEmpty()){
            return result;
        }
        int size = dailyQuoteList.size();
        String date = "";
        if(lastNumber < 0 || lastNumber > size){
            lastNumber = dailyQuoteList.size();
        }
        for (int index = size - lastNumber; index < size ; index ++){
            date = dailyQuoteList.get(index).date;
            if(CommonUtils.isEmptyString(date)){
                date = "";
                Log.w(ERROR_TAG, "Date empty: " + dailyQuoteList.get(index) );
            }
            result.add(date);
        }
        return result;
    }

    /**
     * Generate the CalculateResult List
     * @param dates The sequence of dates
     * @param indicators The sequence of indicators
     * @return A list of CalculateResult
     */
    protected List<CalculateResult> generateResult(List<String> dates, List<Double> indicators){
        List<CalculateResult> list = new ArrayList<>();
        if(dates == null || indicators == null){
            return  list;
        }
        int sizeOfDates = dates.size();
        int sizeOfIndicators = indicators.size();
        if(sizeOfDates != sizeOfIndicators){
            Log.w(ERROR_TAG, "generateResult: size of dates and indicators not match.");
        }
        int finalSize = Math.min(sizeOfDates,sizeOfIndicators);
        for (int element = 0 ; element < finalSize; element ++ ){
            CalculateResult result = new CalculateResult();
            result.date = dates.get(element);
            result.data = indicators.get(element);
            list.add(result);
        }
        return list;
    }

    /**
     * get EMAIndex
     * @param term
     * @return
     */
    protected  static Double[] getEMAIndex(int term){
        return new Double[]{2D / (term + 1), (term - 1) * 1.0D / (term + 1)};
    }

}
