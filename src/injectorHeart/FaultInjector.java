package injectorHeart;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Semaphore;

import dataModel.Experiment;
import edcDataCollector.Edc;
import edcDataCollector.OnBoardComputerDataCollector;
import edcDataCollector.SerialPortCommunication;
import faultTypes.Fault;
import faultTypes.SpaceBasedFault;
import faultTypes.TimeBasedFault;
import language.LanguageInterface;
import otherDataCollectorVersion.DataCollectorV2;
import otherDataCollectorVersion.DataCollectorStaticMethods;
import sideNavigation.DefineExperimentController;
import sideNavigation.ExperimentExecutionController;
import targets.AbstractFaultInjectionAlgorithm;
import targets.EdcTarget;

public class FaultInjector implements Runnable{
	
	public static final String OPENODC_PATH = "openocd\\bin";
	public static final String OPENODC_COMMAND = "openocd.exe ";
	

	private LanguageInterface stringTable;
	private ExperimentExecutionController viewController = null;
	private TelnetClient telnetClient = null;
	private Experiment experimentDefenitions = null;
	private List<Fault> faults = null;
	private List<String> headers = null;
	private Thread executionThread = null;
	private Thread telnetThread = null;
	private boolean isPausedFlag = false;
	private boolean toAbortFlag = false;
	private boolean firstFaultInjected = true;
	private Semaphore semaphore;
	
	
	public String dataCollectorUSBPort;
		
	
	public FaultInjector(ExperimentExecutionController viewController, LanguageInterface stringTable) throws IOException {
		super();
		this.startOpenODC();
		try { Thread.sleep(1000); } catch (InterruptedException ex) { }
		this.viewController = viewController;
		this.telnetClient = new TelnetClient();
		this.stringTable = stringTable;
		this.semaphore = new Semaphore(1);
	}
	
	public FaultInjector(ExperimentExecutionController viewController, LanguageInterface stringTable, String usbPortID) throws IOException {
		super();
		this.startOpenODC();
		try { Thread.sleep(1000); } catch (InterruptedException ex) { }
		this.viewController = viewController;
		this.telnetClient = new TelnetClient();
		this.stringTable = stringTable;
		this.semaphore = new Semaphore(1);
		this.dataCollectorUSBPort = usbPortID;
	}

	/**
	 * Start running OpenODC Server
	 * @throws IOException
	 */
	public void startOpenODC() throws IOException {
		
		String target = CSVReader.getConfigs().get(0);
		switch(target) {
			case EdcTarget.TARGET_EDC_ARM_CORTEX_M3 :
				Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd " + OPENODC_PATH + " && "
						+ OPENODC_COMMAND + EdcTarget.OPENODC_ARGUMENT_COMMAND + EdcTarget.OPENODC_ARGUMENT_FILE +"\"");
				break;
			default:
				return;
		}
	}	
	
	
	/**
	 * Injection Loop: Responsible for inject all the fault readed from the CSV file
	 * @throws Exception
	 */
	private void loopInjectionCampaign() throws Exception {
		int runIndex = 0;
		int auxView = 0;
		telnetThread = new Thread(telnetClient);
		telnetThread.start();
		//System.out.println(faults.size());
		System.out.println(faults.size());
		for (Fault fault : this.faults) {
			// Pause injection
			while(this.isPausedFlag) { 
				if(this.isToAbortFlag())
					break;
			}
			// If is to abort injection
			if(this.toAbortFlag) {
				abortingInjectionCampaign();
				return;
			}
			// Write feedback information into the log view	
			runIndex++;
			if(auxView <= 100) {
				viewController.getTfLogWindow().setText(stringTable.getInjectionLogPart1() + runIndex
						+ stringTable.getInjectionLogPart2() + this.faults.size() + ".\n" + viewController.getTfLogWindow().getText()); 
			}else {
				viewController.getTfLogWindow().setText(stringTable.getInjectionLogPart1() + runIndex
					+ stringTable.getInjectionLogPart2() + this.faults.size()); 
				auxView = 0;
			}
			//Inject the fault
			injectFault(fault);
			if(firstFaultInjected==true)
				firstFaultInjected = false;
		}
	}
	
	
	
	/**
	 * Injection Run Steps
	 * @param fault
	 * @throws Exception 
	 */
	private void injectFault(Fault fault) throws Exception  {
		AbstractFaultInjectionAlgorithm faultInjector;
		//System.out.println("========== Start inject fault method ===========");
		
		String target = CSVReader.getConfigs().get(0);
		switch(target) {
			case EdcTarget.TARGET_EDC_ARM_CORTEX_M3 :
				faultInjector = new EdcTarget(telnetClient, fault, experimentDefenitions.getCsvFile().replace(".csv", "_results.csv"), headers, dataCollectorUSBPort);
				break;
			default:
				return;
		}
			
		String faultType = fault.getFaultType();
		switch(faultType) {
			case TimeBasedFault.FAULT_TYPE_TIMEBASED :
				faultInjector.injectBitFlipTimeBasedFault();
				break;
			case SpaceBasedFault.FAULT_TYPE_SPACEBASED:
				faultInjector.injectBitFlipSpaceBased();
				break;
			default:
				return;
		}
		
	}
	
	
	/**
	 * XOR bit flip
	 * @param oldValue
	 * @param mask
	 * @return
	 */
	private String xorBitFliping(String oldValue, String mask) throws Exception {
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
	private String getRegisterID(String targetRegister) {
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
	private String hexToBin(String hex){
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
		
	/**
	 * Get Experiment Definitions
	 * @return
	 */
	public Experiment getExperimentDefenitions() {
		return experimentDefenitions;
	}


	/**
	 * Set Experiment Definitions
	 * @param experimentDefenitions
	 */
	public void setExperimentDefenitions(Experiment experimentDefenitions) {
		this.experimentDefenitions = experimentDefenitions;
		this.faults =  CSVReader.readFaultsFromCSV(experimentDefenitions.getCsvFile());
		this.headers = CSVReader.getExperimentInformation(experimentDefenitions.getCsvFile());
	}
	
		
	public Thread getExecutionThread() {
		return executionThread;
	}

	public void setExecutionThread(Thread executionThread) {
		this.executionThread = executionThread;
	}
	
	
	public boolean isPausedFlag() {
		return isPausedFlag;
	}

	public void setPausedFlag(boolean isPausedFlag) {
		this.isPausedFlag = isPausedFlag;
	}

	public boolean isToAbortFlag() {
		return toAbortFlag;
	}

	public void setToAbortFlag(boolean toAbortFlag) {
		try { acquireSemaphore();} catch (InterruptedException e) { };
		this.toAbortFlag = toAbortFlag;
	}
	

	@Override
	public void run() {
		try {
			loopInjectionCampaign();
		} catch (Exception e) {
			// If an error occurs.
			// For example, if the JTAG and the board is not connect to the Host PC
			
			telnetClient.shutdownClient();
			viewController.setTimerTerminater();
			viewController.setCampaignExecutionFlag(false);
			viewController.getTfLogWindow().setText(stringTable.getAbortingError());
			viewController.disableButtons();
			return ;
		}
		
		telnetClient.shutdownClient();
		viewController.setTimerTerminater();
		viewController.getTfLogWindow().setText( stringTable.getExecutionFinalizeWithSuccess() + faults.size() + stringTable.getFaultString() + "\n\n" + viewController.getTfLogWindow().getText()); 
		viewController.setCampaignExecutionFlag(false);
		viewController.disableButtons();
	}
	
	/**
	 * Aborting Injection Campaign
	 */
	private void abortingInjectionCampaign() {
		try { Thread.sleep(1); } catch (InterruptedException e) {	}
		//Close telnet Client	
		telnetClient.shutdownClient();
		releaseSemaphore();
	}

	
	
	
	
	public void acquireSemaphore() throws InterruptedException {
		semaphore.acquire();
	}

	public void releaseSemaphore() {
		semaphore.release();
	}
	

	
}
