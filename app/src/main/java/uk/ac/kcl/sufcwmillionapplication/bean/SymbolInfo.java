package uk.ac.kcl.sufcwmillionapplication.bean;

import java.io.Serializable;

public class SymbolInfo implements Serializable {

    private String symbol;
    private String shortName;
    private String longName;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Override
    public String toString() {
        return "SymbolInfo{" +
                "symbol='" + symbol + '\'' +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                '}';
    }
}
