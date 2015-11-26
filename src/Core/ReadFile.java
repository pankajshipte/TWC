package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadFile {
	public static final Charset charset = Charset.forName("UTF-8");
	/**
	 * Returns the buffered reader object
	 * @param file
	 * @return
	 */
	public static List<String> getBufferedReader(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		System.out.println("file is :::"+file.getAbsolutePath());
		try {
			System.out.println(Files.isReadable(file.toPath()));
			return Files.readAllLines(path, charset);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads content from file to set
	 * @param file
	 * @param ignoreCase
	 * @param useId (Whether to generate id for each line)
	 * @return
	 */
	public static Set<String> readLinesToSet(File file, 
			boolean ignoreCase, boolean useId) {
		Set<String> set = new HashSet<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));//getBufferedReader(file);
			String line = null;

			while((line = br.readLine()) != null) {
				line = line.toLowerCase();
				set.add(line);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return set;
	}

	public static List<String> readLinesToList(File file) {

		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));//getBufferedReader(file);
			String line = null;

			while((line = br.readLine()) != null) {
				line = line.toLowerCase();
				lines.add(line);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

}
