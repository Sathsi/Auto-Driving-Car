package com.gic.utils;

import com.gic.exception.CarInputDetailValidationException;
import com.gic.models.AutonomousCar;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RequestValidator {

    public void validateCarAutoDriveInputDetails(String currentCoordinates, String currentFacingDirection,
                                                 String commands) throws Exception{

        validateStartCoordinate(currentCoordinates);
        validateStartDirection(currentFacingDirection);
        validateCommands(commands);

    }

    public void validateCarAutoDriveInputDetails (List<AutonomousCar> carInputDetailsList) throws Exception {
        if(!(carInputDetailsList.size() > 1)){
            throw new CarInputDetailValidationException(ValidationConst.NO_MULTIPLE_CARS,
                    ValidationConst.NO_MULTIPLE_CARS.message());
        }

        for (AutonomousCar carInput : carInputDetailsList) {
            validateCarAutoDriveInputDetails(carInput.getCurrentCoordinates(),
                    carInput.getCurrentFacingDirection(), carInput.getCommands());
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

        if(!Arrays.asList(directions).contains(startDirection.toUpperCase())){
            throw new CarInputDetailValidationException(ValidationConst.INVALID_DIRECTION,
                    ValidationConst.INVALID_DIRECTION.message());
        }
    }

    private void validateCommands(String commands) throws Exception {
        String[] commandsList = {"L", "R", "F"};
        AtomicBoolean isValidCommand = new AtomicBoolean(false);

        commands.chars().mapToObj(command -> String.valueOf((char) command))
                .forEach(command -> {
                    if(Arrays.asList(commandsList).contains(command.toUpperCase()))
                        isValidCommand.set(true);
                });

        if(!isValidCommand.get()){
            throw new CarInputDetailValidationException(ValidationConst.INVALID_COMMAND,
                    ValidationConst.INVALID_COMMAND.message());
        }

    }

}
