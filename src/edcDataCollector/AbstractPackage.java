package edcDataCollector;

/**
 *
 * @author david
 */
abstract public class AbstractPackage {
    protected byte[] raw_data;
    
    abstract public AbstractPackage loadPackage(byte[] data);
    abstract public void print();
    
    public int fromByteArray(byte[] bytes) {
    	//A ORDEN ESTAVA AO CONTRÁRIO. ESTAVA DE 0 PARA 3 E NÃO DE 3 PARA 0. PENSO QUE O CORRETO É DE 3 PARA 0
        return ((bytes[3] & 0xFF) << 24) | 
                ((bytes[2] & 0xFF) << 16) | 
                ((bytes[1] & 0xFF) << 8 ) | 
                ((bytes[0] & 0xFF) << 0 );
    }
}
