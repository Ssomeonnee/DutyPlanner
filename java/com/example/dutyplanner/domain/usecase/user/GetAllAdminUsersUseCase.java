package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

import java.util.ArrayList;

public class GetAllAdminUsersUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public GetAllAdminUsersUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public ArrayList<User> invoke(Admin admin)
    {
        return this.adminUserRepozitory.getUsers(admin);
    }
}
