package atlassian;

import java.util.HashMap;

class Logger {

    private HashMap<String,Integer> msgToTimestamp = new HashMap<>();
    public Logger() {
        
    }
    
    public boolean shouldPrintMessage(int timestamp, String message) {
       return  !(msgToTimestamp.containsKey(message) && msgToTimestamp.get(message) == timestamp) &&  timestamp ==  msgToTimestamp.compute(message, (k,currT)-> (currT==null || (timestamp-currT)>=10)?timestamp:currT);
    }
}