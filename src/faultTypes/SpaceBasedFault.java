package faultTypes;

public class SpaceBasedFault extends Fault {
	
	private final static int NUMBER_OF_FIELDS = 4;
	public final static String FAULT_TYPE_SPACEBASED = "FAULT_TYPE_SPACEBASED";

	private String tPc;
	private int endTime;
	
	
	public SpaceBasedFault() {
	}
	
	public SpaceBasedFault(String register, String mask, String tPc, int endTime) {
		super(register, mask);
		this.tPc = tPc;
		this.endTime = endTime;
	}
	
	public SpaceBasedFault(String [] attributes) {
		super(attributes[0], attributes[1]);
		this.tPc = attributes[2];
		this.endTime = Integer.parseInt(attributes[3]);
	}

	@Override
	public String[] toArray() {
		String[] result = new String[NUMBER_OF_FIELDS];
		result[0] = this.getRegister();
		result[1] = this.getMask();
		result[2] = this.tPc;
		result[3] = this.endTime + "";
		return result;
	}

	@Override
	public String[] headerToArray() {
		String[] result = new String[NUMBER_OF_FIELDS];
		result[0] = "Register";
		result[1] = "Mask";
		result[2] = "PC Location";
		result[3] = "EndTime";
		return result;
	}

	@Override
	public String getFaultType() {
		return FAULT_TYPE_SPACEBASED;
	}
	
	@Override
	public void setTpc(String tPc) {
		this.tPc = tPc;
	}

	@Override
	public String getTpc() {
		return tPc;
	}
	
	@Override
	public void setTfim(int tfim) {
		this.endTime = tfim;
	}
	
	@Override
	public int getTfim() {
		return endTime;
	}
	

	@Override
	public void setT0(int t0) {}
	@Override
	public void setT1(int t1) {}
	@Override
	public void setTinj(int tinj) {}
	
	@Override
	public int getT0() {return 0;}
	@Override
	public int getT1() {return 0;}
	@Override
	public int getTinj() {return 0;}
	
}
