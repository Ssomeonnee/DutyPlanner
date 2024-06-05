package com.example.dutyplanner.presentation.config;

import com.example.dutyplanner.data.AdminUserServer;
import com.example.dutyplanner.data.DateTimeServer;
import com.example.dutyplanner.domain.port.AdminUserRepozitory;
import com.example.dutyplanner.domain.port.DateTimeRepozitory;
import com.example.dutyplanner.domain.usecase.admin.AddAdminUseCase;
import com.example.dutyplanner.domain.usecase.admin.CheckLoginUniquenessUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminIndexUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAdminWhenLoginUseCase;
import com.example.dutyplanner.domain.usecase.admin.GetAllAdminsUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentDayUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentHourUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetCurrentYearUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetHolidaysUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetLengthOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.datetime.GetNameOfMonthUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.AddDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.GetAllDutyPlansUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.GetDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.RemoveDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.dutyplan.UpdateDutyPlanUseCase;
import com.example.dutyplanner.domain.usecase.place.AddPlaceUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesDescriptionsUseCase;
import com.example.dutyplanner.domain.usecase.place.GetAllPlacesUseCase;
import com.example.dutyplanner.domain.usecase.place.GetPlaceByNameUseCase;
import com.example.dutyplanner.domain.usecase.place.GetPlaceUseCase;
import com.example.dutyplanner.domain.usecase.place.RemovePlaceUseCase;
import com.example.dutyplanner.domain.usecase.place.UpdatePlaceUseCase;
import com.example.dutyplanner.domain.usecase.user.AddUserUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAdminForUserUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAdminUsersFullNamesUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAdminUsersInitialsUseCase;
import com.example.dutyplanner.domain.usecase.user.GetAllAdminUsersUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserIndexUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserUseCase;
import com.example.dutyplanner.domain.usecase.user.GetUserWhenLoginUseCase;
import com.example.dutyplanner.domain.usecase.user.RemoveUserUseCase;
import com.example.dutyplanner.domain.usecase.user.UpdateUserUseCase;

public class DefaultConfig {

    private static DefaultConfig instanse;
    private final DateTimeRepozitory dateTimeRepozitory = DateTimeServer.getInstance();
    private final AdminUserRepozitory adminUserRepozitory = AdminUserServer.getInstance();

    public static DefaultConfig getInstance() {
        if (instanse == null) {
            instanse = new DefaultConfig();
        }
        return instanse;
    }

    public GetCurrentDayUseCase getCurrentDay()
    { return new GetCurrentDayUseCase(dateTimeRepozitory);}

    public GetCurrentHourUseCase getCurrentHour()
    { return new GetCurrentHourUseCase(dateTimeRepozitory);}

    public GetCurrentMonthUseCase getCurrentMonth()
    { return new GetCurrentMonthUseCase(dateTimeRepozitory);}

    public GetCurrentYearUseCase getCurrentYear()
    { return new GetCurrentYearUseCase(dateTimeRepozitory);}

    public GetHolidaysUseCase getHoliday()
    { return new GetHolidaysUseCase(dateTimeRepozitory);}

    public GetLengthOfMonthUseCase getLengthOfMonth()
    { return new GetLengthOfMonthUseCase(dateTimeRepozitory);}

    public GetNameOfMonthUseCase getNameOfMonth()
    { return new GetNameOfMonthUseCase(dateTimeRepozitory);}

    public AddAdminUseCase addAdmin()
    { return new AddAdminUseCase(adminUserRepozitory);}

    public CheckLoginUniquenessUseCase checkLoginUniqueness()
    { return new CheckLoginUniquenessUseCase(adminUserRepozitory);}

    public GetAdminIndexUseCase getAdminIndex()
    { return new GetAdminIndexUseCase(adminUserRepozitory);}

    public GetAdminUseCase getAdmin()
    { return new GetAdminUseCase(adminUserRepozitory);}

    public GetAdminWhenLoginUseCase getAdminWhenLogin()
    { return new GetAdminWhenLoginUseCase(adminUserRepozitory);}

    public GetAllAdminsUseCase getAllAdmins()
    { return new GetAllAdminsUseCase(adminUserRepozitory);}

    public AddDutyPlanUseCase addDutyPlan()
    { return new AddDutyPlanUseCase(adminUserRepozitory);}

    public UpdateDutyPlanUseCase updateDutyPlan()
    { return new UpdateDutyPlanUseCase(adminUserRepozitory);}

    public RemoveDutyPlanUseCase removeDutyPlan()
    { return new RemoveDutyPlanUseCase(adminUserRepozitory);}

    public GetDutyPlanUseCase getDutyPlan()
    { return new GetDutyPlanUseCase(adminUserRepozitory);}

    public GetAllDutyPlansUseCase getDutyPlans()
    { return new GetAllDutyPlansUseCase(adminUserRepozitory);}

    public AddPlaceUseCase addPlace()
    { return new AddPlaceUseCase(adminUserRepozitory);}

    public GetAllPlacesDescriptionsUseCase getAllPlacesDescriptions()
    { return new GetAllPlacesDescriptionsUseCase(adminUserRepozitory);}

    public RemovePlaceUseCase removePlace()
    { return new RemovePlaceUseCase(adminUserRepozitory);}

    public UpdatePlaceUseCase updatePlace()
    { return new UpdatePlaceUseCase(adminUserRepozitory);}

    public GetPlaceUseCase getPlace()
    { return new GetPlaceUseCase(adminUserRepozitory);}

    public GetPlaceByNameUseCase getPlaceByName()
    { return new GetPlaceByNameUseCase(adminUserRepozitory);}

    public GetAllPlacesUseCase getAllPlaces()
    { return new GetAllPlacesUseCase(adminUserRepozitory);}

    public AddUserUseCase addUser()
    { return new AddUserUseCase(adminUserRepozitory);}

    public GetAdminForUserUseCase getAdminForUser()
    { return new GetAdminForUserUseCase(adminUserRepozitory);}

    public GetAdminUsersFullNamesUseCase getAdminUsersFullNames()
    { return new GetAdminUsersFullNamesUseCase(adminUserRepozitory);}

    public GetAdminUsersInitialsUseCase getAdminUsersInitials()
    { return new GetAdminUsersInitialsUseCase(adminUserRepozitory);}

    public GetAllAdminUsersUseCase getAllAdminUsers()
    { return new GetAllAdminUsersUseCase(adminUserRepozitory);}

    public GetUserWhenLoginUseCase getUserWhenLogin()
    { return new GetUserWhenLoginUseCase(adminUserRepozitory);}

    public GetUserIndexUseCase getUserIndex()
    { return new GetUserIndexUseCase(adminUserRepozitory);}

    public GetUserUseCase getUser()
    { return new GetUserUseCase(adminUserRepozitory);}

    public RemoveUserUseCase removeUser()
    { return new RemoveUserUseCase(adminUserRepozitory);}

    public UpdateUserUseCase updateUser()
    { return new UpdateUserUseCase(adminUserRepozitory);}
    
}
