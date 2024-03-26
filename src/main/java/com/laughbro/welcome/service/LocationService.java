package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.location_params.LocationAddParams;
import com.laughbro.welcome.vo.params.location_params.LocationDeleteParams;
import com.laughbro.welcome.vo.params.location_params.LocationEditParams;

public interface LocationService {
    Result AddLocation(LocationAddParams params);

    Result DeleteLocation(LocationDeleteParams params);

    Result EditLocation(LocationEditParams params);

    Result GetLocationAll();
}
