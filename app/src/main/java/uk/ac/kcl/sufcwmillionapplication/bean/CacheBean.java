package uk.ac.kcl.sufcwmillionapplication.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CacheBean implements Serializable {
    String name;
    Date startDate;
    Date endDate;
    List<DailyQuote> results;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<DailyQuote> getResults() {
        return results;
    }

    public void setResults(List<DailyQuote> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CacheBean{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
