package com.example.dutyplanner.presentation.gui;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.dutyplanner.R;
import com.example.dutyplanner.data.AdminUserServer;
import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.place.AddPlaceUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesUseCase;
import com.example.dutyplanner.domain.usecase.place.GetPlaceUseCase;
import com.example.dutyplanner.domain.usecase.place.UpdatePlaceUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

import java.util.ArrayList;

public class InputPlaceDialogFragment extends DialogFragment {

    //компоненты
    private View view;
    private EditText descriptionEditText;
    private EditText shortenedPlaceEditText;
    private Button addPlaceFragmentButton;

    //конфигурация
    private DefaultConfig config;
    private GetAdminUseCase getAdminUseCase;
    private GetPlaceUseCase getPlaceUseCase;
    private UpdatePlaceUseCase updatePlaceUseCase;
    private AddPlaceUseCase addPlaceUseCase;
    private GetAllPlacesUseCase getAllPlacesUseCase;

    //глобальные переменные для окна
    private Admin admin;
    private Place place;
    private boolean isExisting = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullWidthDialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_input_place_form, container, false);

        //usecase
        config = DefaultConfig.getInstance();
        getAdminUseCase = config.getAdmin();
        getPlaceUseCase = config.getPlace();
        updatePlaceUseCase = config.updatePlace();
        addPlaceUseCase = config.addPlace();
        getAllPlacesUseCase = config.getAllPlaces();

        //получаю админа
        admin = getAdminUseCase.invoke(getArguments().getInt("AdminIndex"));

        //компоненты
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        shortenedPlaceEditText = view.findViewById(R.id.shortenedPlaceEditText);
        addPlaceFragmentButton = view.findViewById(R.id.addPlaceFragmentButton);

        //увеличение первой буквы слова при вводе в поле
        capitalizeText(descriptionEditText);
        capitalizeText(shortenedPlaceEditText);

        //тип открытия окна == редактирование???
        if (getArguments().getString("InputType").equals("existing"))
        {
            ((TextView)view.findViewById(R.id.titlePlaceFr)).setText("Редактирование");
            addPlaceFragmentButton.setText("СОХРАНИТЬ");

            place = getPlaceUseCase.invoke(admin, getArguments().getInt("Index"));

            descriptionEditText.setText(place.getDescription());
            shortenedPlaceEditText.setText(place.getName());

            isExisting=true;
        }

        buttonPress();

        return view;
    }

    private void capitalizeText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ничего не делаем перед изменением текста
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ничего не делаем во время изменения текста
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();

                String capitalizedText = capitalizeFirstLetter(newText);

                editText.removeTextChangedListener(this);
                editText.setText(capitalizedText);
                editText.setSelection(capitalizedText.length());
                if (capitalizedText.length()>1)
                    shortenedPlaceEditText.setText(capitalizedText.substring(0, 1).toUpperCase());
                else if (capitalizedText.length()==1)
                    shortenedPlaceEditText.setText(capitalizedText.toUpperCase());
                else
                    shortenedPlaceEditText.setText("");
                editText.addTextChangedListener(this);
            }
        });
    }

    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        if (text.length()==1) {
            return text.toUpperCase();
        }
        else {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }
    }

    private void buttonPress()
    {
        addPlaceFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInput())
                {
                    if (getArguments().getString("InputType").equals("existing"))
                        updatePlaceUseCase.invoke(admin, place, shortenedPlaceEditText.getText().toString(), descriptionEditText.getText().toString());
                    else
                        addPlaceUseCase.invoke(admin, new Place(shortenedPlaceEditText.getText().toString(), descriptionEditText.getText().toString()));
                    getActivity().recreate();
                    dismiss();
                }

            }
        });
    }

    private boolean checkInput()
    {
        if (descriptionEditText.getText().toString().isEmpty()&&shortenedPlaceEditText.getText().toString().isEmpty()) {
            if (descriptionEditText.getText().toString().isEmpty()) {
                descriptionEditText.setText("");
                descriptionEditText.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (shortenedPlaceEditText.getText().toString().isEmpty()) {
                shortenedPlaceEditText.setText("");
                shortenedPlaceEditText.setHintTextColor(getResources().getColor(R.color.red));
            }
            return false;
        }
        else if (!checkShortenedPlaceUniqueness())
        {
            shortenedPlaceEditText.setText("");
            shortenedPlaceEditText.setHintTextColor(getResources().getColor(R.color.red));
            shortenedPlaceEditText.setHint("Неуникальное сокращение");
            return false;
        }
        return true;
    }

    private boolean checkShortenedPlaceUniqueness()
    {
        ArrayList<Place> list = getAllPlacesUseCase.invoke(admin);

        if (!list.isEmpty()) {
            for (Place currentPlace : list) {
                if (isExisting && place==currentPlace && currentPlace.getName().equals(shortenedPlaceEditText.getText().toString()))
                    return true;
                if (currentPlace.getName().equals(shortenedPlaceEditText.getText().toString()))
                    return false;
            }
            return true;
        }
        else
            return true;
    }

}
