package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class UpdateUserUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public UpdateUserUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(User user, String login, String password, String surname, String name, String middleName)
    {
        this.adminUserRepozitory.updateUser(user, login, password, surname, name, middleName);
    }
}
