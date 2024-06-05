package com.example.dutyplanner.domain.usecase.dutyplan;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class RemoveDutyPlanUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public RemoveDutyPlanUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(Admin admin, int month, int year, DutyPlan dutyPlan)
    {
        this.adminUserRepozitory.removeDutyPlan(admin, month, year, dutyPlan);
    }
}
