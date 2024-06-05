package com.example.dutyplanner.domain.usecase.admin;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class AddAdminUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public AddAdminUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(Admin admin)
    {
        this.adminUserRepozitory.addAdmin(admin);
    }

}
