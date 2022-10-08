package language;

import javafx.scene.text.Text;

public interface LanguageInterface {
	// Menu Buttons Strings
	public String getHomePage();
	public String getBtnDefineExperiment();
	public String getBtnExecuteExperiment();
	public String getBtnAbout();
	public String getBtnEdit();
	public String getBtnSearch();
	public String getBtnExecute();
	public String getBtnOption();
	
	//Define Experiment Page Strings
	public String getDefineExperiment();
	public String getExpirimentName();
	public String getExperimentDec();
	public String getNumFaults();
	public String getBitFlipsNum();
	public String getSeedString();
	public String getGenerateExperiment();
	public String getImportExperiemnt();
	public String getDefMascAndT();
	public String getTEnd();
	public String getExperimentGenaretedWithSucess();
	public String getResponsableLabelText();
	public String getT0ToolTip();
	public String getT1ToolTip();
	public String getTEndToolTip();
	
	// Home Page Strings
	public String getHomePageTitle();
	public String getLastExpeirienceLabel();
	public String getBackAndLoseData();
	public String getName();
	public String getDate();
	public String getPath();
	
	// Execution Page String
	public String getInjectionLogPart1();
	public String getInjectionLogPart2();
	public String getInjectionThreadExceptionMessage();
	public String getInitializeConnectionWithOpenOCDServerExceptionMessage();
	public String getExecutionFinalizeWithSuccess();
	public String getFaultString();
	public String getAbortingError();
	public String getBtnAbort();
	public String getBtnPause();
	public String getBtnPlay();
	public String getExperimentExecution();
	public String getBoardUsbPort();
	public String getChooseUsbPort();
	public String getAbortMessage();
	
	//Exceptions  Strings
	public String getBitsPerFaultsNotCorrect();
	public String getMasksNotChangedEx();
	public String getNumBitsNotDefinedEx();
	public String getNumFaultsNotDefinedEx();
	public String getT1Ex();
	public String getTFimEx();
	public String getMasksLengthSmallEx();
	public String getJsonEx();
	public String getCsvEx();
	public String getNoTableRowSelected();
	public String getJSONProblemException();
	
	//Options Page Strings
	public String getToolConfigurations();
	
	
	//Others
	public String getAlert();
	
	
	//ABout Page Strings
	public String getAboutProduct();
	public String getAboutVersion();
	public String getAboutDate();
	public String getAboutAuthors();
	public String getAboutDavid();
	public String getAboutHenrique();
	public String getAboutINPE();
	
}
