package com.techmojo.controller;

import com.techmojo.services.TimeServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {

    private static final Logger logger = LogManager.getLogger(TimeController.class);

    @Autowired
    TimeServices timeServices;

    @PostMapping(value = "/getAvgTime")
    public ResponseEntity<String> getAvgTime(@RequestBody String transactions, @RequestHeader HttpHeaders rawHeaders){
        logger.info("In getAvgTime");
        try{
            return new ResponseEntity<>(timeServices.getAvgTransactionTime(transactions), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Something went wrong while getting Avg transaction Time", e);
            return new ResponseEntity<>("Something went wrong while getting Avg transaction Time", HttpStatus.BAD_REQUEST);
        }
    }
}
