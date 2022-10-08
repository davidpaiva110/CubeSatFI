package faultTypes;

public abstract class Fault{

	
	private String register;
	private String mask;
	
	
	public abstract String[] toArray();
	public abstract String[] headerToArray();
	public abstract String getFaultType();
	
	public abstract void setT0(int t0);
	public abstract void setT1(int t1);
	public abstract void setTinj(int tinj);
	public abstract void setTfim(int tfim);
	public abstract void setTpc(String tPc);
	
	public abstract int getT0();
	public abstract int getT1();
	public abstract int getTinj();
	public abstract int getTfim();
	public abstract String getTpc();

	public Fault(String register, String mask) {
		super();
		this.register = register;
		this.mask = mask;
	}
	

	public Fault() {
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}
	
	
	public static String[] experimentNametoArray(String  name) {
		String[] result = new String[2];
		result[0] = "Experiment Name: ";
		result[1] = name;
		return result;
		
	}

	public static String[] experimentDescriptiontoArray(String  description) {
		String[] result = new String[2];
		result[0] = "Experiment Description: ";
		result[1] = description;
		return result;
	}
	
	public static String[] experimentDatetoArray(String  date) {
		String[] result = new String[2];
		result[0] = "Date: ";
		result[1] = date;
		return result;
	}
	
	public static String[] experimentResponsabletoArray(String  responsable) {
		String[] result = new String[2];
		result[0] = "Responsable: ";
		result[1] = responsable;
		return result;
	}
	
	
}
