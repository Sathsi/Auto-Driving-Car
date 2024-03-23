package com.gic.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarInputDetails {
    private String name;
    private String currentCoordinates;
    private String currentFacingDirection;
    private String commands;
}
