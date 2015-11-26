package Core;

/**
 * Created by SAMARTH on 3/26/2015.
 */
import java.util.Calendar;
import java.util.Date;

public class PQObject implements Comparable<PQObject> {
    Date date;
    String tweet;

    public PQObject(Date date, String tweet) {
        this.tweet = tweet;
        this.date = date;
    }

    @Override
    public int compareTo(PQObject o) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(this.date);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(o.date);
        if (cal1.before(cal2)){
            return -1;
        } else if(cal1.after(cal2)){
            return 1;
        }else{
            return 0;
        }
    }
}