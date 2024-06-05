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
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;
import com.example.dutyplanner.domain.usecase.admin.AddAdminUseCase;
import com.example.dutyplanner.domain.usecase.admin.CheckLoginUniquenessUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.user.AddUserUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserUseCase;
import com.example.dutyplanner.domain.usecase.user.UpdateUserUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

public class InputUserDialogFragment extends DialogFragment {

    //компоненты
    private View view;
    private Button addFragmentButton;
    private EditText nameEditText;
    private EditText middleNameEditText;
    private EditText surnameEditText;
    private EditText loginEditTextFr;
    private EditText passwordEditTextFr;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetAdminUseCase getAdminUseCase;
    private GetUserUseCase getUserUseCase;
    private AddUserUseCase addUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private CheckLoginUniquenessUseCase checkLoginUniquenessUseCase;

    //глобальные переменные для окна
    private Admin admin;
    private User user;
    private boolean isExisting = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullWidthDialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_input_user_form, container, false);

        //usecase
        config = DefaultConfig.getInstance();
        getAdminUseCase = config.getAdmin();
        getUserUseCase = config.getUser();
        addUserUseCase = config.addUser();
        updateUserUseCase = config.updateUser();
        checkLoginUniquenessUseCase = config.checkLoginUniqueness();

        //получаю админа
        admin = getAdminUseCase.invoke(getArguments().getInt("AdminIndex"));

        //компоненты
        nameEditText = view.findViewById(R.id.nameEditText);
        middleNameEditText = view.findViewById(R.id.middleNameEditText);
        surnameEditText = view.findViewById(R.id.surnameEditText);
        addFragmentButton = view.findViewById(R.id.addFragmentButton);
        loginEditTextFr = view.findViewById(R.id.loginEditTextFr);
        passwordEditTextFr = view.findViewById(R.id.passwordEditTextFr);

        //делаю первую букву заглавной при вводе в поле
        capitalizeText(nameEditText);
        capitalizeText(middleNameEditText);
        capitalizeText(surnameEditText);

        //тип входа в окно == редактирование??
        if (getArguments().getString("InputType").equals("existing"))
        {
            ((TextView)view.findViewById(R.id.titleUserFr)).setText("Редактирование");
            ((TextView)view.findViewById(R.id.hintUserDataFr)).setText("Измените личные данные пользователя");
            ((TextView)view.findViewById(R.id.hintUserAuthFr)).setText("Измените логин и пароль для авторизации пользователя");
            addFragmentButton.setText("СОХРАНИТЬ");

            user = getUserUseCase.invoke(admin, getArguments().getInt("Index"));

            surnameEditText.setText(user.getSurname());
            nameEditText.setText(user.getName());
            middleNameEditText.setText(user.getMiddleName());
            loginEditTextFr.setText(user.getLogin());
            passwordEditTextFr.setText(user.getPassword());

            isExisting=true;

        }

        //обработка нажатия на кнопку
        buttonPress();

        return view;
    }

    private void buttonPress()
    {
        addFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInput() && checkInputLogin(loginEditTextFr) && checkInputPassword(passwordEditTextFr))
                {
                    if (getArguments().getString("InputType").equals("existing"))
                        updateUserUseCase.invoke(user, loginEditTextFr.getText().toString(), passwordEditTextFr.getText().toString(), surnameEditText.getText().toString(), nameEditText.getText().toString(), middleNameEditText.getText().toString());
                    else
                        addUserUseCase.invoke(new User(loginEditTextFr.getText().toString(), passwordEditTextFr.getText().toString(), surnameEditText.getText().toString(), nameEditText.getText().toString(), middleNameEditText.getText().toString()), admin);
                    getActivity().recreate();
                    dismiss();
                }

            }
        });
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
                editText.addTextChangedListener(this);
            }
        });
    }

    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        if (text.length()==1)
            return text.toUpperCase();
        else
            return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private boolean checkInputLogin(EditText editText)
    {
        if (editText.getText().toString().length()<8 && editText.getText().toString().length()>0)
        {
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.red));
            editText.setHint("Логин не меньше 8 символов");
            return false;
        }
        else if ((!checkLoginUniquenessUseCase.invoke(editText.getText().toString())) && ((!isExisting)||(isExisting && !user.getLogin().equals(loginEditTextFr.getText().toString()))))
        {
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.red));
            editText.setHint("Такой логин уже существует");
            return false;
        }
        else if (editText.getText().toString().contains(" ") ) {
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.red));
            editText.setHint("Удалите пробел");
            return false;
        }
        return true;

    }

    private boolean checkInputPassword(EditText editText)
    {
        if (editText.getText().toString().length()<8)
        {
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.red));
            editText.setHint("Пароль не меньше 8 символов");
            return false;
        }
        else if (editText.getText().toString().contains(" ") ) {
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.red));
            editText.setHint("Удалите пробел");
            return false;
        }
        return true;

    }

    private boolean checkInput()
    {
        if (nameEditText.getText().toString().isEmpty()&&surnameEditText.getText().toString().isEmpty()&&middleNameEditText.getText().toString().isEmpty()&&loginEditTextFr.getText().toString().isEmpty()&&passwordEditTextFr.getText().toString().isEmpty()) {
            if (nameEditText.getText().toString().isEmpty()) {
                nameEditText.setText("");
                nameEditText.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (surnameEditText.getText().toString().isEmpty()) {
                surnameEditText.setText("");
                surnameEditText.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (middleNameEditText.getText().toString().isEmpty()) {
                middleNameEditText.setText("");
                middleNameEditText.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (loginEditTextFr.getText().toString().isEmpty()) {
                loginEditTextFr.setText("");
                loginEditTextFr.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (passwordEditTextFr.getText().toString().isEmpty()) {
                passwordEditTextFr.setText("");
                passwordEditTextFr.setHintTextColor(getResources().getColor(R.color.red));
            }
        return false;
        }

        return true;
    }

}
