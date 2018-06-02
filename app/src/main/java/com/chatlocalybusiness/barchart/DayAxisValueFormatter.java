package com.chatlocalybusiness.barchart;

import com.chatlocalybusiness.activity.PaymentStatsActivity;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter
{
  int i=0;
  String monthNAme="";
    protected String[] mMonths = new String[]{
            " ","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        i++;
              int days = (int) value;

        int year = determineYear(days);

        int month = determineMonth(days);
        String monthName = mMonths[month % mMonths.length];
        String yearName = String.valueOf(year);
        int range= (int) chart.getVisibleXRange();

/*
        if ( range> 30*6) {

            return monthName + " " + yearName;
        } else {

*/
       if(PaymentStatsActivity.salesby.equalsIgnoreCase("Earnings by month")){

            int dayOfMonth = determineDayOfMonth(days, month + 12 * (year - 2016));

            String appendix = "th";

            switch (dayOfMonth) {
                case 1:
                    appendix = "Jan";
                    break;
                case 2:
                    appendix = "Fab";
                    break;
                case 3:
                    appendix = "Mar";
                    break;
                case 4:
                    appendix = "Apr";
                    break;
                case 5:
                    appendix = "May";
                    break;
                case 6:
                    appendix = "Jun";
                    break;
                case 7:
                    appendix = "Jul";
                    break;
                case 8:
                    appendix = "Aug";
                    break;
                case 9:
                    appendix = "Sep";
                    break;
                case 10:
                    appendix = "Oct";
                    break;
                case 11:
                    appendix = "Nov";
                    break;
                case 12:
                    appendix = "Dec";
                    break;

            }
//            return dayOfMonth == 0 ? "" : dayOfMonth + appendix + " " + monthName;
            return dayOfMonth == 0 ? "" : appendix;
        }
        else{
           int dayOfMonth = determineDayOfMonth(days, month + 12 * (year - 2016));

           String appendix = "th";

           switch (dayOfMonth) {
               case 1:
                   appendix = "Q1";
                   break;
               case 2:
                   appendix = "Q2";
                   break;
               case 3:
                   appendix = "Q3";
                   break;
               case 4:
                   appendix = "Q4";
                   break;
           }
           return dayOfMonth == 0 ? "" : appendix;
       }

    }

/*if(i<13)
    monthNAme=mMonths[i];
    else
        monthNAme="";

        return monthNAme;
    }*/

    private int getDaysForMonth(int month, int year) {

        // month is 0-based

        if (month == 1) {
            boolean is29Feb = false;

            if (year < 1582)
                is29Feb = (year < 1 ? year + 1 : year) % 4 == 0;
            else if (year > 1582)
                is29Feb = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);

            return is29Feb ? 29 : 28;
        }

        if (month == 3 || month == 5 || month == 8 || month == 10)
            return 30;
        else
            return 31;
    }
    private int determineMonth(int dayOfYear) {

        int month = -1;
        int days = 0;

        while (days < dayOfYear) {
            month = month + 1;

            if (month >= 12)
                month = 0;

            int year = determineYear(days);
            days += getDaysForMonth(month, year);
        }

        return Math.max(month, 0);
    }
    private int determineDayOfMonth(int days, int month) {

        int count = 0;
        int daysForMonths = 0;

        while (count < month) {

            int year = determineYear(daysForMonths);
            daysForMonths += getDaysForMonth(count % 12, year);
            count++;
        }
        return days - daysForMonths;
    }
    private int determineYear(int days) {

        if (days <= 366)
            return 2016;
        else if (days <= 730)
            return 2017;
        else if (days <= 1094)
            return 2018;
        else if (days <= 1458)
            return 2019;
        else
            return 2020;

    }
}
