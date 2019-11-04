package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataAccessObject {
    private DBConnection database = new DBConnection();
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Connection connect;


    public ObservableList<Payment> getPaymentData(String query){
        ObservableList list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstmt = connect.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                list.add(new Payment(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                        rs.getDouble(4), rs.getDouble(5),rs.getString(6),rs.getString(7),
                        rs.getInt(8),rs.getInt(9)));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
