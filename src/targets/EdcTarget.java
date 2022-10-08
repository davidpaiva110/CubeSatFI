package targets;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import edcDataCollector.OnBoardComputerDataCollector;
import faultTypes.Fault;
import injectorHeart.TelnetClient;
import otherDataCollectorVersion.DataCollectorV2;
import otherDataCollectorVersion.DataCollectorStaticMethods;

public class EdcTarget extends AbstractFaultInjectionAlgorithm {
	
	//OPENODC CONFIGURATION PARAMETERS
	public static final String OPENODC_ARGUMENT_COMMAND = "--command \"set DEVICE M2S060\" ";
	public static final String OPENODC_ARGUMENT_FILE = "--file board/microsemi-cortex-m3.cfg";
	
	public static final String TARGET_EDC_ARM_CORTEX_M3 = "EDC (CONASAT)";
	
	private static final int MINIMUM_START_WAITING_TIME = 500;  //500ms

	
	OnBoardComputerDataCollector edcDataCollector = null;
	
	
	
	public EdcTarget(TelnetClient telnetClient, Fault fault, String resultsFileName, List<String> fileHeaders) {
		super(telnetClient, fault, resultsFileName, fileHeaders);
	}
	
	public EdcTarget(TelnetClient telnetClient, Fault fault, String resultsFileName, List<String> fileHeaders, String uartInterfaceSelected) {
		super(telnetClient, fault, resultsFileName, fileHeaders, uartInterfaceSelected);
	}
	
	
		
	
	

	@Override
	public void resetTarget() {
		try { Thread.sleep(MINIMUM_START_WAITING_TIME); } catch (InterruptedException e) {  }  ;
		super.resetTarget();
	}
	
	


	@Override
	public void resetTargetWithoutRun() {
		try { Thread.sleep(MINIMUM_START_WAITING_TIME); } catch (InterruptedException e) {  }  ;
		super.resetTargetWithoutRun();
	}

	@Override
	public void waitUntilInjectionMoment() {
		Fault fault =  getFault();
		//System.out.println("Injection moment: " + fault.getTinj());
		try { Thread.sleep(fault.getTinj()); } catch (InterruptedException e) { }

	}

	@Override
	public void waitUntilFaultInjectionEnd() {
		Fault fault = getFault();
		//System.out.println("After injection waiting time: " + (fault.getTfim() - fault.getTinj()) );
		try { Thread.sleep(fault.getTfim() - fault.getTinj()); } catch (InterruptedException e) {	}
	}

	@Override
	public void injectFault() {
		//Get Target Register
		//System.out.println("1 - Get Target Register");
		String targetRegister = getRegisterID(getFault().getRegister());
		// Get Register Value
		//System.out.println("2 - Get Register value");
		String binaryRegValue = getTelnetClient().getRegisterValue(targetRegister);
		// Change Register Value
		//System.out.println("3 - Change Register Value");
		try {
			binaryRegValue = this.xorBitFliping(binaryRegValue, getFault().getMask());
		} catch (Exception e) {  // Test this!
			System.err.println("xorBitFliping error!");
			return;
		}
		String hexRegValue = new BigInteger(binaryRegValue, 2).toString(16).toUpperCase();
		hexRegValue = "0x" + hexRegValue;
		// Insert the fault into the target
		//System.out.println("4 - Inject fault");
		getTelnetClient().modifyRegisterValue(targetRegister, hexRegValue);
		//System.out.println("========== End of fault run ===========");
	}

	@Override
	public String getProgramCounterValue() {
		//Get Program Counter register Value
		String programCounter = getTelnetClient().getProgramCounter();
		return programCounter;
	}
	
	Thread t;
	DataCollectorV2 dataCollector;
	@Override
	public void initializeDataCollection(List<String> fileHeaders, String fileName, boolean firstFaultInjected) {
		/*
		 * Other way to collect data in an asynchronous way
		 * 
		 * 
		if(firstFaultInjected)
			try {
				DavidDataCollector.createFileResults(fileHeaders, fileName);
			} catch (IOException e) {
				// TODO Look to this exception!
			}
		DavidDataCollector.createfaultHeader(getFault(), fileName);
		dataCollector = new DataCollectorV2(this.getUarfInterfaceSelected(), fileName, getFault());      
		t = new Thread(dataCollector);
		t.start();
		*/
		
		
		// Collect data in an synchronous way -> this means request and read the response 
		try {
			OnBoardComputerDataCollector.createFileResults(fileHeaders, fileName);
			edcDataCollector = new OnBoardComputerDataCollector(getFault().getTfim()-getFault().getT1(),
					fileName, getFault());
		} catch (IOException e) {}
		
		if(edcDataCollector != null) {
			edcDataCollector.createEdcObject(this.getUarfInterfaceSelected());  
			edcDataCollector.sample_start();
			edcDataCollector.rtc_set_current_time();
			edcDataCollector.ptt_resume();
			edcDataCollector.ptt_clear();
		}
	}
	

	@Override
	public void endDataCollection(String programCounter, String fileName) {
		/*
		 * 
		 * Other way to collect data in an asynchronous way
		 *           Close the objects
		 * 
		dataCollector.stop();
		try { t.join(); } catch (InterruptedException e) {}
		DavidDataCollector.wirteProgramCounter(programCounter, fileName);
		*/
		
		edcDataCollector.setProgramCounter(programCounter);
		edcDataCollector.collectData();
		edcDataCollector.Disconnect();
	}
	
	@Override
	public void waitUntilFaultInjectionEnd(int time) {
		try { Thread.sleep(time); } catch (InterruptedException e) { }
	}

	@Override
	public void setBreakPoint() {
		String bp = getFault().getTpc();
		if(getFault().getTpc().length() < 8) {
			for(int i=0; i< 8-getFault().getTpc().length(); i++) {
				bp = "0"+bp;
			}
		}
		bp = "0x" + bp;
		getTelnetClient().setBreakPoint(bp);
	}

	@Override
	public void removeBreakPoint() {
		String bp = getFault().getTpc();
		if(getFault().getTpc().length() < 8) {
			for(int i=0; i< 8-getFault().getTpc().length(); i++) {
				bp = "0"+bp;
			}
		}
		bp = "0x" + bp;
		getTelnetClient().removeBreakPoint(bp);
	}

	@Override
	public void waitUntilBreakPointAlert() {
		String bp = getFault().getTpc();
		if(getFault().getTpc().length() < 8) {
			for(int i=0; i< 8-getFault().getTpc().length(); i++) {
				bp = "0"+bp;
			}
		}
		bp = "0x" + bp;
		getTelnetClient().getHaltedConfirmation(bp);
	}
	
	
	

	@Override
	public void dataCollectWindow() {}

	

}
