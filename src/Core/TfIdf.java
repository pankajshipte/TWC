package Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TfIdf {
	static HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
	static Tokenizer tokenizer = new Tokenizer(true, false, true, true, true, false, true);
	
	public static void getTfIdfFrequencyMap(int timeslot, HashMap<String, Double> ldaMap, List<String> lines, String rootPath){
		map.clear();
		for(String line : lines){
			if(StringUtils.isBlank(line))
				continue;

			line = line.toLowerCase().split(" ",3)[2];
			String[] tokens = tokenizer.getNGrams(line, 1);
			String[] tags = POSTagger.getInstance().tag(tokens);
			Set<String> dfs = new HashSet<String>();
			
			for(int i =0 ;i< tags.length; i++){
				String token = "";
				do {
					token += tokens[i];
					i++;
				} while (i< tags.length && tags[i]=="NNP");

				i--;
				
				if(StringUtils.isBlank(token))
					continue;

				List<Integer> values = new ArrayList<Integer>();
				if(!map.containsKey(token)){
					values.add(1);
					values.add(1);
				}else{
					List<Integer> vals = map.get(token);
					values.add(0 + vals.get(0));
					
					if(!dfs.contains(token)){
						values.add(1 + vals.get(1));
					}else{
						values.add(vals.get(1));
					}
				}
				
				dfs.add(token);
				map.put(token, values);
			}
		}
	}

	public static void getFinalTfIdfScore(double n, String output, int maxLimit){
		Formatter out = null;
		try {
			out = new Formatter(new FileOutputStream(output));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, Double> scoreMap = new HashMap<String, Double>();
		for(Map.Entry<String, List<Integer>> entries : map.entrySet()){
			double value = entries.getValue().get(0) * (Math.log(n/entries.getValue().get(1))) + 100;
			scoreMap.put(entries.getKey(), value);
		}
		
		List<Map.Entry<String, Double>> entries = new ArrayList<Map.Entry<String, Double>>(
				scoreMap.entrySet());
		
		Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		out.format("%s,%s\n","text","size");
		int count = 0;
		for (Map.Entry<String, Double> entry : entries) {
			if(count == maxLimit)
				break;
			count++;
			out.format("%s,%.0f\n", entry.getKey(),entry.getValue());
		}
		
		out.close();
	}

	public static int startTfIdf(String input , int timeInterval, String rootPath, int minLimit, int maxLimit, int prefix){
		HashMap<String,Double> hm = new HashMap<String,Double>();
		long start1 = System.currentTimeMillis();
		Main.timeInterval = timeInterval;
		try {
			Main.readLinesToList(new File(input), rootPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String outputFolder = rootPath+"web"+File.separator+"Outputs"+File.separator+"topic_words"+String.valueOf(prefix);
		
		int count = -1;
		for(File inp: Main.intervalFilePtr) {
			List<String> lines;
			lines = ReadFile.readLinesToList(inp.getAbsoluteFile());
			
			try {
				hm = TopicModel.getTopicScores(inp.getAbsolutePath(), lines.size(), rootPath);
				inp.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}

			GetFrequency.getLDAFrequencyMap(timeInterval, hm, lines, rootPath);

			if(hm.size() >= minLimit ) {
				count++;
				String outputFile = outputFolder+String.valueOf(count)+".csv";
				getFinalTfIdfScore(lines.size(), outputFile, maxLimit);
			}

		}
		System.out.println("Total time ::::" + (System.currentTimeMillis() - start1));
		return count+1;
	}
	
	public static void main(String[] args){
		TfIdf.startTfIdf("tp.txt", 1, "./", 2, 250, 1);
	}
}
