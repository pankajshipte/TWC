package Core;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by np on 3/25/2015.
 */
public class mallet_map {
	public static HashMap<String,Integer> getMalletMap(String input) throws IOException
	{
		String line;
		HashMap<String,Integer> hm = new HashMap<String, Integer>();
		InputStream fis = new FileInputStream(input);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		while ((line = br.readLine()) != null) {
			//  System.out.println(line);
			String[] splited = line.split("\\s+");    // split the line on Space..
			//  System.out.println(splited[2]);
			for(int i=2;i<splited.length;i=i+2)   // Keys occurs at every even location..
			{
				//    System.out.println(splited[i]);

				Integer value = hm.get(splited[i]);     // next position of key is its frequency..

				if(value == null) {   // check if Key is already mapped..
					String temp = splited[i+1];
					String[] split1 = temp.split("\\(");
					String[] split2 = split1[1].split("\\)");
					int freq = Integer.parseInt(split2[0]);

					//      System.out.println(split2[0]);
					hm.put(splited[i], freq);
				}

				else if(value != null)
				{
					String temp = splited[i+1];
					String[] split1 = temp.split("\\(");
					String[] split2 = split1[1].split("\\)");
					int freq = Integer.parseInt(split2[0]);
					value = value + freq;
					hm.put(splited[i],value);

				}
			}
		}
		br.close();
		System.out.println("hm.size"+hm.size());
		return hm;
	}

	public static void main(String[] args){
		try {
			mallet_map.getMalletMap("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}