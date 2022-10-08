package sideNavigation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import dataModel.Experiment;
import injectorHeart.GenerationTool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import language.LanguageInterface;

public class DefineExperimentController implements Initializable {

	public final static String MASK_ZEROS = "00000000000000000000000000000000";
	public final static String MASK_ONES = "11111111111111111111111111111111";
	public final static int NUMBER_OF_BITS = 32;
	
	private LanguageInterface stringTable;
	private Tooltip mouseToolTip = new Tooltip("");

	@FXML
	private AnchorPane apExpirienceDefinition;
	
	@FXML
    private TabPane tabTriggers;

	@FXML
	private TextField tfNomeExperiencia;

	@FXML
	private TextArea tfDescricaoExperiencia;

	@FXML
	private TextField tfNFalhas;

	@FXML
	private TextField tfNBits;

	@FXML
	private CheckBox checkSemente;

	@FXML
	private TextField tfSemente;

	@FXML
	private Button btnGererate;

	@FXML
	private Button btnImport;
	
	@FXML
	private Button btnGoldenRun;

	@FXML
	private Button btnZero0;

	@FXML
	private Button btnOne0;

	@FXML
	private TextField tfZero;

	@FXML
	private Button btnZero1;

	@FXML
	private Button btnOne1;

	@FXML
	private TextField tfOne;

	@FXML
	private Button btnZero2;

	@FXML
	private Button btnOne2;

	@FXML
	private TextField tfTwo;

	@FXML
	private Button btnZero3;

	@FXML
	private Button btnOne3;

	@FXML
	private TextField tfThree;

	@FXML
	private Button btnZero4;

	@FXML
	private Button btnOne4;

	@FXML
	private TextField tfFour;

	@FXML
	private Button btnZero5;

	@FXML
	private Button btnOne5;

	@FXML
	private TextField tfFive;

	@FXML
	private Button btnZero6;

	@FXML
	private Button btnOne6;

	@FXML
	private TextField tfSix;

	@FXML
	private Button btnZero7;

	@FXML
	private Button btnOne7;

	@FXML
	private TextField tfSeven;

	@FXML
	private Button btnZero8;

	@FXML
	private Button btnOne8;

	@FXML
	private TextField tfEight;

	@FXML
	private Button btnZero9;

	@FXML
	private Button btnOne9;

	@FXML
	private TextField tfNine;

	@FXML
	private Button btnZero10;

	@FXML
	private Button btnOne10;

	@FXML
	private TextField tfTen;

	@FXML
	private Button btnZero11;

	@FXML
	private Button btnOne11;

	@FXML
	private TextField tfEleven;

	@FXML
	private Button btnZero12;

	@FXML
	private Button btnOne12;

	@FXML
	private TextField tfTwelve;

	@FXML
	private Button btnZeroSP;

	@FXML
	private Button btnOneSP;

	@FXML
	private TextField tfSP;

	@FXML
	private Button btnZeroLR;

	@FXML
	private Button btnOneLR;

	@FXML
	private TextField tfLR;

	@FXML
	private Button btnZeroPC;

	@FXML
	private Button btnOnePC;

	@FXML
	private TextField tfPC;

	@FXML
	private TextField tfT0;

	@FXML
	private TextField tfT1;

	@FXML
	private TextField tfTFim;
	
	@FXML
	private TextField tfTriggerPC;
	
	@FXML
	private TextField tfTriggerPCEnd;
	
	@FXML
    private Text lbExpirienceDef, lbExpirienceName, lbExpirienceDec, lbNumFaults, lbNumBitFlips, lbTEnd, lbDefMascAndTig, lbResponsavel;

	@FXML
	private TextField tfResponsavel;
	
	@FXML
	private TextField tfTEndSpacial;
	
	
	
	
	
	@FXML
	void onClick110(MouseEvent event) {
		tfEleven.setText(MASK_ZEROS);
	}

	@FXML
	void onClick50(MouseEvent event) {
		tfFive.setText(MASK_ZEROS);
	}

	@FXML
	void onClick70(MouseEvent event) {
		tfSeven.setText(MASK_ZEROS);
	}

	@FXML
	void onClick90(MouseEvent event) {
		tfNine.setText(MASK_ZEROS);
	}

	@FXML
	void onClickGenarateBtn(MouseEvent event) {
		String path;
		try { path = getDirectoryPath(); } catch (Exception e1) { return; }
		GenerationTool businessLogic = new GenerationTool(collectDataFromView(), stringTable);
		try {
				businessLogic.validateData(tabTriggers.getSelectionModel().getSelectedIndex());
				businessLogic.writeCsvFileWithFaults(path);
				businessLogic.experimentDefinitionToJSONFile(path);
		} catch (Exception e) {
			showAletDialog(e.getMessage());
			return;
		}
		showAletDialog(stringTable.getExperimentGenaretedWithSucess());
	}
	
	@FXML
	void onClickGoldenRun(MouseEvent event) {
		String path;
		try { path = getDirectoryPath(); } catch (Exception e1) { return; }
		GenerationTool businessLogic = new GenerationTool(collectDataFromView(), stringTable, true);
		try {
			businessLogic.validateData(0);
			businessLogic.writeCsvFileWithFaults(path);
			businessLogic.experimentDefinitionToJSONFile(path);
		} catch (Exception e) {
			showAletDialog(e.getMessage());
			return;
		}
		showAletDialog(stringTable.getExperimentGenaretedWithSucess());
	}
	
	@FXML
	void onClickImportBtn(MouseEvent event){
		FileChooser filechoser = new FileChooser();
		configureFileChooser(filechoser);
		Stage stage = (Stage) apExpirienceDefinition.getScene().getWindow();
		File file = filechoser.showOpenDialog(stage);
		
		BufferedReader br;
		Experiment experimentData;
		FileReader fr;
		try {
			fr = new FileReader(file.getCanonicalPath());
			br = new BufferedReader(fr);
			experimentData = new Gson().fromJson(br, Experiment.class);
			fr.close();
		} catch (Exception e) { return; }
		setImportData(experimentData);	
	}

	@FXML
	void onClickLR0(MouseEvent event) {
		tfLR.setText(MASK_ZEROS);
	}

	@FXML
	void onClickLR1(MouseEvent event) {
		tfLR.setText(MASK_ONES);
	}

	@FXML
	void onClickPC0(MouseEvent event) {
		tfPC.setText(MASK_ZEROS);
	}

	@FXML
	void onClickPC1(MouseEvent event) {
		tfPC.setText(MASK_ONES);
	}


	@FXML
	void onClickR00(MouseEvent event) {
		tfZero.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR01(MouseEvent event) {
		tfZero.setText(MASK_ONES);
	}

	@FXML
	void onClickR10(MouseEvent event) {
		tfOne.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR100(MouseEvent event) {
		tfTen.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR101(MouseEvent event) {
		tfTen.setText(MASK_ONES);
	}

	@FXML
	void onClickR11(MouseEvent event) {
		tfOne.setText(MASK_ONES);
	}

	@FXML
	void onClickR111(MouseEvent event) {
		tfEleven.setText(MASK_ONES);
	}

	@FXML
	void onClickR120(MouseEvent event) {
		tfTwelve.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR121(MouseEvent event) {
		tfTwelve.setText(MASK_ONES);
	}

	@FXML
	void onClickR20(MouseEvent event) {
		tfTwo.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR21(MouseEvent event) {
		tfTwo.setText(MASK_ONES);
	}

	@FXML
	void onClickR30(MouseEvent event) {
		tfThree.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR31(MouseEvent event) {
		tfThree.setText(MASK_ONES);
	}

	@FXML
	void onClickR40(MouseEvent event) {
		tfFour.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR41(MouseEvent event) {
		tfFour.setText(MASK_ONES);
	}

	@FXML
	void onClickR51(MouseEvent event) {
		tfFive.setText(MASK_ONES);
	}

	@FXML
	void onClickR60(MouseEvent event) {
		tfSix.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR61(MouseEvent event) {
		tfSix.setText(MASK_ONES);
	}

	@FXML
	void onClickR71(MouseEvent event) {
		tfSeven.setText(MASK_ONES);
	}

	@FXML
	void onClickR80(MouseEvent event) {
		tfEight.setText(MASK_ZEROS);
	}

	@FXML
	void onClickR81(MouseEvent event) {
		tfEight.setText(MASK_ONES);
	}

	@FXML
	void onClickR91(MouseEvent event) {
		tfNine.setText(MASK_ONES);
	}

	@FXML
	void onClickSP0(MouseEvent event) {
		tfSP.setText(MASK_ZEROS);
	}

	@FXML
	void onClickSP1(MouseEvent event) {
		tfSP.setText(MASK_ONES);
	}
	

	public LanguageInterface getStringTable() {
		return stringTable;
	}

	public void setStringTable(LanguageInterface stringTable) {
		this.stringTable = stringTable;
	}


	/**
	 * Checkbox "Usar Semente Geração" Event Handler
	 */
	EventHandler<ActionEvent> sementeCheckBoxHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (!checkSemente.isSelected())
				tfSemente.setDisable(true);
			else
				tfSemente.setDisable(false);
		}
	};
	
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
	 * Set the imported data in the interface 
	 * @param data
	 */
	private void setImportData(Experiment data) {
		tfNomeExperiencia.setText(data.getName());
		tfDescricaoExperiencia.setText(data.getDescription());
		tfNBits.setText(Integer.toString(data.getBitsPerFault()));
		tfNFalhas.setText(Integer.toString(data.getNumFaults()));
		if(data.getSeed()) 
			checkSemente.setSelected(true);		
		if (!checkSemente.isSelected())
			tfSemente.setDisable(true);
		else
			tfSemente.setDisable(false);
		tfSemente.setText(Integer.toString(data.getSeedValue()));

		tfZero.setText(data.getR0());
		tfOne.setText(data.getR1());
		tfTwo.setText(data.getR2());
		tfThree.setText(data.getR3());
		tfFour.setText(data.getR4());
		tfFive.setText(data.getR5());
		tfSix.setText(data.getR6());
		tfSeven.setText(data.getR7());
		tfEight.setText(data.getR8());
		tfNine.setText(data.getR9());
		tfTen.setText(data.getR10());
		tfEleven.setText(data.getR12());
		tfTwelve.setText(data.getR12());
		tfSP.setText(data.getSP());
		tfLR.setText(data.getLR());
		tfPC.setText(data.getPC());
		tfT0.setText(Integer.toString(data.getT0()));
		tfT1.setText(Integer.toString(data.getT1()));
		tfTFim.setText(Integer.toString(data.getTfim()));
		tfResponsavel.setText(data.getResponsable());
	}
	
	/**
	 * Public method to set the experiment data from a JSON file
	 * @param path
	 */
	public void setImportData(String path) {
		BufferedReader br;
		Experiment experimentData;
		FileReader fr;
		try {
			fr = new FileReader(new File(path));
			br = new BufferedReader(fr);
			experimentData = new Gson().fromJson(br, Experiment.class);
			fr.close();
		} catch (Exception e) { return; }
		setImportData(experimentData);
		this.setImportData(experimentData);
	}
	
	/**
	 * Collect all the data from the view
	 */
	public Experiment collectDataFromView() {
		Experiment data = new Experiment();
		data.setName(tfNomeExperiencia.getText());
		data.setDescription(tfDescricaoExperiencia.getText());
		data.setBitsPerFault(Integer.parseInt(tfNBits.getText()));
		data.setNumFaults(Integer.parseInt(tfNFalhas.getText()));
		if(checkSemente.isSelected()) {
			data.setSeed(true);
			data.setSeedValue(Integer.parseInt(tfSemente.getText()));
		}else {
			data.setSeed(false);
			data.setSeedValue(0);
		}
		data.setR0(tfZero.getText());
		data.setR1(tfOne.getText());
		data.setR2(tfTwo.getText());
		data.setR3(tfThree.getText());
		data.setR4(tfFour.getText());
		data.setR5(tfFive.getText());
		data.setR6(tfSix.getText());
		data.setR7(tfSeven.getText());
		data.setR8(tfEight.getText());
		data.setR9(tfNine.getText());
		data.setR10(tfTen.getText());
		data.setR11(tfEleven.getText());
		data.setR12(tfTwelve.getText());
		data.setSP(tfSP.getText());
		data.setLR(tfLR.getText());
		data.setPC(tfPC.getText());
		data.setT0(Integer.parseInt(tfT0.getText()));
		data.setT1(Integer.parseInt(tfT1.getText()));
		data.setTfim(Integer.parseInt(tfTFim.getText()));
		data.setTriggerPC(tfTriggerPC.getText());
		data.setTriggerPCEnd(tfTriggerPCEnd.getText());
		data.setTimeEnd(Integer.parseInt(tfTEndSpacial.getText()));
		data.setResponsable(tfResponsavel.getText());
		data.setDate(getCurrentDate());
		return data;
	}
	
	private String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date(); 
	    return formatter.format(date); 
	}
	
	/**
	 * Add Validation Listeners to the Input TextField
	 */
	private void addValidationListener() {
		//Registers Validations
		tfZero.textProperty().addListener(new ChangeRegisterValueListener(tfZero));
		tfOne.textProperty().addListener(new ChangeRegisterValueListener(tfOne));
		tfTwo.textProperty().addListener(new ChangeRegisterValueListener(tfTwo));
		tfThree.textProperty().addListener(new ChangeRegisterValueListener(tfThree));
		tfFour.textProperty().addListener(new ChangeRegisterValueListener(tfFour));
		tfFive.textProperty().addListener(new ChangeRegisterValueListener(tfFive));
		tfSix.textProperty().addListener(new ChangeRegisterValueListener(tfSix));
		tfSeven.textProperty().addListener(new ChangeRegisterValueListener(tfSeven));
		tfEight.textProperty().addListener(new ChangeRegisterValueListener(tfEight));
		tfNine.textProperty().addListener(new ChangeRegisterValueListener(tfNine));
		tfTen.textProperty().addListener(new ChangeRegisterValueListener(tfTen));	
		tfEleven.textProperty().addListener(new ChangeRegisterValueListener(tfEleven));
		tfTwelve.textProperty().addListener(new ChangeRegisterValueListener(tfTwelve));
		tfSP.textProperty().addListener(new ChangeRegisterValueListener(tfSP));	
		tfLR.textProperty().addListener(new ChangeRegisterValueListener(tfLR));
		tfPC.textProperty().addListener(new ChangeRegisterValueListener(tfPC));
		//Others fields validation
		tfNBits.textProperty().addListener(new ChangeNumberValuesListener(tfNBits));
		tfNFalhas.textProperty().addListener(new ChangeNumberValuesListener(tfNFalhas));
		tfSemente.textProperty().addListener(new ChangeNumberValuesListener(tfSemente));
		tfT0.textProperty().addListener(new ChangeNumberValuesListener(tfT0));
		tfT1.textProperty().addListener(new ChangeNumberValuesListener(tfT1));
		tfTFim.textProperty().addListener(new ChangeNumberValuesListener(tfTFim));
	}
	
	/**
	 * Get a Directory Path
	 * @return
	 * @throws Exception 
	 */
	private String getDirectoryPath() throws Exception {
		Stage stage = (Stage) apExpirienceDefinition.getScene().getWindow();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(stage);
		if(selectedDirectory == null){
			throw new Exception();
		}else{
		     return selectedDirectory.getAbsolutePath();
		}
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
	
	public void setLabelsText() {
		lbExpirienceName.setText(stringTable.getExpirimentName());
		lbExpirienceDec.setText(stringTable.getExperimentDec());
		lbExpirienceDef.setText(stringTable.getBtnDefineExperiment());
		lbTEnd.setText(stringTable.getTEnd());
		btnGererate.setText(stringTable.getGenerateExperiment());
		btnImport.setText(stringTable.getImportExperiemnt());
		lbNumBitFlips.setText(stringTable.getBitFlipsNum());
		lbNumFaults.setText(stringTable.getNumFaults());
		checkSemente.setText(stringTable.getSeedString());
		lbDefMascAndTig.setText(stringTable.getDefMascAndT());
		lbResponsavel.setText(stringTable.getResponsableLabelText());
	}
	
	/**
	 * Show a pop-up with {@param msg} tooltip when mouse is over a component. 
	 * @param msg - Message to display
	 * @param event - Trigger event
	 */
	public void showMouseToolTip(String msg, MouseEvent event) {
		mouseToolTip.setText(msg);
        Node node = (Node) event.getSource();
        mouseToolTip.show(node, event.getScreenX() + 50, event.getScreenY());
	}
	
	/**
	 * Hide the pop-up tootip when mouse is no more over the target componet
	 */
	public void hideMouseToolTip() {
		mouseToolTip.hide();
	}
	
	
	@FXML
    void onT0MouseEntered(MouseEvent event) {
		showMouseToolTip(stringTable.getT0ToolTip(), event);
    }

    @FXML
    void onT0MouseExited(MouseEvent event) {
    	hideMouseToolTip();
    }

    @FXML
    void onT1MouseEntered(MouseEvent event) {
    	showMouseToolTip(stringTable.getT1ToolTip(), event);
    }

    @FXML
    void onT1MouseExited(MouseEvent event) {
    	hideMouseToolTip();
    }

    @FXML
    void onTfimMouseEntered(MouseEvent event) {
    	showMouseToolTip(stringTable.getTEndToolTip(), event);
    }

    @FXML
    void onTfimMouseExited(MouseEvent event) {
    	hideMouseToolTip();
    }
    
    @FXML
    void onT0MouseClicked(MouseEvent event) {
    	hideMouseToolTip();
    }
    
    @FXML
    void onT1MouseClicked(MouseEvent event) {
    	hideMouseToolTip();
    }
    
    @FXML
    void onTfimMouseClicked(MouseEvent event) {
    	hideMouseToolTip();
    }
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Set the Check-Box "Semente Geração" Listener
		checkSemente.setOnAction(sementeCheckBoxHandler);
		// Disable Horizontal Scroll-bar Experience Description TextArea
		tfDescricaoExperiencia.setWrapText(true);
		// Add Validation Listeners to the Input TextField
		addValidationListener();
	}
	
		
	

}
