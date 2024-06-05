package com.example.dutyplanner.domain.usecase.place;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class GetPlaceUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetPlaceUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public Place invoke(Admin admin, int index)
    {
        return this.adminUserRepozitory.getPlace(admin, index);
    }

}
