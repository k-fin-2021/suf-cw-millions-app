package uk.ac.kcl.sufcwmillionapplication.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class FindQuoteBean {

    private String date = "";
    private List errors = new ArrayList();

    public FindQuoteBean(Context _c) {

    }

    public void setdate(String datex) {
        date = datex;
    }

    public void resetData() {
        date = "";
    }

    public boolean isfindQuoteerror() {
        errors.clear();
        return errors.size() > 0;
    }

    public String errors() {
        return errors.toString();
    }


}

