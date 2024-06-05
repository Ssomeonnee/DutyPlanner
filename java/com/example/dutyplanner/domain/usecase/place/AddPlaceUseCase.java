package com.example.dutyplanner.domain.usecase.place;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class AddPlaceUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public AddPlaceUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(Admin admin, Place place)
    {
        this.adminUserRepozitory.addPlace(admin, place);
    }

}
