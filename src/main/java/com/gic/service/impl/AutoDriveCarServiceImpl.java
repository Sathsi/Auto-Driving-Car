package com.gic.service.impl;

import com.gic.models.CarInputDetails;
import com.gic.models.CarEndingPosition;
import com.gic.service.AutoDriveCarService;
import com.gic.utils.RequestValidator;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AutoDriveCarServiceImpl implements AutoDriveCarService {

    private RequestValidator requestValidator;

    public AutoDriveCarServiceImpl(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }

    @Override
    public CarEndingPosition getCarEndingPositionAndDirection(CarInputDetails carInputDetails) throws Exception {

        requestValidator.validateCarAutoDriveInputDetails(carInputDetails);

        String[] startCoordinates = carInputDetails.getCurrentCoordinates().split(",");
        int startXCoord = Integer.parseInt(startCoordinates[0]);
        int startYCoord = Integer.parseInt(startCoordinates[1]);

        CarEndingPosition carEndingPosition = calculateCarEndingPosition(carInputDetails.getWidth(),
                carInputDetails.getHeight(), startXCoord,startYCoord,
                carInputDetails.getCurrentFacingDirection(), carInputDetails.getCommands());


        return carEndingPosition;
    }

    private CarEndingPosition calculateCarEndingPosition(int width, int height,
                                                         int startXCoord, int startYCoord,
                                                         String startDirection, String commands){
        int[] xCoord = {0, 1, 0, -1};
        int[] yCoord = {1, 0, -1, 0};
        AtomicInteger x = new AtomicInteger(startXCoord);
        AtomicInteger y = new AtomicInteger(startYCoord);
        AtomicInteger facingDirectionIndex = new AtomicInteger(getIndex(startDirection.charAt(0)));

        commands.chars().forEach(command -> {
            if(Character.toUpperCase(command) == 'L'){
                facingDirectionIndex.set((facingDirectionIndex.get() - 1 + 4) % 4);

            } else if(Character.toUpperCase(command) == 'R'){
                facingDirectionIndex.set((facingDirectionIndex.get() + 1) % 4);

            } else if(Character.toUpperCase(command) == 'F') {
                int newXCoordinate = x.get() + xCoord[facingDirectionIndex.get()];
                int newYCoordinate = y.get() + yCoord[facingDirectionIndex.get()];
                if(newXCoordinate >= 0 && newXCoordinate < width
                        && newYCoordinate >= 0 && newYCoordinate < height){
                    x.set(newXCoordinate);
                    y.set(newYCoordinate);
                }

            }
        });

        CarEndingPosition carEndingPosition = CarEndingPosition.builder()
                .xCoordinate(x.get())
                .yCoordinate(y.get())
                .currentDirection(getDirection(facingDirectionIndex.get()))
                .build();

        return carEndingPosition;

    }

    public static int getIndex(char direction) {
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

    public static char getDirection(int index) {
        char[] directions = {'N', 'E', 'S', 'W'};
        return directions[index];
    }

}
