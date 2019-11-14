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
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerManagerPageController implements Initializable {
    @FXML private TableColumn<Customer, Integer> colOne;
    @FXML private TableColumn<Customer, String> colTwo;
    @FXML private TableColumn<Customer, String> colThree;
    @FXML private TableColumn<Customer, String> colThirteen;
    @FXML private TableColumn<Customer, String> colFour;
    @FXML private TableColumn<Customer, String> colFive;
    @FXML private TableColumn<Customer, Integer> colSix;
    @FXML private TableColumn<Customer, String> colSeven;
    @FXML private TableColumn<Customer, Integer> colEight;
    @FXML private TableColumn<Customer, String> colNine;
    @FXML private TableColumn<Customer, String> colTen;
    @FXML private TableColumn<Customer, Integer> colEleven;
    @FXML private TableColumn<Customer, Integer> colTwelve;
    @FXML private TableView<Customer> customerTV;

    @FXML private TextField customerIdTF;
    @FXML private TextField firstNameTF;
    @FXML private TextField lastNameTF;
    @FXML private TextField organizationTF;
    @FXML private TextField customerAddressTF;
    @FXML private TextField customerCityTF;
    @FXML private ComboBox<Integer> customerStateCB;
    @FXML private TextField customerZipcodeTF;
    @FXML private ComboBox<Integer> customerCountryCB;
    @FXML private TextField phoneNumberTF;
    @FXML private TextField emailTF;
    @FXML private ComboBox<Integer> customerStatusIdCB;
    @FXML private ComboBox<Integer> customerTypeIdCB;

    @FXML private TabPane customerTP;
    @FXML private Tab tabOne;
    @FXML private Tab tabTwo;
    @FXML private Button editButton;
    @FXML private Button homeButton;
    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button addNewCustomer;

    private DBConnection db;
    private ObservableList<Customer> data;
    private ObservableList<Integer> CountryId;
    private ObservableList<Integer> StateId;
    private ObservableList<Integer> customerStatusId;
    private ObservableList<Integer> customerTypeId;

    Connection con = null;
    PreparedStatement pstmt = null;


    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("customerSubmenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleHomeButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainMenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleEditButton(ActionEvent event) {

        try {
            Customer c = customerTV.getSelectionModel().getSelectedItem();

            customerIdTF.setText(Integer.toString(c.getCustomerId().get()));
            firstNameTF.setText(c.getCustomerFirst().get());
            lastNameTF.setText(c.getCustomerLast().get());
            organizationTF.setText(c.getCustomerOrg().get());
            customerAddressTF.setText(c.getStreetAddress().get());
            customerCityTF.setText(c.getCustomerCity().get());
            customerStateCB.getSelectionModel().select(c.getStateId().get());
            phoneNumberTF.setText(c.getCustomerPhone().get());


            tabTwo.setText("Edit Customer");
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addNewCustomer.setVisible(false);
            homeButton.setDisable(true);
            backButton.setDisable(true);
            customerTP.getSelectionModel().select(tabTwo);
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
    protected void handleCancelButton(ActionEvent event)  {

        clearFields();
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        addNewCustomer.setVisible(true);
        homeButton.setDisable(false);
        backButton.setDisable(false);
        tabTwo.setText("Add New Customer");
        customerTP.getSelectionModel().select(tabOne);
        tabOne.setDisable(false);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        customerIdTF.setVisible(false);

        initStateIdCB();
        customerStateCB.setVisibleRowCount(100);
        initCountryIdCB();
        customerCountryCB.setVisibleRowCount(100);
        initCustomerStatusIdCB();
        customerStatusIdCB.setVisibleRowCount(10);
        initCustomerTypeIdCB();
        customerTypeIdCB.setVisibleRowCount(30);
        refreshTable();

    }
    @FXML
    protected void handleAddButton(ActionEvent event) {

        try{
            String firstName = firstNameTF.getText();
            String lastName = lastNameTF.getText();
            String org = organizationTF.getText();
            String streetAddress = customerAddressTF.getText();
            String city = customerCityTF.getText();
            int stateId = customerStateCB.getValue();
            String zipcode = customerZipcodeTF.getText();
            int countryId = customerCountryCB.getValue();
            String phoneNumber = phoneNumberTF.getText();
            String email = emailTF.getText();
            int customerStatusId = customerStatusIdCB.getValue();
            int customerTypeId = customerTypeIdCB.getValue();

            String SQL = "INSERT INTO CUSTOMER(Customer_First_Name, Customer_Last_Name, Customer_Organization, " +
                    "Customer_Street_Address, Customer_City, State_ID, Customer_Zipcode, Country_ID, Customer_Phone_Number, Customer_Email_Address, " +
                    "Customer_Status_ID, Customer_Type_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Record");
            alert.setHeaderText("Are you sure want to add this record?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,firstName);
                pstmt.setString(2,lastName);
                pstmt.setString(3,org);
                pstmt.setString(4,streetAddress);
                pstmt.setString(5,city);
                pstmt.setInt(6,stateId);
                pstmt.setString(7,zipcode);
                pstmt.setInt(8,countryId);
                pstmt.setString(9,phoneNumber);
                pstmt.setString(10,email);
                pstmt.setInt(11,customerStatusId);
                pstmt.setInt(12,customerTypeId);
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
    protected void handleDeleteButton(ActionEvent event) {
        try{
            Customer selected = customerTV.getSelectionModel().getSelectedItem();
            int customerId = selected.getCustomerId().get();
            String SQL = "DELETE FROM CUSTOMER WHERE Customer_ID="+customerId+"";
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
        colOne.setCellValueFactory(cell->cell.getValue().getCustomerId().asObject());
        colTwo.setCellValueFactory(cell->cell.getValue().getCustomerFirst());
        colThree.setCellValueFactory(cell->cell.getValue().getCustomerLast());
        colThirteen.setCellValueFactory(cell->cell.getValue().getCustomerOrg());
        colFour.setCellValueFactory(cell->cell.getValue().getStreetAddress());
        colFive.setCellValueFactory(cell->cell.getValue().getCustomerCity());
        colSix.setCellValueFactory(cell->cell.getValue().getStateId().asObject());
        colSeven.setCellValueFactory(cell->cell.getValue().getCustomerZipcode());
        colEight.setCellValueFactory(cell->cell.getValue().getCountryId().asObject());
        colNine.setCellValueFactory(cell->cell.getValue().getCustomerPhone());
        colTen.setCellValueFactory(cell->cell.getValue().getCustomerEmail());
        colEleven.setCellValueFactory(cell->cell.getValue().getCustomerStatusId().asObject());
        colTwelve.setCellValueFactory(cell->cell.getValue().getCustomerTypeId().asObject());

    }

    private void refreshTable() {
        initTable();
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM CUSTOMER");

            while(rs.next()){
                data.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),rs.getString(6),rs.getInt(7),
                        rs.getString(8),rs.getInt(9),rs.getString(10),rs.getString(11),
                        rs.getInt(12),rs.getInt(13)));
            }
            initTable();
            customerTV.setItems(null);
            customerTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStateIdCB() {
        db = new DBConnection();
        StateId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT State_ID FROM STATE_TERRITORY");
            while (rs.next()) {
                StateId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerStateCB.setItems(null);
        customerStateCB.setItems(StateId);
    }

    private void initCountryIdCB() {
        db = new DBConnection();
        CountryId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Country_ID FROM COUNTRY");
            while (rs.next()) {
                CountryId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerCountryCB.setItems(null);
        customerCountryCB.setItems(CountryId);
    }

    private void initCustomerStatusIdCB() {
        db = new DBConnection();
        customerStatusId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Customer_Status_ID FROM CUSTOMER_STATUS");
            while (rs.next()) {
                customerStatusId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerStatusIdCB.setItems(null);
        customerStatusIdCB.setItems(customerStatusId);
    }

    private void initCustomerTypeIdCB() {
        db = new DBConnection();
        customerTypeId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Customer_Type_Id FROM CUSTOMER_TYPE");
            while (rs.next()) {
                customerTypeId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerTypeIdCB.setItems(null);
        customerTypeIdCB.setItems(customerStatusId);
    }

    private void clearFields() {
        firstNameTF.clear();
        lastNameTF.clear();
        organizationTF.clear();
        customerAddressTF.clear();
        customerCityTF.clear();
        customerStateCB.setValue(null);
        customerZipcodeTF.clear();
        customerCountryCB.setValue(null);
        phoneNumberTF.clear();
        emailTF.clear();
        customerStatusIdCB.setValue(null);
        customerTypeIdCB.setValue(null);

    }

    private void confirmAdd() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Success!");
        alert.setContentText("Customer has been added successfully!");

        alert.showAndWait();
    }
    private void confirmEdit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Commit Success!");
        alert.setContentText("Instance has been edited successfully!");

        alert.showAndWait();
    }
}
