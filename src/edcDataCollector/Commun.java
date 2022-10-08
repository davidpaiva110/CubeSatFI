package edcDataCollector;

/**
 *
 * @author david
 */
public class Commun {
    
    /**
     * Convert a byte value into a short value
     * @param valueToConvert - byte value to convert
     * @return short value converted
     */
    public static short byteToShort(byte valueToConvert){
        byte javaByte = valueToConvert;
        short javaShort = (short) javaByte;
        return (short) (javaShort & 0x00FF);  
    }
}
