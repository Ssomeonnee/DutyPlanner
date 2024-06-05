package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetHolidaysUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetHolidaysUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public int[] invoke(int month, int year)
    {
        return dateTimeRepozitory.getHolidays(month, year);
    }
}
