package com.gic.utils;

import com.gic.exception.CarInputDetailValidationException;
import com.gic.models.CarInputDetails;
import com.gic.models.CarInputRequest;
import com.gic.service.AutoDriveCarService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RequestValidator {

    public void validateCarAutoDriveInputDetails(CarInputRequest carInputDetailsRequest) throws Exception{

        for (CarInputDetails car : carInputDetailsRequest.getCarInputDetailsList()) {
            validateStartCoordinate(car.getCurrentCoordinates());
            validateStartDirection(car.getCurrentFacingDirection());
            validateCommands(car.getCommands());
        }

    }

    private void validateStartCoordinate (String currentCoordinates) throws Exception {
        String[] startCoordinates = currentCoordinates.split(",");

        if(startCoordinates.length != 2){
            throw new CarInputDetailValidationException(ValidationConst.INSUFFICIENT_COORDINATES,
                    ValidationConst.INSUFFICIENT_COORDINATES.message());
        }
        AtomicBoolean isDigit = new AtomicBoolean(false);
        Arrays.stream(startCoordinates).forEach(coordinate -> {
            if(coordinate.matches("\\d+")){
                isDigit.set(true);
            }
        });

        if(!isDigit.get()){
            throw new CarInputDetailValidationException(ValidationConst.INVALID_START_COORDINATES,
                    ValidationConst.INVALID_START_COORDINATES.message());
        }
    }

    private void validateStartDirection(String startDirection) throws Exception {
        String[] directions = {"N", "E", "S", "W"};

        if(!Arrays.asList(directions).contains(startDirection)){
            throw new CarInputDetailValidationException(ValidationConst.INVALID_DIRECTION,
                    ValidationConst.INVALID_DIRECTION.message());
        }
    }

    private void validateCommands(String commands) throws Exception {
        String[] commandsList = {"L", "R", "F"};
        AtomicBoolean isValidCommand = new AtomicBoolean(false);

        commands.chars().mapToObj(com -> String.valueOf((char) com))
                .forEach(command -> {
                    if(Arrays.asList(commandsList).contains(command))
                        isValidCommand.set(true);
                });

        if(!isValidCommand.get()){
            throw new CarInputDetailValidationException(ValidationConst.INVALID_COMMAND,
                    ValidationConst.INVALID_COMMAND.message());
        }

    }

}
