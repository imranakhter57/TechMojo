package com.techmojo.services.Impl;

import com.techmojo.services.TimeServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeServicesImpl implements TimeServices {

    private static final Logger logger = LogManager.getLogger(TimeServicesImpl.class);

    public static Map<String,Long> timeTracker = new HashMap<>();
    public  static List<Long> timeDiff = new ArrayList<>();


    /**
     * @param transactions
     * @return
     * @throws ParseException
     */
    @Override
    public String getAvgTransactionTime(String transactions) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy - MM - dd h:mm a");
        String[] transactionDetails = transactions.split(",");
        Date date = format.parse(transactionDetails[1]+transactionDetails[2]);
        if(transactionDetails[3].trim().equalsIgnoreCase("start")){
            timeTracker.put(transactionDetails[0],date.getTime());
        }else if (transactionDetails[3].trim().equalsIgnoreCase("end")){
            if(timeTracker.containsKey(transactionDetails[0])) {
                timeDiff.add(date.getTime() - timeTracker.get(transactionDetails[0]));
            }
        }
        if(timeDiff.size() >0)
            return timeDiff.stream().mapToDouble(i -> i).average().getAsDouble()/1000+"s";
        else
            return null;
    }
}
