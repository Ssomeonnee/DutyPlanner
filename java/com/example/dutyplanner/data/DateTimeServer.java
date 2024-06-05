package com.example.dutyplanner.data;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeServer implements DateTimeRepozitory {

    private static DateTimeServer instanse;
    private Calendar currentDate; //текущая дата

    private DateTimeServer()
    {
        currentDate = Calendar.getInstance();
        currentDate.setTimeZone(TimeZone.getTimeZone("Asia/Irkutsk"));
    }

    public static DateTimeServer getInstance() {
        if (instanse == null) {
            instanse = new DateTimeServer();
        }
        return instanse;
    }

    public int getCurrentDay() {return currentDate.get(Calendar.DAY_OF_MONTH);}
    public int getCurrentMonth() {return currentDate.get(Calendar.MONTH);}
    public int getCurrentYear() {return currentDate.get(Calendar.YEAR);}
    public int getCurrentHour() {return currentDate.get(Calendar.HOUR_OF_DAY);}
    public String getNameOfMonth(int month)
    {
        switch (month)
        {
            case 0:
                return "Январь";
            case 1:
                return "Февраль";
            case 2:
                return "Март";
            case 3:
                return "Апрель";
            case 4:
                return "Май";
            case 5:
                return "Июнь";
            case 6:
                return "Июль";
            case 7:
                return "Август";
            case 8:
                return "Сентябрь";
            case 9:
                return "Октябрь";
            case 10:
                return "Ноябрь";
            case 11:
                return "Декабрь";
        }
        return "";
    }

    public int[] getHolidays(int month, int year)
    {
        Calendar date = Calendar.getInstance();
        date.set(year, month, 1);

        int daysInMonth = getLengthOfMonth(month,year);
        int[] holidays = new int[daysInMonth];

        for (int day = 1; day <= daysInMonth; day++) {
            date.set(Calendar.DAY_OF_MONTH, day);
            int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY)
                holidays[day-1]=2;
            else if (dayOfWeek == Calendar.SUNDAY)
                holidays[day-1]=1;
            else
                holidays[day-1]=0;
        }

        return holidays;
    }

    public int getLengthOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,1);
        return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

}
