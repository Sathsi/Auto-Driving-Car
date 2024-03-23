package com.gic.controller;

import com.gic.models.CarInputDetails;
import com.gic.models.CarEndingPosition;
import com.gic.service.AutoDriveCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;

@CrossOrigin
@RestController
@RequestMapping("/autoDriveCar")
@Api(tags = "Auto Drive Car Simulation")
public class AutoDriveCarController {

    private final AutoDriveCarService autoDriveCarService;

    public AutoDriveCarController(AutoDriveCarService autoDriveCarService) {
        this.autoDriveCarService = autoDriveCarService;
    }

    @ApiOperation(value = "Get the ending position and facing direction of the car", notes = "Enter input details of the current car position and field size")
    @RequestMapping(value = "/endingPosition", method = RequestMethod.POST)
    public ResponseEntity<Object> getCarEndingPositionAndDirection(@Valid final @RequestBody CarInputDetails carInputDetailsRequest) throws Exception{

        final CarEndingPosition carEndingPos = autoDriveCarService.getCarEndingPositionAndDirection(carInputDetailsRequest);
        return new ResponseEntity<Object>(carEndingPos, HttpStatus.OK);
    }

}
