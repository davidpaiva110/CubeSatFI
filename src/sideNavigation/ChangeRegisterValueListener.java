package sideNavigation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ChangeRegisterValueListener implements ChangeListener<String>{
	
	TextField tf;
	
	public ChangeRegisterValueListener(TextField tf) {
		super();
		this.tf = tf;
	}

	@Override
	public void changed(ObservableValue<? extends String> observableValue, String oldV, String newV) {
		if(newV.matches("[0-1]+") && newV.length() <= DefineExperimentController.NUMBER_OF_BITS)
			tf.setText(newV);
		else
			tf.setText(oldV);
		// Red border if the number of values is different of 32
		if(tf.getText().length() != DefineExperimentController.NUMBER_OF_BITS)
			tf.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
		else
			tf.setStyle("");
	}
	
}
