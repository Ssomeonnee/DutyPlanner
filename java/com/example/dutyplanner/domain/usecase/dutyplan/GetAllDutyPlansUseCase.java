package com.example.dutyplanner.domain.usecase.dutyplan;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

import java.util.HashMap;

public class GetAllDutyPlansUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetAllDutyPlansUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public HashMap<String, DutyPlan> invoke(Admin admin)
    {
        return this.adminUserRepozitory.getDutyPlans(admin);
    }

}
