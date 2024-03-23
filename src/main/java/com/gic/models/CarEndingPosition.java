package com.gic.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class CarEndingPosition {
    private int xCoordinate;
    private int yCoordinate;
    private char direction;

    // Override equals method for comparing positions
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CarEndingPosition position = (CarEndingPosition) obj;
        return xCoordinate == position.xCoordinate && yCoordinate == position.yCoordinate;
    }

    // Override hashCode method for using Position as key in Map
    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

}
