package com.example.dutyplanner.presentation.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentDayUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentHourUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentYearUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetLengthOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.GetDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.place.GetPlaceByNameUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

import java.util.Calendar;
import java.util.TimeZone;

public class NotificationService extends Service {

    //глобальные переменные для класса
    private Admin admin;
    private int userIndex;
    private int month;
    private int year;
    private int day;

    //количество уведомлений, которое предстоит отправить
    private static int numberOfAlarms = 0;
    //уникальное число
    private static int counter = 0;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetAdminUseCase getAdminUseCase;
    private GetCurrentMonthUseCase getCurrentMonthUseCase;
    private GetCurrentYearUseCase getCurrentYearUseCase;
    private GetCurrentDayUseCase getCurrentDayUseCase;
    private GetCurrentHourUseCase getCurrentHourUseCase;
    private GetDutyPlanUseCase getDutyPlanUseCase;
    private GetPlaceByNameUseCase getPlaceUseCase;
    private GetLengthOfMonthUseCase getLengthOfMonthUseCase;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //usecase
        config = DefaultConfig.getInstance();
        getAdminUseCase = config.getAdmin();
        getCurrentMonthUseCase = config.getCurrentMonth();
        getCurrentYearUseCase = config.getCurrentYear();
        getCurrentDayUseCase = config.getCurrentDay();
        getCurrentHourUseCase = config.getCurrentHour();
        getDutyPlanUseCase = config.getDutyPlan();
        getPlaceUseCase = config.getPlaceByName();
        getLengthOfMonthUseCase = config.getLengthOfMonth();

        //получение данных
        admin = getAdminUseCase.invoke(intent.getIntExtra("AdminIndex", -1));
        userIndex = intent.getIntExtra("UserIndex", -1);

        month = getCurrentMonthUseCase.invoke();
        year = getCurrentYearUseCase.invoke();
        day = getCurrentDayUseCase.invoke();

        //назначение дат дежурств
        if (getDutyPlanUseCase.invoke(admin, month, year)!=null) {

            cancelAlarms();

            String[] duties = getDutyPlanUseCase.invoke(admin, month, year).getPlan()[userIndex];

            Log.d("Длина плана", duties.length + "");

            for (int i = 0; i < duties.length; i++) {
                if (!duties[i].isEmpty() && (i+1)>day) {

                    if (i==0 && month!=0)
                    {
                        setAlarm(year, month-1, getLengthOfMonthUseCase.invoke(month-1, year), 10, duties[i]);
                        setAlarm(year, month-1, getLengthOfMonthUseCase.invoke(month-1, year), 20, duties[i]);
                    }
                    else if (i==0 && month==0)
                    {
                        setAlarm(year-1, 11, getLengthOfMonthUseCase.invoke(11, year-1), 10, duties[i]);
                        setAlarm(year-1, 11, getLengthOfMonthUseCase.invoke(11, year-1), 20, duties[i]);
                    }
                    else
                    {
                        setAlarm(year, month, i, 10, duties[i]);
                        setAlarm(year, month, i, 20, duties[i]);
                    }

                    numberOfAlarms++;
                }


            }
        }
        return START_STICKY;
    }

    private void setAlarm(int year, int month, int day, int hour, String placeName)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Irkutsk"));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);

        Intent alarmIntent = new Intent(this, NotificationReceiver.class);
        alarmIntent.putExtra("PlaceName", getPlaceUseCase.invoke(admin, placeName).getDescription());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, generateUniqueInt(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static int generateUniqueInt() {
        return counter++;
    }

    private void cancelAlarms() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, NotificationReceiver.class);

        for (int i = 0; i < numberOfAlarms; i++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }

    protected static void decreaseNumberOfAlarms()
    {
        numberOfAlarms--;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
