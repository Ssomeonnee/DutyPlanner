package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class AddUserUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public AddUserUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(User user, Admin admin)
    {
        this.adminUserRepozitory.addUser(admin, user);
    }

}
