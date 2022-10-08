package edcDataCollector;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class SerialPortCommunication {
    
    // EDC Serial Port Boud Rate
    public static final int EDC_BAUD_RATE = 115200;
    // EDC Serial Port Input read Timeout
    public static final int TIMEOUT_INPUT = 1600;
    

    private ArrayList<String> portList = new ArrayList<>();
    private SerialPort choosenPort = null;
    private OutputStream output = null;
    private InputStream input = null;

    
    
    /**
     * Get Available USB port names and store them inside the class
     */
    public void getPortNames() {
        for (SerialPort port : SerialPort.getCommPorts()) {
            portList.add(port.getSystemPortName());
        }
    }
    
    /**
     * Get Available USB port names and return them
     * @return Available USB port names
     */
    public static List<String> getAvailablePorts(){
    	ArrayList<String> portList = new ArrayList<>();
    	for (SerialPort port : SerialPort.getCommPorts()) {
            portList.add(port.getSystemPortName());
        }
    	if(portList.size() == 0)
    		portList = null;
    	return portList;
    }

    /**
     * Open the connection and configure baud rate setting
     * @param portName - Serial Port Identifier. Example "COM5"
     * @param baudRate - Baud Rate value to configure the connection
     */
    public void connect(String portName, int baudRate) {
        //Get the Serial Port
        choosenPort = SerialPort.getCommPort(portName);
        //Open the Serial Port
        choosenPort.openPort();
        //Set the baud rate
        choosenPort.setBaudRate(baudRate);
        //Set Timeout
        choosenPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, TIMEOUT_INPUT, 0);
        //Initialize the Input and Output Stream
        initializeStreams();
    }

    /**
     * Initialize the Input and Output Stream of the connection
     */
    private void initializeStreams() {
        if (choosenPort != null) {
            output = choosenPort.getOutputStream();
            input = choosenPort.getInputStream();
        }
        cleanInputStreamBuffer();
    }

    /**
     * Disconnect the Serial Port Connection
     */
    public void disconect() {
        if (choosenPort != null) {
            choosenPort.removeDataListener();
            choosenPort.closePort();
        }
    }

    /**
     * Write a byte array to the output stream of the connection
     * @param data - Byte Array to write
     */
    public void writeData(byte[] data) {
        try {
            output.write(data);
            output.flush();
            try { Thread.sleep(50); } catch (InterruptedException ex) { }
        } catch (IOException ex) {
            System.out.println("Failed to write data. (" + ex.toString() + ")");
        }
                
        //System.out.println("INSIDE WRITE_DATA:  ");
        //for(int i=0; i<data.length; i++)
        //    System.out.print(data[i] + " ");
        //System.out.println("\n");
    }
    
    /**
     * Write a byte to the output stream of the connection
     * @param data - byte to write
     */
    public void writeData(byte data) {
        try {
            output.write(data);
            output.flush();
            try { Thread.sleep(50); } catch (InterruptedException ex) { }
        } catch (IOException ex) {
            System.out.println("Failed to write data. (" + ex.toString() + ")");
        }
        //System.out.println("INSIDE WRITE_DATA byte:  " + data);
        //System.out.println("\n");
    }
    
    
    public byte[] readData(int pktLenght){
        byte[] data = null;
        try {
            data = input.readNBytes(pktLenght);
        } catch (IOException ex) {
           // System.out.println("Failed to read data. (" + ex.toString() + ")");
        }
        return data;
    }
    
    /**
     * Clean the InputStream by reading all bytes of theme.
     */
    public void cleanInputStreamBuffer() {
    	try {
			input.readAllBytes();
		} catch (IOException e) {
			
		}
    }
    
    public ArrayList<String> getPortList() {
		return portList;
	}
    
    
    
    
    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    
    */

    

	/**
     * 
     * Listener of the Serial Port Connection
     * 
     */
    private final class PacketListener implements SerialPortPacketListener {
        
        /*
        
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_WRITTEN;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_WRITTEN) {
                System.out.println("\nAll bytes were successfully transmitted!");
            }
        }

        @Override
        public int getPacketSize() {
            return 100;
        }
         */
        
        
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        
        @Override
        public int getPacketSize() {
            return 0;
        }
        

        @Override
        public void serialEvent(SerialPortEvent event) {
            byte[] newData = event.getReceivedData();
            System.out.println("Received data of size: " + newData.length);
            for (int i = 0; i < newData.length; ++i) {
                System.out.print((char) newData[i]);
            }
            System.out.println("\n");
        }
    }

}
