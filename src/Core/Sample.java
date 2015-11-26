package Core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public class Sample {
	static HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
	static Tokenizer tokenizer = new Tokenizer(true, false, true, true, true, false, true);

	public static void getFrequencyMap(int timeslot, HashMap<String, Integer> ldaMap, List<String> lines){
		//		System.out.println("lda Map "+ ldaMap);
		//		Set<String> keys = ldaMap.keySet();
		for(String line : lines){
			if(StringUtils.isBlank(line))
				continue;

			line = line.toLowerCase().split(" ",3)[2];
			String[] tokens = tokenizer.getNGrams(line, 1);
			String[] tags = POSTagger.getInstance().tag(tokens);
			Set<String> dfs = new HashSet<String>();

			for(int i =0 ;i< tags.length; i++){
				String token = "";
				int score = 0;
				do {
					/*if(!ldaMap.containsKey(tokens[i])){
//						System.out.println("token is ::: "+ tokens[i]);
						i++;
						break;
					}*/
					token += tokens[i];
					//					score += ldaMap.get(tokens[i]);
					i++;
				} while (i< tags.length && tags[i]=="NNP");

				i--;

				if(StringUtils.isBlank(token))
					continue;

				List<Integer> values = new ArrayList<Integer>();
				if(!map.containsKey(token)){
					values.add(score);
					values.add(1);
					values.add(1);
				}else{
					List<Integer> vals = map.get(token);
					values.add(score + vals.get(0));
					values.add(1 + vals.get(1));

					if(!dfs.contains(token)){
						values.add(1 + vals.get(2));
					}else{
						values.add(vals.get(2));
					}
				}

				dfs.add(token);
				map.put(token, values);
			}
		}

	}

	public static void getFinalScore(double n, String output, int count){
		Formatter out = null;
		try {
			out = new Formatter(new FileOutputStream(output));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		HashMap<String, Double> scoreMap = new HashMap<String, Double>();
		out.format("%s,%s,%s\n","text","size","topic");
		for(Map.Entry<String, List<Integer>> entries : map.entrySet()){
			double value = entries.getValue().get(0)+ entries.getValue().get(1) * (Math.log(n/entries.getValue().get(2)));
			//			scoreMap.put(entries.getKey(), value);
			out.format("%s,%.0f,%d\n", entries.getKey(),value, count);
		}

		out.close();
	}

	/*public static void start(String input , int timeInterval){
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		long start1 = System.currentTimeMillis();
		long start = System.currentTimeMillis();

		Main.timeInterval = timeInterval;
		TreeMap<String, List<String>> tweetMap = new TreeMap<String, List<String>>();

		try {
			tweetMap = Main.readLinesToList(new File(input));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Total Phase one time ::::"+(System.currentTimeMillis()-start));

		String outputFolder = "Outputs/topic_words";
		int count = 0;
		count++;

		start = System.currentTimeMillis();
		
		System.out.println("tweetmap size ::: "+ tweetMap.size());

		for(Map.Entry<String, List<String>> entry : tweetMap.entrySet()){
			count++;
			String outputFile = outputFolder+String.valueOf(count)+".csv";

			List<String> lines = entry.getValue();
			System.out.println("no of lines in file " + lines.size());

			Sample.getFrequencyMap(1, hm, lines);
			System.out.println("time 1: " + (System.currentTimeMillis() - start));

			start = System.currentTimeMillis();
			getFinalScore(lines.size(), outputFile, count);
			System.out.println("time 2: " + (System.currentTimeMillis() - start));
		}
		System.out.println("Total time ::::"+(System.currentTimeMillis()-start1));
	}*/

	/*public static void main(String[] args){
		GetFrequency.startLDA(args[0], Integer.parseInt(args[1]), "/", 1, 250, 123.0);
	}*/
}
