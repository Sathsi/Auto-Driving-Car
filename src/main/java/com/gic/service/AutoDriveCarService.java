package com.gic.service;

import com.gic.models.CarCollisionResponse;
import com.gic.models.CarInputDetails;
import com.gic.models.CarEndingPosition;
import com.gic.models.CarInputRequest;

public interface AutoDriveCarService {

    CarEndingPosition getCarEndingPositionAndDirection(CarInputRequest carInputDetails) throws Exception;

    CarCollisionResponse isCarCollisionHappen(CarInputRequest carInputDetails) throws Exception;
}
