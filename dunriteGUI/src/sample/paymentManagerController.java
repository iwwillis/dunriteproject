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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class paymentManagerController implements Initializable {

    @FXML private TableColumn<Payment,Integer> paymentIdColumn;
    @FXML private TableColumn<Payment,String> paymentDateColumn;
    @FXML private TableColumn<Payment,Double> originalAmountColumn;
    @FXML private TableColumn<Payment,Double> paymentAmountColumn;
    @FXML private TableColumn<Payment,Double> paymentRemainingColumn;
    @FXML private TableColumn<Payment,String> paymentTermsColumn;
    @FXML private TableColumn<Payment,String> validationCodeColumn;
    @FXML private TableColumn<Payment,Integer> jobNumberRefColumn;
    @FXML private TableColumn<Payment,Integer> paymentMethodIdRefColumn;
    @FXML private TableView<Payment> paymentTV;

    @FXML private TextField paymentNumberTF;
    @FXML private DatePicker paymentDateDP;
    @FXML private TextField originalAmountTF;
    @FXML private TextField paymentAmountTF;
    @FXML private TextField paymentRemainingTF;
    @FXML private TextField paymentTermsTF;
    @FXML private TextField validationCodeTF;
    @FXML private TextField jobNumberPaymentRefTF;
    @FXML private TextField paymentMethodIdPaymentRefTF;
    @FXML private TabPane paymentTabPane;
    @FXML private Tab paymentManagerTab;
    @FXML private Tab switchPaymentTab;
    @FXML private Button homeButton;
    @FXML private Button backButton;
    @FXML private Button cancelPayment;
    @FXML private Button savePayment;
    @FXML private Button addPaymentButton;

    private DBConnection db;
    private ObservableList<Payment> data;

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
            LocalDate d = paymentDateDP.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String pDate = d.format(formatter);
            double oTotal = Double.parseDouble(originalAmountTF.getText());
            double pAmount = Double.parseDouble(paymentAmountTF.getText());
            double pRemaining = Double.parseDouble(paymentRemainingTF.getText());
            String pTerms = paymentTermsTF.getText();
            String validationCode = validationCodeTF.getText();
            int jNumber = Integer.parseInt(jobNumberPaymentRefTF.getText());
            int pMethodId = Integer.parseInt(paymentMethodIdPaymentRefTF.getText());
            String SQL = "INSERT INTO PAYMENT(Payment_Date,Original_Total_Due,Payment_Amount,Payment_Remaining,Payment_Terms,Authorization_Code,Job_Number,Payment_Method_ID) VALUES(?,?,?,?,?,?,?,?)";

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Add Record");
            alert.setHeaderText("Are you sure want to add this record?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,pDate);
                pstmt.setDouble(2,oTotal);
                pstmt.setDouble(3,pAmount);
                pstmt.setDouble(4,pRemaining);
                pstmt.setString(5,pTerms);
                pstmt.setString(6,validationCode);
                pstmt.setInt(7,jNumber);
                pstmt.setInt(8,pMethodId);
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
            Payment selected = paymentTV.getSelectionModel().getSelectedItem();
            int paymentId = selected.getPaymentNumberId().get();
            String SQL = "DELETE FROM PAYMENT WHERE Payment_Number="+paymentId+"";
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
            Payment p = paymentTV.getSelectionModel().getSelectedItem();


            String a = p.getPaymentDate().get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter = formatter.withLocale(Locale.US);
            LocalDate ld = LocalDate.parse(a,formatter);


            paymentNumberTF.setText(Integer.toString(p.getPaymentNumberId().get()));
            paymentDateDP.setValue(ld);
            originalAmountTF.setText(Double.toString(p.getOriginalTotal().get()));
            paymentAmountTF.setText(Double.toString(p.getPaymentAmount().get()));
            paymentRemainingTF.setText(Double.toString(p.getPaymentRemaining().get()));
            paymentTermsTF.setText(p.getPaymentTerms().get());
            validationCodeTF.setText(p.getValidationCode().get());
            jobNumberPaymentRefTF.setText(Integer.toString(p.getJobNumber().get()));
            paymentMethodIdPaymentRefTF.setText(Integer.toString(p.getPaymentMethodId().get()));

            switchPaymentTab.setText("Edit Payment");
            cancelPayment.setVisible(true);
            savePayment.setVisible(true);
            addPaymentButton.setVisible(false);
            homeButton.setDisable(true);
            backButton.setDisable(true);
            paymentTabPane.getSelectionModel().select(switchPaymentTab);
            paymentManagerTab.setDisable(true);

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
        cancelPayment.setVisible(false);
        savePayment.setVisible(false);
        addPaymentButton.setVisible(true);
        homeButton.setDisable(false);
        backButton.setDisable(false);
        switchPaymentTab.setText("Add New Payment");
        paymentTabPane.getSelectionModel().select(paymentManagerTab);
        paymentManagerTab.setDisable(false);


    }

    @FXML
    protected void handleSaveButton(ActionEvent event){
        try{

            int pId = Integer.parseInt(paymentNumberTF.getText());
            LocalDate d = paymentDateDP.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String pDate = d.format(formatter);
            double oTotal = Double.parseDouble(originalAmountTF.getText());
            double pAmount = Double.parseDouble(paymentAmountTF.getText());
            double pRemaining = Double.parseDouble(paymentRemainingTF.getText());
            String pTerms = paymentTermsTF.getText();
            String validationCode = validationCodeTF.getText();
            int jNumber = Integer.parseInt(jobNumberPaymentRefTF.getText());
            int pMethodId = Integer.parseInt(paymentMethodIdPaymentRefTF.getText());
            String SQL = "UPDATE PAYMENT SET Payment_Date ='"+pDate+"', Original_Total_Due ="+oTotal+", Payment_Amount= "+pAmount+", Payment_Remaining= "+pRemaining+", Payment_Terms='"+pTerms+"', Authorization_Code='"+validationCode+"', Job_Number= "+jNumber+", Payment_Method_ID="+pMethodId+" WHERE Payment_Number="+pId+";";

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
            cancelPayment.setVisible(false);
            savePayment.setVisible(false);
            addPaymentButton.setVisible(true);
            homeButton.setDisable(false);
            backButton.setDisable(false);
            switchPaymentTab.setText("Add New Payment");
            paymentTabPane.getSelectionModel().select(paymentManagerTab);
            paymentManagerTab.setDisable(false);
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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        paymentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        cancelPayment.setVisible(false);
        savePayment.setVisible(false);
        paymentNumberTF.setVisible(false);

        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT\n" +
                    "Payment_Number,\n" +
                    "FORMAT (Payment_Date,'MM/dd/yyyy','en-US'),\n" +
                    "Original_Total_Due,\n" +
                    "Payment_Amount,\n" +
                    "Payment_Remaining,\n" +
                    "Payment_Terms,\n" +
                    "Authorization_Code,\n" +
                    "Job_Number,\n" +
                    "Payment_Method_ID\n" +
                    "\n" +
                    "FROM PAYMENT;");

            while(rs.next()){
                data.add(new Payment(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                        rs.getDouble(4), rs.getDouble(5),rs.getString(6),rs.getString(7),
                        rs.getInt(8),rs.getInt(9)));
            }
            initTable();
            paymentTV.setItems(null);
            paymentTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initTable() {
        paymentIdColumn.setCellValueFactory(cell->cell.getValue().getPaymentNumberId().asObject());
        originalAmountColumn.setCellValueFactory(cell->cell.getValue().getOriginalTotal().asObject());
        paymentDateColumn.setCellValueFactory(cell->cell.getValue().getPaymentDate());
        paymentAmountColumn.setCellValueFactory(cell->cell.getValue().getPaymentAmount().asObject());
        paymentRemainingColumn.setCellValueFactory(cell->cell.getValue().getPaymentRemaining().asObject());
        paymentTermsColumn.setCellValueFactory(cell->cell.getValue().getPaymentTerms());
        validationCodeColumn.setCellValueFactory(cell->cell.getValue().getValidationCode());
        jobNumberRefColumn.setCellValueFactory(cell->cell.getValue().getJobNumber().asObject());
        paymentMethodIdRefColumn.setCellValueFactory(cell->cell.getValue().getPaymentMethodId().asObject());
    }

    private void refreshTable() {
        initTable();
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT\n" +
                    "Payment_Number,\n" +
                    "FORMAT (Payment_Date,'MM/dd/yyyy','en-US'),\n" +
                    "Original_Total_Due,\n" +
                    "Payment_Amount,\n" +
                    "Payment_Remaining,\n" +
                    "Payment_Terms,\n" +
                    "Authorization_Code,\n" +
                    "Job_Number,\n" +
                    "Payment_Method_ID\n" +
                    "\n" +
                    "FROM PAYMENT;");

            while(rs.next()){
                data.add(new Payment(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                        rs.getDouble(4), rs.getDouble(5),rs.getString(6),rs.getString(7),
                        rs.getInt(8),rs.getInt(9)));
            }
            initTable();
            paymentTV.setItems(null);
            paymentTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

    private void clearFields() {
        paymentNumberTF.clear();
        paymentDateDP.getEditor().clear();
        originalAmountTF.clear();
        paymentAmountTF.clear();
        paymentRemainingTF.clear();
        paymentTermsTF.clear();
        validationCodeTF.clear();
        jobNumberPaymentRefTF.clear();
        paymentMethodIdPaymentRefTF.clear();
    }
    private void confirmAdd() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Add Success!");
        alert.setContentText("Payment has been added successfully!");

        alert.showAndWait();
    }
    private void confirmEdit() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Commit Success!");
        alert.setContentText("Instance has been edited successfully!");

        alert.showAndWait();
    }




}
