package com.example.dutyplanner.domain.usecase.admin;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class CheckLoginUniquenessUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public CheckLoginUniquenessUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public boolean invoke(String login) {
        return this.adminUserRepozitory.checkLoginUniqueness(login);
    }

}
