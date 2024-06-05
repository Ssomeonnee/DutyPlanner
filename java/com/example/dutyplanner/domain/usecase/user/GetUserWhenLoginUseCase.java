package com.example.dutyplanner.domain.usecase.user;

import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetUserWhenLoginUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetUserWhenLoginUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public int[] invoke(String login, String password)
    {
        return this.adminUserRepozitory.getUserForLogin(login, password);
    }

}
