package com.example.dutyplanner.presentation.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dutyplanner.R;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.usecase.admin.GetAdminIndexUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentDayUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentHourUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentYearUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetHolidaysUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetLengthOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetNameOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.AddDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.GetDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.UpdateDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAdminUsersInitialsUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

import java.util.ArrayList;

public class ListOfDutyPlansActivity extends AppCompatActivity {

    // глобальные переменные для активити
    private Admin admin;
    private DutyPlan dutyPlan;
    private ArrayList<Place> placesList;
    private int month;
    private int year;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetAdminIndexUseCase getAdminIndexUseCase;
    private GetAdminUseCase getAdminUseCase;
    private GetAdminUsersInitialsUseCase getAdminUsersInitialsUseCase;
    private GetDutyPlanUseCase getDutyPlanUseCase;
    private GetLengthOfMonthUseCase getLengthOfMonthUseCase;
    private GetCurrentDayUseCase getCurrentDayUseCase;
    private GetCurrentMonthUseCase getCurrentMonthUseCase;
    private GetCurrentYearUseCase getCurrentYearUseCase;
    private GetCurrentHourUseCase getCurrentHourUseCase;
    private GetHolidaysUseCase getHolidaysUseCase;
    private GetNameOfMonthUseCase getNameOfMonthUseCase;
    private GetAllPlacesUseCase getAllPlacesUseCase;
    private UpdateDutyPlanUseCase updateDutyPlanUseCase;
    private AddDutyPlanUseCase addDutyPlanUseCase;

    //компоненты
    private ImageView exitToMenuDPButton;
    private ImageView arrowLeftMenuDP;
    private ImageView arrowRightMenuDP;
    private TextView monthNameTextMenuDP;
    private TableLayout datesTableMenuDP;
    private TableLayout usersTableMenuDP;
    private TableLayout planTableMenuDP;
    private Button saveMenuDPButton;
    private HorizontalScrollView planScrollMenuDP;
    private HorizontalScrollView datesScrollMenuDP;
    private CardView cancelDPButton;
    private ConstraintLayout backgroundDP;

    //хранение выделенной ячейки
    private View selectedCell = null;
    //хранение количества неверно заполненных ячеек
    private int numberOfErrorCells = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_duty_plans);

        //usecase
        config = DefaultConfig.getInstance();
        getCurrentDayUseCase = config.getCurrentDay();
        getCurrentHourUseCase = config.getCurrentHour();
        getCurrentMonthUseCase = config.getCurrentMonth();
        getCurrentYearUseCase = config.getCurrentYear();
        getLengthOfMonthUseCase = config.getLengthOfMonth();
        getHolidaysUseCase = config.getHoliday();
        getNameOfMonthUseCase = config.getNameOfMonth();
        getAdminUseCase = config.getAdmin();
        getAdminUsersInitialsUseCase = config.getAdminUsersInitials();
        getDutyPlanUseCase = config.getDutyPlan();
        getAdminIndexUseCase = config.getAdminIndex();
        getAllPlacesUseCase = config.getAllPlaces();
        updateDutyPlanUseCase = config.updateDutyPlan();
        addDutyPlanUseCase = config.addDutyPlan();

        //получаю админа
        admin = getAdminUseCase.invoke(getIntent().getIntExtra("AdminIndex",-1));

        //получаю план на текущий месяц
        month = getCurrentMonthUseCase.invoke();
        year = getCurrentYearUseCase.invoke();
        dutyPlan = getDutyPlanUseCase.invoke(admin, month, year);

        //получаю список мест
        placesList = getAllPlacesUseCase.invoke(admin);

        //компоненты
        exitToMenuDPButton = findViewById(R.id.exitToMenuDPButton);
        monthNameTextMenuDP = findViewById(R.id.monthNameTextMenuDP);
        datesTableMenuDP = findViewById(R.id.datesTableMenuDP);
        usersTableMenuDP = findViewById(R.id.usersTableMenuDP);
        planTableMenuDP = findViewById(R.id.planTableMenuDP);
        saveMenuDPButton = findViewById(R.id.saveMenuDPButton);
        datesScrollMenuDP = findViewById(R.id.datesScrollMenuDP);
        planScrollMenuDP = findViewById(R.id.planScrollMenuDP);
        cancelDPButton = findViewById(R.id.cancelDPButton);
        backgroundDP = findViewById(R.id.backgroundDP);
        arrowLeftMenuDP = findViewById(R.id.arrowLeftMenuDP);
        arrowRightMenuDP = findViewById(R.id.arrowRightMenuDP);

        //их видимость
        arrowLeftMenuDP.setVisibility(View.INVISIBLE);

        //установление название месяца и года в графике
        monthNameTextMenuDP.setText(getNameOfMonthUseCase.invoke(getCurrentMonthUseCase.invoke())+" "+getCurrentYearUseCase.invoke());

        //создание таблиц
        createUsersTable(getAdminUsersInitialsUseCase.invoke(admin));////
        createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year));
        createPlanTable(getLengthOfMonthUseCase.invoke(month, year), getAdminUsersInitialsUseCase.invoke(admin).length, dutyPlan, getHolidaysUseCase.invoke(month, year));
        connectDatesAndPlanTable();

        //обработка нажатия кнопок
        backToMainWindowPress();
        saveDutyPlan();
        cancelEditing();
        pressArrowRightMenuDP();
        pressArrowLeftMenuDP();

    }

    private void backToMainWindowPress()
    {
        exitToMenuDPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createDatesTable(int cols, int[] holidays)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TableRow tableRow = new TableRow(this);

        for (int j = 1; j <= cols; j++) {
            View cellLayout = inflater.inflate(R.layout.dates_list_item, null);
            TextView cellTextView = cellLayout.findViewById(R.id.datesTableTextItem);

            cellTextView.setText(String.valueOf(j));

            cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.white_border_style_table_item));

            if (holidays[j-1] == 2) {
                cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.light_blue_border_style_table_item));

            } else if (holidays[j-1] == 1) {
                cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.dark_blue_border_style_table_item));
            }

            tableRow.addView(cellLayout);
        }
        datesTableMenuDP.addView(tableRow);
    }


    private void createUsersTable(String[] list) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (list.length == 0) {

            CardView card = findViewById(R.id.usersTableCardViewMenuDP);

            View cellLayout = inflater.inflate(R.layout.users_list_item, null);
            TextView cellTextView = cellLayout.findViewById(R.id.usersTableTextItem);
            cellTextView.setText("\nНе добавлен \nни один \nпользователь\n");
            cellTextView.setTextColor(Color.parseColor("#626262"));

            card.addView(cellLayout);


        } else {

            for (int i = 0; i < list.length; i++) {
                TableRow tableRow = new TableRow(this);
                View cellLayout = inflater.inflate(R.layout.users_list_item, null);
                TextView cellTextView = cellLayout.findViewById(R.id.usersTableTextItem);
                cellTextView.setText(list[i]);

                cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.white_border_style_table_item));


                tableRow.addView(cellLayout);
                usersTableMenuDP.addView(tableRow);
            }
        }
    }
    private void createPlanTable(int cols, int rows, DutyPlan plan, int[] holidays) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < rows; i++) {
            TableRow tableRow = new TableRow(this);

            for (int j = 0; j < cols; j++) {
                View cellLayout = inflater.inflate(R.layout.table_item, null);
                TextView cellTextView = cellLayout.findViewById(R.id.tableTextItem);

                if (plan!=null)
                    cellTextView.setText(plan.getPlan()[i][j]);

                if (holidays[j] == 2) {
                    cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.light_blue_border_style_table_item));

                } else if (holidays[j] == 1) {
                    cellTextView.setBackground(AppCompatResources.getDrawable(this, R.drawable.dark_blue_border_style_table_item));
                }

                cellTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cellTextView.setInputType(InputType.TYPE_CLASS_TEXT);
                        cellTextView.setFocusableInTouchMode(true);
                        cellTextView.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(cellTextView, InputMethodManager.SHOW_IMPLICIT);

                        if (selectedCell==null) {
                            editingModeOn();
                            selectedCell=cellLayout;
                        }

                    }
                });

                cellTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String text = s.toString();

                        if (!text.isEmpty()) {
                            text = text.toUpperCase();
                        }

                        if (!checkCellFilling(text)) {
                            makeErrorCell(cellTextView);
                            numberOfErrorCells++;
                            saveMenuDPButton.setTextColor(getColor(R.color.light_grey));
                        } else
                        {
                            if (cellTextView.getBackground().getConstantState().equals(getDrawable(R.drawable.red_border_style_table_item).getConstantState())) {
                                makePrimaryCell(cellTextView, holidays, ((TableRow) cellTextView.getParent()).indexOfChild(cellTextView));
                                if (numberOfErrorCells > 0) {
                                    numberOfErrorCells--;
                                }
                                if (numberOfErrorCells == 0) {
                                    saveMenuDPButton.setTextColor(getColor(R.color.darkest_blue));
                                }
                            }
                        }

                        cellTextView.removeTextChangedListener(this);
                        cellTextView.setText(text);
                        cellTextView.addTextChangedListener(this);
                    }
                });

                tableRow.addView(cellLayout);
            }

            planTableMenuDP.addView(tableRow);
        }
    }
    private void fillTable(DutyPlan plan, int[] holidays)
    {
        for (int i = 0; i < planTableMenuDP.getChildCount(); i++) {
            View child = planTableMenuDP.getChildAt(i);

            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                for (int j = 0; j < row.getChildCount(); j++) {
                    View cell = row.getChildAt(j);

                    if (plan!=null)
                        ((TextView)cell).setText(plan.getPlan()[i][j]);
                    else
                        ((TextView)cell).setText("");

                    if (holidays[j] == 2) {
                        cell.setBackground(AppCompatResources.getDrawable(this, R.drawable.light_blue_border_style_table_item));

                    } else if (holidays[j] == 1) {
                        cell.setBackground(AppCompatResources.getDrawable(this, R.drawable.dark_blue_border_style_table_item));
                    }
                    else
                        cell.setBackground(AppCompatResources.getDrawable(this, R.drawable.white_border_style_table_item));
                }
            }
        }
    }

    private void pressArrowRightMenuDP()
    {
        arrowRightMenuDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrowLeftMenuDP.setVisibility(View.VISIBLE);

                if (month<11)
                    month++;
                else
                {
                    month=0;
                    year++;
                }

                while (planTableMenuDP.getChildCount() > 0) {
                    View view = planTableMenuDP.getChildAt(0);
                    if (view instanceof TableRow) {
                        planTableMenuDP.removeView(view);
                    }
                }
                while (datesTableMenuDP.getChildCount() > 0) {
                    View view = datesTableMenuDP.getChildAt(0);
                    if (view instanceof TableRow) {
                        datesTableMenuDP.removeView(view);
                    }
                }

                dutyPlan = getDutyPlanUseCase.invoke(admin, month, year);
                monthNameTextMenuDP.setText(getNameOfMonthUseCase.invoke(month)+" "+year);
                //fillTable(dutyPlan, getHolidaysUseCase.invoke(month, year));

                createPlanTable(getLengthOfMonthUseCase.invoke(month, year), getAdminUsersInitialsUseCase.invoke(admin).length, dutyPlan, getHolidaysUseCase.invoke(month, year));
                createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year));
            }
        });
    }

    private void pressArrowLeftMenuDP()
    {
        arrowLeftMenuDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((month>getCurrentMonthUseCase.invoke() && year==getCurrentYearUseCase.invoke()) || year>getCurrentYearUseCase.invoke())
                {

                    if ((month-1)==getCurrentMonthUseCase.invoke() && year==getCurrentYearUseCase.invoke())
                        arrowLeftMenuDP.setVisibility(View.INVISIBLE);

                    if (month > 0)
                        month--;
                    else {
                        month = 11;
                        year--;
                    }

                    while (planTableMenuDP.getChildCount() > 0) {
                        View view = planTableMenuDP.getChildAt(0);
                        if (view instanceof TableRow) {
                            planTableMenuDP.removeView(view);
                        }
                    }
                    while (datesTableMenuDP.getChildCount() > 0) {
                        View view = datesTableMenuDP.getChildAt(0);
                        if (view instanceof TableRow) {
                            datesTableMenuDP.removeView(view);
                        }
                    }

                    dutyPlan = getDutyPlanUseCase.invoke(admin, month, year);
                    monthNameTextMenuDP.setText(getNameOfMonthUseCase.invoke(month) + " " + year);

                    createPlanTable(getLengthOfMonthUseCase.invoke(month, year), getAdminUsersInitialsUseCase.invoke(admin).length, dutyPlan, getHolidaysUseCase.invoke(month, year));
                    createDatesTable(getLengthOfMonthUseCase.invoke(month, year), getHolidaysUseCase.invoke(month, year));
                }
            }
        });
    }

    private void makeErrorCell(TextView textView)
    {
        textView.setBackground(AppCompatResources.getDrawable(this, R.drawable.red_border_style_table_item));
        textView.setTextColor(getColor(R.color.red));
    }

    private void makePrimaryCell(TextView textView, int[] holidays, int day)
    {
        if (holidays[day] == 0)
            textView.setBackground(AppCompatResources.getDrawable(this, R.drawable.white_border_style_table_item));
        else if (holidays[day] == 1)
            textView.setBackground(AppCompatResources.getDrawable(this, R.drawable.dark_blue_border_style_table_item));
        else
            textView.setBackground(AppCompatResources.getDrawable(this, R.drawable.light_blue_border_style_table_item));
        textView.setTextColor(getColor(R.color.black));
    }

    private void connectDatesAndPlanTable()
    {
        datesScrollMenuDP.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                planScrollMenuDP.scrollTo(scrollX, scrollY);
            }
        });
       planScrollMenuDP.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                datesScrollMenuDP.scrollTo(scrollX, scrollY);
            }
        });
    }

    private void editingModeOn()
    {
        backgroundDP.setBackgroundColor(Color.parseColor("#40000000"));
        cancelDPButton.setVisibility(View.VISIBLE);
        saveMenuDPButton.setVisibility(View.VISIBLE);
        arrowLeftMenuDP.setVisibility(View.INVISIBLE);
        arrowRightMenuDP.setVisibility(View.INVISIBLE);
        exitToMenuDPButton.setVisibility(View.INVISIBLE);

    }

    private void editingModeOff()
    {
        backgroundDP.setBackgroundColor(Color.parseColor("#00000000"));
        cancelDPButton.setVisibility(View.INVISIBLE);
        saveMenuDPButton.setVisibility(View.INVISIBLE);
        arrowLeftMenuDP.setVisibility(View.VISIBLE);
        arrowRightMenuDP.setVisibility(View.VISIBLE);
        exitToMenuDPButton.setVisibility(View.VISIBLE);
    }

    public void saveDutyPlan()
    {
        saveMenuDPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (numberOfErrorCells==0) {
                    editingModeOff();
                    selectedCell = null;

                    if (dutyPlan==null)
                        addDutyPlanUseCase.invoke(admin, month, year, new DutyPlan(getTextFromTable()));
                    else {
                        updateDutyPlanUseCase.invoke(dutyPlan, getTextFromTable());
                    }
                }
            }
        });
    }

    private String[][] getTextFromTable()
    {
        String[][] plan = new String[planTableMenuDP.getChildCount()][((TableRow) planTableMenuDP.getChildAt(0)).getChildCount()];

        for (int i = 0; i < planTableMenuDP.getChildCount(); i++) {
            View child = planTableMenuDP.getChildAt(i);

            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                for (int j = 0; j < row.getChildCount(); j++) {
                    View cell = row.getChildAt(j);

                    plan[i][j] = ((TextView)cell).getText().toString();

                }
            }
        }

        return plan;
    }


    private void cancelEditing()
    {
        cancelDPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTable(getDutyPlanUseCase.invoke(admin, month, year), getHolidaysUseCase.invoke(month, year));
                editingModeOff();
                selectedCell=null;
                numberOfErrorCells=0;
            }
        });
    }
    private boolean checkCellFilling(String name)
    {
        for (Place place: placesList)
        {
            if (place.getName().equals(name) || name.isEmpty())
                return true;
        }

        return false;
    }
}