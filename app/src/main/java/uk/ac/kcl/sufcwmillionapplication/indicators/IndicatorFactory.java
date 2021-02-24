package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.util.HashMap;
import java.util.Map;

import uk.ac.kcl.sufcwmillionapplication.utils.CommonUtils;

public class IndicatorFactory {

    private static IndicatorFactory INSTANCE = new IndicatorFactory();

    private Map<String, TechnicalIndicators> beanMap;

    private IndicatorFactory(){
        beanMap = new HashMap<>();
    }

    public static TechnicalIndicators get(String indicatorName){
        return INSTANCE.findOrInitBean(indicatorName);
    }

    public TechnicalIndicators findOrInitBean(String indicatorName){
        if(CommonUtils.isEmptyString(indicatorName)){
            return null;
        }
        TechnicalIndicators indicators = beanMap.get(indicatorName);
        if(indicators != null){
            return indicators;
        }
        if(IndicatorNames.EMA_9.equals(indicatorName)){
            //TODO: Add initial logic here
            //E.g.
            // indicators = new EMAIndicators();
            // beanMap.put(indicatorName,indicators);
        }else if (IndicatorNames.EMA_12.equals(indicatorName)){
            //TODO: Add initial logic here
        }else if(IndicatorNames.EMA_26.equals(indicatorName)){
            //TODO: Add initial logic here
        }else if(IndicatorNames.MACD_12_26.equals(indicatorName)){
            //TODO: Add initial logic here
            indicators = new MACDIndicators();
            beanMap.put(indicatorName, indicators);
        }else if(IndicatorNames.SMA_26.equals(indicatorName)){
            //TODO: Add initial logic here
        }else if (IndicatorNames.MACD_AVG.equals(indicatorName)){
            //TODO: Add initial logic here
        }else {
            throw new IllegalArgumentException("Indicator Strategy Not Exists");
        }
        return indicators;
    }

}
