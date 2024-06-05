package com.example.dutyplanner.presentation.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.example.dutyplanner.R;
import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.usecase.admin.AddAdminUseCase;
import com.example.dutyplanner.domain.usecase.admin.CheckLoginUniquenessUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminWhenLoginUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAllAdminsUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserWhenLoginUseCase;
import com.example.dutyplanner.presentation.config.DefaultConfig;

public class EnterActivity extends AppCompatActivity {

    //компоненты
    private EditText loginEditText;
    private EditText passwordEditText;
    private Button enterButton;
    private TextView emptyLoginText;
    private TextView emptyPasswordText;
    private TextView invalidLoginPasswordText;
    private EditText adminNameEditText;
    private String enterType;
    private TextView createProfileTextView;

    //конфигурация
    private DefaultConfig config;
    //usecase
    private GetAdminWhenLoginUseCase getAdminWhenLoginUseCase;
    private GetUserWhenLoginUseCase getUserWhenLoginUseCase;
    private GetAllAdminsUseCase getAllAdminsUseCase;
    private AddAdminUseCase addAdminUseCase;
    private CheckLoginUniquenessUseCase checkLoginUniquenessUseCase;

    //флаг на тип аккаунта
    private boolean isNewAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        //инициализация компонент
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        enterButton = findViewById(R.id.enterButton);
        emptyLoginText = findViewById(R.id.emptyLoginText);
        emptyPasswordText = findViewById(R.id.emptyPasswordText);
        invalidLoginPasswordText = findViewById(R.id.invalidLoginPasswordText);
        createProfileTextView = findViewById(R.id.createProfileTextView);
        adminNameEditText = findViewById(R.id.adminNameEditText);
        //server = AdminUserServer.getInstance();

        //создаю usecase
        config = DefaultConfig.getInstance();
        getAdminWhenLoginUseCase = config.getAdminWhenLogin();
        getUserWhenLoginUseCase = config.getUserWhenLogin();
        getAllAdminsUseCase = config.getAllAdmins();
        addAdminUseCase = config.addAdmin();
        checkLoginUniquenessUseCase = config.checkLoginUniqueness();

        //получаю тип входа
        enterType = getIntent().getStringExtra("EnterType");

        if (enterType.equals("Admin"))
        {
            createProfileTextView.setVisibility(View.VISIBLE);
            createAccountAction();
        }

        //обработка нажатия на кнопку
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputForAuthorization();
            }
        });
    }

    private void existingEnter()
    {
        try
        {
            Intent intent = new Intent(EnterActivity.this, MainActivity.class);
            if (enterType.equals("Admin"))
            {
                intent.putExtra("EnterType", "Admin");////////сделать методы админа
                intent.putExtra("AdminIndex",getAdminWhenLoginUseCase.invoke(loginEditText.getText().toString(), passwordEditText.getText().toString()));
            }
            else
            {
                intent.putExtra("EnterType", "User");
                int[] indexes = getUserWhenLoginUseCase.invoke(loginEditText.getText().toString(), passwordEditText.getText().toString());
                intent.putExtra("UserIndex", indexes[0]);
                intent.putExtra("AdminIndex", indexes[1]);
            }
            startActivity(intent);
        }
        catch (IndexOutOfBoundsException ex)
        {
            invalidLoginPasswordText.setVisibility(View.VISIBLE);
            passwordEditText.setText("");
            loginEditText.setText("");
        }
    }

    private void getNewAdminName()
    {
        passwordEditText.setVisibility(View.GONE);
        loginEditText.setVisibility(View.GONE);

        findViewById(R.id.enterPasswordTitleTextView).setVisibility(View.GONE);
        findViewById(R.id.enterExplanationTextView).setVisibility(View.GONE);

        adminNameEditText.setVisibility(View.VISIBLE);

        ((TextView)findViewById(R.id.titleEnterActivityTextView)).setText("Как к вам обращаться?");
        ((TextView) findViewById(R.id.enterLoginTitleTextView)).setText("Введите имя");


        enterButton.setText("Зарегистрироваться");
    }


    private void newEnter()
    {
        addAdminUseCase.invoke(new Admin(adminNameEditText.getText().toString(), loginEditText.getText().toString(), passwordEditText.getText().toString()));

        Intent intent = new Intent(EnterActivity.this, MainActivity.class);
        intent.putExtra("EnterType", "Admin");

        intent.putExtra("AdminIndex", (getAllAdminsUseCase.invoke().size()-1));
        startActivity(intent);
    }

    private void checkInputForAuthorization()
    {
        emptyLoginText.setVisibility(View.INVISIBLE);
        emptyPasswordText.setVisibility(View.INVISIBLE);
        invalidLoginPasswordText.setVisibility(View.INVISIBLE);

        if (loginEditText.getText().toString().length()>1 && loginEditText.getText().toString().substring(loginEditText.getText().toString().length() - 1).equals(" ")) {
            loginEditText.setText(loginEditText.getText().toString().substring(0, loginEditText.getText().toString().length() - 1));
            enterButton.setText(loginEditText.getText().toString());
        }

        if (checkInputNewLogin() && checkInputNewPassword() && checkInputLogin() && checkInputPassword() && checkInputName())
        {
            if (!isNewAccount)
                existingEnter();
            else
            if (adminNameEditText.getVisibility()==View.INVISIBLE)
            {
                getNewAdminName();
            }
            else
                newEnter();
        }

    }

    private boolean checkInputNewLogin()
    {
        if (loginEditText.getVisibility()==View.VISIBLE && isNewAccount) {
            if (loginEditText.getText().toString().length() < 8 && loginEditText.getText().toString().length() > 0) {
                emptyLoginText.setVisibility(View.VISIBLE);
                emptyLoginText.setText("Логин не меньше 8 символов");
                return false;
            } else if (!checkLoginUniquenessUseCase.invoke(loginEditText.getText().toString())) {
                emptyLoginText.setVisibility(View.VISIBLE);
                emptyLoginText.setText("Неуникальный логин");
                return false;
            } else if (loginEditText.getText().toString().contains(" ")) {
                emptyLoginText.setVisibility(View.VISIBLE);
                emptyLoginText.setText("Удалите пробел");
                return false;
            }
            return true;
        }
        return true;
    }

    private boolean checkInputNewPassword()
    {
        if (passwordEditText.getVisibility()==View.VISIBLE && isNewAccount) {
            if (passwordEditText.getText().toString().length() < 8) {
                emptyPasswordText.setVisibility(View.VISIBLE);
                emptyPasswordText.setText("Пароль не меньше 8 символов");
                return false;
            } else if (passwordEditText.getText().toString().contains(" ")) {
                emptyPasswordText.setVisibility(View.VISIBLE);
                emptyPasswordText.setText("Удалите пробел");
                return false;
            }
            return true;
        }
        return true;

    }

    private boolean checkInputPassword()
    {
        if (passwordEditText.getText().toString().isEmpty() && passwordEditText.getVisibility()==View.VISIBLE)
        {
            emptyPasswordText.setVisibility(View.VISIBLE);
            emptyPasswordText.setText("Введите пароль");
            return false;
        }
        return true;
    }

    private boolean checkInputLogin()
    {
        if (loginEditText.getText().toString().isEmpty() && loginEditText.getVisibility()==View.VISIBLE)
        {
            emptyLoginText.setVisibility(View.VISIBLE);
            emptyLoginText.setText("Введите логин");
            return false;
        }
        return true;
    }

    private boolean checkInputName()
    {
        if (adminNameEditText.getText().toString().isEmpty() && adminNameEditText.getVisibility()==View.VISIBLE)
        {
            emptyLoginText.setVisibility(View.VISIBLE);
            emptyLoginText.setText("Введите имя");
            return false;
        }
        return true;
    }

    private void createAccountAction()
    {
        createProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emptyLoginText.setVisibility(View.INVISIBLE);
                emptyPasswordText.setVisibility(View.INVISIBLE);
                invalidLoginPasswordText.setVisibility(View.INVISIBLE);

                TextView titleEnterActivityTextView = findViewById(R.id.titleEnterActivityTextView);
                TextView enterLoginTitleTextView = findViewById(R.id.enterLoginTitleTextView);
                TextView enterPasswordTitleTextView = findViewById(R.id.enterPasswordTitleTextView);
                TextView enterExplanationTextView = findViewById(R.id.enterExplanationTextView);

                titleEnterActivityTextView.setText("Создание аккаунта");
                enterLoginTitleTextView.setText("Придумайте логин");
                enterPasswordTitleTextView.setText("Придумайте пароль");
                enterExplanationTextView.setText("Пожалуйста, придумайте логин и пароль для авторизации в системе.");

                enterButton.setText("Продолжить");

                createProfileTextView.setVisibility(View.INVISIBLE);
                isNewAccount=true;
            }
        });
    }

}