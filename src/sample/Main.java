package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcomePage.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("customerManagerPage.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("addNewCustomer.fxml"));
        primaryStage.setTitle("Welcome to Dunrite!");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
