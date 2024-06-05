package com.example.dutyplanner.domain.usecase.admin;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAdminWhenLoginUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetAdminWhenLoginUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public int invoke(String login, String password)
    {
        return this.adminUserRepozitory.getAdminForLogin(login, password);
    }

}
