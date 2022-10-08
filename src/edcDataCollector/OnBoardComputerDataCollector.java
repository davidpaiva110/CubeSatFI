package edcDataCollector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import faultTypes.Fault;

public class OnBoardComputerDataCollector implements Runnable{

	// Serial Communication
	private SerialPortCommunication serialCommun;
	
	// EDC USB interface
	private Edc edc;
	
	//Data collection time interval
	private int timeInterval;
	
	//Semaphore
	private Semaphore semaphore = new Semaphore(1);
	private boolean toContinue = true;
	
	
	//File Results Name
	private String fileName;
	
	//CSV Writer to save data
	private CSVDataWriter csvWriter;
	
	//Fault injected
	Fault fault;
	
	//Program Counter
	String programCounter;
	
	
	public OnBoardComputerDataCollector(int timeInterval, String fileName, Fault fault, String programCounter) throws IOException {
		this.serialCommun = new SerialPortCommunication();
		this.timeInterval = timeInterval;
		this.fileName = fileName;
		this.csvWriter = new CSVDataWriter(fileName);
		this.fault = fault;
		this.programCounter = programCounter;
	}
	
	public OnBoardComputerDataCollector(int timeInterval, String fileName, Fault fault) throws IOException {
		this.serialCommun = new SerialPortCommunication();
		//this.serialCommun.cleanInputStreamBuffer(); // ??
		this.timeInterval = timeInterval;
		this.fileName = fileName;
		this.csvWriter = new CSVDataWriter(fileName);
		this.fault = fault;
	}
	
	
	
	/**
	 * Create the file where the results will be saved
	 * @throws IOException
	 */
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


	/**
	 * Get the List of Available USB Ports in the host PC
	 * @return
	 */
	public ArrayList<String> getPortList() {
		return serialCommun.getPortList();
	}
	
	/**
	 * Create Edc object and configure serial Communication with chosen USB Port and the right boud rate value
	 * @param usbPortID
	 */
	public void createEdcObject(String usbPortID) {
		serialCommun.connect(usbPortID, SerialPortCommunication.EDC_BAUD_RATE);
		edc = new Edc(serialCommun);
	}
	
	public void collectData() {
		PTTPackage pkg = null;
		HKPackage hkP = null;
		int countDecodedMessages = 0;
		List<String[]> decodedMessages = new ArrayList<>();
	
		// Write Fault Information
		try {
			csvWriter.addMessageDecodedToFile(fault.headerToArray());
			csvWriter.addMessageDecodedToFile(fault.toArray());
		} catch (IOException e1) {}
		
		// Write Program Counter
		String[] pcT = new String[1];
        pcT[0] = programCounter;
        //try { csvWriter.addMessageDecodedToFile(pcT); } catch (IOException e1) {}
        decodedMessages.add(pcT);
		
		//Set EDC Current Time
		//edc.rtc_set_current_time();
		
        
        //PTT Resume
        //System.out.println("ppt_resume");
        //edc.ptt_resume();
        try { Thread.sleep(50); } catch (InterruptedException ex) { }
        
        //clean buffer
        //serialCommun.cleanInputStreamBuffer();
        
        //Get EDC State
        //System.out.println("get_state");
        SSFPackage state = null;
        try {
        	state = edc.get_state();
        }catch(Exception ex) {
        	String[] errorMsg = new String[1];
        	errorMsg[0] = "[Error getting SSFPackage] " + ex.getMessage();
            //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
            decodedMessages.add(errorMsg);
        }
        //state.print();
        
        //Create Timer
        Thread timer = new Thread(new CountDownTimer());
        timer.start();
        
        // Read message until the end of the time window
        try { semaphore.acquire(); } catch (InterruptedException e) { }
        while(toContinue){
        	semaphore.release();
        	//If the buffer is not empty
        	if(state != null) {
	            if(state.getPttAv() > 0){
	            	//Get House Keeping Package
	            	try {
	            		hkP = edc.get_hk_pkg();
	            	}catch(Exception ex) {
	            		String[] errorMsg = new String[1];
	                	errorMsg[0] = "[Error getting HK Package] " + ex.getMessage();
	                    //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
	                    decodedMessages.add(errorMsg);
	            	};
	            	
	            	//System.out.println("get_ptt_pkg");
	                //Get Last Message from the buffer
	            	try {
		                pkg = edc.get_ptt_package();
		                String [] msgDecoded = pkg.getMessage();
		               // try { csvWriter.addMessageDecodedToFile(msgDecoded); } catch (IOException e) {}
		                countDecodedMessages++;
		                decodedMessages.add(msgDecoded);
	            	}catch(Exception ex){
	            		String[] errorMsg = new String[1];
	                	errorMsg[0] = "[Error getting PTTPackage] " + ex.getMessage();
	                    //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
	                    decodedMessages.add(errorMsg);
	            	}
	                //Remove the Last Message read from the buffer.
	                edc.ptt_pop();
	            }
	            else //Wait one second if buffer is empty
	                try { Thread.sleep(1000); } catch (InterruptedException ex) { }
        	}
            //Get EDC State
            try {
            	state = edc.get_state();
            }catch(Exception ex) {
            	String[] errorMsg = new String[1];
            	errorMsg[0] = "[Error getting SSFPackage] " + ex.getMessage();;
                //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
                decodedMessages.add(errorMsg);
            }
            //state.print();
            try { semaphore.acquire(); } catch (InterruptedException e) { }
        } // End if
        
        //Disconnect Port
        serialCommun.disconect();
        
        //Save number of Message decoded
        String[] numberMsgDecoded = new String[2];
        numberMsgDecoded[0] = "Nº Message Decoded";
        numberMsgDecoded[1] = ""+ countDecodedMessages;
        try { csvWriter.addMessageDecodedToFile(numberMsgDecoded); } catch (IOException e1) {}
        //Save the messages decoded or the error messages
        for (String[] msg : decodedMessages)
        	try { csvWriter.addMessageDecodedToFile(msg); } catch (IOException e1) {}
        	
		
        
        // Write One Space
        String[] space = new String[1];
		space[0] = "-";
        try { csvWriter.addMessageDecodedToFile(space); } catch (IOException e1) {}
        try { csvWriter.closeWriter(); } catch (IOException e) {}
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
	
	
	/*
	 * Timer Thread - Calculate the time window to collect data
	 */
	public class CountDownTimer implements Runnable {		
		public void run() {
			try { Thread.sleep(timeInterval); } catch (InterruptedException e) {	}
			try { semaphore.acquire();} catch (InterruptedException e) { }
			toContinue = false;
			semaphore.release();	
		}
	}
	
	/**
	 * Disconnect USB connection
	 */
	public void Disconnect() {
		//Disconnect Port
        serialCommun.disconect();
	}

	public void ptt_resume() {
		edc.ptt_resume();
	}

	public long rtc_set_current_time() {
		return edc.rtc_set_current_time();
	}
	
	public void writeMessageOnFile(String text) {
		String [] msg = new String[1];
		msg[0] =  text;
		try {
			csvWriter.addMessageDecodedToFile(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProgramCounter() {
		return programCounter;
	}

	public void setProgramCounter(String programCounter) {
		this.programCounter = programCounter;
	}
	
	public void ppt_pause() {
		edc.ptt_pause();
	}
	
	public void ptt_clear() {
		edc.ptt_clear();
	}
	
	public void sample_start() {
		edc.sampler_start();
	}
	
	
	
	
	
	
	@Override
	public void run() {
		collectDataThread();
	}
	
	
	public void collectDataThread() {
		PTTPackage pkg = null;
		HKPackage hkP = null;
		int countDecodedMessages = 0;
		List<String[]> decodedMessages = new ArrayList<>();
	
		// Write Fault Information
		try {
			csvWriter.addMessageDecodedToFile(fault.headerToArray());
			csvWriter.addMessageDecodedToFile(fault.toArray());
		} catch (IOException e1) {}
		
		// Write Program Counter
		String[] pcT = new String[1];
        pcT[0] = programCounter;
        //try { csvWriter.addMessageDecodedToFile(pcT); } catch (IOException e1) {}
        decodedMessages.add(pcT);
		

        
        //Get EDC State
        //System.out.println("get_state");
        SSFPackage state = null;
        try {
        	state = edc.get_state();
        }catch(Exception ex) {
        	String[] errorMsg = new String[1];
        	errorMsg[0] = "[Error getting SSFPackage] " + ex.getMessage();
            decodedMessages.add(errorMsg);
        }
        //state.print();
        
        //Create Timer
        //Thread timer = new Thread(new CountDownTimer());
        //timer.start();
        
        // Read message until the end of the time window
        try { semaphore.acquire(); } catch (InterruptedException e) { }
        while(toContinue){
        	semaphore.release();
        	//If the buffer is not empty
        	if(state != null) {
	            if(state.getPttAv() > 0){
	            	//Get House Keeping Package
	            	try {
	            		hkP = edc.get_hk_pkg();
	            	}catch(Exception ex) {
	            		String[] errorMsg = new String[1];
	                	errorMsg[0] = "[Error getting HK Package] " + ex.getMessage();
	                    //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
	                    decodedMessages.add(errorMsg);
	            	};
	            	
	            	//System.out.println("get_ptt_pkg");
	                //Get Last Message from the buffer
	            	try {
		                pkg = edc.get_ptt_package();
		                String [] msgDecoded = pkg.getMessage();
		               // try { csvWriter.addMessageDecodedToFile(msgDecoded); } catch (IOException e) {}
		                countDecodedMessages++;
		                decodedMessages.add(msgDecoded);
	            	}catch(Exception ex){
	            		String[] errorMsg = new String[1];
	                	errorMsg[0] = "[Error getting PTTPackage] " + ex.getMessage();
	                    //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
	                    decodedMessages.add(errorMsg);
	            	}
	                //Remove the Last Message read from the buffer.
	                edc.ptt_pop();
	            }
	            else //Wait one second if buffer is empty
	                try { Thread.sleep(1000); } catch (InterruptedException ex) { }
        	}
            //Get EDC State
            try {
            	state = edc.get_state();
            }catch(Exception ex) {
            	String[] errorMsg = new String[1];
            	errorMsg[0] = "[Error getting SSFPackage] " + ex.getMessage();;
                //try { csvWriter.addMessageDecodedToFile(errorMsg); } catch (IOException e1) {}
                decodedMessages.add(errorMsg);
            }
            //state.print();
            try { semaphore.acquire(); } catch (InterruptedException e) { }
        } // End if
        
        //Disconnect Port
        serialCommun.disconect();
        
        //Save number of Message decoded
        String[] numberMsgDecoded = new String[2];
        numberMsgDecoded[0] = "Nº Message Decoded";
        numberMsgDecoded[1] = ""+ countDecodedMessages;
        try { csvWriter.addMessageDecodedToFile(numberMsgDecoded); } catch (IOException e1) {}
        //Save the messages decoded or the error messages
        for (String[] msg : decodedMessages)
        	try { csvWriter.addMessageDecodedToFile(msg); } catch (IOException e1) {}
        	
		
        
        // Write One Space
        String[] space = new String[1];
		space[0] = "-";
        try { csvWriter.addMessageDecodedToFile(space); } catch (IOException e1) {}
        try { csvWriter.closeWriter(); } catch (IOException e) {}
	}
	
	
	
	public void endOfFaultRun() {
		try { semaphore.acquire();} catch (InterruptedException e) { }
		toContinue = false;
		semaphore.release();	
	}
	
	
	
}
