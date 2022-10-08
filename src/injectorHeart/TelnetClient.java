package injectorHeart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import sideNavigation.ExperimentExecutionController;

/**
 *
 * @author david
 */
public class TelnetClient implements Runnable {
        
    public static final String TELNET_SERVER_IP = "localhost";
    public static final int TELNET_SERVER_PORT = 4444;
    
    private Socket pingSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String response;
    private String freeResponse;
    private boolean shutdownFlag = false;
    private Semaphore semaphore = null;
        
        
    public TelnetClient() throws IOException{
        this.initializeClient();
        this.semaphore = new Semaphore(1);
    }
        
    /**
     * Send a request to the Telnet Server
     * @param request 
     */    
    public void sendTelnetRequest(String request){
        out.println(request);
    }
    
    /**
     * Halt processor
     */
    public void haltProcessor(){
        out.println("halt");
    }
 
     /**
     * Resume processor execution
     */
    public void resumeProcessor(){
        out.println("resume");
    }
    
    /**
     * Reset processor 
     */
    public void resetProcessor(){
        out.println("reset run");
    }
    
    /**
     * Reset processor 
     */
    public void resetProcessorWithoutRun(){
        out.println("reset halt");
    }
    
    
    public void setBreakPoint(String address) {
    	out.println("bp " + address + " 2");
    }
    

    public void removeBreakPoint(String address) {	
    	out.println("rbp " + address.toLowerCase());
    }
    
    public void getHaltedConfirmation(String address) {
    	String addressCopy = address;
    	String lastOne = address.substring(address.length()-1);
    	if(!lastOne.equals("0")) {
    		String aux = address.substring(2);
        	int value = Integer.parseInt(aux, 16);
        	value--;
        	String incHex = Integer.toHexString(value);
        	
        	//System.out.println("lenght: " + (8-incHex.length()));
        	int size = (8-incHex.length());
        	for(int i=0; i<size ; i++) {
        		incHex = "0"+incHex;
			}
        	
        	address = "0x" + incHex.toLowerCase();
    	}
    	
    	//System.out.println(address);
    	String aux;
    	while(true) {
        	aux = this.getResponser();
        	if(aux.contains(("pc: " + address.toLowerCase() )) || aux.contains(("pc: " + addressCopy.toLowerCase() )))  //If it's even the adress ts stop on the par
        		return;
        }
    }
    
    
    /**
     * Get the value stored in the register with the registerID
     * @param registerID
     * @return 
     */
    public String getRegisterValue(String registerID){
        out.println("reg " + registerID);
        String response;
        registerID = specialRegister(registerID);
        while(true) {
        	//System.out.println("bloqueado aqui");
        	response = this.getResponser();
        	if(response.contains((registerID + " (/32):")))
        		return this.getResponser();
        }
    }
    
    public String specialRegister(String registerID) {
    	if(registerID.equals("13"))
    		registerID = "sp";
		else if(registerID.equals("14"))
			registerID = "lr";
		else if(registerID.equals("15"))
			registerID = "pc";
    	return registerID;
    }
    
    /**
     * Get the value stored in the register with the registerID
     * @return 
     */
    public String getProgramCounter(){
        out.println("reg pc");
        String response;
        while(true) {
        	response = this.getResponser();
        	if(response.contains(("pc (/32):")))
        		return this.getResponser();
        }
    }
    
    /**
     * Put in the register - registerID - the value - newValue
     * @param registerID
     * @param newValue 
     */
    public void modifyRegisterValue(String registerID, String newValue){
        out.println("reg " + registerID + " " + newValue);
    }
    
    // Thread Responsible for read the responses that come from the Telnet Server
    @Override
    public void run() {
        try {
            while (!this.shutdownFlag){
                response = in.readLine();
                if(response == null)
                    break;
                if(response.length() > 0){
                	try { semaphore.acquire(); } catch (InterruptedException e) { }
                    freeResponse = response;
                    semaphore.release();
                }
                if(this.shutdownFlag)
                	break;
            }
        } catch (IOException ex) {
        	System.out.println(ex);
        	 try { out.close(); in.close(); pingSocket.close();} catch (IOException e) { }
        }
    }
    
    /**
     * Initialize the objects needed to establish a Telnet Connection
     * @throws IOException 
     */
    public void initializeClient() throws IOException{
            try {
                pingSocket = new Socket(TELNET_SERVER_IP, TELNET_SERVER_PORT);
                out = new PrintWriter(pingSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            } catch (IOException ex) {
            	System.out.println("Telnet class | initializeClient " + ex);
                throw ex;
            }
            
            
    }
    
    /**
     * Shutdown the Telnet Connection
     */
    public void shutdownClient(){
        this.shutdownFlag = true;
        sendTelnetRequest("shutdown");
        try { out.close(); in.close(); pingSocket.close();} catch (IOException e) {  }
    }
    
    /**
     * Get the last response received from the Telnet Server
     * @return 
     */
    public String getResponser(){
    	try { semaphore.acquire(); } catch (InterruptedException e) { }
    	String str = freeResponse;
    	semaphore.release();
        return str;    
    }
    
    
}
