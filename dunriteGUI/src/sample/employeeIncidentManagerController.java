package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class employeeIncidentManagerController implements Initializable {

    @FXML private TableColumn<EmployeeIncident,Integer> colOne;
    @FXML private TableColumn<EmployeeIncident,String> colTwo;
    @FXML private TableColumn<EmployeeIncident,String> colThree;
    @FXML private TableColumn<EmployeeIncident,String> colFour;
    @FXML private TableColumn<EmployeeIncident,Integer> colFive;
    @FXML private TableColumn<EmployeeIncident,Integer> colSix;
    @FXML private TabPane employeeIncidentTP;
    @FXML private TableView<EmployeeIncident> employeeIncidentTV;

    @FXML private TextField empIncidentTF;
    @FXML private TextArea descriptionTA;
    @FXML private TextArea resolutionTA;
    @FXML private TextArea followupTA;
    @FXML private TextField empIncidentTypeIdTF;
    @FXML private TextField empIncidentStatusIdTF;

    @FXML private Tab tabOne;
    @FXML private Tab tabTwo;
    @FXML private Button homeButton;
    @FXML private Button backButton;
    @FXML private Button editButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button addButton;


    private DBConnection db;
    private ObservableList<EmployeeIncident> data;

    Connection con =null;
    PreparedStatement pstmt = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeIncidentTP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        empIncidentTF.setVisible(false);

        db = new DBConnection();
        data = FXCollections.observableArrayList();
        refreshTable();
        employeeIncidentTV.setItems(null);
        employeeIncidentTV.setItems(data);

    }

    @FXML
    protected void handleAddButton(ActionEvent event) {

        try{
            String description = descriptionTA.getText();
            String resolution = resolutionTA.getText();
            String followup = followupTA.getText();
            int empIncidentType = Integer.parseInt(empIncidentTypeIdTF.getText());
            int empIncidentStatus = Integer.parseInt(empIncidentStatusIdTF.getText());
            String SQL = "INSERT INTO EMPLOYEE_INCIDENT(employee_incident_description, employee_incident_resolution, employee_incident_followup, employee_incident_type_id, employee_incident_status_id) VALUES(?,?,?,?,?)";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Record");
            alert.setHeaderText("Are you sure want to add this record?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,description);
                pstmt.setString(2,resolution);
                pstmt.setString(3,followup);
                pstmt.setInt(4,empIncidentType);
                pstmt.setInt(5,empIncidentStatus);
                pstmt.executeUpdate();
                confirmAdd();
                clearFields();
                refreshTable();
            } else if (option.get() == ButtonType.CANCEL) {

            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Could not add record!");
            alert.showAndWait();
        }
        finally {
            try { pstmt.close(); } catch (Exception e) { }
        }

    }

    @FXML
    protected void handleEditButton(ActionEvent event) {

        try {
            EmployeeIncident ei = employeeIncidentTV.getSelectionModel().getSelectedItem();

            empIncidentTF.setText(Integer.toString(ei.getEmployeeIncidentNumber().get()));
            descriptionTA.setText(ei.getEmployeeIncidentDescription().get());
            resolutionTA.setText(ei.getEmployeeIncidentResolution().get());
            followupTA.setText(ei.getEmployeeIncidentFollowup().get());
            empIncidentTypeIdTF.setText(Integer.toString(ei.getEmployeeIncidentTypeId().get()));
            empIncidentStatusIdTF.setText(Integer.toString(ei.getEmployeeIncidentStatusId().get()));

            tabTwo.setText("Edit Employee Incident");
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addButton.setVisible(false);
            homeButton.setDisable(true);
            backButton.setDisable(true);
            employeeIncidentTP.getSelectionModel().select(tabTwo);
            tabOne.setDisable(true);

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("No record is selected to editing!");
            alert.showAndWait();
        }


    }

    @FXML
    protected void handleSaveButton(ActionEvent event){
        tabTwo.setText("Add New Payment");
        try{

            int eiId = Integer.parseInt(empIncidentTF.getText());
            String desc = descriptionTA.getText();
            String resolution = resolutionTA.getText();
            String followup = followupTA.getText();
            int empIncidentType = Integer.parseInt(empIncidentTypeIdTF.getText());
            int empIncidentStatus = Integer.parseInt(empIncidentStatusIdTF.getText());

            String SQL = "UPDATE EMPLOYEE_INCIDENT SET Employee_Incident_Description ='"+desc+"', Employee_Incident_Resolution ='"+resolution+"', " +
                    "Employee_Incident_Followup= '"+followup+"', Employee_Incident_Type_ID= "+empIncidentType+", Employee_Incident_Status_ID="+empIncidentStatus+" WHERE Employee_Incident_Number="+eiId+";";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Record");
            alert.setHeaderText("Are you sure want commit this edit?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.executeUpdate();
                confirmEdit();
                clearFields();
                refreshTable();
                cancelButton.setVisible(false);
                saveButton.setVisible(false);
                addButton.setVisible(true);
                homeButton.setDisable(false);
                backButton.setDisable(false);
                tabTwo.setText("Add New Payment");
                employeeIncidentTP.getSelectionModel().select(tabOne);
                tabOne.setDisable(false);
            } else if (option.get() == ButtonType.CANCEL) {

            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Could not edit record!");
            alert.showAndWait();
        }
        finally {
            try { pstmt.close(); } catch (Exception e) { }
        }
    }

    @FXML
    protected void handleDeleteButton(ActionEvent event) {
        try{
            EmployeeIncident selected = employeeIncidentTV.getSelectionModel().getSelectedItem();
            int empIncidentId = selected.getEmployeeIncidentNumber().get();
            String SQL = "DELETE FROM EMPLOYEE_INCIDENT WHERE Employee_Incident_Number="+empIncidentId+"";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Record");
            alert.setHeaderText("Are you sure want to delete this record?");


            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {
                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.executeUpdate();
                refreshTable();
            } else if (option.get() == ButtonType.CANCEL) {

            }


        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("No record is selected to deletion!");
            alert.showAndWait();
        }
        finally {

            try { pstmt.close(); } catch (Exception e) { }
        }


    }

    @FXML
    protected void handleCancelButton(ActionEvent event)  {

        clearFields();
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        addButton.setVisible(true);
        homeButton.setDisable(false);
        backButton.setDisable(false);
        tabTwo.setText("Add New Employee Incident");
        employeeIncidentTP.getSelectionModel().select(tabOne);
        tabOne.setDisable(false);


    }

    private void initTable() {
        colOne.setCellValueFactory(cell->cell.getValue().getEmployeeIncidentNumber().asObject());
        colTwo.setCellValueFactory(cell->cell.getValue().getEmployeeIncidentDescription());
        colThree.setCellValueFactory(cell->cell.getValue().getEmployeeIncidentResolution());
        colFour.setCellValueFactory(cell->cell.getValue().getEmployeeIncidentFollowup());
        colFive.setCellValueFactory(cell->cell.getValue().getEmployeeIncidentTypeId().asObject());
        colSix.setCellValueFactory(cell->cell.getValue().getEmployeeIncidentStatusId().asObject());
    }

    private void refreshTable() {
        initTable();
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM EMPLOYEE_INCIDENT");

            while(rs.next()){
                data.add(new EmployeeIncident(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5),rs.getInt(6)));
            }
            initTable();
            employeeIncidentTV.setItems(null);
            employeeIncidentTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        empIncidentTF.clear();
        descriptionTA.clear();
        resolutionTA.clear();
        followupTA.clear();
        empIncidentTypeIdTF.clear();
        empIncidentStatusIdTF.clear();
    }
    private void confirmAdd() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Success!");
        alert.setContentText("Employee Incident has been added successfully!");

        alert.showAndWait();
    }
    private void confirmEdit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Commit Success!");
        alert.setContentText("Instance has been edited successfully!");

        alert.showAndWait();
    }

    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("employeeIncidentSubmenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleHomeButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainMenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }
}
