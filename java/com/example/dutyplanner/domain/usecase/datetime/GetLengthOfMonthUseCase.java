package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetLengthOfMonthUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetLengthOfMonthUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public int invoke(int month, int year)
    {
        return dateTimeRepozitory.getLengthOfMonth(month, year);
    }
}
