package sideNavigation;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import dataModel.Experiment;
import dataModel.ExperimentHistoryDataBase;
import dataModel.LastExperiment;
import injectorHeart.ExperimentDataBaseLogic;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import language.LanguageInterface;


public class HomePageController  implements Initializable {

	 @FXML
	private AnchorPane apHomePage;
	
	@FXML
    private Text tlTitle, tlLastExp;

    @FXML
    private TableView<LastExperiment> tableHomePage;
    
    @FXML
    private TableColumn<LastExperiment, String> columnName;

    @FXML
    private TableColumn<LastExperiment, String> columnPath;
    
    @FXML
    private TableColumn<LastExperiment, String> columnDate;

    @FXML
    private ImageView ivIconLeft, ivIconCenter, ivIconRight;
    
    @FXML
    private Button btnEditHP, btnExecuteHM, btnSearchHP;
    
    private LanguageInterface stringTable;
    
    private TableViewSelectionModel<LastExperiment> selectionModel;
	
    private SideBarController sideBarController;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Set Logos
		this.setImage("img/inpe_logo.png", ivIconRight);
		this.setImage("img/cisuc_logo.png", ivIconLeft);
		this.setImage("img/fctuc_logo.png", ivIconCenter);
		
		initializeTableHistory();
		selectionModel = tableHomePage.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
	}
	
	/**
	 * Initialize the history table with last experiments.
	 */
	public void initializeTableHistory() {
		columnName.setCellValueFactory(new PropertyValueFactory<>("experimentName")); 
		columnPath.setCellValueFactory(new PropertyValueFactory<>("path"));           
		columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));           
		this.updateExeperiemntHistory();
	}
	
	public void setImage(String path, ImageView iv) {
		File logo = new File(path);
		Image image = new Image(logo.toURI().toString());
		iv.setImage(image);
	}

	public LanguageInterface getStringTable() {
		return stringTable;
	}

	public void setStringTable(LanguageInterface stringTable) {
		this.stringTable = stringTable;
	}
	
	public void updateLanguageView() {
		tlTitle.setText(stringTable.getHomePageTitle());
		tlLastExp.setText(stringTable.getLastExpeirienceLabel());
		btnEditHP.setText(stringTable.getBtnEdit());
		btnExecuteHM.setText(stringTable.getBtnExecute());
		btnSearchHP.setText(stringTable.getBtnSearch());
		columnName.setText(stringTable.getName());
		columnPath.setText(stringTable.getPath());
		columnDate.setText(stringTable.getDate());
	}
	
	@FXML
    void onClickEditBtnHP(MouseEvent event) {
    	LastExperiment exp = null;
    	try {
			exp = getTableRowSelected();
		} catch (Exception e) {
			showAletDialog(e.getMessage());
			return;
		}
    	if(!theFileExists(exp.getPath()))
    		return;  //TODO ALERT DIAOLOG
    	this.sideBarController.loadPage("DefineExperiment", SideBarController.DEFINITION_PAGE_CODE);
    	this.sideBarController.viewSettingFromHomePageToExperimentDefenition();
    	this.sideBarController.getExperimentController().setImportData(exp.getPath());
    }

    @FXML
    void onClickExecuteBtnHP(MouseEvent event) {
    	LastExperiment exp = null;
    	try {
			exp = getTableRowSelected();
		} catch (Exception e) {
			showAletDialog(e.getMessage());
			return;
		}
    	if(!theFileExists(exp.getPath()))
    		return;  //TODO ALERT DIAOLOG
    	this.sideBarController.setPreScreenExecutionLayout(exp.getPath(), this.sideBarController.btnMenu1);
    }

    @FXML
    void onClickSearchBtnHP(MouseEvent event) {
    	FileChooser filechoser = new FileChooser();
		configureFileChooser(filechoser);
		Stage stage = (Stage) apHomePage.getScene().getWindow();
		File file = filechoser.showOpenDialog(stage);
		
		BufferedReader br;
		Experiment experimentData;
		FileReader fr;
		try {
			fr = new FileReader(file.getCanonicalPath());
			br = new BufferedReader(fr);
			experimentData = new Gson().fromJson(br, Experiment.class);
			fr.close();
		} catch (Exception e) { return; } //TODO ShowAlert que o ficheiro não  respeita a estrutura decidida
		
		//Update the DataBase
		ExperimentHistoryDataBase db = null;
		try {
			 db = ExperimentDataBaseLogic.getDataBaseHistory();
		} catch (Exception e) {
			this.showAletDialog("Não existe histórico de experiências recentes. Configuração do PROGRAMA está corrompida. Base de dados foi apagada.");  //TODO
			//Colocar na tabela no meio a frase que não tem histórico de experiências recentes.
			return;
		}
		
		try {
			db.getExperimentsHistory().add(new LastExperiment(file.getCanonicalPath(), experimentData.getName(), experimentData.getDate()));
			ExperimentDataBaseLogic.updateDataBaseHistory(db);
		} catch (IOException e) {
			// TODO
		}
		updateExeperiemntHistory();
    }
    
    
    /**
     * Get table row selected
     * @return
     * @throws Exception
     */
    private LastExperiment getTableRowSelected() throws Exception {
    	ObservableList<LastExperiment> selectedItems = selectionModel.getSelectedItems();
    	LastExperiment ex = null;
    	try {
    		ex = selectedItems.get(0);
    	}catch(Exception e) {
    		throw new Exception(stringTable.getNoTableRowSelected());
    	}
		return ex;
    }
    
    /**
     * Update Experiment History View
     */
    private void updateExeperiemntHistory() {
    	ExperimentHistoryDataBase db = null;
    	try {
			 db = ExperimentDataBaseLogic.getDataBaseHistory();
		} catch (Exception e) {
			this.showAletDialog("Não existe histórico de experiências recentes.");  //TODO
			//Colocar na tabela no meio a frase que não tem histórico de experiências recentes.
			return;
		}
    	tableHomePage.getItems().clear();
    	for(int i = db.getExperimentsHistory().size()-1; i >= 0; i--) { 
    		LastExperiment exp = db.getExperimentsHistory().get(i);
    		// Verify if the file exists in the file system
    		boolean fileExists = this.theFileExists(exp.getPath());
    		//Add the experiment line into the history if the file exists
    		if(fileExists)
    			tableHomePage.getItems().add(exp);
    		else
    			db.getExperimentsHistory().remove(exp);
    			
    	}
    	try {
			ExperimentDataBaseLogic.updateDataBaseHistory(db);
		} catch (IOException e) {
			// TODO
		}
    }
    
    /**
     * Verify if a file exists in the file system
     * @param path
     * @return
     */
    public boolean theFileExists(String path) {
    	try {
    		FileInputStream fileIn = new FileInputStream(path);
			fileIn.read();
			fileIn.close();
		} catch (IOException e) {
			return false;
		}
    	return true;
    }
    
    
    /**
	 * Show an alert message
	 * @param message
	 */
	public void showAletDialog(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.setHeaderText("");
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.setTitle(stringTable.getAlert());	
		alert.showAndWait();
	}
	
	public void setSideBarController(SideBarController sideBarController) {
		this.sideBarController = sideBarController;
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
	
	

}
