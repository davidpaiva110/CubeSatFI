package edcDataCollector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author david
 */
public class Edc {
    
    /**
     * 
     *  EDC commands ID
     * 
     */

    public static final int RTC_SET_ID = Integer.parseInt("01", 16);
    public static final int PTT_POP_ID = Integer.parseInt("06", 16);
    public static final int PTT_PAUSE_ID = Integer.parseInt("08", 16);
    public static final int PTT_RESUME_ID = Integer.parseInt("09", 16);
    public static final int SAMPLER_START_ID = Integer.parseInt("0A", 16);
    public static final int SAMPLER_RESET_ID = Integer.parseInt("0D", 16);
    public static final int GET_STATE_ID = Integer.parseInt("30", 16);
    public static final int GET_PTT_PKG_ID = Integer.parseInt("31", 16);
    public static final int GET_HK_PKG_ID = Integer.parseInt("32", 16);
    public static final int GET_ADC_SEQ_ID = Integer.parseInt("34", 16);
    public static final int ECHO_ID = Integer.parseInt("F0", 16);
    
    
    /**
     * 
     *  Frame IDs
     * 
     */
    public static final int SYS_FRAME_ID = Integer.parseInt("11", 16);
    public static final int PTT_FRAME_ID = Integer.parseInt("22", 16);
    public static final int ADC_FRAME_ID = Integer.parseInt("33", 16);
    public static final int HK_FRAME_ID = Integer.parseInt("44", 16);
    public static final int EMP_FRAME_ID = Integer.parseInt("FF", 16);
    
    
    /**
     * 
     *  Frame Lenghts
     * 
     */
    public static final int SYS_FRAME_LENGTH = 9;
    public static final int PTT_FRAME_LENGTH = 49;
    public static final int ADC_FRAME_LENGTH = 8200;
    public static final int HK_FRAME_LENGTH = 26;
    public static final int EMP_FRAME_LENGTH = 1;
    
    
    
    /**
     * 
     *  Others
     * 
     */
    public static final int PTT_BUFFER_LENGTH = 64;
    
    
    /**
     * 
     *  Objects Definition
     * 
     */
    public SerialPortCommunication com;
    
    
    /**
     * Constructor
     * @param com 
     */
    public Edc(SerialPortCommunication com) {
        this.com = com;
    }
    
       
    
    
    /**
     * Set RTC current time
     * @return the number of seconds elapsed since January 1, 2000, at 12h
     */
    public long rtc_set_current_time(){
        int secPassJ2000 = getSecPassJ2000();
        byte[] bytes = intToByteArray(secPassJ2000);
        bytes = addMessageHeader(RTC_SET_ID, bytes);
        com.writeData(bytes);
        return secPassJ2000;
    }
    
    /**
     * Get the seconds since January 2000
     * @return 
     */
    public int getSecPassJ2000(){
        //2000 January 1 , 12h
        Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar1.clear();
        calendar1.set(2000, Calendar.JANUARY, 1, 12, 0);
        int seconds1 = (int) (calendar1.getTimeInMillis() / 1000L);
        Calendar calender2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int seconds2 = (int) (calender2.getTimeInMillis() / 1000L);
        return seconds2 - seconds1;
    }
    
    /**
     * Remove one message from PTT BUFFER
     */
    public void ptt_pop(){
        byte message = getByteHeader(PTT_POP_ID);
        com.writeData(message);
    }
    
    /**
     * Issue a sequence of multiple PTT POP commands to clear the
     * EDC's PTT BUFFER.
     */
    public void ptt_clear(){
        for(int i = 0; i < PTT_BUFFER_LENGTH; i++){
            ptt_pop();
            try { Thread.sleep(50); } catch (InterruptedException ex) { }
        }
    }
    
    
    public void ptt_pause(){
        byte message = getByteHeader(PTT_PAUSE_ID);
        com.writeData(message);
    }
    
    public void ptt_resume(){
       byte message = getByteHeader(PTT_RESUME_ID);
       com.writeData(message);
    }
    
    public void sampler_start(){
        byte message = getByteHeader(SAMPLER_START_ID);
        com.writeData(message);
    }
    
    public SSFPackage get_state() throws Exception{
        SSFPackage pckg = null;
        byte[] rawPckg = requestAndRead(GET_STATE_ID, SYS_FRAME_LENGTH);
        if(isFrameOk(rawPckg, SYS_FRAME_ID)){
            pckg = new SSFPackage();
            pckg = (SSFPackage) pckg.loadPackage(rawPckg);
        }
        return pckg;
    }
    
    public PTTPackage get_ptt_package() throws Exception{
        PTTPackage pckg = null;
        byte [] data0;
        byte [] data1;
        
        data0 = requestAndRead(GET_PTT_PKG_ID, 13);
        if(data0 == null || data0.length == 0)
            return pckg;
        int msgByteLength = data0[12];
        
        data1 = com.readData(msgByteLength+1);
        
        byte[] allByteArray = new byte[data0.length + data1.length];
        ByteBuffer buff = ByteBuffer.wrap(allByteArray);
        buff.put(data0);
        buff.put(data1);    
        byte[] rawPckg = buff.array();
        
        if(isFrameOk(rawPckg, PTT_FRAME_ID)){
            PTTPackage pttPackage = new PTTPackage();
            pckg = (PTTPackage) pttPackage.loadPackage(rawPckg);
        }
        
        /*
        //REMOVE ... JUST FOR TESTING
        if(pckg.getMsgByteLength() != 35){
            Logger logger = Logger.getLogger("MyLog");  
        FileHandler fh;  
        try {  
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("C:/temp/EDCLogErrorDecoded.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
            // the following statement is used to log any messages  
                logger.info("Mensagem recebida com erro! MsgLen=" + pckg.getMsgByteLength());  
            
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        }
        */
        
        return pckg;
    }
    
    public HKPackage get_hk_pkg() throws Exception{
        byte[] rawPckg = requestAndRead(GET_HK_PKG_ID, HK_FRAME_LENGTH);
        HKPackage pckg = null;
        
        if(isFrameOk(rawPckg, HK_FRAME_ID)){
            pckg = new HKPackage();
            pckg = (HKPackage) pckg.loadPackage(rawPckg);
        }
        return pckg;
    }
    
    public void echo(){
        byte message = getByteHeader(ECHO_ID);
        com.writeData(message);
    }
    
    
    
    
    public byte[] requestAndRead(final int getCmdId, final int pkgLength) throws Exception{
        // Send the request
        byte message = getByteHeader(getCmdId);
        com.writeData(message);
        try {
	        byte[] data = com.readData(pkgLength);
	        return data;
        }catch(Exception e) {
        	throw new Exception(e.getMessage());
        }
    }
    
    private boolean isFrameOk(byte[] frame, int expectedType) throws Exception{
        boolean pass = false;
        if(frame == null || frame.length == 0){
        	throw new Exception("Warning: Read operation return empty!");
        }
        byte frameType = frame[0];
        try {
			ifFrameTypeOk(frameType, expectedType);
		} catch (Exception e) {
			throw e;
		}

        // Verify checksum
        pass = checkSumPass(frame);
        
        if(!pass){
        	throw new Exception("Verify checksum fail.");
        }  
        return pass;
    }
    
    private boolean ifFrameTypeOk(byte frameType, int expectedType) throws Exception{
        if( (expectedType == PTT_FRAME_ID || expectedType == ADC_FRAME_ID) && frameType == EMP_FRAME_ID ){
        	throw new Exception("Warning: An Empty Frame was returned!");
        } 
        else if(frameType != expectedType){
            throw new Exception("Warning: Wrong frame type returned! + [FrameType Returned = " + frameType + "]");
        }
        return true;
    }
    
    private boolean checkSumPass(byte[] frame){
        int checksum = 0;
        for(int i=0; i<frame.length; i++){
            checksum = frame[i] ^ checksum;
        }
        return checksum == 0;
    }
   
    
    /**
     * Convert an int into a byte array order by Little-Endian 
     * @param data - int value to convert to a byte array
     * @return 
     */
    public byte[] intToByteArray(int data){
        return new byte[] {
        (byte)((data >> 24) & 0xff),
        (byte)((data >> 16) & 0xff),
        (byte)((data >> 8) & 0xff),
        (byte)((data) & 0xff),
        };
    }
    
    public byte getByteHeader(int data){
        return (byte) data;
    }
    
    
    /**
     * 
     * @param header
     * @param data
     * @return 
     */
    public byte[] addMessageHeader(final int header, final byte[] data){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );        
        byte val = getByteHeader(header);
        //Merge header with message
        try {
            outputStream.write(val);
            outputStream.write(data);
        } catch (IOException ex) {  }
        return outputStream.toByteArray( );
    }
    
    
    
    
    
}
