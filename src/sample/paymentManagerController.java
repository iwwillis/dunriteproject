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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    @FXML private DatePicker paymentDateDP;
    @FXML private TextField originalAmountTF;
    @FXML private TextField paymentAmountTF;
    @FXML private TextField paymentRemainingTF;
    @FXML private TextField paymentTermsTF;
    @FXML private TextField paymentMethodTF;
    @FXML private TextField jobNumberPaymentRefTF;
    @FXML private TextField validationCodeTF;
    @FXML private TextField paymentMethodIdPaymentRefTF;

    private DBConnection db;
    private ObservableList<Payment> data;

    String URL = "jdbc:sqlserver://localhost;databaseName =CIS3365Testing";
    String username = "sa";
    String password ="root";
    Connection con =null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    int paymentId;
    String paymentDate, validationCode;
    Double originalTotalDue;


    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainMenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }

    @FXML
    protected void handleAddButton(ActionEvent event) throws IOException {
        Payment selected = paymentTV.getSelectionModel().getSelectedItem();
        paymentId = selected.getPaymentNumberId().get();
        String SQL = "DELETE FROM PAYMENT WHERE Payment_Number="+paymentId+"";
        try{
            db = new DBConnection();
            pstmt = con.prepareStatement(SQL);
            pstmt.executeUpdate();
            refreshTable();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try { pstmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) { }
        }
    }
    @FXML
    protected void handleDeleteButton(ActionEvent event) throws IOException {
        Payment selected = paymentTV.getSelectionModel().getSelectedItem();
        paymentId = selected.getPaymentNumberId().get();
        String SQL = "DELETE FROM PAYMENT WHERE Payment_Number="+paymentId+"";
        try{
        db = new DBConnection();
        pstmt = con.prepareStatement(SQL);
        pstmt.executeUpdate();
        refreshTable();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try { pstmt.close(); } catch (Exception e) { }
            try { con.close(); } catch (Exception e) { }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PAYMENT");

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
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PAYMENT");

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




}
