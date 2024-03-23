package com.gic.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AutoDriveCarServiceTest {

    @Test
    public void whenValidCarInputDetailsThenReturnEndingPosition(){

    }

    //validations
    // invalid input -> exception

    @Test
    public void whenXEqualsToBoundaryAndFaceEastThenIgnoreForwardCommand(){

    }

    @Test
    public void whenYZeroAndFaceSouthThenIgnoreForwardCommand(){

    }

    @Test
    public void whenYEqualsToBoundaryAndFaceNorthThenIgnoreForwardCommand(){

    }
}
