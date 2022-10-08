package injectorHeart;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import faultTypes.Fault;
import faultTypes.SpaceBasedFault;
import faultTypes.TimeBasedFault;

public class CSVReader {

	public static List<Fault> readFaultsFromCSV(String filePath){
		List<Fault> faults = new ArrayList<>();
		Path pathToFile = Paths.get(filePath);
		
		 try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			 
			 //Discard the Header's Lines
			 for(int i=0; i<9; i++)
				 br.readLine();
			 
			 //Read target
			 String target = br.readLine();
			 target = target.replace("\"", "");
			 String[] targetArr = target.split(",");
			 
			//Read type of faults
			 String typeOfFault = br.readLine();
			 typeOfFault = typeOfFault.replace("\"", "");
			 String[] faultTypeArr = typeOfFault.split(",");
			 
			 br.readLine(); // Discard faults header line

			 //Loop reading the faults until the end of the file
			 String line = br.readLine();
			 while(line != null) {
				 line = line.replace("\"", "");
				 String[] attributes = line.split(",");
		
				 //Check the type of fault!
				 Fault newFault = null;
				 if(faultTypeArr[1].equals(TimeBasedFault.FAULT_TYPE_TIMEBASED)) {
					 newFault = new TimeBasedFault(attributes);
				 }
				 else if(faultTypeArr[1].equals(SpaceBasedFault.FAULT_TYPE_SPACEBASED)) {
					 newFault = new SpaceBasedFault(attributes);
			
				 }
				 
				 if(newFault != null)
					 faults.add(newFault);
				 line = br.readLine();
			 }
			 
		 } catch (IOException e) {
	
		 }
		
		return faults;
	}
	
	public static List<String> getExperimentInformation(String filePath){
		ArrayList<String> header = new ArrayList<>();
		Path pathToFile = Paths.get(filePath);
		String line = null;
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			 //Read the Header's Lines
			 for(int i=0; i<4; i++) {
				 line = br.readLine();
				 header.add(line);
			 }
		 } catch (IOException e) { }
		
		return header;
	}
	
	public static List<String> getTargetsOptions(){
		ArrayList<String> options = new ArrayList<>();
		Path pathToFile = Paths.get("targets.csv");
		String line = null;
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			 line = br.readLine();
			 while(line != null) {
				 line = line.replace("\"", "");
				 options.add(line);
				 line = br.readLine();
			 }
		 } catch (IOException e) { }
		return options;
	}
	
	public static List<String> getConfigs(){
		ArrayList<String> configs = new ArrayList<>();
		Path pathToFile = Paths.get("config.csv");
		String line = null;
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			 line = br.readLine();
			 while(line != null) {
				 line = line.replace("\"", "");
				 configs.add(line);
				 line = br.readLine();
			 }
		 } catch (IOException e) { }
		return configs;
	}
	
}
