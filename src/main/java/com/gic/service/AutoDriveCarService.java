package com.gic.service;

import com.gic.models.CarCollisionResponse;
import com.gic.models.CarInputDetails;
import com.gic.models.CarEndingPosition;

import java.util.List;

public interface AutoDriveCarService {

    CarEndingPosition getCarEndingPositionAndDirection(CarInputDetails carInputDetails) throws Exception;

    CarCollisionResponse isCarCollisionHappen(List<CarInputDetails> carInputDetails) throws Exception;
}
