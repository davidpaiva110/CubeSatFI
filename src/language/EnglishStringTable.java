package language;

import javax.sound.sampled.Port;

import javafx.scene.text.Text;

public class EnglishStringTable implements LanguageInterface{
	
	public EnglishStringTable() {}

	@Override
	public String getHomePage() {
		return "Home Page";
	}

	@Override
	public String getBtnDefineExperiment() {
		return "Define Experiment";
	}

	@Override
	public String getBtnExecuteExperiment() {
		return "Execute Experiment";
	}

	@Override
	public String getBtnAbout() {
		return "About";
	}

	@Override
	public String getDefineExperiment() {
		return "Define Experiment";
	}

	@Override
	public String getExpirimentName() {
		return "Experiment Name";
	}

	@Override
	public String getExperimentDec() {
		return "Experiment Description";
	}

	@Override
	public String getNumFaults() {
		return "Total Number of Failures to be Generated";
	}

	@Override
	public String getBitFlipsNum() {
		return "Number of Bit Flips per Fault";
	}

	@Override
	public String getSeedString() {
		return "Use Generation Seed";
	}

	@Override
	public String getGenerateExperiment() {
		return "Generate Experiment";
	}

	@Override
	public String getImportExperiemnt() {
		return "Import Experimet";
	}

	@Override
	public String getDefMascAndT() {
		return "Define Failure Masks and Triggers";
	}

	@Override
	public String getTEnd() {
		return "Trigger TEnd";
	}

	@Override
	public String getAlert() {
		return "Alert";
	}

	@Override
	public String getExperimentGenaretedWithSucess() {
		return "Experiment successfully genereted and saved.";
	}

	@Override
	public String getMasksNotChangedEx() {
		return "No bitmasks have been changed. Please set the masks for the registers you want to change.";
	}

	@Override
	public String getNumBitsNotDefinedEx() {
		return "The number of bits to be injected into a fault has not been defined.";
	}

	@Override
	public String getNumFaultsNotDefinedEx() {
		return "The number of experiment faults has not been defined.";
	}

	@Override
	public String getT1Ex() {
		return "Trigger T1 is less or equal than trigger T0. Please enter a higher value.";
	}

	@Override
	public String getTFimEx() {
		return "The Final Trigger is less or equal than trigger T1. Please enter a higher value.";
	}

	@Override
	public String getMasksLengthSmallEx() {
		return "The masks of one or more registers are less than 32 bits.";
	}

	@Override
	public String getJsonEx() {
		return "There was an error creating the JSON file.";
	}

	@Override
	public String getCsvEx() {
		return "There was an error creating the CSV file.";
	}

	@Override
	public String getHomePageTitle() {
		return "CubeSat Fault Injection Tool";
	}

	@Override
	public String getLastExpeirienceLabel() {
		return "Experiment History";
	}

	@Override
	public String getBtnEdit() {
		return "Edit";
	}

	@Override
	public String getBtnSearch() {
		return "Search";
	}

	@Override
	public String getBtnExecute() {
		return "Execute";
	}

	@Override
	public String getBackAndLoseData() {
		return "Do you want to return to the home page? All data will be lost!";
	}

	@Override
	public String getBitsPerFaultsNotCorrect() {
		return "The masks of one or more registers have a number of positive bits less than the number of bit flips per fault specified.";
	}

	@Override
	public String getNoTableRowSelected() {
		return "Please select an exeperiment.";
	}

	@Override
	public String getResponsableLabelText() {
		return "Responsable Person";
	}

	@Override
	public String getInjectionLogPart1() {
		return "Injection fault number ";
	}

	@Override
	public String getInjectionLogPart2() {
		return " of ";
	}

	@Override
	public String getInjectionThreadExceptionMessage() {
		return "An error occours during the faults injection.";
	}

	@Override
	public String getInitializeConnectionWithOpenOCDServerExceptionMessage() {
		return "An error occurred when connecting to the OpenOCD server.";
	}

	@Override
	public String getExecutionFinalizeWithSuccess() {
		return "The fault injection campaign ended successfully. \nWere injected: ";
	}

	@Override
	public String getFaultString() {
		return " faults";
	}

	@Override
	public String getJSONProblemException() {
		return "The chosen JSON file is not formatted for the tool. Please choose another file.";
	}

	@Override
	public String getT0ToolTip() {
		return "Start of the injection \nwindow (miliseconds).";
	}

	@Override
	public String getT1ToolTip() {
		return "End of the injection \nwindow (miliseconds).";
	}

	@Override
	public String getTEndToolTip() {
		return "End point of the experiment (miliseconds).\n The interval time between T1 and TEnd\n will be used to collect data.";
	}

	@Override
	public String getAbortingError() {
		return "\nAn error occurs. Aborting fault injection campaign.\nPlease, verify if the JTAG is properly connected to the host PC.";
	}

	@Override
	public String getBtnAbort() {
		return "ABORT";
	}

	@Override
	public String getBtnPause() {
		return "PAUSE";
	}

	@Override
	public String getBtnPlay() {
		return "PLAY";
	}

	@Override
	public String getExperimentExecution() {
		return "Experiment Execution";
	}

	@Override
	public String getBtnOption() {
		return "Options";
	}

	@Override
	public String getBoardUsbPort() {
		return "Select the Board USB Port:";
	}

	@Override
	public String getChooseUsbPort() {
		return "USB Port ...";
	}

	@Override
	public String getAbortMessage() {
		return "Do you want to abort the experience?";
	}

	@Override
	public String getName() {
		return "Name";
	}

	@Override
	public String getDate() {
		return "Date";
	}

	@Override
	public String getPath() {
		return "File Path";
	}

	@Override
	public String getToolConfigurations() {
		return "Tool Configurations";
	}

	
	
	
	@Override
	public String getAboutProduct() {
		return "Product";
	}

	@Override
	public String getAboutVersion() {
		return "Version: 2.0";
	}

	@Override
	public String getAboutDate() {
		return "24/06/2022";
	}

	@Override
	public String getAboutAuthors() {
		return "Autores";
	}

	@Override
	public String getAboutDavid() {
		return "David Paiva - CISUC/University of Coimbra";
	}

	@Override
	public String getAboutHenrique() {
		return "Dr. Henrique Madeira - CISUC/University of Coimbra";
	}

	@Override
	public String getAboutINPE() {
		return "In partnership with the Brazilian National Institute for Space Research";
	}
}
