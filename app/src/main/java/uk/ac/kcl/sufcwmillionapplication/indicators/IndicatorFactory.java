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
            indicators = new EMAIndicators(9);
            beanMap.put(IndicatorNames.EMA_9, indicators);
        }else if (IndicatorNames.EMA_12.equals(indicatorName)){
            indicators = new EMAIndicators(12);
            beanMap.put(IndicatorNames.EMA_12, indicators);
        }else if(IndicatorNames.EMA_26.equals(indicatorName)){
            indicators = new EMAIndicators(26);
            beanMap.put(IndicatorNames.EMA_26, indicators);
        }else if(IndicatorNames.MACD_12_26.equals(indicatorName)){
            indicators = new MACDIndicators();
            beanMap.put(IndicatorNames.MACD_12_26, indicators);
        }else if(IndicatorNames.SMA_26.equals(indicatorName)){
            indicators = new SMAIndicators();
            beanMap.put(IndicatorNames.SMA_26, indicators);
        }else if(IndicatorNames.SMA_20.equals(indicatorName)){
            indicators = new SMAIndicators();
            beanMap.put(IndicatorNames.SMA_20, indicators);
        }else if (IndicatorNames.MACD_AVG.equals(indicatorName)){
            indicators = new MACDAVGIndicators();
            beanMap.put(IndicatorNames.MACD_AVG, indicators);
        }else {
            throw new IllegalArgumentException("Indicator Strategy Not Exists");
        }
        return indicators;
    }

}
