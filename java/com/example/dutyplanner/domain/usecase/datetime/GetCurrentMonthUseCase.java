package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetCurrentMonthUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetCurrentMonthUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public int invoke()
    {
        return dateTimeRepozitory.getCurrentMonth();
    }
}
