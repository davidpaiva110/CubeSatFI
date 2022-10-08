package targets;

import java.util.List;

import faultTypes.Fault;
import injectorHeart.TelnetClient;

public abstract class AbstractFaultInjectionAlgorithm {
	
	private TelnetClient telnetClient;
	private Fault fault;
	private String resultsFileName;
	List<String> fileHeaders;
	private String uartInterfaceSelected = null;
	
	public abstract void waitUntilInjectionMoment();
	public abstract void waitUntilFaultInjectionEnd();
	public abstract void waitUntilFaultInjectionEnd(int time);
	public abstract void injectFault();
	public abstract String getProgramCounterValue();
	public abstract void dataCollectWindow();
	public abstract void initializeDataCollection(List<String> fileHeaders, String fileName, boolean firstFaultInjected);
	public abstract void endDataCollection(String programCounter, String fileName);
	public abstract void setBreakPoint();
	public abstract void removeBreakPoint();
	public abstract void waitUntilBreakPointAlert();
	
	
	
	
	
	
	public AbstractFaultInjectionAlgorithm(TelnetClient telnetClient, Fault fault, String resultsFileName) {
		super();
		this.telnetClient = telnetClient;
		this.fault = fault;
		this.resultsFileName = resultsFileName;
	}
	
	public AbstractFaultInjectionAlgorithm(TelnetClient telnetClient, Fault fault, String resultsFileName, List<String> fileHeaders) {
		super();
		this.telnetClient = telnetClient;
		this.fault = fault;
		this.resultsFileName = resultsFileName;
		this.fileHeaders = fileHeaders;
	}
	
	public AbstractFaultInjectionAlgorithm(TelnetClient telnetClient, Fault fault, String resultsFileName, List<String> fileHeaders, String uartInterfaceSelected) {
		super();
		this.telnetClient = telnetClient;
		this.fault = fault;
		this.resultsFileName = resultsFileName;
		this.fileHeaders = fileHeaders;
		this.uartInterfaceSelected = uartInterfaceSelected;
	}
	
	public void resetTarget() {
		telnetClient.resetProcessor();
	}
	
	public void resetTargetWithoutRun() {
		telnetClient.resetProcessorWithoutRun();
	}
	
	public void stopTarget() {
		telnetClient.haltProcessor();
	}
	
	
	public void startTarget() {
		telnetClient.resumeProcessor();
	}
	
	
	public void injectBitFlipTimeBasedFault() {
		resetTarget();
		initializeDataCollection(fileHeaders, resultsFileName, false);
		waitUntilInjectionMoment();
		stopTarget();
		String programCounter = getProgramCounterValue();
		injectFault();
		startTarget();
		waitUntilFaultInjectionEnd();
		endDataCollection(programCounter, resultsFileName);
	}
	
	public void injectBitFlipSpaceBased() {

		resetTargetWithoutRun();
		
		setBreakPoint();

		resetTarget();

		initializeDataCollection(fileHeaders, resultsFileName, false);

		waitUntilBreakPointAlert();

		removeBreakPoint();
		
		String programCounter = getProgramCounterValue();
		
		injectFault();
		
		startTarget();
		
		waitUntilFaultInjectionEnd(fault.getTfim());  
		
		endDataCollection(programCounter, resultsFileName);
	}
	
	
	public TelnetClient getTelnetClient() {
		return telnetClient;
	}
	
	public Fault getFault() {
		return fault;
	}
	
	public String getResultsFileName() {
		return resultsFileName;
	}
	
	public String getUarfInterfaceSelected() {
		return uartInterfaceSelected;
	}
	
	
	
	
	
	
	/**
	 * XOR bit flip
	 * @param oldValue
	 * @param mask
	 * @return
	 */
	public String xorBitFliping(String oldValue, String mask) throws Exception {
		String [] split = oldValue.split("0x");
		try{ oldValue = hexToBin(split[1]); } catch(Exception e) {throw e;}
		StringBuilder builder = new StringBuilder(oldValue);
		for(int i=0; i<oldValue.length(); i++) {
				if(oldValue.charAt(i) == mask.charAt(i))     //    0 0 -> 0    |    1 1 -> 0
					builder.setCharAt(i, '0');
				if(oldValue.charAt(i) != mask.charAt(i))     //    0 1 -> 1    |    1 0 -> 1
					builder.setCharAt(i, '1');
		}
		return builder.toString();
	}
	
	/**
	 * Get the target Register ID from the CSV information
	 * @param targetRegister
	 * @return
	 */
	public String getRegisterID(String targetRegister) {
		if(targetRegister.equals("SP"))
			targetRegister = "13";
		else if(targetRegister.equals("LR"))
			targetRegister = "14";
		else if(targetRegister.equals("PC"))
			targetRegister = "15";
		if(!targetRegister.equals("CONTROL"))
			targetRegister = targetRegister.replace("R", "");
		return targetRegister;
	}
	
	/**
	 * Convert hexadecimal String to Binary String
	 * @param hex
	 * @return
	 */
	public String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
	
	
	
	

}
