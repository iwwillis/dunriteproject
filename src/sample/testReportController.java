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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class testReportController implements Initializable {
    @FXML private TableColumn<testReportObj1,Integer> reportOneC1;
    @FXML private TableColumn<testReportObj1,String> reportOneC2;
    @FXML private TableColumn<testReportObj1,String> reportOneC3;
    @FXML private TableColumn<testReportObj1,String> reportOneC4;
    @FXML private TableView<testReportObj1> testReportTV;

    private DBConnection db;
    private ObservableList<testReportObj1> data;

    Connection con =null;


    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("reportsSubmenu.fxml"));
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

    private void initTable() {
        reportOneC1.setCellValueFactory(cell->cell.getValue().empIncidentNumberProperty().asObject());
        reportOneC2.setCellValueFactory(cell->cell.getValue().empIncidentDescriptionProperty());
        reportOneC3.setCellValueFactory(cell->cell.getValue().empIncidentTypeProperty());
        reportOneC4.setCellValueFactory(cell->cell.getValue().empIncidentStatusProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DBConnection();
        data = FXCollections.observableArrayList();

        try {
            con = db.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT EMPLOYEE_INCIDENT.Employee_Incident_Number AS 'Employee Incident Number',\n" +
                    "\tEMPLOYEE_INCIDENT.Employee_Incident_Description AS 'Description',\n" +
                    "\tEMPLOYEE_INCIDENT_TYPE.Employee_Incident_Type AS 'Type of Incident',\n" +
                    "\tEMPLOYEE_INCIDENT_STATUS.Employee_Incident_Status AS 'Status of Incident'\n" +
                    "\n" +
                    "FROM (EMPLOYEE_INCIDENT \n" +
                    "LEFT JOIN EMPLOYEE_INCIDENT_TYPE ON EMPLOYEE_INCIDENT_TYPE.Employee_Incident_Type_ID=EMPLOYEE_INCIDENT.Employee_Incident_Type_ID\n" +
                    "LEFT JOIN EMPLOYEE_INCIDENT_STATUS ON EMPLOYEE_INCIDENT_STATUS.Employee_Incident_Status_ID=EMPLOYEE_INCIDENT.Employee_Incident_Status_ID)\n" +
                    "\n" +
                    "WHERE EMPLOYEE_INCIDENT_STATUS.Employee_Incident_Status= 'Resolved'\n" +
                    "\n" +
                    "ORDER BY EMPLOYEE_INCIDENT.Employee_Incident_Number;");

            while(rs.next()){
                data.add(new testReportObj1(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4)));
            }
            initTable();
            testReportTV.setItems(null);
            testReportTV.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
