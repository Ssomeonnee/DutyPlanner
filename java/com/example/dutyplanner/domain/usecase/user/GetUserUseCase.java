package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetUserUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetUserUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public User invoke(Admin admin, int index)
    {
        return this.adminUserRepozitory.getUser(admin, index);
    }
}
