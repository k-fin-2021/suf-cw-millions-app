package uk.ac.kcl.sufcwmillionapplication.bean;

import java.util.Objects;

import uk.ac.kcl.sufcwmillionapplication.utils.CommonUtils;

public class CalculateResult {
    public String date;
    public double data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculateResult that = (CalculateResult) o;
        return Double.compare(that.data, data) == 0 &&
                CommonUtils.isStringEquals(date, that.date);
    }

    @Override
    public String toString() {
        return "CalculateResult{" +
                "date='" + date + '\'' +
                ", data=" + data +
                '}';
    }
}
