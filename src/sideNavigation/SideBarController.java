package sideNavigation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import dataModel.Experiment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import language.EnglishStringTable;
import language.LanguageInterface;
import language.PortugueseStringTable;

public class SideBarController implements Initializable {

	public final static int ABOUT_PAGE_CODE = 0;                          // About Page Code
	public final static int HOME_PAGE_CODE = 1;                           // Home Page Code
	public final static int DEFINITION_PAGE_CODE = 2;                     // Definition Page Code
	public final static int EXECUTE_EXPERIENCE_CODE = 3;                  // Execute Experiment Page Code
	public final static int PRE_EXECUTE_EXPERIENCE_CODE = 4;              // Experiment Information Page Code
	public final static int OPTION_PAGE_CODE = 5;						  // Option Page Code


	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane ap;
	@FXML
	public Button btnMenu1, btnMenu2, btnMenu3, btnMenu4, btnMenuHelp;
	@FXML
	private Button btnEN, btnPT;
	@FXML
	private ImageView sideBarLogo;

	private int actualPage = HOME_PAGE_CODE;
	private Pane root = null;
	private boolean campaignInExecution = false;
	
	// Controllers
	private DefineExperimentController experimentController;
	private HomePageController homePageController;
	private PreScreenExecution preScreenExecution;
	private ExperimentExecutionController experimentExecutionController;
	private AboutController aboutController;
	private OptionPageController helpPageController;
	
	// Strings Table
	private LanguageInterface stringTable = new PortugueseStringTable();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.setImage("img/cubesatFI.png", sideBarLogo);
		changeMenuButtonColor(btnMenu1, true);
		this.setButtonsText(new PortugueseStringTable());
		this.loadPage("HomePage", HOME_PAGE_CODE);
	}
	

	/**
	 * Change the color of the menu buttons
	 * @param btn
	 * @param selected
	 */
	private void changeMenuButtonColor(Button btn, boolean selected) {
		if (selected) {
			if (btn == btnMenu1)
				btn.setStyle(
						"-fx-background-color: #7f9eb2; -fx-border-color: white; -fx-border-width: 2px 0px 2px 0px;");
			else
				btn.setStyle(
						"-fx-background-color: #7f9eb2; -fx-border-color: white; -fx-border-width: 0px 0px 2px 0px;");
		} else {
			if(btn == btnMenu1)
				btn.setStyle(
						"-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px 0px 2px 0px;");
			else
				btn.setStyle(
						"-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 0px 0px 2px 0px;");
		}
	}
	
	/**
	 * Set the Buttons labels Text
	 * @param stringTable
	 */
	public void setButtonsText(LanguageInterface stringTable) {
		btnMenu1.setText(stringTable.getHomePage());
		btnMenu2.setText(stringTable.getBtnDefineExperiment());
		btnMenu3.setText(stringTable.getBtnExecuteExperiment());
		btnMenu4.setText(stringTable.getBtnAbout());
		btnMenuHelp.setText(stringTable.getBtnOption());
	}

	/**
	 * Home Page Button event handler
	 * @param event
	 */
	@FXML
	private void onClickHomePage(MouseEvent event) {
		switch (actualPage) {
		case DEFINITION_PAGE_CODE:
			if (showAletDialog(stringTable.getBackAndLoseData())) {
				loadPage("HomePage", HOME_PAGE_CODE);
				changeMenuButtonColor(btnMenu2, false);
				changeMenuButtonColor(btnMenu1, true);
				btnEN.setVisible(true);
				btnPT.setVisible(true);
			}
			break;
		case EXECUTE_EXPERIENCE_CODE:
			if(!campaignInExecution) {
				loadPage("HomePage", HOME_PAGE_CODE);
				changeMenuButtonColor(btnMenu3, false);
				changeMenuButtonColor(btnMenu1, true);
				btnEN.setVisible(true);
				btnPT.setVisible(true);
			}
			break;
		case ABOUT_PAGE_CODE:
			loadPage("HomePage", HOME_PAGE_CODE);
			changeMenuButtonColor(btnMenu4, false);
			changeMenuButtonColor(btnMenu1, true);
			btnEN.setVisible(true);
			btnPT.setVisible(true);
			break;
		case PRE_EXECUTE_EXPERIENCE_CODE:
			loadPage("HomePage", HOME_PAGE_CODE);
			changeMenuButtonColor(btnMenu3, false);
			changeMenuButtonColor(btnMenu1, true);
			btnEN.setVisible(true);
			btnPT.setVisible(true);
			break;
		case OPTION_PAGE_CODE:
			loadPage("HomePage", HOME_PAGE_CODE);
			changeMenuButtonColor(btnMenuHelp, false);
			changeMenuButtonColor(btnMenu1, true);
			btnEN.setVisible(true);
			btnPT.setVisible(true);
			break;
		}
	}

	/**
	 * Define Experiment Button event handler
	 * @param event
	 */
	@FXML
	private void onClickButton2(MouseEvent event) {
		switch (actualPage) {
		case HOME_PAGE_CODE:
			loadPage("DefineExperiment", DEFINITION_PAGE_CODE);
			changeMenuButtonColor(btnMenu2, true);
			changeMenuButtonColor(btnMenu1, false);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case EXECUTE_EXPERIENCE_CODE:
			if(!campaignInExecution) {
				loadPage("DefineExperiment", DEFINITION_PAGE_CODE);
				changeMenuButtonColor(btnMenu2, true);
				changeMenuButtonColor(btnMenu3, false);
				btnEN.setVisible(false);
				btnPT.setVisible(false);
			}
			break;
		case OPTION_PAGE_CODE:
			loadPage("DefineExperiment", DEFINITION_PAGE_CODE);
			changeMenuButtonColor(btnMenu2, true);
			changeMenuButtonColor(btnMenuHelp, false);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case ABOUT_PAGE_CODE:
			loadPage("DefineExperiment", DEFINITION_PAGE_CODE);
			changeMenuButtonColor(btnMenu2, true);
			changeMenuButtonColor(btnMenu4, false);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Execution Button event Handler
	 * @param event
	 */
	@FXML
	private void onClickButton3(MouseEvent event) {
		switch (actualPage) {
		case HOME_PAGE_CODE:
			try {
				setPreScreenExecutionLayout(getJSONFilePath(), btnMenu1);
			} catch (Exception e) {
				showAletDialog(stringTable.getJSONProblemException());
			}
			break;
		case DEFINITION_PAGE_CODE:
			try {
				setPreScreenExecutionLayout(getJSONFilePath(), btnMenu2);
			} catch (Exception e) {
				showAletDialog(stringTable.getJSONProblemException());
			}
			break;
		case ABOUT_PAGE_CODE:
			try {
				setPreScreenExecutionLayout(getJSONFilePath(), btnMenu4);
			} catch (Exception e) {
				showAletDialog(stringTable.getJSONProblemException());
			}
			break;
		case OPTION_PAGE_CODE:
			try {
				setPreScreenExecutionLayout(getJSONFilePath(), btnMenuHelp);
			} catch (Exception e) {
				showAletDialog(stringTable.getJSONProblemException());
			}
			break;
		}
	}

	/**
	 * About Button event handler
	 * @param event
	 */
	@FXML
	private void onClickButton4(MouseEvent event) {
		switch (actualPage) {
		case HOME_PAGE_CODE:
			loadPage("About", ABOUT_PAGE_CODE);
			changeMenuButtonColor(btnMenu1, false);
			changeMenuButtonColor(btnMenu4, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case DEFINITION_PAGE_CODE:
			loadPage("About", ABOUT_PAGE_CODE);
			changeMenuButtonColor(btnMenu2, false);
			changeMenuButtonColor(btnMenu4, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case EXECUTE_EXPERIENCE_CODE:
			loadPage("About", ABOUT_PAGE_CODE);
			changeMenuButtonColor(btnMenu3, false);
			changeMenuButtonColor(btnMenu4, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case PRE_EXECUTE_EXPERIENCE_CODE:
			loadPage("About", ABOUT_PAGE_CODE);
			changeMenuButtonColor(btnMenu3, false);
			changeMenuButtonColor(btnMenu4, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case OPTION_PAGE_CODE:
			loadPage("About", ABOUT_PAGE_CODE);
			changeMenuButtonColor(btnMenuHelp, false);
			changeMenuButtonColor(btnMenu4, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		}
	}
	
	/**
	 * Help Button event handler
	 * @param event
	 */
	@FXML
	private void onClickButtonHelp(MouseEvent event) {
		switch (actualPage) {
		case HOME_PAGE_CODE:
			loadPage("OptionPage", OPTION_PAGE_CODE);
			changeMenuButtonColor(btnMenu1, false);
			changeMenuButtonColor(btnMenuHelp, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case DEFINITION_PAGE_CODE:
			if (showAletDialog(stringTable.getBackAndLoseData())) {
			loadPage("OptionPage", OPTION_PAGE_CODE);
			changeMenuButtonColor(btnMenu2, false);
			changeMenuButtonColor(btnMenuHelp, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			}
			break;
		case EXECUTE_EXPERIENCE_CODE:
			if(!campaignInExecution) {
				loadPage("OptionPage", OPTION_PAGE_CODE);
				changeMenuButtonColor(btnMenu3, false);
				changeMenuButtonColor(btnMenuHelp, true);
				btnEN.setVisible(false);
				btnPT.setVisible(false);
			}
			break;
		case PRE_EXECUTE_EXPERIENCE_CODE:
			loadPage("OptionPage", OPTION_PAGE_CODE);
			changeMenuButtonColor(btnMenu3, false);
			changeMenuButtonColor(btnMenuHelp, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		case ABOUT_PAGE_CODE:
			loadPage("OptionPage", OPTION_PAGE_CODE);
			changeMenuButtonColor(btnMenu4, false);
			changeMenuButtonColor(btnMenuHelp, true);
			btnEN.setVisible(false);
			btnPT.setVisible(false);
			break;
		}
	}
	
	public String getJSONFilePath() throws Exception {
		FileChooser filechoser = new FileChooser();
		configureFileChooser(filechoser);
		Stage stage = (Stage) bp.getScene().getWindow();
		File file = filechoser.showOpenDialog(stage);
		
		BufferedReader br;
		Experiment experimentData;
		FileReader fr;
		String path;
		try {
			path = file.getCanonicalPath();
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			experimentData = new Gson().fromJson(br, Experiment.class);
			if(experimentData.getCsvFile().equals(""))
				throw new Exception();
			fr.close();
		} catch (Exception e) { throw e; } 
		
		return path;
	}
	
	/**
	 * FileChooser Configuration (JSON Files)
	 * @param fileChooser
	 */
	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.setTitle("View Pictures");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
	}
	


	/**
	 * Load a page to the center of the screen
	 * 
	 * @param page name
	 */
	public void loadPage(String page, int pageCode) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource(page + ".fxml")));
			fxmlLoader.setResources(null);
			root = fxmlLoader.load();
			switch (pageCode) {
			case HOME_PAGE_CODE:
				homePageController = fxmlLoader.getController();
				homePageController.setStringTable(stringTable);
				homePageController.updateLanguageView();
				homePageController.setSideBarController(this);
				actualPage = HOME_PAGE_CODE;
				break;
			case DEFINITION_PAGE_CODE:
				experimentController = fxmlLoader.getController();
				experimentController.setStringTable(stringTable);
				experimentController.setLabelsText();
				actualPage = DEFINITION_PAGE_CODE;
				break;
			case ABOUT_PAGE_CODE:
				aboutController = fxmlLoader.getController();
				aboutController.setStringTable(stringTable);
				aboutController.setTextLabels();
				actualPage = ABOUT_PAGE_CODE;
				break;
			case OPTION_PAGE_CODE:
				helpPageController = fxmlLoader.getController();
				helpPageController.setStringTable(stringTable);
				actualPage = OPTION_PAGE_CODE;
				break;
			}
		} catch (IOException e) {
			return;
		}
		bp.setCenter(root);
	}
	
	
	/**
	 * EN Button event handler.
	 * Change the app language to English
	 * @param event
	 */
	@FXML
	private void onClickButtonEN(MouseEvent event) {
		stringTable = new EnglishStringTable();
		refreshLanguageEvent();
	}

	/**
	 * PT Button event handler
	 * Change the app language to Portuguese
	 * @param event
	 */
	@FXML
	private void onClickButtonPT(MouseEvent event) {
		stringTable = new PortugueseStringTable();
		refreshLanguageEvent();
	}

	/**
	 * Update view with the new language
	 */
	private void refreshLanguageEvent() {
		setButtonsText(stringTable);
		switch (actualPage) {
		case HOME_PAGE_CODE:
			homePageController.setStringTable(stringTable);
			homePageController.updateLanguageView();
			break;
		case DEFINITION_PAGE_CODE:
			experimentController.setStringTable(stringTable);
			experimentController.setLabelsText();
			break;
		}
	}


	/**
	 * Show an alert message
	 * 
	 * @param message
	 */
	public boolean showAletDialog(String message) {
		Alert alert = new Alert(AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
		alert.setHeaderText("");
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.setTitle(stringTable.getAlert());
		ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		if (ButtonType.NO.equals(result)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Set the PreScreen of the Experiment Execution in the BorderPane centers.
	 * @param jsonFilePath
	 */
	public void setPreScreenExecutionLayout(String jsonFilePath, Button toChange)  {
		FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("PreScreenExecution.fxml")));
		fxmlLoader.setResources(null);
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
		}
		//Configure new Page Settings
		this.preScreenExecution = fxmlLoader.getController();
		this.preScreenExecution.setJsonFilePath(jsonFilePath);
		this.preScreenExecution.configureView(this);
		this.preScreenExecution.setLabelsText();
		//Set the new Page
		bp.setCenter(root);
		//Change Button Colors
		changeMenuButtonColor(toChange, false);
		changeMenuButtonColor(btnMenu3, true);
		if(actualPage == HOME_PAGE_CODE) {
			btnEN.setVisible(false);
			btnPT.setVisible(false);
		}
		this.actualPage = PRE_EXECUTE_EXPERIENCE_CODE;
	}

	public DefineExperimentController getExperimentController() {
		return experimentController;
	}

	public void setExperimentController(DefineExperimentController experimentController) {
		this.experimentController = experimentController;
	}
	
	/**
	 * Configure and set the Experiment Execution layout visible
	 * @param jsonFilePath
	 * @param defenitions
	 */
	public void setExperimentExecutionLayout(String jsonFilePath, Experiment defenitions) {
		FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("ExperimentExecution.fxml")));
		fxmlLoader.setResources(null);
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
		}
		//Configure new Page Settings
		this.experimentExecutionController = fxmlLoader.getController();
		this.experimentExecutionController.defineSettings(stringTable, defenitions, this);
		//Set the new Page
		bp.setCenter(root);
		this.actualPage = EXECUTE_EXPERIENCE_CODE;
		//Change Button Colors
		changeMenuButtonColor(btnMenu1, false);
		changeMenuButtonColor(btnMenu3, true);
	}
	
	/**
	 * Configure and set the Experiment Execution layout visible with usbPortID in order to collect Data 
	 * @param jsonFilePath
	 * @param defenitions
	 * @param usbPortID
	 */
	public void setExperimentExecutionLayout(String jsonFilePath, Experiment defenitions, String usbPortID) {
		FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("ExperimentExecution.fxml")));
		fxmlLoader.setResources(null);
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
		}
		//Configure new Page Settings
		this.experimentExecutionController = fxmlLoader.getController();
		this.experimentExecutionController.defineSettings(stringTable, defenitions, this, usbPortID);
		//Set the new Page
		bp.setCenter(root);
		this.actualPage = EXECUTE_EXPERIENCE_CODE;
		//Change Button Colors
		changeMenuButtonColor(btnMenu1, false);
		changeMenuButtonColor(btnMenu3, true);
	}
	
	/**
	 * Change Button Colors when user change from Execution Page to Home Page
	 */
	public void menuBtnChangeExecutionToHomePage() {
		this.changeMenuButtonColor(btnMenu1, true);
		this.changeMenuButtonColor(btnMenu3, false);
		btnEN.setVisible(true);
		btnPT.setVisible(true);
	}

	public LanguageInterface getStringTable() {
		return stringTable;
	}
	
	public void setCampaignInExecution(boolean value) {
		this.campaignInExecution = value;
	}

	public void viewSettingFromHomePageToExperimentDefenition() {
		changeMenuButtonColor(btnMenu2, true);
		changeMenuButtonColor(btnMenu1, false);
		btnEN.setVisible(false);
		btnPT.setVisible(false);
	}
	
	public void setImage(String path, ImageView iv) {
		File logo = new File(path);
		Image image = new Image(logo.toURI().toString());
		iv.setImage(image);
	}
	
}
