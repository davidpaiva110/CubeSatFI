package injectorHeart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dataModel.ExperimentHistoryDataBase;

public class ExperimentDataBaseLogic {
	
	public static ExperimentHistoryDataBase getDataBaseHistory() throws ClassNotFoundException, IOException {
		ExperimentHistoryDataBase db = new ExperimentHistoryDataBase();
		FileInputStream fileIn = new FileInputStream("experimentHistoryDataBase");
		ObjectInputStream objectIn = new ObjectInputStream(fileIn);
		db = (ExperimentHistoryDataBase) objectIn.readObject();
		objectIn.close();
		fileIn.close();
		return db;
	}
	
	public static void updateDataBaseHistory(ExperimentHistoryDataBase database) throws IOException {
		FileOutputStream fileOut = new FileOutputStream("experimentHistoryDataBase");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(database);
        objectOut.close();
        fileOut.close();
	}
}
