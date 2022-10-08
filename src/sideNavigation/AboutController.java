package sideNavigation;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import language.LanguageInterface;

public class AboutController implements Initializable{

	
	
	
	private LanguageInterface stringTable;
	
	

    @FXML
    private Text tlProduct;

    @FXML
    private Text tlVersion;

    @FXML
    private Text tlDate;

    @FXML
    private Text tlAuthors;

    @FXML
    private Text tlDavid;

    @FXML
    private Text tlHenrique;

    @FXML
    private Text tlINPE;
	
	
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	public LanguageInterface getStringTable() {
		return stringTable;
	}
	
	public void setTextLabels() {
		try {
			tlProduct.setText(stringTable.getAboutProduct());
			tlVersion.setText(stringTable.getAboutVersion());
			tlDate.setText(stringTable.getAboutDate());
			tlAuthors.setText(stringTable.getAboutAuthors());
			tlDavid.setText(stringTable.getAboutDavid());
			tlHenrique.setText(stringTable.getAboutHenrique());
			tlINPE.setText(stringTable.getAboutINPE());
		}catch(Exception e) {
			//System.out.println(e.getMessage());
		}
	}

	public void setStringTable(LanguageInterface stringTable) {
		this.stringTable = stringTable;
	}


}
