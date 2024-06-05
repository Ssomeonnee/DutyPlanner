package com.example.dutyplanner.domain.usecase.admin;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAdminIndexUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetAdminIndexUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public int invoke(Admin admin)
    {
        return this.adminUserRepozitory.getAdminIndex(admin);
    }

}
