package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetCurrentYearUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetCurrentYearUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public int invoke()
    {
        return dateTimeRepozitory.getCurrentYear();
    }
}
