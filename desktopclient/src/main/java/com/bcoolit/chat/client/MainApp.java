package com.bcoolit.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {


	public static void main(String[] args) throws Exception {

		launch(args);
	}

	public void start(Stage stage) throws Exception {

		String fxmlFile = "/fxml/UI.fxml";
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

		Scene scene = new Scene(rootNode, 600, 400);

		stage.setTitle("Desktop client");
		stage.setScene(scene);
		stage.show();
	}
}
