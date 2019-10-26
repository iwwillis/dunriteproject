package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String URL = "jdbc:sqlserver://localhost;databaseName =Test3365";
        String username = "sa";
        String password ="root";
        Connection con =null;
        Statement st = null;
        ResultSet rs = null;

        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("Welcome to Dunrite!");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL,username,password);

            String SQL = "INSERT INTO PAYMENT(Payment_Date,Payment_Amount,Payment_Remaining,Payment_Terms,Payment_Method,Job_Number,Payment_Method_ID)" +
                    "            VALUES ('9/21/2016',51.56,251.71,'Advance','Credit',543,12)," +
                    "                    ('10/4/2019',150.50,1056.82,'Advance','Cash',643,34); ";

            st = con.createStatement();
            st.execute(SQL);


        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
