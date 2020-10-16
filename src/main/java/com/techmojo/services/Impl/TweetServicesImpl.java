package com.techmojo.services.Impl;

import com.techmojo.controller.TweetController;
import com.techmojo.services.TweetServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TweetServicesImpl  implements TweetServices {

    private static final Logger logger = LogManager.getLogger(TweetServicesImpl.class);
    public static Map<String,Integer> hashtagTracker = new HashMap<>();

    private void hashtagExtractor(String tweet){
        Pattern hashtagPattern = Pattern.compile("#(\\S+)");
        Matcher mat = hashtagPattern.matcher(tweet);
        while (mat.find()) {
            hashtagTracker.merge(mat.group(1).toLowerCase(), 1, Integer::sum);
            //hashtagTracker.merge(mat.group(1).toLowerCase(), 1, Integer::sum); -> to have case senstive count
        }
    }

    private void sortHashtags(){
        hashtagTracker =  hashtagTracker.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
    @Override
    public List<String> getTrendingHashtag(String tweet) {
        List<String> response = new ArrayList<>();
        hashtagExtractor(tweet);
        sortHashtags();
        int count = 0;
        for(Map.Entry<String,Integer> hashtags : hashtagTracker.entrySet()){
            if(count >= 10){
                break;
            }
            response.add(hashtags.getKey());
            count++;
        }
        return response;
    }
}
