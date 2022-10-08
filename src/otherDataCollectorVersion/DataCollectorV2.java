package otherDataCollectorVersion;

import java.util.concurrent.Semaphore;

import faultTypes.Fault;

public class DataCollectorV2 implements Runnable {

	public boolean toContinue = true;
	UARTComm ser;
	String uart_id;
	
	private Semaphore semaphore = new Semaphore(1);
	
	
	

	public DataCollectorV2(String uart_id, String fileName, Fault fault) {
		super();
		this.uart_id = uart_id;
		ser = new UARTComm(fileName);
	}




	@Override
	public void run() {
		ser.connect(uart_id, UARTComm.EDC_BAUD_RATE);
		try {semaphore.acquire();} catch (InterruptedException e1) {}
		while(toContinue) {
			semaphore.release();
			try {Thread.sleep(20);} catch (InterruptedException e) {}
			try {semaphore.acquire();} catch (InterruptedException e1) {}
		}
		ser.disconect();
	}
	
	
	public void acquireSemaphore() {
		try {semaphore.acquire();} catch (InterruptedException e1) {};
	}
	
	public void releaseSemaphore() {
		semaphore.release();
	}
	
	
	public void stop() {
		try {semaphore.acquire();} catch (InterruptedException e1) {};
		this.toContinue = false;
		semaphore.release();
	}
	
	
	
	
}
