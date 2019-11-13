package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;

public class quoteFilterController {
    DBConnection db;
    Connection con;

    @FXML private TextField nameTF;
    @FXML private TextField jobIdTF;

    @FXML
    protected void searchNameInvoiceButton(ActionEvent event) {
        db = new DBConnection();
        try {
            String name = nameTF.getText();
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\Quote.jrxml");
            String SQL = "SELECT DISTINCT\n" +
                    "ts3.Job_Number AS 'Job ID',\n" +
                    "CONCAT(c.Customer_First_Name, ' ', c.Customer_Last_Name) AS 'Customer Name',\n" +
                    "c.Customer_Street_Address AS 'Address',\n" +
                    "CONCAT (j.Job_City, ', ',  st.State_Name, ' ', c.Customer_Zipcode) AS 'City, State and Zipcode',\n" +
                    "co.Country_Abbreviation AS 'Country',\n" +
                    "c.Customer_Phone_Number As 'Phone Number',\n" +
                    "c.Customer_Email_Address AS 'Email Address',\n" +
                    "CONVERT(VARCHAR, GETDATE(), 101) AS 'Date',\n" +
                    "(CONVERT(VARCHAR, (GETDATE() + 30), 101)) AS 'Quote Expiration Date',\n" +
                    "CONVERT(INT, GETDATE(), 101) AS 'Invoice Number',\n" +
                    "CONVERT(INT, GETDATE(), 101) AS 'Quote Number',\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "FORMAT (CAST((ts3.[Admin Overhead] * 1.00) AS DECIMAL (10,2)), 'C', 'en-US') AS 'Overhead',\n" +
                    "FORMAT (ts3.[Labor Cost], 'C', 'en-US') AS 'Labor Cost',\n" +
                    "jm.Material_Name AS 'Material Name',\n" +
                    "jm.Material_Quantity AS 'Quantity',\n" +
                    "FORMAT(CAST((jtl.Job_Task_Line_Cost/jm.Material_Quantity)AS DECIMAL(15,2)), 'C', 'en-US') AS 'Unit Price',\n" +
                    "FORMAT (ts3.[Material Cost], 'C', 'en-US') AS 'Material Cost',\n" +
                    "FORMAT ((ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]), 'C', 'en-US') AS 'Total Cost',\n" +
                    "FORMAT (p.Original_Total_Due - (ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]), 'C', 'en-US') AS 'Profit',\n" +
                    "FORMAT (((p.Original_Total_Due - (ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]))/p.Original_Total_Due), 'P') AS 'Profit Margin',\n" +
                    "jt.Job_Type AS 'Job Type'\n" +
                    "\n" +
                    "FROM\n" +
                    "CUSTOMER c\n" +
                    "JOIN JOB j ON c.Customer_ID = j.Customer_ID\n" +
                    "JOIN STATE_TERRITORY st ON st.State_ID = j.State_ID\n" +
                    "JOIN COUNTRY co ON co.Country_ID = j.Country_ID\n" +
                    "JOIN PAYMENT p ON p.Job_Number = j.Job_Number\n" +
                    "JOIN JOB_TYPE jt ON jt.Job_Type_ID = j.Job_Type_ID\n" +
                    "JOIN JOB_MATERIAL jm On jm.Job_Number = j.Job_Number\n" +
                    "JOIN JOB_TASK_LINE jtl ON jtl.Job_Number = j.Job_Number\n" +
                    "\n" +
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
                    "WHERE CONCAT (c.Customer_First_Name, ' ', c.Customer_Last_Name) = '"+name+"';";
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport, null, con);
            JasperViewer.viewReport(jprint, false);

            nameTF.clear();
            jobIdTF.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void searchJobInvoiceButton(ActionEvent event) {
        db = new DBConnection();
        try {
            int jobId = Integer.parseInt(jobIdTF.getText());
            con = db.getConnection();
            JasperDesign testDesign = JRXmlLoader.load("C:\\Users\\iwwil\\JaspersoftWorkspace\\MyReports\\Quote.jrxml");
            String SQL = "SELECT DISTINCT\n" +
                    "ts3.Job_Number AS 'Job ID',\n" +
                    "CONCAT(c.Customer_First_Name, ' ', c.Customer_Last_Name) AS 'Customer Name',\n" +
                    "c.Customer_Street_Address AS 'Address',\n" +
                    "CONCAT (j.Job_City, ', ',  st.State_Name, ' ', c.Customer_Zipcode) AS 'City, State and Zipcode',\n" +
                    "co.Country_Abbreviation AS 'Country',\n" +
                    "c.Customer_Phone_Number As 'Phone Number',\n" +
                    "c.Customer_Email_Address AS 'Email Address',\n" +
                    "CONVERT(VARCHAR, GETDATE(), 101) AS 'Date',\n" +
                    "(CONVERT(VARCHAR, (GETDATE() + 30), 101)) AS 'Quote Expiration Date',\n" +
                    "CONVERT(INT, GETDATE(), 101) AS 'Invoice Number',\n" +
                    "CONVERT(INT, GETDATE(), 101) AS 'Quote Number',\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "FORMAT (CAST((ts3.[Admin Overhead] * 1.00) AS DECIMAL (10,2)), 'C', 'en-US') AS 'Overhead',\n" +
                    "FORMAT (ts3.[Labor Cost], 'C', 'en-US') AS 'Labor Cost',\n" +
                    "jm.Material_Name AS 'Material Name',\n" +
                    "jm.Material_Quantity AS 'Quantity',\n" +
                    "FORMAT(CAST((jtl.Job_Task_Line_Cost/jm.Material_Quantity)AS DECIMAL(15,2)), 'C', 'en-US') AS 'Unit Price',\n" +
                    "FORMAT (ts3.[Material Cost], 'C', 'en-US') AS 'Material Cost',\n" +
                    "FORMAT ((ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]), 'C', 'en-US') AS 'Total Cost',\n" +
                    "FORMAT (p.Original_Total_Due - (ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]), 'C', 'en-US') AS 'Profit',\n" +
                    "FORMAT (((p.Original_Total_Due - (ts3.[Admin Overhead]+ts3.[Labor Cost]+ts3.[Material Cost]))/p.Original_Total_Due), 'P') AS 'Profit Margin',\n" +
                    "jt.Job_Type AS 'Job Type'\n" +
                    "\n" +
                    "FROM\n" +
                    "CUSTOMER c\n" +
                    "JOIN JOB j ON c.Customer_ID = j.Customer_ID\n" +
                    "JOIN STATE_TERRITORY st ON st.State_ID = j.State_ID\n" +
                    "JOIN COUNTRY co ON co.Country_ID = j.Country_ID\n" +
                    "JOIN PAYMENT p ON p.Job_Number = j.Job_Number\n" +
                    "JOIN JOB_TYPE jt ON jt.Job_Type_ID = j.Job_Type_ID\n" +
                    "JOIN JOB_MATERIAL jm On jm.Job_Number = j.Job_Number\n" +
                    "JOIN JOB_TASK_LINE jtl ON jtl.Job_Number = j.Job_Number\n" +
                    "\n" +
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
                    "ON ts3.Job_Number = "+jobId+"";
            System.out.println(jobId);
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            testDesign.setQuery(query);

            JasperReport jReport = JasperCompileManager.compileReport(testDesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport, null, con);
            JasperViewer.viewReport(jprint, false);

            nameTF.clear();
            jobIdTF.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleBackButton(ActionEvent event) throws IOException {
        nameTF.clear();
        jobIdTF.clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("reportsSubmenu.fxml"));
        Parent finalPageParent = loader.load();

        Scene viewFinalPageScene = new Scene(finalPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewFinalPageScene);
        window.show();

    }
}
