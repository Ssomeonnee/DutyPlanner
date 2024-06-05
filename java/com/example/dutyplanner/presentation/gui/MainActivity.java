package com.example.dutyplanner.presentation.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dutyplanner.R;
import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.usecase.admin.GetAdminIndexUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentDayUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentHourUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentYearUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetHolidaysUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetLengthOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetNameOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.GetAllDutyPlansUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.GetDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAdminUsersInitialsUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAllAdminUsersUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserIndexUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;
import com.example.dutyplanner.presentation.notifications.NotificationService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //компоненты
    private TableLayout planTable;
    private TableLayout usersTable;
    private TableLayout datesTable;
    private HorizontalScrollView planScrollView;
    private HorizontalScrollView datesScrollView;
    private NestedScrollView  planAndUsersScroll;
    private TextView monthName;
    private TextView greetingText;
    private ImageView infoImage;
    private ImageView menuImage;
    private NavigationView navView;
    private DrawerLayout drawerLayoutMainActivity;
    private ImageView arrowRight;
    private ImageView arrowLeft;
    private View popupView;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetLengthOfMonthUseCase getLengthOfMonthUseCase;
    private GetCurrentDayUseCase getCurrentDayUseCase;
    private GetCurrentMonthUseCase getCurrentMonthUseCase;
    private GetCurrentYearUseCase getCurrentYearUseCase;
    private GetCurrentHourUseCase getCurrentHourUseCase;
    private GetHolidaysUseCase getHolidaysUseCase;
    private GetNameOfMonthUseCase getNameOfMonthUseCase;
    private GetAdminUseCase getAdminUseCase;
    private GetUserUseCase getUserUseCase;
    private GetUserIndexUseCase getUserIndexUseCase;
    private GetAdminUsersInitialsUseCase getAdminUsersInitialsUseCase;
    private GetDutyPlanUseCase getDutyPlanUseCase;
    private GetAllDutyPlansUseCase getAllDutyPlansUseCase;
    private GetAllAdminUsersUseCase getAllAdminUsersUseCase;
    private GetAdminIndexUseCase getAdminIndexUseCase;
    private GetAllPlacesUseCase getAllPlacesUseCase;

    //глобальные переменные для активити
    private String enterType;
    private Admin currentAdmin;
    private int userIndex;
    private DutyPlan dutyPlan;
    private int month;
    private int year;

    //хранение флага на отработку метода onResume
    private boolean isBack = false;

    //хранение выделенного пункта меню
    private MenuItem selectedItem;

    //хранение выделенной строки
    private TableRow selectedRow = null;

    //@SuppressLint("ClickableViewAccessibility")
   // @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //инициализируем компоненты
        datesScrollView = findViewById(R.id.datesScroll);
        planScrollView = findViewById(R.id.planScroll);
        monthName = findViewById(R.id.monthNameText);
        greetingText = findViewById(R.id.greetingText);
        datesTable = findViewById(R.id.datesTable);
        usersTable = findViewById(R.id.usersTable);
        planTable = findViewById(R.id.planTable);
        planAndUsersScroll = findViewById(R.id.planAndUsersScroll);
        drawerLayoutMainActivity = findViewById(R.id.drawerLayoutMainActivity);
        arrowRight = findViewById(R.id.arrowRight);
        arrowLeft = findViewById(R.id.arrowLeft);

        //создаю usecase
        config = DefaultConfig.getInstance();
        getCurrentDayUseCase = config.getCurrentDay();
        getCurrentHourUseCase = config.getCurrentHour();
        getCurrentMonthUseCase = config.getCurrentMonth();
        getCurrentYearUseCase = config.getCurrentYear();
        getLengthOfMonthUseCase = config.getLengthOfMonth();
        getHolidaysUseCase = config.getHoliday();
        getNameOfMonthUseCase = config.getNameOfMonth();
        getAdminUseCase = config.getAdmin();
        getUserUseCase = config.getUser();
        getUserIndexUseCase = config.getUserIndex();
        getAdminUsersInitialsUseCase = config.getAdminUsersInitials();
        getDutyPlanUseCase = config.getDutyPlan();
        getAdminIndexUseCase = config.getAdminIndex();
        getAllPlacesUseCase = config.getAllPlaces();
        getAllAdminUsersUseCase = config.getAllAdminUsers();
        getAllDutyPlansUseCase = config.getDutyPlans();

        //получаю тип входа
        enterType = getIntent().getStringExtra("EnterType");

        //заполняю основые объекты, которые несут информацию для заполения
        userIndex=-1; // для индекса пользователя (если его нет, останется -1)
        String name; //имя вошедшего пользователя

        if (enterType.equals("Admin"))
        {
           showMenuOnClick();
           currentAdmin = getAdminUseCase.invoke(getIntent().getIntExtra("AdminIndex", -1));
           name=currentAdmin.getName();
           chooseMenuItem();
        }
        else
        {
            //отключение выдвигающегося меню
            drawerLayoutMainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            currentAdmin = getAdminUseCase.invoke(getIntent().getIntExtra("AdminIndex", -1));
            User currentUser = getUserUseCase.invoke(currentAdmin, getIntent().getIntExtra("UserIndex", -1));
            userIndex=getUserIndexUseCase.invoke(currentAdmin,currentUser);
            name=currentUser.getName();

            //запуск отправки уведомлений
            Intent serviceIntent = new Intent(this, NotificationService.class);
            serviceIntent.putExtra("AdminIndex", getAdminIndexUseCase.invoke(currentAdmin));
            serviceIntent.putExtra("UserIndex", userIndex);
            startService(serviceIntent);
        }

        //получаю текущий месяц, год, график
        month = getCurrentMonthUseCase.invoke();
        year = getCurrentYearUseCase.invoke();
        dutyPlan = getDutyPlanUseCase.invoke(currentAdmin, month, year);

        // создаем и заполняем таблицы
        createUsersTable(getAdminUsersInitialsUseCase.invoke(currentAdmin), userIndex);////
        createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year), getCurrentDayUseCase.invoke());////////////////HOW
        createPlanTable(getDutyPlanUseCase.invoke(currentAdmin, month, year), getHolidaysUseCase.invoke(month, year), getCurrentDayUseCase.invoke(), userIndex);
        connectDatesAndPlanTable();
        scrollTablesToDay(getCurrentDayUseCase.invoke()-1);
        scrollTablesToUser(userIndex);

        arrowLeft.setVisibility(View.INVISIBLE);

        // заполняем текстовые надписи
        monthName.setText(getNameOfMonthUseCase.invoke(month)+" "+getCurrentYearUseCase.invoke());
        greetingText.setText(defineGreetingText(getCurrentHourUseCase.invoke(), name));

        // создание выпадающей надписи мест дежурств
        showPlacesOnInfoClick(getAllPlacesUseCase.invoke(currentAdmin));

        //обработка нажатий на кнопки
        pressArrowLeftMenuDP();
        pressArrowRightMenuDP();
        openMenu();
    }

    @Override
    protected void onResume() {

        if (isBack) {

            if (selectedItem != null) {
                selectedItem.setCheckable(false);
                selectedItem.setChecked(false);
            }

            while (planTable.getChildCount() > 0) {
                View view = planTable.getChildAt(0);
                if (view instanceof TableRow) {
                    planTable.removeView(view);
                }
            }
            while (usersTable.getChildCount() > 0) {
                View view = usersTable.getChildAt(0);
                if (view instanceof TableRow) {
                    usersTable.removeView(view);
                }
            }
            while (datesTable.getChildCount() > 0) {
                View view = datesTable.getChildAt(0);
                if (view instanceof TableRow) {
                    datesTable.removeView(view);
                }
            }

            month = getCurrentMonthUseCase.invoke();
            year = getCurrentYearUseCase.invoke();
            dutyPlan = getDutyPlanUseCase.invoke(currentAdmin, month, year);

            monthName.setText(getNameOfMonthUseCase.invoke(month) + " " + year);

            arrowLeft.setVisibility(View.INVISIBLE);

            createPlanTable(dutyPlan, getHolidaysUseCase.invoke(month, year), getCurrentDayUseCase.invoke(), userIndex);
            createUsersTable(getAdminUsersInitialsUseCase.invoke(currentAdmin), userIndex);
            createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year), getCurrentDayUseCase.invoke());
            connectDatesAndPlanTable();
            scrollTablesToDay(getCurrentDayUseCase.invoke() - 1);

            isBack=false;

        }
        super.onResume();
    }


    private void createDatesTable(int cols, int[] holidays, int day)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TableRow tableRow = new TableRow(this);

        for (int j = 1; j <= cols; j++) {
            View cellLayout = inflater.inflate(R.layout.dates_list_item, null);
            TextView cellTextView = cellLayout.findViewById(R.id.datesTableTextItem);

            cellTextView.setText(String.valueOf(j));

            if (j==day)
            {cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.green_border_style_table_item));}
            else if (holidays[j-1]==2)
            {cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.light_blue_border_style_table_item));}
            else if (holidays[j-1]==1)
            {cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.dark_blue_border_style_table_item));}

            tableRow.addView(cellLayout);
        }
        datesTable.addView(tableRow);
    }

    private void createUsersTable(String[] list, int userIndex) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (list.length == 0) {

            TableRow tableRow = new TableRow(this);
            View cellLayout = inflater.inflate(R.layout.users_list_item, null);
            TextView cellTextView = cellLayout.findViewById(R.id.usersTableTextItem);
            cellTextView.setText("\nНе добавлен \nни один \nпользователь\n");
            cellTextView.setTextColor(getColor(R.color.light_grey));
            tableRow.addView(cellLayout);
            usersTable.addView(tableRow);


        } else {

            for (int i = 0; i < list.length; i++) {

                TableRow tableRow = new TableRow(this);
                View cellLayout = inflater.inflate(R.layout.users_list_item, null);
                TextView cellTextView = cellLayout.findViewById(R.id.usersTableTextItem);
                cellTextView.setText(list[i]);

                if (i == userIndex) {
                    cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.green_border_style_table_item));
                }

                tableRow.addView(cellLayout);

                usersTable.addView(tableRow);

                tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (selectedRow != null) {
                            // Сбросьте выделение предыдущей выбранной ячейки
                            makePrimaryRow((TextView) selectedRow.getChildAt(0));

                            if (dutyPlan!=null) {
                                int rowIndex = ((TableLayout) usersTable).indexOfChild((TableRow) selectedRow);

                                TableRow row = (TableRow) planTable.getChildAt(rowIndex);
                                for (int j = 0; j < ((TableRow) planTable.getChildAt(0)).getChildCount(); j++) {
                                    if (((TextView) row.getChildAt(j)).getBackground().getConstantState().equals(getDrawable(R.drawable.transparent_blue_border_style_table_item).getConstantState()))
                                        makePrimaryRow((TextView) row.getChildAt(j));
                                }
                            }
                        }
                        // Выделите текущую ячейку

                        if (!(((TextView) tableRow.getChildAt(0)).getBackground().getConstantState().equals(getDrawable(R.drawable.green_border_style_table_item).getConstantState())))
                            makeSelectedRow((TextView) tableRow.getChildAt(0));

                        if (dutyPlan!=null) {
                            int rowIndex = ((TableLayout) usersTable).indexOfChild((TableRow) tableRow);

                            TableRow row = (TableRow) planTable.getChildAt(rowIndex);
                            for (int j = 0; j < ((TableRow) planTable.getChildAt(0)).getChildCount(); j++) {
                                if (((TextView) row.getChildAt(j)).getBackground().getConstantState().equals(getDrawable(R.drawable.white_border_style_table_item).getConstantState()))
                                    makeSelectedRow((TextView) row.getChildAt(j));
                            }
                        }
                        selectedRow = tableRow;

                        return true;
                    }
                });

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tableRow==selectedRow) {
                            makePrimaryRow((TextView) tableRow.getChildAt(0));

                            if (dutyPlan!=null) {
                                int rowIndex = ((TableLayout) usersTable).indexOfChild((TableRow) tableRow);

                                TableRow row = (TableRow) planTable.getChildAt(rowIndex);
                                for (int j = 0; j < ((TableRow) planTable.getChildAt(0)).getChildCount(); j++) {
                                    if (((TextView) row.getChildAt(j)).getBackground().getConstantState().equals(getDrawable(R.drawable.transparent_blue_border_style_table_item).getConstantState()))
                                        makePrimaryRow((TextView) row.getChildAt(j));
                                }
                            }
                        }
                    }
                });
            }

        }
    }

    private void makeSelectedRow(TextView cellTextView)
    {
        cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.transparent_blue_border_style_table_item));
    }

    private void makePrimaryRow(TextView cellTextView)
    {
        cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.white_border_style_table_item));
    }

    private void createPlanTable(DutyPlan plan, int[] holidays, int day, int userIndex) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (plan==null)
        {
            TableRow tableRow = new TableRow(this);
            View cellLayout = inflater.inflate(R.layout.users_list_item, null);
            TextView cellTextView = cellLayout.findViewById(R.id.usersTableTextItem);
            cellTextView.setText("\nГрафик на этот месяц \nне добавлен\n");
            cellTextView.setTextColor(getColor(R.color.light_grey));
            tableRow.addView(cellLayout);
            planTable.addView(tableRow);
        }
        else {


            for (int i = 0; i < plan.getRows(); i++) {
                TableRow tableRow = new TableRow(this);

                for (int j = 0; j < plan.getCols(); j++) {
                    View cellLayout = inflater.inflate(R.layout.table_item, null);
                    TextView cellTextView = cellLayout.findViewById(R.id.tableTextItem);

                    cellTextView.setText(plan.getPlan()[i][j]);

                    if (j == day - 1) {
                        cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.green_border_style_table_item));
                    } else if (holidays[j] == 2) {
                        cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.light_blue_border_style_table_item));
                    } else if (holidays[j] == 1) {
                        cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.dark_blue_border_style_table_item));
                    }

                    if (i == userIndex) {
                        cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.green_border_style_table_item));
                    }

                    tableRow.addView(cellLayout);
                }

                planTable.addView(tableRow);
            }
        }
    }

    private void pressArrowRightMenuDP()
    {
        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrowLeft.setVisibility(View.VISIBLE);

                if (month<11)
                    month++;
                else
                {
                    month=0;
                    year++;
                }

                while (planTable.getChildCount() > 0) {
                    View view = planTable.getChildAt(0);
                    if (view instanceof TableRow) {
                        planTable.removeView(view);
                    }
                }
                while (datesTable.getChildCount() > 0) {
                    View view = datesTable.getChildAt(0);
                    if (view instanceof TableRow) {
                        datesTable.removeView(view);
                    }
                }
                if (selectedRow!=null) {
                    makePrimaryRow((TextView) selectedRow.getChildAt(0));
                    selectedRow = null;
                }

                dutyPlan = getDutyPlanUseCase.invoke(currentAdmin, month, year);
                monthName.setText(getNameOfMonthUseCase.invoke(month)+" "+year);

                createPlanTable(dutyPlan, getHolidaysUseCase.invoke(month, year), -1, userIndex);
                createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year), -1);
                scrollTablesToUser(userIndex);
            }
        });
    }

    private void pressArrowLeftMenuDP()
    {
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((month>getCurrentMonthUseCase.invoke() && year==getCurrentYearUseCase.invoke()) || year>getCurrentYearUseCase.invoke())
                {

                    if ((month-1)==getCurrentMonthUseCase.invoke() && year==getCurrentYearUseCase.invoke())
                        arrowLeft.setVisibility(View.INVISIBLE);

                    if (month > 0)
                        month--;
                    else {
                        month = 11;
                        year--;
                    }

                    while (planTable.getChildCount() > 0) {
                        View view = planTable.getChildAt(0);
                        if (view instanceof TableRow) {
                            planTable.removeView(view);
                        }
                    }
                    while (datesTable.getChildCount() > 0) {
                        View view = datesTable.getChildAt(0);
                        if (view instanceof TableRow) {
                            datesTable.removeView(view);
                        }
                    }

                    if (selectedRow!=null) {
                        makePrimaryRow((TextView) selectedRow.getChildAt(0));
                        selectedRow = null;
                    }

                    dutyPlan = getDutyPlanUseCase.invoke(currentAdmin, month, year);
                    monthName.setText(getNameOfMonthUseCase.invoke(month) + " " + year);

                    int day;
                    if (month==getCurrentMonthUseCase.invoke() && year==getCurrentYearUseCase.invoke())
                        day=getCurrentDayUseCase.invoke();
                    else
                        day=-1;

                    createPlanTable(dutyPlan, getHolidaysUseCase.invoke(month, year), day, userIndex);
                    createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year), day);
                    scrollTablesToDay(day-1);
                    scrollTablesToUser(userIndex);
                }
            }
        });
    }

    private void connectDatesAndPlanTable()
    {
        datesScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                planScrollView.scrollTo(scrollX, scrollY);
            }
        });
        planScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                datesScrollView.scrollTo(scrollX, scrollY);
            }
        });
    }

    private String defineGreetingText(int hour, String name)
    {
        if (hour>=5 && hour<12)
            return "Доброе утро, "+name;
        else if (hour>=12 && hour<19)
            return "Добрый день, "+name;
        else if (hour>=19 && hour<23)
            return "Добрый вечер, "+name;
        else
            return "Доброй ночи, "+name;

    }

    private void scrollTablesToDay(int columnToScrollTo) {
        TableRow row = (TableRow) datesTable.getChildAt(0);
        View targetColumn = row.getChildAt(columnToScrollTo);
        if (targetColumn != null) {
            datesScrollView.post(() -> {
                datesScrollView.smoothScrollTo(targetColumn.getLeft(), 0);
            });
        }
    }

    private void scrollTablesToUser(int rowToScrollTo) {
        TableRow targetRow = (TableRow) planTable.getChildAt(rowToScrollTo);
        if (targetRow != null) {
            planAndUsersScroll.post(() -> {
                planAndUsersScroll.smoothScrollTo(0, targetRow.getTop());
            });
        }
    }

    private void showPlacesOnInfoClick(ArrayList<Place> places) {

        infoImage = findViewById(R.id.infoImage);
        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(MainActivity.this);

                popupView = getLayoutInflater().inflate(R.layout.places_list, null);
                TextView popupText = popupView.findViewById(R.id.placesNames);


                String text="";

                if (places.isEmpty())
                {
                    popupText.setTextColor(getResources().getColor(R.color.light_grey));
                    text="Места еще \nне добавлены";
                }
                else {
                    for (Place place : places)
                        if (places.indexOf(place) == places.size() - 1)
                            text += place.getWholeDescription();
                        else
                            text += place.getWholeDescription()+"\n";
                }
                popupText.setText(text);

                popupWindow.setContentView(popupView);
                popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(v, 0, 35);

                View backgroundView = new View(MainActivity.this);
                backgroundView.setBackgroundColor(Color.parseColor("#40000000")); // полупрозрачный черный цвет
                backgroundView.setClickable(true);

                ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
                root.addView(backgroundView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                backgroundView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            root.removeView(backgroundView);
                        }
                    }
                });

            }}
            );
    }


    private void showMenuOnClick() {


        menuImage = findViewById(R.id.menuImage);
        menuImage.setVisibility(View.VISIBLE);

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayoutMainActivity.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutMainActivity.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayoutMainActivity.openDrawer(GravityCompat.START);
                }

            }}
        );
    }

    private void chooseMenuItem()
    {
        navView = drawerLayoutMainActivity.findViewById(R.id.navView);
        navView.bringToFront();
        navView.setItemTextAppearance(R.style.MenuItemText);

        navView.setDividerInsetEnd(40);

        navView.setItemBackgroundResource(R.drawable.menu_item_selector); // Устанавливаем селектор для фона пунктов меню

        navView.setNavigationItemSelectedListener(menuItem -> {
            // Обработка нажатия на пункт меню

            if (menuItem.getItemId()==R.id.nav_places)
            {
                Intent intent = new Intent(MainActivity.this, ListOfUsersAndPlacesActivity.class);

                intent.putExtra("Data", "places");
                intent.putExtra("AdminIndex", getAdminIndexUseCase.invoke(currentAdmin));

                selectedItem = menuItem;
                startActivity(intent);

                drawerLayoutMainActivity.closeDrawers();

                isBack=true;
                return true;
            }
            else if (menuItem.getItemId()==R.id.nav_users)
            {
                Intent intent = new Intent(MainActivity.this, ListOfUsersAndPlacesActivity.class);

                intent.putExtra("Data", "users");
                intent.putExtra("AdminIndex", getAdminIndexUseCase.invoke(currentAdmin));

                selectedItem = menuItem;
                startActivity(intent);

                drawerLayoutMainActivity.closeDrawers();

                isBack=true;
                return true;
            }
            else if (menuItem.getItemId()==R.id.nav_plans)
            {
                Intent intent = new Intent(MainActivity.this, ListOfDutyPlansActivity.class);

                intent.putExtra("AdminIndex", getAdminIndexUseCase.invoke(currentAdmin));

                selectedItem = menuItem;
                startActivity(intent);

                drawerLayoutMainActivity.closeDrawers();

                isBack=true;
                return true;
            }
            return false;
        });
    }

    public void openMenu()
    {
        drawerLayoutMainActivity.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                Menu menu = navView.getMenu();
                MenuItem plansMenuItem = menu.findItem(R.id.nav_plans);

                if (!getAllAdminUsersUseCase.invoke(currentAdmin).isEmpty() && !getAllPlacesUseCase.invoke(currentAdmin).isEmpty()) {
                    plansMenuItem.setEnabled(true);
                }
                else
                    plansMenuItem.setEnabled(false);

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {


            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

}
