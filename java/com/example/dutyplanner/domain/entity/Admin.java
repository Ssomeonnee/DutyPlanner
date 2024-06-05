package com.example.dutyplanner.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin {

    private String name;
    private String login;
    private String password;
    private ArrayList<User> users;
    private HashMap<String, DutyPlan> dutyPlans;

    private ArrayList<Place> places;

    public Admin(String name, String login, String password)
    {
        this.name=name;
        this.login=login;
        this.password=password;

        users=new ArrayList<>();
        dutyPlans=new HashMap<>();
        places=new ArrayList<>();
    }

    public void setLogin(String login){this.login=login;}
    public void setPassword(String password){this.password=password;}
    public String getLogin(){return login;}
    public String getName(){return name;}
    public String getPassword(){return password;}
    public void addUser(User user){users.add(user);}
    public ArrayList<User> getUsers(){return users;}
    public void removeUser(User user){users.remove(user);}

    public void addDutyPlan(int month, int year, DutyPlan dutyPlan) {dutyPlans.put(month+"."+year, dutyPlan);}
    public HashMap<String, DutyPlan> getDutyPlans(){return dutyPlans;}
    public DutyPlan getDutyPlan(int month, int year){return dutyPlans.get(month+"."+year);}
    public DutyPlan getDutyPlan(String key){return dutyPlans.get(key);}
    public void removeDutyPlan(int month, int year, DutyPlan dutyPlan){dutyPlans.remove(month+"."+year);}

    public void addPlace(Place place) {places.add(place);}
    public void removePlace(Place place) {places.remove(place);}
    public ArrayList<Place> getPlaces() {return places;}

}
