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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class paymentMethodManagerController implements Initializable {

    @FXML private TableColumn<PaymentMethod,Integer> paymentMethodIdColumn;
    @FXML private TableColumn<PaymentMethod,String> paymentMethodColumn;
    @FXML private TableView<PaymentMethod> paymentMethodTV;

    @FXML private TextField idField;
    @FXML private TextField paymentMethodTF;

    @FXML private TabPane mainTabPane;
    @FXML private Tab firstTab;
    @FXML private Tab secondTab;
    @FXML private Button homeButton;
    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button addButton;

    private DBConnection db;
    private ObservableList<PaymentMethod> data;

    Connection con =null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
        clearFields();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("miscellaneousSubmenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleHomeButton(ActionEvent event) throws IOException {
        clearFields();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainMenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleAddButton(ActionEvent event) {

        try{

            String pMethod = paymentMethodTF.getText();

            String SQL = "INSERT INTO PAYMENT_METHOD(Payment_Method) VALUES(?)";

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Add Payment Method");
            alert.setHeaderText("Are you sure want to add this payment method?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,pMethod);

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
            //try { con.close(); } catch (Exception e) { }
        }

    }
    @FXML
    protected void handleDeleteButton(ActionEvent event) {
        try{
            PaymentMethod selected = paymentMethodTV.getSelectionModel().getSelectedItem();
            int paymentMethodId = selected.getPaymentMethodNumberId().get();
            String SQL = "DELETE FROM PAYMENT_METHOD WHERE Payment_Method_ID="+paymentMethodId+"";
            Alert alert = new Alert(AlertType.CONFIRMATION);
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
            //try { con.close(); } catch (Exception e) { }
        }


    }

    @FXML
    protected void handleEditButton(ActionEvent event) {

        try {
            PaymentMethod pm = paymentMethodTV.getSelectionModel().getSelectedItem();

            idField.setText(Integer.toString(pm.getPaymentMethodNumberId().get()));
            paymentMethodTF.setText(pm.getPaymentMethodName().get());

            secondTab.setText("Edit Payment Method");
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addButton.setVisible(false);
            homeButton.setDisable(true);
            backButton.setDisable(true);
            mainTabPane.getSelectionModel().select(secondTab);
            firstTab.setDisable(true);

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
        addButton.setVisible(true);
        homeButton.setDisable(false);
        backButton.setDisable(false);
        secondTab.setText("Add Payment Method");
        mainTabPane.getSelectionModel().select(firstTab);
        firstTab.setDisable(false);


    }

    @FXML
    protected void handleSaveButton(ActionEvent event) throws IOException {
        secondTab.setText("Add Payment Method");
        try{
        int pId = Integer.parseInt(idField.getText());
        String pMethod = paymentMethodTF.getText();
        String SQL ="UPDATE PAYMENT_METHOD SET Payment_Method ='"+pMethod+"' WHERE Payment_Method_ID ="+pId+" ;";

        Alert alert = new Alert(AlertType.CONFIRMATION);
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
            secondTab.setText("Add New Payment");
            mainTabPane.getSelectionModel().select(firstTab);
            firstTab.setDisable(false);
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
        //try { con.close(); } catch (Exception e) { }
    }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        idField.setVisible(false);

        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PAYMENT_METHOD");

            while(rs.next()){
                data.add(new PaymentMethod(rs.getInt(1), rs.getString(2)));
            }
            initTable();
            paymentMethodTV.setItems(null);
            paymentMethodTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initTable() {
        paymentMethodIdColumn.setCellValueFactory(cell->cell.getValue().getPaymentMethodNumberId().asObject());
        paymentMethodColumn.setCellValueFactory(cell->cell.getValue().getPaymentMethodName());

    }

    private void refreshTable() {
        initTable();
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PAYMENT_METHOD");

            while(rs.next()){
                data.add(new PaymentMethod(rs.getInt(1), rs.getString(2)));
            }
            initTable();
            paymentMethodTV.setItems(null);
            paymentMethodTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idField.clear();
        paymentMethodTF.clear();

    }
    private void confirmAdd() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Add Success!");
        alert.setContentText("Payment method has been added successfully!");

        alert.showAndWait();
    }
    private void confirmEdit() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Commit Success!");
        alert.setContentText("Instance has been edited successfully!");

        alert.showAndWait();
    }




}
