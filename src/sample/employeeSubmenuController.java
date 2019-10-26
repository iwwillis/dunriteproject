package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class employeeSubmenuController {

    @FXML
    protected void handleEmployeeManagerButton(ActionEvent event) throws IOException {
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
    protected void handleEmployeeTypeButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("employeeTypeManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleEmployeeStatusButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("employeeStatusManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleEmployeeTeamRoleButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("employeeTeamRoleManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleEmployeeRequiredSkillButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("employeeRequiredSkillManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void teamRoleButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("teamRoleManager.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        //mainMenuController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void skillButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("skillManager.fxml"));
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
