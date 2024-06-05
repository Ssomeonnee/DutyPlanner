package com.example.dutyplanner.domain.usecase.place;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

public class UpdatePlaceUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public UpdatePlaceUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public void invoke(Admin admin, Place place, String name, String description)
    {
        this.adminUserRepozitory.updatePlace(admin, place, name, description);
    }

}
