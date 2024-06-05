package com.example.dutyplanner.domain.usecase.dutyplan;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetDutyPlanUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public GetDutyPlanUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public DutyPlan invoke(Admin admin, int month, int year)
    {
        return this.adminUserRepozitory.getDutyPlan(admin, month, year);
    }
}
