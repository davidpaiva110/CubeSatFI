package edcDataCollector;

/**
 *
 * @author david
 */
public class HKPackage extends AbstractPackage{
    
    public static final int HK_TIME_TAG_SIZE = 8;
    public static final int HK_PACKAGE_SIZE = 26;
    public static final int HK_PCKG_ID = Integer.parseInt("44", 16);
    
    
    private int frameType;              // Identification byte of the HK package.
    private int timeTag;                // RTC time tag, indicatig when the package was collected
    private int ellapsedTime;           // Elapsed time since last system initialization in seconds.
    private int currentSupplyD;         // Current supply of the digital components in mA.
    private double currentSupplyA;      // Current supply of the RF-Front-End in mA (EDC v1.1).
    private double voltageSupply_mV;    // Voltage supply sensor in mV.
    private double tempC;               // Board temperature in Celsius.
    private int pllSyncBit;             // PLL Synch state flag.
    private int adcRms;                 // ADC RMS latest value.
    private int numOfRxPtt;             // Number of generated PTT package since last system initialization
    private int maxParlDecod;           // Maximum number of active PTT decoder channels registered since last system initialization.
    private int memErrCount;            // Number of double bit errors detected by MSS data memory controller since last system initialization.
    private int chksum;                 // Result from a bitwise XOR operation between all transmitted bytes. 
    

    @Override
    public AbstractPackage loadPackage(byte[] data) {
        if(data.length != HK_PACKAGE_SIZE){
            System.out.println("Error: Data length.");
            return null;
        }
        else if(data[0] != HK_PCKG_ID){
            System.out.println("Error: First byte");
            return null;
        }
        
        HKPackage obj = new HKPackage();
        
        obj.raw_data = data;
        obj.frameType = data[0];
        
        byte[] timeTag = new byte[4];
        timeTag[0] = data[1];
        timeTag[1] = data[2];
        timeTag[2] = data[3];
        timeTag[3] = data[4];
        obj.timeTag = fromByteArray(timeTag);
        
        byte[] ellapsedTime = new byte[4];
        ellapsedTime[0] = data[5];
        ellapsedTime[1] = data[6];
        ellapsedTime[2] = data[7];
        ellapsedTime[3] = data[8];
        obj.ellapsedTime = fromByteArray(ellapsedTime);
        
        obj.currentSupplyD = ( ((data[10] & 0xFF) << 8) | data[9] );
        obj.currentSupplyA = ( ((data[12] & 0xFF) << 8) | data[11] );
        obj.voltageSupply_mV = ( ((data[14] & 0xFF) << 8) | data[13] );
        obj.tempC = data[15]-40;
        obj.pllSyncBit = data[16];
        obj.adcRms = ( ((data[18] & 0xFF) << 8) | data[17] );
        
        byte[] numOfRxPtt = new byte[4];
        numOfRxPtt[0] = data[19];
        numOfRxPtt[1] = data[20];
        numOfRxPtt[2] = data[21];
        numOfRxPtt[3] = data[22];
        obj.numOfRxPtt = fromByteArray(numOfRxPtt);
        
        obj.maxParlDecod = data[23];
        obj.memErrCount = data[24];
        obj.chksum = data[25];
         
        return obj;
    }

    @Override
    public void print() {
    	 System.out.println("====== HK Package ======");
    	 System.out.println("frameType: " + frameType);
    	 System.out.println("timeTag: " + timeTag);
    	 System.out.println("ellapsedTime: " + ellapsedTime);
    	 System.out.println("currentSupplyD: " + currentSupplyD);
    	 System.out.println("currentSupplyA: " + currentSupplyA);
    	 System.out.println("currentSupplyA: " + voltageSupply_mV);
    	 System.out.println("tempC: " + tempC);
    	 System.out.println("pllSyncBit: " + pllSyncBit);
    	 System.out.println("adcRms: " + adcRms);
    	 System.out.println("numOfRxPtt: " + numOfRxPtt);
    	 System.out.println("maxParlDecod: " + maxParlDecod);
    	 System.out.println("memErrCount: " + memErrCount);
    	 System.out.println("chksum: " + chksum);
    }
    
}
