package injectorHeart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import dataModel.Experiment;
import dataModel.ExperimentHistoryDataBase;
import dataModel.LastExperiment;
import faultTypes.Fault;
import faultTypes.SpaceBasedFault;
import faultTypes.TimeBasedFault;
import language.LanguageInterface;
import language.PortugueseStringTable;
import sideNavigation.DefineExperimentController;

public class GenerationTool {

	private Experiment data;
	private HashMap<String, String> registersAffected;
	private LanguageInterface stringTable; 
	private boolean goldenRun;
	private int triggerType;
	private String typeOfFaults;

	public GenerationTool(Experiment data, LanguageInterface stringTable, boolean goldenRun) {
		this.data = data;
		this.registersAffected = new HashMap<>();
		this.stringTable = stringTable;
		this.goldenRun = goldenRun;
	}
	
	public GenerationTool(Experiment data, LanguageInterface stringTable) {
		this.data = data;
		this.registersAffected = new HashMap<>();
		this.stringTable = stringTable;
		this.goldenRun = false;
	}

	/**
	 * Validate the experiment data
	 *
	 * @return
	 * @throws Exception
	 */
	public void validateData(int triggerType) throws Exception {
		
		validateRegisterFaults(data.getR0());
		validateRegisterFaults(data.getR1());
		validateRegisterFaults(data.getR2());
		validateRegisterFaults(data.getR3());
		validateRegisterFaults(data.getR4());
		validateRegisterFaults(data.getR5());
		validateRegisterFaults(data.getR6());
		validateRegisterFaults(data.getR7());
		validateRegisterFaults(data.getR8());
		validateRegisterFaults(data.getR9());
		validateRegisterFaults(data.getR10());
		validateRegisterFaults(data.getR11());
		validateRegisterFaults(data.getR12());
		validateRegisterFaults(data.getSP());
		validateRegisterFaults(data.getLR());
		validateRegisterFaults(data.getPC());


		this.sortRegiter();
		if (registersAffected.isEmpty())
			throw new Exception(stringTable.getMasksNotChangedEx());
		if (data.getBitsPerFault() == 0)
			throw new Exception(stringTable.getNumBitsNotDefinedEx());
		if (data.getNumFaults() == 0)
			throw new Exception(stringTable.getNumFaultsNotDefinedEx());

		// Trigeers Validation
		if(triggerType == 0) { //Time Based
			this.triggerType = 0;
			if (data.getT1() <= data.getT0())
				throw new Exception(stringTable.getT1Ex());
			if (data.getTfim() <= data.getT1())
				throw new Exception(stringTable.getTFimEx());
		}else if(triggerType == 1) {  // Spatial Based
			this.triggerType = 1;
			
		}
		
	}

	/**
	 * Validate if the faults number is not bigger than the max bits per fault value
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void validateRegisterFaults(String value) throws Exception {
		 int count = value.length() - value.replace("1", "").length();
		 if(count < data.getBitsPerFault() && count != 0)
			 throw new Exception(stringTable.getBitsPerFaultsNotCorrect());
		if (value.length() != DefineExperimentController.NUMBER_OF_BITS)
			throw new Exception(stringTable.getMasksLengthSmallEx());
	}

	/**
	 * Save the target registers
	 */
	private void sortRegiter() {
		if (verifyRegisterAlteration(data.getR0()))
			registersAffected.put("R0", data.getR0());
		if (verifyRegisterAlteration(data.getR1()))
			registersAffected.put("R1", data.getR1());
		if (verifyRegisterAlteration(data.getR2()))
			registersAffected.put("R2", data.getR2());
		if (verifyRegisterAlteration(data.getR3()))
			registersAffected.put("R3", data.getR3());
		if (verifyRegisterAlteration(data.getR4()))
			registersAffected.put("R4", data.getR4());
		if (verifyRegisterAlteration(data.getR5()))
			registersAffected.put("R5", data.getR5());
		if (verifyRegisterAlteration(data.getR6()))
			registersAffected.put("R6", data.getR6());
		if (verifyRegisterAlteration(data.getR7()))
			registersAffected.put("R7", data.getR7());
		if (verifyRegisterAlteration(data.getR8()))
			registersAffected.put("R8", data.getR8());
		;
		if (verifyRegisterAlteration(data.getR9()))
			registersAffected.put("R9", data.getR9());
		if (verifyRegisterAlteration(data.getR10()))
			registersAffected.put("R10", data.getR10());
		if (verifyRegisterAlteration(data.getR11()))
			registersAffected.put("R11", data.getR11());
		if (verifyRegisterAlteration(data.getR12()))
			registersAffected.put("R12", data.getR12());
		if (verifyRegisterAlteration(data.getSP()))
			registersAffected.put("SP", data.getSP());
		if (verifyRegisterAlteration(data.getLR()))
			registersAffected.put("LR", data.getLR());
		if (verifyRegisterAlteration(data.getPC()))
			registersAffected.put("PC", data.getPC());
	}

	/**
	 * Verify if the all 32 bits of a register assumes the value ZERO
	 * 
	 * @param register
	 * @return
	 */
	private boolean verifyRegisterAlteration(String register) {
		return !register.equals(DefineExperimentController.MASK_ZEROS);
	}

	/*
	 * Save experiment definition in a JSON file
	 */
	public void experimentDefinitionToJSONFile(String path) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter fw = null;
		String arr[] = data.getName().split(" ", 2);
		try {
			fw = new FileWriter(path + "//" + arr[0].toLowerCase() + ".json");
			data.setCsvFile(path + "\\" + arr[0].toLowerCase() + ".csv");
			data.setFaulyType(typeOfFaults);  //SET THE TYPE OF FAULT INTO THE JSON FILE
			gson.toJson(data, fw);
		} catch (IOException e) {

		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					throw new Exception(stringTable.getJsonEx());
				}
		}

	}

	/**
	 * Genrerate a list of faults
	 * 
	 * @return
	 */
	private List<Fault> generateFaults() {
		Random rnd = new Random();
		Random rndSortFaults = new Random();
		
		int actualR;
		Set<String> keys = registersAffected.keySet();
		List<Fault> faults = new ArrayList<>();

		if (data.getSeed()) {
			rnd.setSeed(data.getSeedValue());
			rndSortFaults.setSeed(data.getSeedValue());
		}

		for (int i = 0; i < data.getNumFaults(); i++) {
			Fault newFault=null;
			if(triggerType == 0)
				newFault = new TimeBasedFault();
			else if(triggerType == 1)
				newFault = new SpaceBasedFault();
			
			actualR = rnd.nextInt(registersAffected.size());
			int j = 0;
			for (String reg : keys) {
				if (j == actualR) {
					newFault.setRegister(reg);
					if(!this.goldenRun)
						newFault.setMask(sortFaults(registersAffected.get(reg), rndSortFaults));
					else
						newFault.setMask(DefineExperimentController.MASK_ZEROS);
					break;
				}
				j++;
			}
			if(triggerType == 0) {
				newFault.setT0(data.getT0());
				newFault.setT1(data.getT1());
				newFault.setTfim(data.getTfim());
				// Generate a number between [T0, T1]
				int tInj = rnd.nextInt(data.getT1() - data.getT0()) + data.getT0();
				newFault.setTinj(tInj);
			}else if(triggerType == 1) {
				int decimalPCStart = Integer.parseInt(data.getTriggerPC(),16);
				int decimalPCEnd = Integer.parseInt(data.getTriggerPCEnd(),16);
				int injectMoment = rnd.nextInt(decimalPCEnd - decimalPCStart) + decimalPCStart;
				String hexadecimalInjMoment = Integer.toHexString(injectMoment);
				newFault.setTpc(hexadecimalInjMoment);
				newFault.setTfim(data.getTimeEnd());
			}
			
			faults.add(newFault);
		}
		return faults;
	}

	/**
	 * Random selection of the bits to inject the faults.
	 * @param register
	 * @param rnd
	 * @return
	 */
	public String sortFaults(String register, Random rnd) {
		List<Integer> pos = new ArrayList<>();
		List<Integer> sorterPositions = new ArrayList<>();
		StringBuilder strBuilder = new StringBuilder(DefineExperimentController.MASK_ZEROS);
		
		if(getNumberofBitsWithValueOne(register) <= data.getBitsPerFault())
			return register;
		
		for (int i = 0; i < register.length(); i++) 
			if (register.charAt(i) == '1')
				pos.add(i);
		
		for(int i=0; i<data.getBitsPerFault(); i++) {
			int p;
			do {
				p = rnd.nextInt(pos.size());
			}while(sorterPositions.contains(p));
			strBuilder.setCharAt(pos.get(p), '1');
			sorterPositions.add(p);
		}
		
		return strBuilder.toString();
	}

	/**
	 * Get the number of bits with the value "1" in a register
	 * @param register
	 * @return
	 */
	public int getNumberofBitsWithValueOne(String register) {
		return register.length() - register.replace("1", "").length();
	}

	public void writeCsvFileWithFaults(String path) throws Exception {
		List<Fault> faults = generateFaults();
		String arr[] = data.getName().split(" ", 2);
		File file = new File(path + "\\" + arr[0].toLowerCase() + ".csv");
		FileWriter outputfile;
		CSVWriter writer;
		try {
			outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);

			// adding header to csv
			writer.writeNext(Fault.experimentNametoArray(data.getName()));
			writer.writeNext(Fault.experimentDescriptiontoArray(data.getDescription().replace("\n", " ")));
			writer.writeNext(Fault.experimentResponsabletoArray(data.getResponsable()));
			writer.writeNext(Fault.experimentDatetoArray(data.getDate()));
			String[] space = new String[1];
			space[0] = "-";
			for(int i=0; i<5; i++)
				writer.writeNext(space);
			
			//Target
			String[] target = new String[2];
			target[0] = "Target: ";
			target[1] = CSVReader.getConfigs().get(0);
			writer.writeNext(target);
			
			//Faults type (triggers)
			String[] faultTypeRecord = new String[2];
			faultTypeRecord[0] = "Fault Type: ";
			
			// add faults to csv
			boolean first = true;
			for (Fault fault : faults) {
				if(first) {
					this.typeOfFaults = fault.getFaultType();
					faultTypeRecord[1] = fault.getFaultType();
					writer.writeNext(faultTypeRecord);
					writer.writeNext(fault.headerToArray()); 	 
					first = false;
				}
				writer.writeNext(fault.toArray());
			}

			writer.close();
		} catch (IOException e) {
			throw new Exception(stringTable.getCsvEx());
		}
		this.updateExpirimentDataBaseFile(path + "\\" + arr[0].toLowerCase() + ".json", data.getName(), data.getDate());
	}
	
	/**
	 * Update the Experiments Generation Database with the last experiment generated
	 * @param newExperimentFile
	 * @throws Exception 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void updateExpirimentDataBaseFile(String newExperimentPath, String newExperimentName, String newExperimentDate) throws Exception {
		ExperimentHistoryDataBase db;
		try {
			db = ExperimentDataBaseLogic.getDataBaseHistory();
		} catch (Exception e1) { db = new ExperimentHistoryDataBase(); }
		try {
			if(db.getExperimentsHistory().size() >= ExperimentHistoryDataBase.DATA_BASE_SIZE)
				db.getExperimentsHistory().remove(0);
			db.getExperimentsHistory().add(new LastExperiment(newExperimentPath, newExperimentName, newExperimentDate));
			ExperimentDataBaseLogic.updateDataBaseHistory(db);
		} catch (IOException e) {
			throw new Exception(e.getMessage());  //TODO tratar esta exception
		}
	}

}
