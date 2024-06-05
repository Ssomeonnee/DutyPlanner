package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetCurrentHourUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetCurrentHourUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public int invoke()
    {
        return dateTimeRepozitory.getCurrentHour();
    }
}
