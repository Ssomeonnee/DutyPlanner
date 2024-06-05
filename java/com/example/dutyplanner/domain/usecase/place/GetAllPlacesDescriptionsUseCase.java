package com.example.dutyplanner.domain.usecase.place;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetAllPlacesDescriptionsUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetAllPlacesDescriptionsUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public String[] invoke(Admin admin) {
        return this.adminUserRepozitory.getDescriptions(admin);
    }
}
