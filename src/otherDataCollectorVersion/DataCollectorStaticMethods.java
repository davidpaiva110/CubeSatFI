package otherDataCollectorVersion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edcDataCollector.CSVDataWriter;
import faultTypes.Fault;

public class DataCollectorStaticMethods {

	
	
	public static void createFileResults(List<String> headers, String fileName) throws IOException {
		CSVDataWriter csvWriterr = new CSVDataWriter(fileName);
		for (String info : headers) {
			String [] msg = info.replace("\"", "").split(",");
			csvWriterr.addMessageDecodedToFile(msg);
		}
		String [] msg = new String[2];
		msg[0] = "Execution Date: ";
		msg[1] = getCurrentDate();
		csvWriterr.addMessageDecodedToFile(msg);
		//Write Spaces
		String[] space = new String[1];
		space[0] = "-";
		for(int i=0; i<5; i++)
			csvWriterr.addMessageDecodedToFile(space);
		csvWriterr.closeWriter();
	}
	
	public static void createfaultHeader(Fault fault, String fileName) {
		try {
			CSVDataWriter csvWriterr = new CSVDataWriter(fileName);
			csvWriterr.addMessageDecodedToFile(fault.headerToArray());
			csvWriterr.addMessageDecodedToFile(fault.toArray());
			csvWriterr.closeWriter();
		} catch (IOException e1) {
		}
	}
	
	
	public static void wirteProgramCounter(String pc, String fileName) {
		try {
			CSVDataWriter csvWriterr = new CSVDataWriter(fileName);
			String [] separator = new String[1];
			separator[0] = "-";
			String [] msg = new String[2];
			csvWriterr.addMessageDecodedToFile(separator);
			msg[0] = "Program Counter: ";
			msg[1] = pc;
			csvWriterr.addMessageDecodedToFile(msg);
			csvWriterr.addMessageDecodedToFile(separator);
			csvWriterr.addMessageDecodedToFile(separator);
			csvWriterr.closeWriter();
		} catch (IOException e1) {
		}
	}
	
	
	
	
	
	
	/**
	 * Get current data
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date(); 
	    return formatter.format(date); 
	}
	
}
