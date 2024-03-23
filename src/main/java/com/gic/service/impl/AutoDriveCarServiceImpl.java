package com.gic.service.impl;

import com.gic.models.CarCollisionResponse;
import com.gic.models.CarInputDetails;
import com.gic.models.CarEndingPosition;
import com.gic.models.CarInputRequest;
import com.gic.models.AutonomousCar;
import com.gic.service.AutoDriveCarService;
import com.gic.utils.RequestValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AutoDriveCarServiceImpl implements AutoDriveCarService {

    private RequestValidator requestValidator;

    public AutoDriveCarServiceImpl(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }

    @Override
    public CarEndingPosition getCarEndingPositionAndDirection(CarInputDetails carInputDetails) throws Exception {

        requestValidator.validateCarAutoDriveInputDetails(carInputDetails.getCurrentCoordinates(),
                carInputDetails.getCurrentFacingDirection(),carInputDetails.getCommands());

        Map<String, Integer> coordinates = getCarCoordinate(carInputDetails.getCurrentCoordinates().split(","));

        CarEndingPosition carEndingPosition = calculateCarEndingPosition(carInputDetails.getFieldDimension().getWidth(),
                carInputDetails.getFieldDimension().getHeight(), coordinates.get("x"),coordinates.get("y"),
                carInputDetails.getCurrentFacingDirection(),
                carInputDetails.getCommands());


        return carEndingPosition;
    }

    @Override
    public CarCollisionResponse isCarCollisionHappen(CarInputRequest carInputRequest) throws Exception {

        requestValidator.validateCarAutoDriveInputDetails(carInputRequest.getCarInputDetailsList());

        //boolean isCollisionDetected = false;

        CarCollisionResponse carCollisionResponse = null;

        //Find the maximum command length among all cars. Max length is consider as max driving time
        int drivingTime = 0;
        for (AutonomousCar car : carInputRequest.getCarInputDetailsList()) {
            String commands = car.getCommands();
            int commandLength = commands.length();
            if (commandLength > drivingTime) {
                drivingTime = commandLength;
            }
        }

        AutonomousCar carOne = new AutonomousCar();
        AutonomousCar carTwo =  new AutonomousCar();


        if (carInputRequest.getCarInputDetailsList().size() >= 2) {
            carOne = carInputRequest.getCarInputDetailsList().get(0);
            carTwo = carInputRequest.getCarInputDetailsList().get(1);
        }

        Map<String, Integer> startCoordinatesCar1 = getCarCoordinate(carOne.getCurrentCoordinates().split(","));
        Map<String, Integer> startCoordinatesCar2 = getCarCoordinate(carTwo.getCurrentCoordinates().split(","));

        CarEndingPosition carOnePos =  CarEndingPosition.builder().xCoordinate(startCoordinatesCar1.get("x"))
                .yCoordinate(startCoordinatesCar1.get("y")).direction(carOne.getCurrentFacingDirection().charAt(0)).build();

        CarEndingPosition carTwoPos = CarEndingPosition.builder().xCoordinate(startCoordinatesCar2.get("x"))
                .yCoordinate(startCoordinatesCar2.get("y")).direction(carTwo.getCurrentFacingDirection().charAt(0)).build();

        // Check for collision with other cars
        for(int i=0; i < drivingTime; i++){
            carOnePos = calculateCarEndingPosition(carInputRequest.getFieldDimension().getWidth()
                    ,carInputRequest.getFieldDimension().getHeight(),carOnePos.getXCoordinate(),carOnePos.getYCoordinate()
                    , String.valueOf(carOnePos.getDirection()), String.valueOf(carOne.getCommands().toCharArray()[i]));

            carTwoPos = calculateCarEndingPosition(carInputRequest.getFieldDimension().getWidth()
                    ,carInputRequest.getFieldDimension().getHeight(),carTwoPos.getXCoordinate(),carTwoPos.getYCoordinate()
                    , String.valueOf(carTwoPos.getDirection()), String.valueOf(carTwo.getCommands().toCharArray()[i]));

            if(carOnePos.getXCoordinate() == carTwoPos.getXCoordinate() &&
                    carOnePos.getYCoordinate() == carTwoPos.getYCoordinate()){
                carCollisionResponse = CarCollisionResponse.builder()
                        .carNames(carOne.getName() + " " + carTwo.getName())
                        .collisionPosition(carOnePos.getXCoordinate() + " " + carOnePos.getYCoordinate())
                        .step(i+1)
                        .build();
                break;
            }
        }
        
        return carCollisionResponse;
    }

    private CarEndingPosition calculateCarEndingPosition(int width, int height,
                                                         int startXCoord, int startYCoord,
                                                         String startDirection, String commands){
        int[] xCoord = {0, 1, 0, -1};
        int[] yCoord = {1, 0, -1, 0};
        AtomicInteger x = new AtomicInteger(startXCoord);
        AtomicInteger y = new AtomicInteger(startYCoord);
        AtomicInteger facingDirectionIndex = new AtomicInteger(getIndex(startDirection.toUpperCase().charAt(0)));

        commands.chars().forEach(command -> {
            if(Character.toUpperCase(command) == 'L'){
                facingDirectionIndex.set((facingDirectionIndex.get() + 3) % 4);

            } else if(Character.toUpperCase(command) == 'R'){
                facingDirectionIndex.set((facingDirectionIndex.get() + 1) % 4);

            } else if(Character.toUpperCase(command) == 'F') {
                int newXCoordinate = x.get() + xCoord[facingDirectionIndex.get()];
                int newYCoordinate = y.get() + yCoord[facingDirectionIndex.get()];

                // Check boundary conditions
                if(newXCoordinate >= 0 && newXCoordinate <= width
                        && newYCoordinate >= 0 && newYCoordinate <= height){
                    x.set(newXCoordinate);
                    y.set(newYCoordinate);
                }

            }
        });

        CarEndingPosition carEndingPosition = CarEndingPosition.builder()
                .xCoordinate(x.get())
                .yCoordinate(y.get())
                .direction(getDirection(facingDirectionIndex.get()))
                .build();

        return carEndingPosition;

    }

    private static int getIndex(char direction) {
        switch (direction) {
            case 'N':
                return 0;
            case 'E':
                return 1;
            case 'S':
                return 2;
            case 'W':
                return 3;
            default:
                return -1; // Invalid direction
        }
    }

    private static char getDirection(int index) {
        char[] directions = {'N', 'E', 'S', 'W'};
        return directions[index];
    }

    private Map<String, Integer> getCarCoordinate(String[] startCoordinates){
        Map<String, Integer> positions = new HashMap<>();
        positions.put("x", Integer.parseInt(startCoordinates[0]));
        positions.put("y", Integer.parseInt(startCoordinates[1]));
        return positions;
    }


}
