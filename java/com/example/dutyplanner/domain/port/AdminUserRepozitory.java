package com.example.dutyplanner.domain.port;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdminUserRepozitory {
    public boolean checkLoginUniqueness(String login);
    public void addAdmin(Admin admin);
    public int getAdminIndex(Admin currentAdmin);
    public int getAdminForLogin(String login, String password);
    public ArrayList<Admin> getAdmins();
    public int getUserIndex(Admin admin, User user);
    public int[] getUserForLogin(String login, String password);
    public int getAdminForUser(User user) throws IndexOutOfBoundsException;
    public User getUser(Admin admin, int index) throws IndexOutOfBoundsException;
    public Admin getAdmin(int index);
    public void addUser(Admin admin, User user);
    public void updateUser(User user, String login, String password, String surname, String name, String middleName);
    public void removeUser(Admin admin, User user);
    public void addDutyPlan(Admin admin, int month, int year, DutyPlan dutyPlan);
    public void updateDutyPlan(DutyPlan dutyPlan, String[][] plan);
    public void removeDutyPlan(Admin admin, int month, int year, DutyPlan dutyPlan);
    public ArrayList<User> getUsers(Admin admin);
    public String[] getUsersInitials(Admin admin);
    public String[] getUsersFullNames(Admin admin);
    public DutyPlan getDutyPlan(Admin admin, int month, int year);
    public HashMap<String, DutyPlan> getDutyPlans(Admin admin);
    public ArrayList<Place> getPlaces(Admin admin);
    public Place getPlace(Admin admin, int index);
    public Place getPlace(Admin admin, String name);
    public void updatePlace(Admin admin, Place place, String name, String description);
    public void addPlace(Admin admin, Place place);
    public void removePlace(Admin admin, Place place);
    public String[] getDescriptions(Admin admin);

}
