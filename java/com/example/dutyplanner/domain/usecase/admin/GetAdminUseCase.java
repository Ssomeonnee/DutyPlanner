package com.example.dutyplanner.domain.usecase.admin;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAdminUseCase {


    private final AdminUserRepozitory adminUserRepozitory;

    public GetAdminUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public Admin invoke(int index)
    {
        return this.adminUserRepozitory.getAdmin(index);
    }

}
