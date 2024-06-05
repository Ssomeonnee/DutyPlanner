package com.example.dutyplanner.presentation.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.dutyplanner.R;
import com.example.dutyplanner.data.AdminUserServer;
import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.place.GetPlaceUseCase;
import com.example.dutyplanner.domain.usecase.place.RemovePlaceUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserUseCase;
import com.example.dutyplanner.domain.usecase.user.RemoveUserUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

public class RemoveUsePlaceFragmentDialog extends DialogFragment {

    //компоненты
    private View view;
    private TextView titleRemoveFr;
    private TextView yesOptionRemoveFr;
    private TextView noOptionRemoveFr;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetAdminUseCase getAdminUseCase;
    private GetUserUseCase getUserUseCase;
    private GetPlaceUseCase getPlaceUseCase;
    private RemoveUserUseCase removeUserUseCase;
    private RemovePlaceUseCase removePlaceUseCase;

    //глобальные переменные для окна
    private  Admin admin;
    private String data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullWidthDialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_remove_user_place_form, container, false);

        //usecase
        config = DefaultConfig.getInstance();
        getAdminUseCase = config.getAdmin();
        getUserUseCase = config.getUser();
        getPlaceUseCase = config.getPlace();
        removePlaceUseCase = config.removePlace();
        removeUserUseCase = config.removeUser();

        //получаю админа
        admin = getAdminUseCase.invoke(getArguments().getInt("AdminIndex"));

        //компоненты
        titleRemoveFr = view.findViewById(R.id.titleRemoveFr);
        yesOptionRemoveFr = view.findViewById(R.id.yesOptionRemoveFr);
        noOptionRemoveFr = view.findViewById(R.id.noOptionRemoveFr);

        //тип входа Место или Пользователь???
        data = getArguments().getString("Data");
        if (data.equals("places"))
            titleRemoveFr.setText("Вы уверены, что хотите удалить место?");
        else
            titleRemoveFr.setText("Вы уверены, что хотите удалить пользователя?");

        //обработка нажатия кнопок
        buttonYesPress();
        buttonNoPress();

        return view;
    }

    private void buttonYesPress()
    {
        yesOptionRemoveFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data.equals("places"))
                   removePlaceUseCase.invoke(admin, getPlaceUseCase.invoke(admin,getArguments().getInt("Index")));
                else
                    removeUserUseCase.invoke(admin, getUserUseCase.invoke(admin,getArguments().getInt("Index")));

                getActivity().recreate();
                dismiss();

            }
        });
    }

    private void buttonNoPress()
    {
        noOptionRemoveFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
