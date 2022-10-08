package dataModel;

public class Experiment {
	
	 private String name;
	 private String description;
	 private int numFaults;
	 private int bitsPerFault;
	 private String csvFile;
	 private boolean seed;
	 private int seedValue;
	 private String R0;
	 private String R1;
	 private String R2;
	 private String R3;
	 private String R4;
	 private String R5;
	 private String R6;
	 private String R7;
	 private String R8;
	 private String R9;
	 private String R10;
	 private String R11;
	 private String R12;
	 private String SP;
	 private String LR;
	 private String PC;
	 private int T0;
	 private int T1;
	 private int Tfim;
	 private String triggerPC;
	 private String triggerPCEnd;
	 private int timeEnd;
	 private String responsable;
	 private String date;
	 
	 
	 
	 private String faulyType;


	 // Getter Methods 

	 public String getName() {
	  return name;
	 }

	 public String getDescription() {
	  return description;
	 }

	 public int getNumFaults() {
	  return numFaults;
	 }

	 public int getBitsPerFault() {
	  return bitsPerFault;
	 }
	 
	 public boolean getSeed() {
		 return seed;
	 }
	 
	 public int getSeedValue() {
		  return seedValue;
	}

	 public String getCsvFile() {
	  return csvFile;
	 }

	 public String getR0() {
	  return R0;
	 }

	 public String getR1() {
	  return R1;
	 }

	 public String getR2() {
	  return R2;
	 }

	 public String getR3() {
	  return R3;
	 }

	 public String getR4() {
	  return R4;
	 }

	 public String getR5() {
	  return R5;
	 }

	 public String getR6() {
	  return R6;
	 }

	 public String getR7() {
	  return R7;
	 }

	 public String getR8() {
	  return R8;
	 }

	 public String getR9() {
	  return R9;
	 }

	 public String getR10() {
	  return R10;
	 }

	 public String getR11() {
	  return R11;
	 }

	 public String getR12() {
	  return R12;
	 }

	 public String getSP() {
	  return SP;
	 }

	 public String getLR() {
	  return LR;
	 }

	 public String getPC() {
	  return PC;
	 }

	 public int getT0() {
	  return T0;
	 }

	 public int getT1() {
	  return T1;
	 }

	 public int getTfim() {
	  return Tfim;
	 }
	 
	 public String getTriggerPC() {
		return triggerPC;
	}
	 
	 public String getTriggerPCEnd() {
			return triggerPCEnd;
	}
	 
	 
	 // Setter Methods 
	public void setName(String name) {
	  this.name = name;
	 }

	 public void setDescription(String description) {
	  this.description = description;
	 }

	 public void setNumFaults(int numFaults) {
	  this.numFaults = numFaults;
	 }

	 public void setBitsPerFault(int bitsPerFault) {
	  this.bitsPerFault = bitsPerFault;
	 }

	 public void setCsvFile(String csvFile) {
	  this.csvFile = csvFile;
	 }
	 
	 public void setSeed(boolean seed) {
		 this.seed = seed;
	 }
	 
	 public void setSeedValue(int seedValue) {
		 this.seedValue = seedValue;
	 }

	 public void setR0(String R0) {
	  this.R0 = R0;
	 }

	 public void setR1(String R1) {
	  this.R1 = R1;
	 }

	 public void setR2(String R2) {
	  this.R2 = R2;
	 }

	 public void setR3(String R3) {
	  this.R3 = R3;
	 }

	 public void setR4(String R4) {
	  this.R4 = R4;
	 }

	 public void setR5(String R5) {
	  this.R5 = R5;
	 }

	 public void setR6(String R6) {
	  this.R6 = R6;
	 }

	 public void setR7(String R7) {
	  this.R7 = R7;
	 }

	 public void setR8(String R8) {
	  this.R8 = R8;
	 }

	 public void setR9(String R9) {
	  this.R9 = R9;
	 }

	 public void setR10(String R10) {
	  this.R10 = R10;
	 }

	 public void setR11(String R11) {
	  this.R11 = R11;
	 }

	 public void setR12(String R12) {
	  this.R12 = R12;
	 }

	 public void setSP(String SP) {
	  this.SP = SP;
	 }

	 public void setLR(String LR) {
	  this.LR = LR;
	 }

	 public void setPC(String PC) {
	  this.PC = PC;
	 }

	 public void setT0(int T0) {
	  this.T0 = T0;
	 }

	 public void setT1(int T1) {
	  this.T1 = T1;
	 }

	 public void setTfim(int Tfim) {
	  this.Tfim = Tfim;
	 }

	public void setTriggerPC(String triggerPC) {
		this.triggerPC = triggerPC;
	}
	
	public void setTriggerPCEnd(String triggerPCEnd) {
		this.triggerPCEnd = triggerPCEnd;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	public int getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(int timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getFaulyType() {
		return faulyType;
	}

	public void setFaulyType(String faulyType) {
		this.faulyType = faulyType;
	}
	
	
	
	 
	 
	}