package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.data.DateTimeServer;
import com.example.dutyplanner.domain.entity.Admin;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;
import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetCurrentDayUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetCurrentDayUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public int invoke()
    {
        return dateTimeRepozitory.getCurrentDay();
    }
}
