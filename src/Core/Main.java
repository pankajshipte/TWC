package Core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Created by SAMARTH on 3/24/2015.
 */


public class Main {

    public static int timeInterval;
    public static final Charset charset = Charset.forName("UTF-8");
    public static String[] patterns = new String[]{"hh:mm aa - dd MMM, yyyy"};
    public static TreeMap<String, List<String>> tweetMap = new TreeMap<String, List<String>>();
    public static List<File> intervalFilePtr = new ArrayList<File>();
    public static PriorityQueue<PQObject> sortedTweets = new PriorityQueue<PQObject>();

    public static String parseData(String tweet){
        int len = tweet.length();
        for (int i = 0; i < len; i++) {
            if((tweet.charAt(i) >= 'a' && tweet.charAt(i) <= 'z') || tweet.charAt(i) == ' ' || tweet.charAt(i) == '#' || (tweet.charAt(i) >= 'A' && tweet.charAt(i) <= 'Z')) {
                continue;
            }else{
                tweet = tweet.replace(tweet.charAt(i), ' ');
            }
        }
        return tweet;
    }

    public static String replaceHindiChars(String line) {
        line = line.replaceAll("अपराह्न", "PM");
        line = line.replaceAll("पूर्वाह्न", "AM");
        line = line.replaceAll("जन", "Jan");
        line = line.replaceAll("जनवरी", "january");
        line = line.replaceAll("फ़र", "Feb");
        line = line.replaceAll("फ़रवरी", "february");
        line = line.replaceAll("मार्च", "march");
        line = line.replaceAll("अप्रै", "Apr");
        line = line.replaceAll("अप्रैल", "april");
        line = line.replaceAll("मई", "May");
        line = line.replaceAll("जून", "june");
        line = line.replaceAll("जुल", "Jul");
        line = line.replaceAll("जुलाई", "july");
        line = line.replaceAll("आग", "Aug");
        line = line.replaceAll("आगस्त", "august");
        line = line.replaceAll("सित", "Sept");
        line = line.replaceAll("सितम्बर", "september");
        line = line.replaceAll("अक्टू", "Oct");
        line = line.replaceAll("अकतूबर", "october");
        line = line.replaceAll("नवं", "Nov");
        line = line.replaceAll("नवेम्बर", "november");
        line = line.replaceAll("दिस", "Dec");
        line = line.replaceAll("दिसम्बर", "december");

        return line;
    }

    public static void parseTimeAndTweet(String line){
        String[] splits = line.split("\t");
        if(splits.length >= 10) {
            splits[9] = splits[9].trim();

            Date date = null;
            try {
                String dat = replaceHindiChars(splits[9]);
                date = DateUtils.parseDateStrictly(dat, patterns);
            } catch (Exception e) {
                return;
            }

            if (date == null || StringUtils.isBlank(splits[8]))
                return;

            String tweet = parseData(splits[8]);
            sortedTweets.add(new PQObject(date, tweet));
        }
    }

    public static List<String> getBufferedReader(File file) {
        Path path = Paths.get(file.getAbsolutePath());
        try {
            return Files.readAllLines(path, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TreeMap<String, List<String>> readLinesToList(File file, String rootPath) throws IOException {
        List<String> lines = new ArrayList<String>();
        long start = System.currentTimeMillis();
        lines.addAll(getBufferedReader(file));
		System.out.println("Time for reading the file "+(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for(String line : lines){
            if(!line.isEmpty()) {
                parseTimeAndTweet(line);
            }
        }

        System.out.println("Time for parsing a line "+(System.currentTimeMillis() - start));
        start = System.currentTimeMillis();

        Date startDate = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Calendar dateCal = Calendar.getInstance();
        PQObject me = sortedTweets.poll();
        List<String> intervalList = new ArrayList<String>();
        while (!sortedTweets.isEmpty()) {
            if(startDate == null){
                startDate = me.date;
                dateCal.setTime(startDate);
                dateCal.add(Calendar.HOUR, timeInterval);
            }
            File tempFile = File.createTempFile("tweetInterval", ".txt", new File(rootPath+"temp"));
            BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(tempFile));
            while(!sortedTweets.isEmpty() && me.date.compareTo(dateCal.getTime()) <= 0){
                intervalList.add(me.tweet);
                tempFileWriter.write(dateFormatter.format(me.date) + " X " + me.tweet);
                tempFileWriter.newLine();
                /*System.out.println(me.date.toString() + ":" + me.tweet);*/
                me = sortedTweets.poll();
            }
            if(sortedTweets.isEmpty()) {
                intervalList.add(me.tweet);
                tempFileWriter.write(dateFormatter.format(me.date)+",X,"+me.tweet);
                tempFileWriter.newLine();
//                System.out.println(me.date.toString()+":"+me.tweet);
            }
            tweetMap.put(dateFormatter.format(startDate), intervalList);
            tempFileWriter.close();
            intervalFilePtr.add(tempFile);
            intervalList.clear();
            startDate = null;
            /*if(sortedTweets.isEmpty() && me.date.compareTo(dateCal.getTime()) > 0) {
                intervalList.add(me.tweet);
                System.out.println(me.date.toString()+":"+me.tweet);
                tweetMap.put(dateFormatter.format(startDate).toString(), intervalList);
                System.out.println();
                intervalList.clear();
                startDate = null;
            }*/
        }

        System.out.println("Total time :: "+(System.currentTimeMillis() - start));
        System.out.println("size of tweet map "+ tweetMap.size());
        return tweetMap;
    }

    /*public static void main(String[] args){
        long start = System.currentTimeMillis();
        if(args.length != 2){
            System.out.println("Usage: <PrgramName> <FileName> <TimeInterval>");
            System.exit(-1);
        }
        timeInterval = Integer.parseInt(args[1]);
        try {
            readLinesToList(new File(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Total final time ::::"+(System.currentTimeMillis()-start));
    }*/

}
