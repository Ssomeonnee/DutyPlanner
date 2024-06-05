package com.example.dutyplanner.domain.port;

import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.entity.User;

import java.util.ArrayList;

public interface Repozitory {

    public int getCurrentDay();
    public int getCurrentMonth();
    public int getCurrentYear();
    public int getCurrentHour();
    public int getUserIndex(String login, String password);
    public User getUser(int index);
    public String getNameOfMonth(int month);
    public int[] getHolidays(int month, int year);
    public int getLengthOfMonth(int month);
    public ArrayList<User> getUsers();
    public String[][] getDutyPlan(int month, int year);
    public ArrayList<Place> getPlaces();
    public String getTextAboutPlaces();
    public String[] getUsersNames();
}
