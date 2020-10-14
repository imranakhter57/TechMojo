package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TransactionTime {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Map<String,Long>   timeTracker = new HashMap<>();
        List<Long> timeDiff = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy - MM - dd h:mm a");
        Scanner sc = new Scanner(new File("src/main/resources/TransactionDetails.txt"));
        while(sc.hasNext()) {
            String[] transactionDetails = sc.nextLine().split(",");
            Date date = format.parse(transactionDetails[1]+transactionDetails[2]);
            if(transactionDetails[3].trim().equalsIgnoreCase("start")){
                timeTracker.put(transactionDetails[0],date.getTime());
            }else if (transactionDetails[3].trim().equalsIgnoreCase("end")){
                if(timeTracker.containsKey(transactionDetails[0])) {
                    timeDiff.add(date.getTime() - timeTracker.get(transactionDetails[0]));
                }
            }
        }
        System.out.println("Average Transaction Time is : "+timeDiff.stream().mapToDouble(i -> i).average().getAsDouble()/1000+"s");
    }
}
