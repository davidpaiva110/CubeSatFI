package faultTypes;

public class TimeBasedFault extends Fault{
	
	private final static int NUMBER_OF_FIELDS = 6;
	public final static String FAULT_TYPE_TIMEBASED = "FAULT_TYPE_TIMEBASED";
	
	private int T0;
	private int Tinj;
	private int T1;
	private int Tfim;
		
	
	public TimeBasedFault() {
	}

	public TimeBasedFault(String register, String mask, int t0, int tinj, int t1, int tfim) {
		super(register, mask);
		T0 = t0;
		Tinj = tinj;
		T1 = t1;
		Tfim = tfim;
	}
	
	public TimeBasedFault(String [] attributes) {
		super(attributes[0], attributes[1]);
		T0 = Integer.parseInt(attributes[2]);
		Tinj = Integer.parseInt(attributes[3]);
		T1 = Integer.parseInt(attributes[4]);
		Tfim = Integer.parseInt(attributes[5]);
	}
	
	@Override
	public String[] toArray() {
		String[] result = new String[NUMBER_OF_FIELDS];
		result[0] = this.getRegister();
		result[1] = this.getMask();
		result[2] = Integer.toString(this.getT0());
		result[3] = Integer.toString(this.getTinj());
		result[4] = Integer.toString(this.getT1());
		result[5] = Integer.toString(this.getTfim());
		return result;
	}
	
	@Override
	public String[] headerToArray() {
		String[] result = new String[NUMBER_OF_FIELDS];
		result[0] = "Register";
		result[1] = "Mask";
		result[2] = "T0";
		result[3] = "Tinj";
		result[4] = "T1";
		result[5] = "Tfim";
		return result;
	}
	
	@Override
	public int getT0() {
		return T0;
	}

	@Override
	public void setT0(int t0) {
		T0 = t0;
	}

	@Override
	public int getTinj() {
		return Tinj;
	}
	
	@Override
	public void setTinj(int tinj) {
		Tinj = tinj;
	}

	@Override
	public int getT1() {
		return T1;
	}

	@Override
	public void setT1(int t1) {
		T1 = t1;
	}

	@Override
	public int getTfim() {
		return Tfim;
	}

	@Override
	public void setTfim(int tfim) {
		Tfim = tfim;
	}

	@Override
	public String getFaultType() {
		return FAULT_TYPE_TIMEBASED;
	}

	
	
	
	
	
	
	
	@Override
	public void setTpc(String tPc) {}
	@Override
	public String getTpc() {return null;}

}
