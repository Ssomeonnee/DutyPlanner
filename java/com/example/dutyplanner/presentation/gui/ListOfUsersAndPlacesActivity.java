package com.example.dutyplanner.presentation.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dutyplanner.R;
import com.example.dutyplanner.data.AdminUserServer;
import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;
import com.example.dutyplanner.domain.usecase.admin.GetAdminIndexUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesDescriptionsUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAdminUsersFullNamesUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

public class ListOfUsersAndPlacesActivity extends AppCompatActivity {

    //компоненты
    private TextView titleInSubMenu;
    private TableLayout list;
    private CardView addNewElementButton;
    private ImageView exitToMenuButton;
    private ImageView editButton;
    private ImageView removeButton;

    //глобальные переменные для активити
    private Admin admin;
    private String listEntered;
    //хранение выделенной строки
    private TableRow selectedRow = null;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetAdminIndexUseCase getAdminIndexUseCase;
    private GetAdminUseCase getAdminUseCase;
    private GetAdminUsersFullNamesUseCase getAdminUsersFullNamesUseCase;
    private GetAllPlacesDescriptionsUseCase getAllPlacesDescriptionsUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users_and_places);

        //компоненты
        titleInSubMenu = findViewById(R.id.titleInSubMenu);
        list = findViewById(R.id.list);
        addNewElementButton = findViewById(R.id.addNewElementButton);
        exitToMenuButton = findViewById(R.id.exitToMenuButton);
        editButton  = findViewById(R.id.editButton);
        removeButton = findViewById(R.id.removeButton);

        //отключение кнопок
        editButton.setEnabled(false);
        removeButton.setEnabled(false);

        //usecase
        //server = AdminUserServer.getInstance();
        config = DefaultConfig.getInstance();
        getAdminIndexUseCase = config.getAdminIndex();
        getAdminUseCase = config.getAdmin();
        getAdminUsersFullNamesUseCase = config.getAdminUsersFullNames();
        getAllPlacesDescriptionsUseCase = config.getAllPlacesDescriptions();

        //получаю админа
        admin = getAdminUseCase.invoke(getIntent().getIntExtra("AdminIndex",-1));

        //тип входа Места или Пользователи???
        listEntered = getIntent().getStringExtra("Data");
        if (listEntered.equals("places"))
        {
            titleInSubMenu.setText("Ваши места");
            createList(getAllPlacesDescriptionsUseCase.invoke(admin));
        }
        else
        {
            titleInSubMenu.setText("Ваши пользователи");
            createList(getAdminUsersFullNamesUseCase.invoke(admin));
        }

        //обработка нажатия кнопок
        backToMainWindowPress();
        addNewElement();
        updateElement();
        removeElement();
    }

    private void createList(String[] array) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (array.length == 0) {

            CardView card = findViewById(R.id.listCardView);

            View cellLayout = inflater.inflate(R.layout.menu_list_item, null);
            TextView cellTextView = cellLayout.findViewById(R.id.menuListItem);
            cellTextView.setText("Пока тут пусто");
            cellTextView.setGravity(Gravity.CENTER);
            cellTextView.setTextColor(getColor(R.color.light_grey));

            card.addView(cellLayout);


        } else {

            for (int i = 0; i < array.length; i++) {
                TableRow tableRow = new TableRow(this);
                View cellLayout = inflater.inflate(R.layout.menu_list_item, null);
                //CardView cardView = cellLayout.findViewById(R.id.usersTableCardItem);
                TextView cellTextView = cellLayout.findViewById(R.id.menuListItem);
                cellTextView.setText(array[i]);

                tableRow.addView(cellLayout);

                tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (selectedRow != null) {
                            // Сбросьте выделение предыдущей выбранной ячейки
                            selectedRow.setBackgroundColor(Color.TRANSPARENT);
                        }
                        // Выделите текущую ячейку
                        tableRow.setBackgroundColor(getColor(R.color.dark_blue));
                        //tableRow.setBackground(getDrawable(R.drawable.filled_style_menu_list_item));
                        // Сохраните ссылку на текущую выбранную ячейку
                        selectedRow = tableRow;

                        editButton.setImageDrawable(getDrawable(R.drawable.edit));
                        removeButton.setImageDrawable(getDrawable(R.drawable.bin));

                        editButton.setEnabled(true);
                        removeButton.setEnabled(true);

                        return true;
                    }
                });

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tableRow==selectedRow) {
                            selectedRow.setBackgroundColor(Color.TRANSPARENT);

                            editButton.setImageDrawable(getDrawable(R.drawable.edit_grey));
                            removeButton.setImageDrawable(getDrawable(R.drawable.bin_grey));

                            editButton.setEnabled(false);
                            removeButton.setEnabled(false);
                        }
                    }
                });

                list.addView(tableRow);
            }
        }
    }

    private void backToMainWindowPress()
    {
        exitToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addNewElement()
    {
        addNewElementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dialog;

                if (listEntered.equals("places"))
                    dialog = new InputPlaceDialogFragment();
                else
                    dialog = new InputUserDialogFragment();

                Bundle args = new Bundle();
                args.putString("InputType", "new");
                args.putInt("AdminIndex", getAdminIndexUseCase.invoke(admin));
                dialog.setArguments(args);

                dialog.show(getSupportFragmentManager(), "input_dialog");
            }
        });
    }

    private void updateElement()
    {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dialog;

                if (listEntered.equals("places"))
                    dialog = new InputPlaceDialogFragment();
                else
                    dialog = new InputUserDialogFragment();

                Bundle args = new Bundle();
                args.putString("InputType", "existing");
                args.putInt("AdminIndex", getAdminIndexUseCase.invoke(admin));


                args.putInt("Index", list.indexOfChild(selectedRow));

                dialog.setArguments(args);

                dialog.show(getSupportFragmentManager(), "input_dialog");

            }
        });
    }

    private void removeElement()
    {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RemoveUsePlaceFragmentDialog dialog = new RemoveUsePlaceFragmentDialog();

                Bundle args = new Bundle();

                args.putInt("AdminIndex", getAdminIndexUseCase.invoke(admin));

                if (listEntered.equals("places"))
                    args.putString("Data", "places");
                else
                    args.putString("Data", "users");


                args.putInt("Index", list.indexOfChild(selectedRow));

                dialog.setArguments(args);

                dialog.show(getSupportFragmentManager(), "input_dialog");

            }
        });
    }
}