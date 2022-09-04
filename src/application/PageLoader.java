package application;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class PageLoader {

	private Pane view;
	
	public Pane getPage(String filename) {
		try {
			URL fileUrl = Main.class.getResource("/application/"+filename+".fxml");
			
			if(fileUrl == null) {
				throw new java.io.FileNotFoundException("FXML file cannot be found");
			}
			
			new FXMLLoader();
			view = FXMLLoader.load(fileUrl);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
}
