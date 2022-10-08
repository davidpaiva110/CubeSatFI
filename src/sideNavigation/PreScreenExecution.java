package sideNavigation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import dataModel.Experiment;
import edcDataCollector.SerialPortCommunication;
import faultTypes.SpaceBasedFault;
import faultTypes.TimeBasedFault;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import language.LanguageInterface;

public class PreScreenExecution implements Initializable {

	 @FXML
	    private AnchorPane apExpirienceDefinition;

	    @FXML
	    private Text lbExpirienceDef;

	    @FXML
	    private Text lbExpirienceName;

	    @FXML
	    private TextField tfNomeExperiencia;

	    @FXML
	    private Text lbExpirienceDec;

	    @FXML
	    private TextArea tfDescricaoExperiencia;

	    @FXML
	    private Text lbNumFaults;

	    @FXML
	    private Text lbNumBitFlips;

	    @FXML
	    private Text lbSeed;

	    @FXML
	    private Text lbResponsavel;

	    @FXML
	    private TextField tfNFalhas;

	    @FXML
	    private TextField tfNBits;

	    @FXML
	    private TextField tfSemente;

	    @FXML
	    private TextField tfResponsavel;

	    @FXML
	    private Button btnImport;

	    @FXML
	    private Text lbDefMascAndTig;

	    @FXML
	    private TextField tfZero;

	    @FXML
	    private TextField tfOne;

	    @FXML
	    private TextField tfTwo;

	    @FXML
	    private TextField tfThree;

	    @FXML
	    private TextField tfFour;

	    @FXML
	    private TextField tfFive;

	    @FXML
	    private TextField tfSix;

	    @FXML
	    private TextField tfSeven;

	    @FXML
	    private TextField tfEight;

	    @FXML
	    private TextField tfNine;

	    @FXML
	    private TextField tfTen;

	    @FXML
	    private TextField tfEleven;

	    @FXML
	    private TextField tfTwelve;

	    @FXML
	    private TextField tfSP;

	    @FXML
	    private TextField tfLR;

	    @FXML
	    private TextField tfPC;

	    @FXML
	    private TextField tfPSR;

	    @FXML
	    private TextField tfPrimask;

	    @FXML
	    private TextField tfControl;

	    @FXML
	    private TextField tfT0;

	    @FXML
	    private TextField tfT1;

	    @FXML
	    private Text lbTEnd;
	    
	    @FXML
	    private Text lbTriggerX1;
	    
	    @FXML
	    private Text lbTriggerX2;
	    
	    @FXML
	    private TextField tfTFim;
	    
	    @FXML
	    private Text lbUsbPort;
	    
	    @FXML
	    private ComboBox<String> usbComboBox;
	    
	    @FXML
	    private ImageView btnRefreshUSBPort;

	    //JSON File Path of the experiment configuration File
	    private String jsonFilePath;  
	    
	private SideBarController sideBarController;
	private String usbName = null;
	 
	    
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	@FXML
    void onClickBtnRefresh(MouseEvent event) {
		ArrayList<String> opt = (ArrayList<String>) SerialPortCommunication.getAvailablePorts();
		String[] options = opt.toArray(new String[0]);
		usbComboBox.setItems(FXCollections.observableArrayList(options));
    }
	
    @FXML
    void onClickExecuteBtn(MouseEvent event) {
    	try {
			this.sideBarController.setExperimentExecutionLayout(jsonFilePath, getExperimentData(jsonFilePath), usbName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }	
	
    /**
     * Set Experiment Data into the view
     * @param data
     */
	private void setExperimentData(Experiment data) {
		tfNomeExperiencia.setText(data.getName());
		tfDescricaoExperiencia.setText(data.getDescription());
		tfNBits.setText(Integer.toString(data.getBitsPerFault()));
		tfNFalhas.setText(Integer.toString(data.getNumFaults()));
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
		
		
		if(data.getFaulyType().equals(TimeBasedFault.FAULT_TYPE_TIMEBASED)) {
			System.out.println("passou aqui");
			tfT0.setText(Integer.toString(data.getT0()));
			tfT1.setText(Integer.toString(data.getT1()));
			tfTFim.setText(Integer.toString(data.getTfim()));
		}else if(data.getFaulyType().equals(SpaceBasedFault.FAULT_TYPE_SPACEBASED)) {
			tfT0.setText(data.getTriggerPC());
			tfT1.setText(data.getTriggerPCEnd());
			tfTFim.setText(Integer.toString(data.getTimeEnd()));
			
			lbTriggerX1.setText("Start Program Counter (PC)");
			lbTriggerX2.setText("End Program Counter (PC)");
		}
		
		tfResponsavel.setText(data.getResponsable());
	}
	
	public void setLabelsText() {
		LanguageInterface stringTable = sideBarController.getStringTable();
		lbExpirienceName.setText(stringTable.getExpirimentName());
		lbExpirienceDec.setText(stringTable.getExperimentDec());
		lbExpirienceDef.setText(stringTable.getBtnDefineExperiment());
		lbTEnd.setText(stringTable.getTEnd());
		btnImport.setText(stringTable.getBtnExecute());
		lbNumBitFlips.setText(stringTable.getBitFlipsNum());
		lbNumFaults.setText(stringTable.getNumFaults());
		lbSeed.setText(stringTable.getSeedString());
		lbDefMascAndTig.setText(stringTable.getDefMascAndT());
		lbResponsavel.setText(stringTable.getResponsableLabelText());
		lbUsbPort.setText(stringTable.getBoardUsbPort());
		//Set USB Port Option
		ArrayList<String> opt = (ArrayList<String>) SerialPortCommunication.getAvailablePorts();
		String[] options = opt.toArray(new String[0]);
		usbComboBox.setItems(FXCollections.observableArrayList(options));
		// Create action event
        EventHandler<ActionEvent> event =
                  new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	usbName = usbComboBox.getValue();
            }
        };
 
        // Set on action
        usbComboBox.setOnAction(event);
        
	}
	
	/**
	 * Get Experiment Data from JSON file
	 * @param path
	 */
	private Experiment getExperimentData(String path) {
		BufferedReader br;
		Experiment experimentData;
		FileReader fr;
		try {
			fr = new FileReader(new File(path));
			br = new BufferedReader(fr);
			experimentData = new Gson().fromJson(br, Experiment.class);
			fr.close();
		} catch (Exception e) {return null; } //TODO EXECPITON
		return experimentData;
	}

	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public void setJsonFilePath(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}

	public void configureView(SideBarController sideBarController) {
		this.setExperimentData(this.getExperimentData(jsonFilePath));
		this.sideBarController = sideBarController;
	}
	
}
