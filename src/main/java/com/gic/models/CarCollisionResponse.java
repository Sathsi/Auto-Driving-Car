package com.gic.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CarCollisionResponse {

    private List<String> carNames;
    private String collisionPosition;
    private String step;
}
