package edcDataCollector;

/**
 *
 * @author david
 */
public class PTTPackage extends AbstractPackage {
    
    public static final int PTT_TIME_TAG_SIZE = 4;
    public static final int PTT_SENSOR_DATA_SIZE = 8;
    public static final int PTT_PACKAGE_SIZE = 49;
    public static final int PTT_PCKG_ID = 34;
    
    
    private byte frameType;
    private int timeTag;
    private int errorCode;
    private int carrierFreq;
    private int carrierAbs;
    private byte msgByteLength;
    private short[] userMsg;
    private short chksum;
    private double freqHz;
    private byte[] sensorData;
    private int pttId;
    
    
    

    @Override
    public AbstractPackage loadPackage(byte[] data) {
        if(data.length > PTT_PACKAGE_SIZE){
            System.out.println("Error: Data length");
            return null;
        }
        else if(data[0] != PTT_PCKG_ID){
            System.out.println("Error: First byte");
            return null;
        }
        
        PTTPackage obj = new PTTPackage();
        obj.frameType = data[0];
        
        byte[] timeTag = new byte[4];
        timeTag[0] = data[1];
        timeTag[1] = data[2];
        timeTag[2] = data[3];
        timeTag[3] = data[4];
        obj.timeTag = fromByteArray(timeTag);
        
        obj.errorCode = data[5];
        
        byte[] carrierFreq = new byte[4];
        carrierFreq[0] = data[6];
        carrierFreq[1] = data[7];
        carrierFreq[2] = data[8];
        carrierFreq[3] = data[9];
        obj.carrierFreq = fromByteArray(carrierFreq);
        
        obj.carrierAbs = ( ((data[10] & 0xFF) << 8) | data[11] );
        
        obj.msgByteLength = data[12];
        
        short[] msgAux = new short[obj.msgByteLength];
        for(int i=13, j=0; i<13+obj.msgByteLength; i++, j++)
            msgAux[j] = Commun.byteToShort(data[i]);
        obj.userMsg = msgAux;
        
        
        
        obj.chksum = data[13+obj.msgByteLength];
        obj.freqHz = obj.carrierFreq*62.5;
        // obj.pttId =  TODO it can be removed.
        
        return obj;
    }

    public byte getMsgByteLength() {
        return msgByteLength;
    }
    

    @Override
    public void print() {
        System.out.println("====== PTT Package ======");
        System.out.println("Ptt Id: " + pttId);
        System.out.println("Time Tag: " + timeTag);
        System.out.println("Error Code: " + errorCode);
        System.out.println("PTT Freq: " + freqHz/1000);
        System.out.println("PTT Amp: " + carrierAbs);
        System.out.println("MsgLen: " + msgByteLength);
        //userMsg
        for(int i=0; i< msgByteLength; i++){
            int a = userMsg[i];
            System.out.print( a + " \n");
        }
    }
    
    public String[] getMessage() {
    	String msg = "";
    	
    	for(int i=0; i< msgByteLength; i++){
            int a = userMsg[i];
            if(i == msgByteLength-1)
            	msg = msg + a;
            else
            	msg = msg + a+" ";
        }
    	String [] result = new String[1];
    	result[0] = msg;
    	return result;
    }
    
}
