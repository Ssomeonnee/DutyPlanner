package com.example.dutyplanner.domain.port;

import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.entity.User;

import java.util.ArrayList;

public interface DateTimeRepozitory {
    public int getCurrentDay();
    public int getCurrentMonth();
    public int getCurrentYear();
    public int getCurrentHour();
    public String getNameOfMonth(int month);
    public int[] getHolidays(int month, int year);
    public int getLengthOfMonth(int month, int year);
}
