package com.example.dutyplanner.data;

import android.util.Log;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminUserServer implements AdminUserRepozitory {

    private static AdminUserServer instanse;
    private ArrayList<Admin> adminsList;
    // private HashMap<Admin, ArrayList<User>> usersList;
    private String TAG = "Admin";


    private AdminUserServer()
    {
        adminsList = new ArrayList<>();


        adminsList.add(new Admin("Виталий","Виталий", "123"));


        adminsList.get(0).addUser(new User("Юрий", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Наталья", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Виктор", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Юлия", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Дмитрий", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Вениамин", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Юрий", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Наталья", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Игорь", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Виктор", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Юлия1", "123","Юрий","Юрий","Юрий"));
        adminsList.get(0).addUser(new User("Дмитрий", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Вениамин", "123","Морозов","А","А"));
        adminsList.get(0).addUser(new User("Irog", "123","Морозов","А","А"));


        DateTimeServer dateTimeServer = DateTimeServer.getInstance();
        adminsList.get(0).addDutyPlan(dateTimeServer.getCurrentMonth(), dateTimeServer.getCurrentYear(), new DutyPlan(adminsList.get(0).getUsers().size(), dateTimeServer.getLengthOfMonth(dateTimeServer.getCurrentMonth(), dateTimeServer.getCurrentYear())));

        adminsList.get(0).addPlace(new Place("M", "что-то"));


        adminsList.add(new Admin("Виталий1","Виталий1", "123"));
        adminsList.get(1).addUser(new User("Юрий123", "123","Сергеев","Юрий","Александрович"));
        adminsList.get(1).addUser(new User("Наталья123", "123","Морозова","Наталья","Андреевна"));
        adminsList.get(1).addUser(new User("Виктор123", "123","Калимулинbhjvjvj","Виктор","Афанасьевич"));
        adminsList.get(1).addPlace(new Place("M", "что-то"));
    }

    public static AdminUserServer getInstance() {
        if (instanse == null) {
            instanse = new AdminUserServer();
        }
        return instanse;
    }

    public boolean checkLoginUniqueness(String login)
    {
        for (Admin admin : adminsList) {

            if (admin.getLogin().equals(login))
            {
                return false;
            }

            ArrayList<User> users = admin.getUsers();

            for (User currentUser : users) {
                if (currentUser.getLogin().equals(login))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void addAdmin(Admin admin)
    {
        adminsList.add(admin);
        //Log.i(TAG, adminsList.size()+"");
    }
    public void addUser(Admin admin, User user)
    {
        for (String key: admin.getDutyPlans().keySet())
        {
            DutyPlan dutyPlan = admin.getDutyPlans().get(key);
            dutyPlan.addRow();
        }
        adminsList.get(adminsList.indexOf(admin)).addUser(user);
    }
   /* public void addPlace(Admin admin, Place place)
    {
        adminsList.get(adminsList.indexOf(admin)).addPlace(place);
    }*/
    public void updateUser(User user, String login, String password, String surname, String name, String middleName)
    {
        if (!user.getSurname().equals(surname))
            user.setSurname(surname);
        if (!user.getName().equals(name))
            user.setName(name);
        if (!user.getMiddleName().equals(middleName))
            user.setMiddleName(middleName);
        if (!user.getLogin().equals(login))
            user.setLogin(login);
        if (!user.getPassword().equals(password))
            user.setPassword(password);
    }
    public void removeUser(Admin admin, User user)
    {
        for (String key: admin.getDutyPlans().keySet())
        {
            DutyPlan dutyPlan = admin.getDutyPlans().get(key);
            dutyPlan.removeRow(getUserIndex(admin, user));
        }
        adminsList.get(adminsList.indexOf(admin)).removeUser(user);
    }
    public void removeUser(Admin admin, User user, int month, int year)
    {
        for (Map.Entry<String, DutyPlan> entry : admin.getDutyPlans().entrySet()) {
            String key = entry.getKey();
            if (key.compareTo(month+"."+year)>=0)
            {
                admin.getDutyPlan(key).removeRow(getUserIndex(admin,user));
            }
        }
        adminsList.get(adminsList.indexOf(admin)).removeUser(user);
    }

    public void addDutyPlan(Admin admin, int month, int year, DutyPlan dutyPlan)
    {
        adminsList.get(adminsList.indexOf(admin)).addDutyPlan(month, year, dutyPlan);
    }
    public void updateDutyPlan(DutyPlan dutyPlan, String[][] plan)
    {
        dutyPlan.updatePlan(plan);
    }
    public void removeDutyPlan(Admin admin, int month, int year, DutyPlan dutyPlan)
    {
        adminsList.get(adminsList.indexOf(admin)).removeDutyPlan(month, year, dutyPlan);
    }

    public void addPlace(Admin admin, Place place)
    {
        adminsList.get(adminsList.indexOf(admin)).addPlace(place);
    }
    public void updatePlace(Admin admin, Place place, String name, String description)
    {
        if (!place.getDescription().equals(description))
            place.setDescription(description);
        if (!place.getName().equals(name)) {

            String[][] newPlan;

            for (String key: admin.getDutyPlans().keySet()) {
                DutyPlan dutyPlan = admin.getDutyPlans().get(key);

                newPlan = dutyPlan.getPlan();

                for (int i = 0; i < newPlan.length; i++) {
                    for (int j = 0; j < newPlan[0].length; j++) {
                        if (newPlan[i][j].equals(place.getName()))
                            newPlan[i][j] = name;
                    }
                }
            }
            place.setName(name);
        }
    }
    public void removePlace(Admin admin, Place place)
    {
        String[][] newPlan;

        for (String key: admin.getDutyPlans().keySet())
        {
            DutyPlan dutyPlan = admin.getDutyPlans().get(key);

            newPlan = dutyPlan.getPlan();

            for (int i=0; i< newPlan.length; i++)
            {
                for (int j=0; j< newPlan[0].length; j++)
                {
                    if (newPlan[i][j].equals(place.getName()))
                        newPlan[i][j]=null;
                }
            }
            dutyPlan.updatePlan(newPlan);
        }

        adminsList.get(adminsList.indexOf(admin)).removePlace(place);
    }
    public int getAdminIndex(Admin currentAdmin)
    {
        return adminsList.indexOf(currentAdmin);
    }
    public int getAdminForLogin(String login, String password) throws IndexOutOfBoundsException
    {
        for (Admin admin: adminsList)
        {
            if (admin.getLogin().equals(login) && admin.getPassword().equals(password))
                return adminsList.indexOf(admin);
        }
        throw new IndexOutOfBoundsException();
    }
    public ArrayList<Admin> getAdmins(){return adminsList;}

    public int getUserIndex(Admin admin, User user)
    {
        return adminsList.get(adminsList.indexOf(admin)).getUsers().indexOf(user);
        //return adminsList.indexOf(admin);
    }
    public int[] getUserForLogin(String login, String password) throws IndexOutOfBoundsException
    {
        int[] indexes = new int[2];
        for (Admin admin : adminsList) {

            ArrayList<User> users = admin.getUsers();

            for (User currentUser : users) {
                if (currentUser.getLogin().equals(login) && currentUser.getPassword().equals(password))
                {
                    indexes[0]=users.indexOf(currentUser);
                    indexes[1]=adminsList.indexOf(admin);
                    return indexes;
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public int getAdminForUser(User user) throws IndexOutOfBoundsException////
    {
        for (Admin admin : adminsList) {

            ArrayList<User> users = admin.getUsers();

            for (User currentUser : users) {
                if (currentUser.equals(user))
                    return adminsList.indexOf(admin);
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public User getUser(Admin admin, int index) throws IndexOutOfBoundsException
    {
        return adminsList.get(adminsList.indexOf(admin)).getUsers().get(index);
    }

    public Admin getAdmin(int index)
    {
        Log.i(TAG, adminsList.size()+"");
        return adminsList.get(index);
    }

    public ArrayList<User> getUsers(Admin admin){

        return adminsList.get(adminsList.indexOf(admin)).getUsers();
    }

    public String[] getUsersInitials(Admin admin)
    {
        String[] names = new String[getUsers(admin).size()];
        for (int i=0; i<getUsers(admin).size(); i++)
        {
            names[i]=getUsers(admin).get(i).getInitials();
        }
        return names;
    }
    public String[] getUsersFullNames(Admin admin)
    {
        String[] names = new String[getUsers(admin).size()];
        for (int i=0; i<getUsers(admin).size(); i++)
        {
            names[i]=getUsers(admin).get(i).getFullName();
        }
        return names;
    }
    public DutyPlan getDutyPlan(Admin admin, int month, int year)
    { return adminsList.get(adminsList.indexOf(admin)).getDutyPlan(month, year);}

    public HashMap<String, DutyPlan> getDutyPlans(Admin admin)
    { return adminsList.get(adminsList.indexOf(admin)).getDutyPlans();}

    public ArrayList<Place> getPlaces(Admin admin)
    {return adminsList.get(adminsList.indexOf(admin)).getPlaces();}

    public Place getPlace(Admin admin, int index) {
        return adminsList.get(adminsList.indexOf(admin)).getPlaces().get(index);
    }
    public Place getPlace(Admin admin, String name) {

        for (Place place: adminsList.get(adminsList.indexOf(admin)).getPlaces())
        {
            if (place.getName().equals(name))
                return place;
        }
        return null;
    }

    public String[] getDescriptions(Admin admin)
    {
        String[] places = new String[getPlaces(admin).size()];
        for (int i=0; i<getPlaces(admin).size(); i++)
        {
            places[i]=getPlaces(admin).get(i).getDescription();
        }
        return places;
    }
}
