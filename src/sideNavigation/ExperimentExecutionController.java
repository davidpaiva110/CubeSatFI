package sideNavigation;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import dataModel.Experiment;
import injectorHeart.FaultInjector;
import injectorHeart.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import language.LanguageInterface;

public class ExperimentExecutionController implements Initializable {

	@FXML
    private AnchorPane apExecutionPage;

    @FXML
    private Text tlTitle;

    @FXML
    private Text tlExperimentName;

    @FXML
    private Button btnPlay, btnPause, btnAbort;

    @FXML
    private TextField tfTimer;
    
    @FXML
    private TextArea tfLogWindow;
    
    private FaultInjector injector;
    private Timer timer;
    private SideBarController sideBarController;
    private LanguageInterface stringTable;
    
    private Semaphore abortSemaphore;
    private boolean exitFlag;

    @FXML
    void onClickBtnAbort(MouseEvent event) {
    	boolean toAbort = showAletDialogWithButtons(stringTable.getAbortMessage()); 
    	if(!toAbort)
    		return;
    	timer.setTerminate(true);
    	injector.setToAbortFlag(true);
    	new Thread(new ThreadAbort()).start();
    	
    	try { abortSemaphore.acquire(); } catch (InterruptedException e) { }
    	while(!exitFlag) {
    		abortSemaphore.release();
    		try { Thread.sleep(1); } catch (InterruptedException e) {	}
    		try { abortSemaphore.acquire();} catch (InterruptedException e) { }
    	}
    	setHomePage();
    }

    @FXML
    void onClickBtnPause(MouseEvent event) {
    	injector.setPausedFlag(true);
    	timer.setPause(true);
    }

    @FXML
    void onClickBtnPlay(MouseEvent event) {
    	injector.setPausedFlag(false);
    	timer.setPause(false);
    }
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.timer = new Timer(this.tfTimer);
		new Thread(this.timer).start();
		abortSemaphore = new Semaphore(1);
		this.exitFlag = false;
	}
	
	/**
	 * Define the environment Settings in order to start the fault injection campaign
	 * @param stringTable
	 * @param defenitions
	 * @param sideBarController
	 */
	public void defineSettings(LanguageInterface stringTable, Experiment defenitions, SideBarController sideBarController) {
		this.stringTable = stringTable;
		this.sideBarController = sideBarController;
		try {
			this.injector = new FaultInjector(this, stringTable);
			this.injector.setExperimentDefenitions(defenitions);
			this.injector.setExecutionThread(new Thread(this.injector));
			this.injector.getExecutionThread().start();
			this.tlExperimentName.setText(defenitions.getName());
			this.setLabels();
			this.setCampaignExecutionFlag(true);
		}catch(Exception ex) {
			this.showAletDialog(stringTable.getInitializeConnectionWithOpenOCDServerExceptionMessage());
			this.setHomePage();
			return;
		}
	}
	
	/**
	 * Define the environment Settings in order to start the fault injection campaign with usbPortID in order to collect Data 
	 * @param stringTable
	 * @param defenitions
	 * @param sideBarController
	 * @param usbPortID
	 */
	public void defineSettings(LanguageInterface stringTable, Experiment defenitions, SideBarController sideBarController, String usbPortID) {
		this.stringTable = stringTable;
		this.sideBarController = sideBarController;
		try {
			this.injector = new FaultInjector(this, stringTable, usbPortID);
			this.injector.setExperimentDefenitions(defenitions);
			this.injector.setExecutionThread(new Thread(this.injector));
			this.injector.getExecutionThread().start();
			this.tlExperimentName.setText(defenitions.getName());
			this.setLabels();
			this.setCampaignExecutionFlag(true);
		}catch(Exception ex) {
			this.showAletDialog(stringTable.getInitializeConnectionWithOpenOCDServerExceptionMessage());
			this.setHomePage();
			return;
		}
	}
	
	/**
	 * Show an alert message with buttons
	 * @param message
	 */
	public boolean showAletDialogWithButtons(String message) {
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
	 * Show an alert message
	 * @param message
	 */
	public void showAletDialog(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.setHeaderText("");
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.setTitle(stringTable.getAlert());
	}
	
	/**
	 * Set HomePage View
	 */
	public void setHomePage() {
		sideBarController.loadPage("HomePage", SideBarController.HOME_PAGE_CODE);
		sideBarController.menuBtnChangeExecutionToHomePage();
	}

	/**
	 * Shutdown timer and return the total duration of the campaign
	 * @return
	 */
	public String setTimerTerminater() {
		timer.setTerminate(true);
		String time = timer.getHours() + ":" + timer.getMinutes() + ":" + timer.getSeconds();
		return time;
	}
	
	
	public void setCampaignExecutionFlag(boolean value) {
		sideBarController.setCampaignInExecution(value);
	}
	
	
	/**
	 * Disable Buttons available in the interface
	 */
	public void disableButtons() {
		btnAbort.setDisable(true);
		btnPause.setDisable(true);
		btnPlay.setDisable(true);
	}
	
	/**
	 * Set the text on Labels and Buttons of the interface
	 */
	public void setLabels() {
		tlTitle.setText(stringTable.getExperimentExecution());
		btnAbort.setText(stringTable.getBtnAbort());
		btnPause.setText(stringTable.getBtnPause());
		btnPlay.setText(stringTable.getBtnPlay());
	}
	
	
	public class ThreadAbort implements Runnable{
		@Override
		public void run() {
			try { injector.acquireSemaphore();} catch (InterruptedException e) { }
			try { abortSemaphore.acquire();} catch (InterruptedException e) { }
			exitFlag = true;
			abortSemaphore.release();
		}
	}
	
	
	
	
	/**
	 * 
	 * Getters of the View's Elements
	 * 
	 */
	
	public Text getTlTitle() {
		return tlTitle;
	}

	public Text getTlExperimentName() {
		return tlExperimentName;
	}

	public TextField getTfTimer() {
		return tfTimer;
	}

	public TextArea getTfLogWindow() {
		return tfLogWindow;
	}

	public Timer getTimer() {
		return timer;
	}
	
		

}
