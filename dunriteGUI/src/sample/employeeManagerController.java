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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class employeeManagerController implements Initializable {

    @FXML private TableColumn<Employee, Integer> colOne;
    @FXML private TableColumn<Employee, String> colTwo;
    @FXML private TableColumn<Employee, String> colThree;
    @FXML private TableColumn<Employee, String> colFour;
    @FXML private TableColumn<Employee, String> colFive;
    @FXML private TableColumn<Employee, Integer> colSix;
    @FXML private TableColumn<Employee, String> colSeven;
    @FXML private TableColumn<Employee, Integer> colEight;
    @FXML private TableColumn<Employee, String> colNine;
    @FXML private TableColumn<Employee, String> colTen;
    @FXML private TableColumn<Employee, String> colEleven;
    @FXML private TableColumn<Employee, String> colTwelve;
    @FXML private TableColumn<Employee,Integer> colThirteen;
    @FXML private TableColumn<Employee,Integer> colFourteen;
    @FXML private TableView<Employee> employeeTV;


    @FXML private TextField employeeNumberTF;
    @FXML private TextField firstNameTF;
    @FXML private TextField lastNameTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private ComboBox<Integer> stateCB;
    @FXML private TextField zipcodeTF;
    @FXML private TextField countryTF;
    @FXML private TextField phoneNumberTF;
    @FXML private TextField emailTF;
    @FXML private DatePicker hireDateDP;
    @FXML private DatePicker leaveDateDP;
    @FXML private ComboBox<Integer> empStatusIdCB;
    @FXML private ComboBox<Integer> empTypeIdCB;

    @FXML private TabPane employeeTP;
    @FXML private Tab tabOne;
    @FXML private Tab tabTwo;
    @FXML private Button homeButton;
    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button addNewEmployee;

    private DBConnection db;
    private ObservableList<Employee> data;
    private ObservableList<Integer> empStatus;
    private ObservableList<Integer> empType;
    private ObservableList<Integer> state;

    Connection con = null;
    PreparedStatement pstmt = null;


    @FXML
    protected void handleEditButton(ActionEvent event) {

        try {
            Employee e = employeeTV.getSelectionModel().getSelectedItem();

            employeeNumberTF.setText(Integer.toString(e.getEmpId().get()));
            firstNameTF.setText(e.getEmpFirst().get());
            lastNameTF.setText(e.getEmpLast().get());
            addressTF.setText(e.getStreetAddress().get());
            cityTF.setText(e.getEmpCity().get());
            stateCB.getSelectionModel().select(e.getStateId().get()-1);
            zipcodeTF.setText(e.getEmpZip().get());
            countryTF.setText(Integer.toString(e.getCountryId().get()));
            phoneNumberTF.setText(e.getEmpPhone().get());
            emailTF.setText(e.getEmpEmail().get());

            String a = e.getHire().get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter = formatter.withLocale(Locale.US);
            LocalDate ld = LocalDate.parse(a,formatter);
            hireDateDP.setValue(ld);

            String b = e.getLeave().get();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter2 = formatter2.withLocale(Locale.US);
            LocalDate ld2 = LocalDate.parse(b,formatter2);
            leaveDateDP.setValue(ld2);

            empTypeIdCB.getSelectionModel().select(e.getEmpTypeId().get()-1);
            empStatusIdCB.getSelectionModel().select(e.getEmpStatusId().get()-1);

            tabTwo.setText("Edit Employee");
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addNewEmployee.setVisible(false);
            homeButton.setDisable(true);
            backButton.setDisable(true);
            employeeTP.getSelectionModel().select(tabTwo);
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
        try{

            int eId = Integer.parseInt(employeeNumberTF.getText());
            String first = firstNameTF.getText();
            String last = lastNameTF.getText();
            String address = addressTF.getText();
            String city = cityTF.getText();
            int stateId = stateCB.getValue();
            String zipcode = zipcodeTF.getText();
            int countryId = Integer.parseInt(countryTF.getText());
            String phoneNumber = phoneNumberTF.getText();
            String email = emailTF.getText();
            LocalDate d = hireDateDP.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String hDate = d.format(formatter);
            LocalDate d2 = leaveDateDP.getValue();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String lDate = d2.format(formatter2);
            int empTypeId = empTypeIdCB.getValue();
            int empStatusId = empStatusIdCB.getValue();

            String SQL = "UPDATE EMPLOYEE SET Employee_First_Name ='"+first+"', Employee_Last_Name ='"+last+"', " +
                    "Employee_Street_Address= '"+address+"', Employee_City='"+city+"', " +
                    "State_ID="+stateId+", Employee_Zipcode= '"+zipcode+"', Country_ID="+countryId+", Employee_Phone_Number='"+phoneNumber+"'," +
                    "Employee_Email_Address = '"+email+"', Hire_Date='"+hDate+"', Leave_Date='"+lDate+"', Employee_Status_ID="+empStatusId+",Employee_Type_ID="+empTypeId+" WHERE Employee_ID="+eId+";";

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
                addNewEmployee.setVisible(true);
                homeButton.setDisable(false);
                backButton.setDisable(false);
                tabTwo.setText("Add New Employee");
                employeeTP.getSelectionModel().select(tabOne);
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
    protected void handleCancelButton(ActionEvent event)  {

        clearFields();
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        addNewEmployee.setVisible(true);
        homeButton.setDisable(false);
        backButton.setDisable(false);
        tabTwo.setText("Add New Employee");
        employeeTP.getSelectionModel().select(tabOne);
        tabOne.setDisable(false);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeTP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        employeeNumberTF.setVisible(false);

        initStateIdCB();
        stateCB.setVisibleRowCount(10);
        initEmployeeStatusIdCB();
        empStatusIdCB.setVisibleRowCount(10);
        initEmployeeTypeIdCB();
        empTypeIdCB.setVisibleRowCount(10);
        refreshTable();

    }
    @FXML
    protected void handleAddButton(ActionEvent event) {

        try{
            String firstName = firstNameTF.getText();
            String lastName = lastNameTF.getText();
            String address = addressTF.getText();
            String city = cityTF.getText();
            int stateId = stateCB.getValue();
            String zipcode = zipcodeTF.getText();
            int countryId = Integer.parseInt(countryTF.getText());
            String phone = phoneNumberTF.getText();
            String email = emailTF.getText();
            LocalDate d = hireDateDP.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String hDate = d.format(formatter);
            LocalDate d2 = leaveDateDP.getValue();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String lDate = d.format(formatter2);
            int empTypeId = empTypeIdCB.getValue();
            int empStatusId = empStatusIdCB.getValue();

            String SQL = "INSERT INTO EMPLOYEE(Employee_First_Name, Employee_Last_Name, Employee_Street_Address, " +
                    "Employee_City, State_ID, Employee_Zipcode, Country_ID, Employee_Phone_Number, Employee_Email_Address, Hire_Date, " +
                    "Leave_Date, Employee_Type_ID, Employee_Status_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Record");
            alert.setHeaderText("Are you sure want to add this record?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,firstName);
                pstmt.setString(2,lastName);
                pstmt.setString(3,address);
                pstmt.setString(4,city);
                pstmt.setInt(5,stateId);
                pstmt.setString(6,zipcode);
                pstmt.setInt(7,countryId);
                pstmt.setString(8,phone);
                pstmt.setString(9,email);
                pstmt.setString(10,hDate);
                pstmt.setString(11,lDate);
                pstmt.setInt(12,empTypeId);
                pstmt.setInt(13,empStatusId);
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
            e.printStackTrace();
        }
        finally {
            try { pstmt.close(); } catch (Exception e) { }
        }

    }
    @FXML
    protected void handleDeleteButton(ActionEvent event) {
        try{
            Employee selected = employeeTV.getSelectionModel().getSelectedItem();
            int employeeId = selected.getEmpId().get();
            String SQL = "DELETE FROM EMPLOYEE WHERE Employee_ID="+employeeId+"";
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

    private void initTable(){
        colOne.setCellValueFactory(cell->cell.getValue().getEmpId().asObject());
        colTwo.setCellValueFactory(cell->cell.getValue().getEmpFirst());
        colThree.setCellValueFactory(cell->cell.getValue().getEmpLast());
        colFour.setCellValueFactory(cell->cell.getValue().getStreetAddress());
        colFive.setCellValueFactory(cell->cell.getValue().getEmpCity());
        colSix.setCellValueFactory(cell->cell.getValue().getStateId().asObject());
        colSeven.setCellValueFactory(cell->cell.getValue().getEmpZip());
        colEight.setCellValueFactory(cell->cell.getValue().getCountryId().asObject());
        colNine.setCellValueFactory(cell->cell.getValue().getEmpPhone());
        colTen.setCellValueFactory(cell->cell.getValue().getEmpEmail());
        colEleven.setCellValueFactory(cell->cell.getValue().getHire());
        colTwelve.setCellValueFactory(cell->cell.getValue().getLeave());
        colThirteen.setCellValueFactory(cell->cell.getValue().getEmpTypeId().asObject());
        colFourteen.setCellValueFactory(cell->cell.getValue().getEmpStatusId().asObject());

    }

    private void refreshTable() {
        initTable();
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT\n" +
                    "Employee_ID,\n" +
                    "Employee_First_Name,\n" +
                    "Employee_Last_Name,\n" +
                    "Employee_Street_Address,\n" +
                    "Employee_City,\n" +
                    "State_ID,\n" +
                    "Employee_Zipcode,\n" +
                    "Country_ID,\n" +
                    "Employee_Phone_Number,\n" +
                    "Employee_Email_Address,\n" +
                    "FORMAT(Hire_Date, 'MM/dd/yyyy','en-us'),\n" +
                    "FORMAT(Leave_Date, 'MM/dd/yyyy','en-us'),\n" +
                    "Employee_Type_ID,\n" +
                    "Employee_Status_ID\n" +
                    "FROM EMPLOYEE");

            while(rs.next()){
                data.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),rs.getInt(6),rs.getString(7),
                        rs.getInt(8),rs.getString(9),rs.getString(10),rs.getString(11),
                        rs.getString(12),rs.getInt(13),rs.getInt(14)));
            }
            initTable();
            employeeTV.setItems(null);
            employeeTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStateIdCB() {
        db = new DBConnection();
        state = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT State_ID FROM STATE_TERRITORY");
            while (rs.next()) {
                state.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stateCB.setItems(null);
        stateCB.setItems(state);
    }

    private void initEmployeeStatusIdCB() {
        db = new DBConnection();
        empStatus = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Employee_Status_ID FROM EMPLOYEE_STATUS");
            while (rs.next()) {
                empStatus.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        empStatusIdCB.setItems(null);
        empStatusIdCB.setItems(empStatus);
    }

    private void initEmployeeTypeIdCB() {
        db = new DBConnection();
        empType = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Employee_Type_ID FROM EMPLOYEE_TYPE");
            while (rs.next()) {
                empType.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        empTypeIdCB.setItems(null);
        empTypeIdCB.setItems(empType);
    }

    private void clearFields() {

        employeeNumberTF.clear();
        firstNameTF.clear();
        lastNameTF.clear();
        addressTF.clear();
        cityTF.clear();
        stateCB.setValue(null);
        zipcodeTF.clear();
        countryTF.clear();
        phoneNumberTF.clear();
        emailTF.clear();
        hireDateDP.getEditor().clear();
        leaveDateDP.getEditor().clear();
        empStatusIdCB.setValue(null);
        empTypeIdCB.setValue(null);

    }

    private void confirmAdd() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Success!");
        alert.setContentText("Employee has been added successfully!");

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
        loader.setLocation(getClass().getResource("employeeSubmenu.fxml"));
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
