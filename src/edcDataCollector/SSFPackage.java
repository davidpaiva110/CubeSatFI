package edcDataCollector;

/**
 *
 * @author david
 */
public class SSFPackage extends AbstractPackage{
    
    public static final int SSF_PACKAGE_SIZE = 9;
    public static final int SSF_PCKG_ID = 17;
    
    private int frameType;
    private int currentTime;
    private int pttAv;
    private int pttIsPaused;
    private int samplerState;
    private int chksum;

    @Override
    public AbstractPackage loadPackage(byte[] data) {
        SSFPackage obj = new SSFPackage();
        
        if(data.length != SSF_PACKAGE_SIZE){
            System.out.println("Error: Wrong data length");
            return null;
        }
        else if(data[0] != SSF_PCKG_ID){
            System.out.println("Error. Wrong first byte");
            return null;
        }
        
        obj.raw_data = data;
        obj.frameType = (int) data[0];
        byte[] currentTime = new byte[4];
        currentTime[0] = data[1];
        currentTime[1] = data[2];
        currentTime[2] = data[3];
        currentTime[3] = data[4];
        obj.currentTime = fromByteArray(currentTime);
        obj.pttAv = data[5];
        obj.pttIsPaused = data[6];
        obj.samplerState = data[7];
        obj.chksum = data[8];
        return obj;
    }

    @Override
    public void print() {
        System.out.println("====== SSF Package ======");
        System.out.println("Frame Type: " + frameType);
        System.out.println("Current Time: " + currentTime);
        System.out.println("pttAv: " + pttAv);
        System.out.println("pttIsPaused: " + pttIsPaused);
        System.out.println("samplerState: " + samplerState);
        System.out.println("chksum: " + chksum);
    }
    
    
    /**
     * 
     * Getters and Setters
     *
     */

    public int getFrameType() {
        return frameType;
    }

    public void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getPttAv() {
        return pttAv;
    }

    public void setPttAv(int pttAv) {
        this.pttAv = pttAv;
    }

    public int getPttIsPaused() {
        return pttIsPaused;
    }

    public void setPttIsPaused(int pttIsPaused) {
        this.pttIsPaused = pttIsPaused;
    }

    public int getSamplerState() {
        return samplerState;
    }

    public void setSamplerState(int samplerState) {
        this.samplerState = samplerState;
    }

    public int getChksum() {
        return chksum;
    }

    public void setChksum(int chksum) {
        this.chksum = chksum;
    }
    
    
    
    
    

}
