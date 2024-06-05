package com.example.dutyplanner.domain.usecase.datetime;

import com.example.dutyplanner.domain.port.DateTimeRepozitory;

public class GetNameOfMonthUseCase {
    private final DateTimeRepozitory dateTimeRepozitory;

    public GetNameOfMonthUseCase(DateTimeRepozitory dateTimeRepozitory)
    {
        this.dateTimeRepozitory=dateTimeRepozitory;
    }
    public String invoke(int month)
    {
        return dateTimeRepozitory.getNameOfMonth(month);
    }
}
