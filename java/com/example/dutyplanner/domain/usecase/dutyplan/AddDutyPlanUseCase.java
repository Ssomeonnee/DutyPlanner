package com.example.dutyplanner.domain.usecase.dutyplan;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class AddDutyPlanUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public AddDutyPlanUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(Admin admin, int month, int year, DutyPlan dutyPlan)
    {
        this.adminUserRepozitory.addDutyPlan(admin, month, year, dutyPlan);
    }
}
