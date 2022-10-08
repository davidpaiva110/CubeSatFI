package edcDataCollector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

public class CSVDataWriter {
	
	private String fileName;
	private CSVWriter writer;
	
	
	public CSVDataWriter(String fileName) throws IOException {
		super();
		this.fileName = fileName;
		writer = new CSVWriter(new FileWriter(fileName, true));
	}
	
	public CSVDataWriter(String fileName, boolean rewirteOpt) throws IOException {
		super();
		this.fileName = fileName;
		writer = new CSVWriter(new FileWriter(fileName, rewirteOpt));
	}


	public void addMessageDecodedToFile( String [] record) throws IOException {
	      writer.writeNext(record);
	}
	
	public void writeAll(List<String[]> data) {
		writer.writeAll(data);
	}
	
	public void closeWriter() throws IOException {
		writer.close();
	}
	
}
