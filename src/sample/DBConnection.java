package sample;

import java.sql.*;

public class DBConnection {

    private final String URL = "jdbc:sqlserver://localhost;databaseName =CIS3365Testing";
    private final String username = "sa";
    private final String password = "root";
    private Connection con = null;

    public DBConnection() {

    }

    public Connection getConnection() throws SQLException {
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(URL, username, password);
        return con;
    }

    public void close(Connection connect, PreparedStatement pstmt, ResultSet rs) {
        try {
            if(connect != null)
                connect.close();
            if(pstmt != null)
                pstmt.close();
            if(rs != null)
                rs.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void close(Connection connect, PreparedStatement pstmt) {
        try {
            close(connect, pstmt, null);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void close(PreparedStatement pstmt) {
        try {
            close(null, pstmt, null);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}