package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;

public class reportsSubmenuController {

    DBConnection db;
    Connection con;

    @FXML
    protected void handleResolvedReportButton(ActionEvent event){
        db = new DBConnection();
        try {
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\employeeIncidentResolved.jrxml");
            String SQL = "SELECT EMPLOYEE_INCIDENT.Employee_Incident_Number AS 'Employee Incident Number',\n" +
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
                    "ORDER BY EMPLOYEE_INCIDENT.Employee_Incident_Number;";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport,null,con);
            JasperViewer.viewReport(jprint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleJobQuotingPendingButton(ActionEvent event){
        db = new DBConnection();
        try {
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\employeeIncidentResolved.jrxml");
            String SQL = "SELECT \n" +
                    "j.Job_Number AS 'Job Number',\n" +
                    "CONCAT(cust.Customer_First_Name,' ', cust.Customer_Last_Name) AS 'Customer',\n" +
                    "j.Job_Street_Address AS 'Address',\n" +
                    "j.Job_City AS 'City',\n" +
                    "st.State_Name AS 'State',\n" +
                    "j.Job_Zipcode AS 'Zipcode',\n" +
                    "c.Country_Abbreviation AS 'Country',\n" +
                    "FORMAT (j.Job_Start_Date, 'MM/dd/yyy', 'en-US') AS 'Start Date',\n" +
                    "FORMAT (j.Job_End_Date, 'MM/dd/yyy', 'en-US') AS 'End Date',\n" +
                    "FORMAT (j.Quote_Date, 'MM/dd/yyy', 'en-US') AS 'Quote Date',\n" +
                    "FORMAT (j.Quote_Expiration_Date, 'MM/dd/yyy', 'en-US') AS 'Quote Exp Date',\n" +
                    "FORMAT (j.Invoice_Date, 'MM/dd/yyy', 'en-US') AS 'Invoice Date',\n" +
                    "js.Job_Size AS 'Job Size',\n" +
                    "jst.Job_Status AS 'Job Status',\n" +
                    "jt.Job_Type AS 'Job Type'\n" +
                    "\n" +
                    "FROM (JOB j\n" +
                    "JOIN STATE_TERRITORY st ON st.State_ID = j.State_ID\n" +
                    "JOIN COUNTRY c ON c.Country_ID = j.Country_ID\n" +
                    "JOIN JOB_SIZE js ON js.Job_Size_ID = j.Job_Size_ID\n" +
                    "JOIN JOB_STATUS jst ON jst.Job_Status_ID = j.Job_Status_ID\n" +
                    "JOIN CUSTOMER cust ON cust.Customer_ID = j.Customer_ID\n" +
                    "JOIN JOB_TYPE jt ON jt.Job_Type_ID = j.Job_Type_ID)\n" +
                    "\n" +
                    "WHERE (jst.Job_Status_ID = 4 \n" +
                    "AND j.Quote_Date IS NULL \n" +
                    "AND j.Quote_Expiration_Date IS NULL\n" +
                    "AND j.Job_Start_Date IS NULL\n" +
                    "AND j.Job_End_Date IS NULL);\n";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport,null,con);
            JasperViewer.viewReport(jprint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleJobSchedulingPendingButton(ActionEvent event){
        db = new DBConnection();
        try {
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\jobsNeedingScheduling.jrxml");
            String SQL = "SELECT \n" +
                    "j.Job_Number AS 'Job Number',\n" +
                    "CONCAT(cust.Customer_First_Name,' ', cust.Customer_Last_Name) AS 'Customer',\n" +
                    "j.Job_Street_Address AS 'Address',\n" +
                    "j.Job_City AS 'City',\n" +
                    "st.State_Name AS 'State',\n" +
                    "j.Job_Zipcode AS 'Zipcode',\n" +
                    "c.Country_Abbreviation AS 'Country',\n" +
                    "FORMAT (j.Job_Start_Date, 'MM/dd/yyy', 'en-US') AS 'Start Date',\n" +
                    "FORMAT (j.Job_End_Date, 'MM/dd/yyy', 'en-US') AS 'End Date',\n" +
                    "FORMAT (j.Quote_Date, 'MM/dd/yyy', 'en-US') AS 'Quote Date',\n" +
                    "FORMAT (j.Quote_Expiration_Date, 'MM/dd/yyy', 'en-US') AS 'Quote Exp Date',\n" +
                    "FORMAT (j.Invoice_Date, 'MM/dd/yyy', 'en-US') AS 'Invoice Date',\n" +
                    "js.Job_Size AS 'Job Size',\n" +
                    "jst.Job_Status AS 'Job Status',\n" +
                    "jt.Job_Type AS 'Job Type'\n" +
                    "\n" +
                    "FROM (JOB j\n" +
                    "JOIN STATE_TERRITORY st ON st.State_ID = j.State_ID\n" +
                    "JOIN COUNTRY c ON c.Country_ID = j.Country_ID\n" +
                    "JOIN JOB_SIZE js ON js.Job_Size_ID = j.Job_Size_ID\n" +
                    "JOIN JOB_STATUS jst ON jst.Job_Status_ID = j.Job_Status_ID\n" +
                    "JOIN CUSTOMER cust ON cust.Customer_ID = j.Customer_ID\n" +
                    "JOIN JOB_TYPE jt ON jt.Job_Type_ID = j.Job_Type_ID)\n" +
                    "\n" +
                    "WHERE (j.Job_Status_ID = 12\n" +
                    "AND j.Job_Start_Date IS NULL\n" +
                    "AND j.Job_End_Date IS NULL);\n";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport,null,con);
            JasperViewer.viewReport(jprint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleJobSummaryButton(ActionEvent event){
        db = new DBConnection();
        try {
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\jobSummary.jrxml");
            String SQL = "SELECT DISTINCT\n" +
                    "ts3.Job_Number AS 'Job ID',\n" +
                    "CONCAT(c.Customer_First_Name, ' ', c.Customer_Last_Name) AS 'Customer Name',\n" +
                    "j.Job_City AS 'City',\n" +
                    "st.State_Name AS 'State',\n" +
                    "co.Country_Abbreviation AS 'Country',\n" +
                    "FORMAT (CAST((ts3.[Admin Overhead] * 1.00) AS DECIMAL (10,2)), 'C', 'en-US') AS 'Overhead',\n" +
                    "FORMAT (ts3.[Labor Cost], 'C', 'en-US') AS 'Labor Cost',\n" +
                    "FORMAT (ts3.[Material Cost], 'C', 'en-US') AS 'Material Cost',\n" +
                    "FORMAT ((ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]), 'C', 'en-US') AS 'Total Cost',\n" +
                    "FORMAT (p.Original_Total_Due - (ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]), 'C', 'en-US') AS 'Profit',\n" +
                    "FORMAT (((p.Original_Total_Due - (ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]))/p.Original_Total_Due), 'P') AS 'Profit Margin',\n" +
                    "jt.Job_Type AS 'Job Type'\n" +
                    "\n" +
                    "FROM \n" +
                    "CUSTOMER c\n" +
                    "JOIN JOB j ON c.Customer_ID = j.Customer_ID\n" +
                    "JOIN STATE_TERRITORY st ON st.State_ID = j.State_ID\n" +
                    "JOIN COUNTRY co ON co.Country_ID = j.Country_ID\n" +
                    "JOIN PAYMENT p ON p.Job_Number = j.Job_Number\n" +
                    "JOIN JOB_TYPE jt ON jt.Job_Type_ID = j.Job_Type_ID\n" +
                    "INNER JOIN\n" +
                    "(\n" +
                    "SELECT\n" +
                    "ts2.Job_Number,\n" +
                    "SUM(ts2.[Admin Overhead]) AS 'Admin Overhead',\n" +
                    "SUM(ts2.[Labor Cost] * 15.00) AS 'Labor Cost',\n" +
                    "SUM(ts2.[Material Cost]) AS 'Material Cost'\n" +
                    "\n" +
                    "FROM (\n" +
                    "SELECT\n" +
                    "j.Job_Number,\n" +
                    "ts.Timesheet_ID,\n" +
                    "ts.Jobs_Worked AS 'Labor Cost',\n" +
                    "ts.Hours_Worked AS 'Admin Overhead',\n" +
                    "jtl.Job_Task_Line_Cost AS 'Material Cost'\n" +
                    "\n" +
                    "FROM(\n" +
                    "JOB_TIMESHEET jts\n" +
                    "JOIN TIMESHEET ts ON ts.Timesheet_ID = jts.Timesheet_ID\n" +
                    "JOIN JOB j ON j.Job_Number = jts.Job_Number\n" +
                    "JOIN JOB_TASK_LINE jtl ON jtl.Job_Number = j.Job_Number\n" +
                    "JOIN CUSTOMER c ON j.Customer_ID = c.Customer_ID)\n" +
                    ")ts2\n" +
                    "\n" +
                    "GROUP BY ts2.Job_Number\n" +
                    ")AS ts3\n" +
                    "ON ts3.Job_Number = j.Job_Number\n" +
                    "\n" +
                    "ORDER BY ts3.Job_Number\n";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport,null,con);
            JasperViewer.viewReport(jprint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    protected void daysToCompletionReportButton(ActionEvent event){
        db = new DBConnection();
        try {
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\employeeIncidentResolved.jrxml");
            String SQL = "SELECT \n" +
                    "j.Job_Number AS 'Job Number',\n" +
                    "CONCAT(cust.Customer_First_Name,' ', cust.Customer_Last_Name) AS 'Customer',\n" +
                    "j.Job_Street_Address AS 'Address',\n" +
                    "j.Job_City AS 'City',\n" +
                    "st.State_Name AS 'State',\n" +
                    "j.Job_Zipcode AS 'Zipcode',\n" +
                    "c.Country_Abbreviation AS 'Country',\n" +
                    "FORMAT (j.Job_Start_Date, 'MM/dd/yyy', 'en-US') AS 'Start Date',\n" +
                    "FORMAT (j.Job_End_Date, 'MM/dd/yyy', 'en-US') AS 'End Date',\n" +
                    "js.Job_Size AS 'Job Size',\n" +
                    "jst.Job_Status AS 'Job Status',\n" +
                    "jt.Job_Type AS 'Job Type',\n" +
                    "DATEDIFF(day, j.Job_Start_Date, j.Job_End_Date) AS 'Job Length'\n" +
                    "\n" +
                    "FROM (JOB j\n" +
                    "JOIN STATE_TERRITORY st ON st.State_ID = j.State_ID\n" +
                    "JOIN COUNTRY c ON c.Country_ID = j.Country_ID\n" +
                    "JOIN JOB_SIZE js ON js.Job_Size_ID = j.Job_Size_ID\n" +
                    "JOIN JOB_STATUS jst ON jst.Job_Status_ID = j.Job_Status_ID\n" +
                    "JOIN CUSTOMER cust ON cust.Customer_ID = j.Customer_ID\n" +
                    "JOIN JOB_TYPE jt ON jt.Job_Type_ID = j.Job_Type_ID)\n" +
                    "\n" +
                    "WHERE j.Job_Status_ID = 1;\n";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport,null,con);
            JasperViewer.viewReport(jprint,false);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    protected void outstandingPaymentButton(ActionEvent event) {
        db = new DBConnection();
        try {
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\employeeIncidentResolved.jrxml");
            String SQL = "SELECT  \n" +
                    "j.Job_Number AS 'Job Number',\n" +
                    "j.Customer_ID AS 'Customer ID',\n" +
                    "FORMAT (j.Job_Start_Date, 'MM/dd/yyy', 'en-US') AS 'Start Date',\n" +
                    "FORMAT (j.Job_End_Date, 'MM/dd/yyy', 'en-US') AS 'End Date',\n" +
                    "FORMAT (p.Original_Total_Due, 'C', 'en-US') AS 'Original Total',\n" +
                    "FORMAT (p.Payment_Remaining, 'C', 'en-US') AS 'Payment Remaining',\n" +
                    "p.Payment_Terms AS 'Payment Terms'\n" +
                    "\n" +
                    "FROM (JOB j\n" +
                    "JOIN PAYMENT p ON p.Job_Number = j.Job_Number\n" +
                    "JOIN JOB_STATUS js ON js.Job_Status_ID = j.Job_Status_ID)\n" +
                    "\n" +
                    "WHERE (js.Job_Status = 'Pending Payment' AND Payment_Remaining > 0.00);\n";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport, null, con);
            JasperViewer.viewReport(jprint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleInvoiceButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("invoiceFilter.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();
    }
    @FXML
    protected void handleQuoteButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("quoteFilter.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }
}
