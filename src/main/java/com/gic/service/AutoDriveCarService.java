package com.gic.service;

import com.gic.models.CarInputDetails;
import com.gic.models.CarEndingPosition;

public interface AutoDriveCarService {

    CarEndingPosition getCarEndingPositionAndDirection(CarInputDetails carInputDetails) throws Exception;
}
