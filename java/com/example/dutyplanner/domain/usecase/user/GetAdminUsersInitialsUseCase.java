package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAdminUsersInitialsUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public GetAdminUsersInitialsUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public String[] invoke(Admin admin)
    {
        return this.adminUserRepozitory.getUsersInitials(admin);
    }
}
