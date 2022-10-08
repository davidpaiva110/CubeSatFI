package dataModel;

import java.io.Serializable;

public class LastExperiment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String path = null;
	private String experimentName = null;
	private String data = null;
	
	public LastExperiment(String path, String experimentName, String data) {
		super();
		this.path = path;
		this.experimentName = experimentName;
		this.data = data;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getExperimentName() {
		return experimentName;
	}
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
}
