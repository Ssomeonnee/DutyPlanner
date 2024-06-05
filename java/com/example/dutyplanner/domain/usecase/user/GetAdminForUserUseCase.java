package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAdminForUserUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetAdminForUserUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public int invoke(User user)
    {
        return this.adminUserRepozitory.getAdminForUser(user);
    }
}
