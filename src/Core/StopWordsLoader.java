package Core;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class StopWordsLoader {
	private static Set<String> stopwords = new HashSet<String>();
	private static final StopWordsLoader INSTANCE = new StopWordsLoader();
	
	/**
	 * Private constructor which loads all the stopwords from the file.
	 */
	private StopWordsLoader(){
		load();
	}

	public void load() {
		String rootPath = getClass().getResource("/").getPath();
		rootPath = rootPath.substring(0, rootPath.indexOf("TWC") + 3)+File.separator;
		System.out.println("***"+rootPath);
		File file = new File(rootPath+"conf"+File.separator+"stopwords");
		Set<String> items = ReadFile.readLinesToSet(file, true, true);
		
		synchronized (stopwords) {
			stopwords.clear();
			stopwords.addAll(items);
		}
		
		items.clear();
		System.out.println("Loaded stopwords of size ::: " + stopwords.size());
	}
	
	/**
	 * Returns the object holding stopword loader instance
	 * @return the instance object
	 */
	public static StopWordsLoader getLoader(){
		return INSTANCE;
	}
	
	/**
	 * Returns the list of stopwords 
	 * @return 
	 */
	public Set<String> getStopWords(){
		return stopwords;
	}
	
	/**
	 * Returns true if the id is a stopword 
	 * else returns false.
	 * @return boolean
	 */
	public boolean isStopWord(String id){
		if(stopwords.contains(id))
			return true;
		else
			return false;
	}
}