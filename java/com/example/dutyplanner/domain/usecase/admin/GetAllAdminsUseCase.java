package com.example.dutyplanner.domain.usecase.admin;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

import java.util.ArrayList;

public class GetAllAdminsUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public GetAllAdminsUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public ArrayList<Admin> invoke()
    {
        return this.adminUserRepozitory.getAdmins();
    }
}
