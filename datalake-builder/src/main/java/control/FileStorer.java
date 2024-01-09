package control;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileStorer implements EventStorer {
	//Constructor
	private String eventsPath;
	public FileStorer(String eventsPath) {
		this.eventsPath = eventsPath;
	}

	@Override
	public void storeEvents(String event) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(getEventsPath(event), true))) {
			writer.write(event);
			writer.newLine(); writer.newLine();
		} catch (IOException e) {
			System.err.println("ERROR: " + e);
		}
	}
	public String getEventsPath(String event) throws IOException {
		String fileName = getFileName(event);
		eventsPath = String.format(eventsPath, "OpenWeatherMapProvider", fileName);
		String fullPath = System.getProperty("user.home") + "/Downloads" + "/" + eventsPath;
		Path absolutePath = Paths.get(fullPath);
		Files.createDirectories(absolutePath.getParent());
		return String.valueOf(absolutePath);
	}
	public String getFileName(String event){
		try{
			String fileName = null;
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(event);
			if (matcher.find()) {
				fileName = matcher.group();
				fileName = fileName.replace("-", "");
			}
			return fileName;
		} catch (Exception e){
			System.out.println("Could not find file name: " + e);
			return null;
		}
	}
}
