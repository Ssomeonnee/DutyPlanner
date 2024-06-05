package com.example.dutyplanner.data;

import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.Repozitory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

public class Server implements Repozitory {

    private ArrayList<User> usersList; //список пользователей
    private Calendar currentDate; //текущая дата
    private HashMap<String, String[][]> plans; //список графиков
    private ArrayList<Place> placeList; //список мест дежурств

    public Server()
    {
        usersList=new ArrayList<>();
        currentDate = Calendar.getInstance();
        currentDate.setTimeZone(TimeZone.getTimeZone("Asia/Irkutsk"));
        plans = new HashMap<>();
        placeList = new ArrayList<>();

        //создание списка пользователей
        usersList.add(new User("Юрий", "123","","",""));
        usersList.add(new User("Наталья", "123","","",""));
        usersList.add(new User("Виктор", "123","","",""));
        usersList.add(new User("Юлия", "123","","",""));
        usersList.add(new User("Дмитрий", "123","","",""));
        usersList.add(new User("Вениамин", "123","","",""));
        usersList.add(new User("Юрий", "123","","",""));
        usersList.add(new User("Наталья", "123","","",""));
        usersList.add(new User("Игорь", "123","","",""));
        usersList.add(new User("Виктор", "123","","",""));
        usersList.add(new User("Юлия", "123","","",""));
        usersList.add(new User("Дмитрий", "123","","",""));
        usersList.add(new User("Вениамин", "123","","",""));
        usersList.add(new User("Irog", "123","","",""));

        //создание списка мест дежурств
        placeList.add(new Place("М", "что-то"));
        placeList.add(new Place("", "что-то"));
        placeList.add(new Place("", "что-то"));
        placeList.add(new Place("", "что-то"));
        placeList.add(new Place("Г", "что-то"));
        placeList.add(new Place("Д", "что-то"));

        //создание плана на месяц
        Random random = new Random();
        String[][] duty = new String[getUsers().size()][getLengthOfMonth(getCurrentMonth())];

        for (int i=0; i<duty.length; i++)
        {
            for (int j=0; j<duty[0].length; j++)
            {
                duty[i][j]=placeList.get(random.nextInt(placeList.size())).getName();
            }
        }
        plans.put(getCurrentMonth()+"."+getCurrentYear(), duty);

    }

    public int getCurrentDay() {return currentDate.get(Calendar.DAY_OF_MONTH);}
    public int getCurrentMonth() {return currentDate.get(Calendar.MONTH);}
    public int getCurrentYear() {return currentDate.get(Calendar.YEAR);}
    public int getCurrentHour() {return currentDate.get(Calendar.HOUR_OF_DAY);}

    public int getUserIndex(String login, String password)
    {
       for (User user: usersList)
       {
           if (user.getLogin().equals(login) && user.getPassword().equals(password))
            return usersList.indexOf(user);
       }
       return -1;
    }
    public User getUser(int index) throws IndexOutOfBoundsException
    {
        return usersList.get(index);
    }
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

        int daysInMonth = getLengthOfMonth(month);
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

    public int getLengthOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getCurrentYear(),currentDate.get(Calendar.MONTH),1);
        return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }
    public ArrayList<User> getUsers(){
        return usersList;
    }

    public String[][] getDutyPlan(int month, int year)
    {
        return plans.get(month+"."+year);
    }

    public ArrayList<Place> getPlaces()
    {
        return placeList;
    }

    public String getTextAboutPlaces()
    {
        String text="";
        for (int i=0; i<placeList.size(); i++)
        {
            text+=placeList.get(i).getWholeDescription()+"\n";
        }
        return text;
    }

    public String[] getUsersNames()
    {
        String[] names = new String[usersList.size()];
        for (int i=0; i<usersList.size(); i++)
        {
            names[i]=usersList.get(i).getLogin();
        }
        return names;
    }


}
