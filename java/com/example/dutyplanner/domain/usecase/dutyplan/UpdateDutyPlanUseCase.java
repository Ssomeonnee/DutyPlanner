package com.example.dutyplanner.domain.usecase.dutyplan;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.DutyPlan;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class UpdateDutyPlanUseCase {
    private final AdminUserRepozitory adminUserRepozitory;

    public UpdateDutyPlanUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(DutyPlan dutyPlan, String[][] plan)
    {
        this.adminUserRepozitory.updateDutyPlan(dutyPlan, plan);
    }
}
