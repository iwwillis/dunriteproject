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

public class jobManagerController implements Initializable {

    @FXML private TableColumn<Job, Integer> colOne;
    @FXML private TableColumn<Job, String> colTwo;
    @FXML private TableColumn<Job, String> colThree;
    @FXML private TableColumn<Job, Integer> colFour;
    @FXML private TableColumn<Job, String> colFive;
    @FXML private TableColumn<Job, Integer> colSix;
    @FXML private TableColumn<Job, String> colSeven;
    @FXML private TableColumn<Job, String> colEight;
    @FXML private TableColumn<Job, String> colNine;
    @FXML private TableColumn<Job, String> colTen;
    @FXML private TableColumn<Job, String> colEleven;
    @FXML private TableColumn<Job,Integer> colTwelve;
    @FXML private TableColumn<Job,Integer> colThirteen;
    @FXML private TableColumn<Job,Integer> colFourteen;
    @FXML private TableColumn<Job,Integer> colFifteen;
    @FXML private TableView<Job> jobTV;


    @FXML private TextField jobNumberTF;
    @FXML private TextField jobStreetAddressTF;
    @FXML private TextField jobCityTF;
    @FXML private ComboBox<Integer> stateCB;
    @FXML private TextField jobZipcodeTF;
    @FXML private TextField countryTF;
    @FXML private DatePicker startDP;
    @FXML private DatePicker endDP;
    @FXML private DatePicker quoteStartDP;
    @FXML private DatePicker expirationDP;
    @FXML private DatePicker invoiceDP;
    @FXML private ComboBox<Integer> jobSizeIdCB;
    @FXML private ComboBox<Integer> jobStatusIdCB;
    @FXML private ComboBox<Integer> customerIdCB;
    @FXML private ComboBox<Integer> jobTypeIdCB;

    @FXML private TabPane jobTP;
    @FXML private Tab tabOne;
    @FXML private Tab tabTwo;
    @FXML private Button homeButton;
    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button addButton;

    private DBConnection db;
    private ObservableList<Job> data;
    private ObservableList<Integer> state;
    private ObservableList<Integer> jobSizeId;
    private ObservableList<Integer> jobStatusId;
    private ObservableList<Integer> customerId;
    private ObservableList<Integer> jobTypeId;

    Connection con = null;
    PreparedStatement pstmt = null;


    @FXML
    protected void handleEditButton(ActionEvent event) {

        try {
            Job j = jobTV.getSelectionModel().getSelectedItem();

            jobNumberTF.setText(Integer.toString(j.getJobNumber().get()));
            jobStreetAddressTF.setText(j.getStreetAddress().get());
            jobCityTF.setText(j.getJobCity().get());
            stateCB.getSelectionModel().select(j.getStateId().get()-1);
            jobZipcodeTF.setText(j.getZip().get());
            countryTF.setText(Integer.toString(j.getCountryId().get()));

            String a = j.getStart().get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter = formatter.withLocale(Locale.US);
            LocalDate ld = LocalDate.parse(a,formatter);
            startDP.setValue(ld);

            String b = j.getEnd().get();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter2 = formatter2.withLocale(Locale.US);
            LocalDate ld2 = LocalDate.parse(b,formatter2);
            endDP.setValue(ld2);

            String c = j.getQuoteDate().get();
            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter3 = formatter3.withLocale(Locale.US);
            LocalDate ld3 = LocalDate.parse(c,formatter3);
            quoteStartDP.setValue(ld3);

            String d = j.getQuoteExp().get();
            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter4 = formatter4.withLocale(Locale.US);
            LocalDate ld4 = LocalDate.parse(d,formatter4);
            expirationDP.setValue(ld4);

            String e = j.getQuoteExp().get();
            DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter5 = formatter5.withLocale(Locale.US);
            LocalDate ld5 = LocalDate.parse(e,formatter5);
            invoiceDP.setValue(ld5);

            jobSizeIdCB.getSelectionModel().select(j.getJobSizeId().get()-1);
            jobStatusIdCB.getSelectionModel().select(j.getJobStatusId().get()-1);
            customerIdCB.getSelectionModel().select(j.getCustomerId().get()-1);
            jobTypeIdCB.getSelectionModel().select(j.getJobTypeId().get()-1);


            tabTwo.setText("Edit Job");
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addButton.setVisible(false);
            homeButton.setDisable(true);
            backButton.setDisable(true);
            jobTP.getSelectionModel().select(tabTwo);
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

            int jId = Integer.parseInt(jobNumberTF.getText());
            String address = jobStreetAddressTF.getText();
            String city = jobCityTF.getText();
            int stateId = stateCB.getValue();
            String zipcode = jobZipcodeTF.getText();
            int countryId = Integer.parseInt(countryTF.getText());
            LocalDate d = startDP.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String sDate = d.format(formatter);
            LocalDate d2 = endDP.getValue();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String eDate = d2.format(formatter2);
            LocalDate d3 = endDP.getValue();
            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String qsDate = d3.format(formatter3);
            LocalDate d4 = expirationDP.getValue();
            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String qeDate = d4.format(formatter4);
            LocalDate d5 = invoiceDP.getValue();
            DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String iDate = d5.format(formatter5);


            int jobSizeId = jobSizeIdCB.getValue();
            int jobStatusId = jobStatusIdCB.getValue();
            int customerId = customerIdCB.getValue();
            int jobTypeId = jobTypeIdCB.getValue();

            String SQL = "UPDATE JOB SET Job_Street_Address ='"+address+"', Job_City ='"+city+"', " +
                    "State_ID= "+stateId+", Job_Zipcode='"+zipcode+"', " +
                    "Country_ID="+countryId+", Job_Start_Date= '"+sDate+"', Job_End_Date='"+eDate+"', Quote_Date='"+qsDate+"'," +
                    "Quote_Expiration_Date = '"+qeDate+"', Invoice_Date='"+iDate+"', Job_Size_ID="+jobSizeId+"," +
                    "Job_Status_ID="+jobStatusId+", Customer_ID="+customerId+", Job_Type_ID="+jobTypeId+" WHERE Job_Number="+jId+";";

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
                tabTwo.setText("Add New Job");
                jobTP.getSelectionModel().select(tabOne);
                tabOne.setDisable(false);
            } else if (option.get() == ButtonType.CANCEL) {

            }

        }
        catch(Exception e){
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Could not edit record!");
            alert.showAndWait();*/
            e.printStackTrace();
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
        tabTwo.setText("Add New Job");
        jobTP.getSelectionModel().select(tabOne);
        tabOne.setDisable(false);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobTP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        jobNumberTF.setVisible(false);

        initStateIdCB();
        stateCB.setVisibleRowCount(10);
        initJobSizeIdCB();
        jobSizeIdCB.setVisibleRowCount(10);
        initJobStatusIdCB();
        jobStatusIdCB.setVisibleRowCount(10);
        initCustomerIdCB();
        customerIdCB.setVisibleRowCount(10);
        initJobTypeIdCB();
        jobTypeIdCB.setVisibleRowCount(10);
        refreshTable();

    }
    @FXML
    protected void handleAddButton(ActionEvent event) {

        try{
            String address = jobStreetAddressTF.getText();
            String city = jobCityTF.getText();
            int stateId = stateCB.getValue();
            String zip = jobZipcodeTF.getText();
            int countryId = Integer.parseInt(countryTF.getText());
            LocalDate d = startDP.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String sDate = d.format(formatter);
            LocalDate d2 = endDP.getValue();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String eDate = d2.format(formatter2);
            LocalDate d3 = quoteStartDP.getValue();
            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String qsDate = d3.format(formatter3);
            LocalDate d4 = expirationDP.getValue();
            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String qeDate = d4.format(formatter4);
            LocalDate d5 = invoiceDP.getValue();
            DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String iDate = d5.format(formatter5);
            int jobSizeId = jobSizeIdCB.getValue();
            int jobStatusId = jobStatusIdCB.getValue();
            int customerId = customerIdCB.getValue();
            int jobTypeId = jobTypeIdCB.getValue();


            String SQL = "INSERT INTO JOB(job_street_address, job_city, State_ID, job_zipcode, " +
                    "Country_ID, Job_Start_Date, job_end_date, quote_date, quote_expiration_date, invoice_date, job_size_id, " +
                    "job_status_id, customer_id, job_type_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Record");
            alert.setHeaderText("Are you sure want to add this record?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {

                db = new DBConnection();
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,address);
                pstmt.setString(2,city);
                pstmt.setInt(3,stateId);
                pstmt.setString(4,zip);
                pstmt.setInt(5,countryId);
                pstmt.setString(6,sDate);
                pstmt.setString(7,eDate);
                pstmt.setString(8,qsDate);
                pstmt.setString(9,qeDate);
                pstmt.setString(10,iDate);
                pstmt.setInt(11,jobSizeId);
                pstmt.setInt(12,jobStatusId);
                pstmt.setInt(13,customerId);
                pstmt.setInt(14,jobTypeId);
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
            Job selected = jobTV.getSelectionModel().getSelectedItem();
            int jobNumber = selected.getJobNumber().get();
            String SQL = "DELETE FROM JOB WHERE Job_Number="+jobNumber+"";
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
        colOne.setCellValueFactory(cell->cell.getValue().getJobNumber().asObject());
        colTwo.setCellValueFactory(cell->cell.getValue().getStreetAddress());
        colThree.setCellValueFactory(cell->cell.getValue().getJobCity());
        colFour.setCellValueFactory(cell->cell.getValue().getStateId().asObject());
        colFive.setCellValueFactory(cell->cell.getValue().getZip());
        colSix.setCellValueFactory(cell->cell.getValue().getCountryId().asObject());
        colSeven.setCellValueFactory(cell->cell.getValue().getStart());
        colEight.setCellValueFactory(cell->cell.getValue().getEnd());
        colNine.setCellValueFactory(cell->cell.getValue().getQuoteDate());
        colTen.setCellValueFactory(cell->cell.getValue().getQuoteExp());
        colEleven.setCellValueFactory(cell->cell.getValue().getInvoiceDate());
        colTwelve.setCellValueFactory(cell->cell.getValue().getJobSizeId().asObject());
        colThirteen.setCellValueFactory(cell->cell.getValue().getJobStatusId().asObject());
        colFourteen.setCellValueFactory(cell->cell.getValue().getCustomerId().asObject());
        colFifteen.setCellValueFactory(cell->cell.getValue().getJobTypeId().asObject());

    }

    private void refreshTable() {
        initTable();
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT\n" +
                    "Job_Number,\n" +
                    "Job_Street_Address,\n" +
                    "Job_City,\n" +
                    "State_ID,\n" +
                    "Job_Zipcode,\n" +
                    "Country_ID,\n" +
                    "FORMAT(Job_Start_Date, 'MM/dd/yyyy','en-us'),\n" +
                    "FORMAT(Job_End_Date, 'MM/dd/yyyy','en-us'),\n" +
                    "FORMAT(Quote_Date, 'MM/dd/yyyy','en-us'),\n" +
                    "FORMAT(Quote_Expiration_Date, 'MM/dd/yyyy','en-us'),\n" +
                    "FORMAT(Invoice_Date, 'MM/dd/yyyy', 'en-us'),\n" +
                    "Job_Size_ID,\n" +
                    "Job_Status_ID,\n" +
                    "Customer_ID,\n" +
                    "Job_Type_ID\n" +
                    "\n" +
                    "FROM JOB");

            while(rs.next()){
                data.add(new Job(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getString(5),rs.getInt(6),rs.getString(7),
                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
                        rs.getInt(12),rs.getInt(13),rs.getInt(14),rs.getInt(15)));
            }
            initTable();
            jobTV.setItems(null);
            jobTV.setItems(data);
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

    private void initJobSizeIdCB() {
        db = new DBConnection();
        jobSizeId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Job_Size_ID FROM JOB_SIZE");
            while (rs.next()) {
                jobSizeId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jobSizeIdCB.setItems(null);
        jobSizeIdCB.setItems(jobSizeId);
    }

    private void initCustomerIdCB() {
        db = new DBConnection();
        customerId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Customer_ID FROM CUSTOMER");
            while (rs.next()) {
                customerId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerIdCB.setItems(null);
        customerIdCB.setItems(customerId);
    }

    private void initJobStatusIdCB() {
        db = new DBConnection();
        jobStatusId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Job_Status_ID FROM JOB_STATUS");
            while (rs.next()) {
                jobStatusId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jobStatusIdCB.setItems(null);
        jobStatusIdCB.setItems(jobStatusId);
    }

    private void initJobTypeIdCB() {
        db = new DBConnection();
        jobTypeId = FXCollections.observableArrayList();
        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Job_Type_ID FROM JOB_TYPE");
            while (rs.next()) {
                jobTypeId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jobTypeIdCB.setItems(null);
        jobTypeIdCB.setItems(jobTypeId);
    }

    private void clearFields() {
        jobNumberTF.clear();
        jobStreetAddressTF.clear();
        jobCityTF.clear();
        stateCB.setValue(null);
        jobZipcodeTF.clear();
        countryTF.clear();
        startDP.getEditor().clear();
        endDP.getEditor().clear();
        quoteStartDP.getEditor().clear();
        expirationDP.getEditor().clear();
        invoiceDP.getEditor().clear();
        jobSizeIdCB.setValue(null);
        jobStatusIdCB.setValue(null);
        customerIdCB.setValue(null);
        jobTypeIdCB.setValue(null);

    }

    private void confirmAdd() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Success!");
        alert.setContentText("Job has been added successfully!");

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
        loader.setLocation(getClass().getResource("jobSubmenu.fxml"));
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
