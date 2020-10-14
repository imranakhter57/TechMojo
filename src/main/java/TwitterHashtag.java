package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TwitterHashtag {

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
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("src/main/resources/tweets.txt"));
        TwitterHashtag twt = new TwitterHashtag();
        while(in.hasNext()) {
            twt.hashtagExtractor(in.nextLine());
        }
        twt.sortHashtags();
        int count = 0;
        for(Map.Entry<String,Integer> hashtags : hashtagTracker.entrySet()){
            if(count >= 10){
                break;
            }
            System.out.println(hashtags.getKey()+" : " + hashtags.getValue());
            count++;
        }
    }
}
