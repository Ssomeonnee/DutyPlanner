package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.User;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetUserIndexUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public GetUserIndexUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public int invoke(Admin admin, User user)
    {
        return this.adminUserRepozitory.getUserIndex(admin, user);
    }
}
