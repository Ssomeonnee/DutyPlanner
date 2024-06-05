package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAdminUsersFullNamesUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public GetAdminUsersFullNamesUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public String[] invoke(Admin admin) {
        return this.adminUserRepozitory.getUsersFullNames(admin);
    }
}
