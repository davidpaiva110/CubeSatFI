package sideNavigation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ChangeNumberValuesListener implements ChangeListener<String>{
	
	TextField tf;
	
	public ChangeNumberValuesListener(TextField tf) {
		super();
		this.tf = tf;
		tf.setText("0");
	}

	@Override
	public void changed(ObservableValue<? extends String> observableValue, String oldV, String newV) {
		 try {
             Integer.parseInt(newV);
             tf.setText(newV);
         } catch (Exception e) {
             tf.setText(oldV);
         }
	}
	
}
