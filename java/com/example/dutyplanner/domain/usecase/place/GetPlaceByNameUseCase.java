package com.example.dutyplanner.domain.usecase.place;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetPlaceByNameUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetPlaceByNameUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public Place invoke(Admin admin, String name)
    {
        return this.adminUserRepozitory.getPlace(admin, name);
    }
}
