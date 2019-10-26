package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class jobSubmenuController {

    @FXML
    protected void handlejobManagerButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("jobManagerPage.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }
    @FXML
    protected void handlejobTypeButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("jobTypeManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleJobStatusButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("jobStatusManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleJobSizeButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("jobSizeManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleJobTaskButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("jobTaskSubmenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainMenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

}
