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
        String URL = "jdbc:sqlserver://localhost;databaseName =CIS3365Testing";
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
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL,username,password);

            /*String SQL = "CREATE TABLE newTable (" +
                    "username Int)";

            st = con.createStatement();
            st.execute(SQL);*/


        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
