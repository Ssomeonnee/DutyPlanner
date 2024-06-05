package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class RemoveUserUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public RemoveUserUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(Admin admin, User user)
    {
        this.adminUserRepozitory.removeUser(admin, user);
    }

}
