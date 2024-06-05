package com.example.dutyplanner.domain.usecase.place;

import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.entity.Place;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;

import java.util.ArrayList;

public class GetAllPlacesUseCase {

    private final AdminUserRepozitory adminUserRepozitory;

    public GetAllPlacesUseCase(AdminUserRepozitory adminUserRepozitory)
    {
        this.adminUserRepozitory=adminUserRepozitory;
    }
    public ArrayList<Place> invoke(Admin admin)
    {
        return this.adminUserRepozitory.getPlaces(admin);
    }

}
