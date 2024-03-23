package com.gic.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarEndingPosition {
    private int xCoordinate;
    private int yCoordinate;
    private char currentDirection;

}
