package com.techmojo.controller;

import com.techmojo.services.TweetServices;
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

import java.util.ArrayList;
import java.util.List;

@RestController
public class TweetController {

    private static final Logger logger = LogManager.getLogger(TweetController.class);

    @Autowired
    TweetServices tweetServices;

    @PostMapping(value = "/getTrendingHashtag")
    public ResponseEntity<List<String>> getTrendingHashtag(@RequestBody String tweet, @RequestHeader HttpHeaders rawHeaders){
        logger.info("In getTrendingHashtag");
        try{
            return new ResponseEntity<>(tweetServices.getTrendingHashtag(tweet), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Something went wrong while getting trending hashtag", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
