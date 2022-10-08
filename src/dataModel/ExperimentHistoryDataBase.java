package dataModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ExperimentHistoryDataBase implements Serializable {


	public static final int DATA_BASE_SIZE = 11;
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<LastExperiment> experimentsHistory;

	public ExperimentHistoryDataBase() {
		super();
		this.experimentsHistory = new ArrayList<>();
	}

	public ArrayList<LastExperiment> getExperimentsHistory() {
		return experimentsHistory;
	}

	public void setExperimentsHistory(ArrayList<LastExperiment> experimentsHistory) {
		this.experimentsHistory = experimentsHistory;

	}
	
	

}
