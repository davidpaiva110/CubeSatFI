package otherDataCollectorVersion;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import edcDataCollector.CSVDataWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class UARTComm {
    // *********************** THIS TWO VALUES CAN BE CHANGED *********************** 
    // EDC Serial Port Boud Rate
    public static final int EDC_BAUD_RATE = 115200;  
    // EDC Serial Port Input read Timeout
    public static final int TIMEOUT_INPUT = 1600;
    

    private ArrayList<String> portList = new ArrayList<>();
    private SerialPort choosenPort = null;
    private OutputStream output = null;
    private InputStream input = null;
    
    
    //Provisório
    private String fileName;
    CSVDataWriter csvWriterr;
       
    

    public UARTComm(String fileName) {
		super();
		this.fileName = fileName;
		try {csvWriterr = new CSVDataWriter(fileName);} catch (IOException e) {}
	}

	public void getPortNames() {
        for (SerialPort port : SerialPort.getCommPorts()) {
            portList.add(port.getSystemPortName());
        }
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
        
        // Add Listner
        choosenPort.addDataListener(new SerialPortDataListener() {
        	
        	
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
               if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                  return;
           
               // DATA FROM USB CAN BE RECEIVED IN THIS METHOD !!!
               
            }
         });
        
        //choosenPort.addDataListener(new PacketListener());
        
        
        
        //Set the baud rate
        choosenPort.setBaudRate(baudRate);
        //Set Timeout
        choosenPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, TIMEOUT_INPUT, 0);
        //Add Listener to receive data
        //Initialize the Input and Output Stream
        initializeStreams();
    }
    
    
    
    
    
    public int fromByteArray(byte[] bytes) {
        return ((bytes[3] & 0xFF) << 24) | 
               ((bytes[2] & 0xFF) << 16) | 
               ((bytes[1] & 0xFF) << 8 ) | 
               ((bytes[0] & 0xFF) << 0 );
       }
    
    
    
    
    

    /**
     * Initialize the Input and Output Stream of the connection
     */
    private void initializeStreams() {
        if (choosenPort != null) {
            output = choosenPort.getOutputStream();
            input = choosenPort.getInputStream();
        }
    }

    /**
     * Disconnect the Serial Port Connection
     */
    public void disconect() {
        if (choosenPort != null) {
            choosenPort.removeDataListener();
            choosenPort.closePort();
        }
        
        
        
        try {
			csvWriterr.closeWriter();
		} catch (IOException e) {

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
        } catch (IOException ex) {
            System.out.println("Failed to write data. (" + ex.toString() + ")");
        }
        
        System.out.println("INSIDE WRITE_DATA:  ");
        for(int i=0; i<data.length; i++)
            System.out.print(data[i] + " ");
        System.out.println("\n");
    }
    
    /**
     * Write a byte to the output stream of the connection
     * @param data - byte to write
     */
    public void writeData(byte data) {
        try {
            output.write(data);
            output.flush();
        } catch (IOException ex) {
            System.out.println("Failed to write data. (" + ex.toString() + ")");
        }
        System.out.println("INSIDE WRITE_DATA byte:  " + data);
        System.out.println("\n");
    }
    
    
    public byte[] readData(int pktLenght){
        byte[] data = null;
        try {
            data = input.readNBytes(pktLenght);
        } catch (IOException ex) {
            System.out.println("Failed to read data. (" + ex.toString() + ")");
        }
        return data;
    }

       
  
    

}
