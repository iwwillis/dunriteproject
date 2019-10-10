package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class mainMenuController {
    @FXML
    protected void handleCustomerManagerButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("customerManagerPage.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleJobManagerButtonAction(ActionEvent event) throws IOException {
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
    protected void handleVendorManagerButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("vendorManagerPage.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleEmployeeManagerButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("employeeManagerPage.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleJobMaterialManagerButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("jobMaterialManagerPage.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }


}
