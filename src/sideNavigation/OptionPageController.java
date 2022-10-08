package sideNavigation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edcDataCollector.CSVDataWriter;
import injectorHeart.CSVReader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import language.LanguageInterface;

public class OptionPageController implements Initializable {

	@FXML
	private AnchorPane apHelpPage;

	@FXML
	private VBox vboxElement;

	@FXML
	private Text tlTitle;

	@FXML
	private Text lbSelectedTarget;

	@FXML
	private ChoiceBox<String> cbSelectedTarget;

	@FXML
	private ImageView ivIconLeft;

	@FXML
	private ImageView ivIconCenter;

	@FXML
	private ImageView ivIconRight;

	private LanguageInterface stringTable;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setTargetsOptions();
	}

	public void setTargetsOptions() {
		// Set TargetsOption
		ArrayList<String> opt = (ArrayList<String>) CSVReader.getTargetsOptions();
		String[] options = opt.toArray(new String[0]);
		cbSelectedTarget.setItems(FXCollections.observableArrayList(options));
		// Create action event
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//Save the new one
				saveConfigs();
			}
		};
		// Set on action
		cbSelectedTarget.setOnAction(event);
		
		ArrayList<String> configs = (ArrayList<String>) CSVReader.getConfigs();
		cbSelectedTarget.setValue(configs.get(0));    	
	}
	
	public void saveConfigs() {
		try {
			CSVDataWriter writer = new CSVDataWriter("config.csv", false);
			 List<String[]> data = new ArrayList<String[]>();
		     data.add(new String[] { cbSelectedTarget.getValue() });
		     writer.writeAll(data);
		     writer.closeWriter();
		} catch (IOException e) { }
	}

	public void setStringTable(LanguageInterface stringTable) {
		this.stringTable = stringTable;
	}

}
