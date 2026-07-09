package connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/qlbv_thucuc?useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root"; // Default xampp user
    private static final String PASSWORD = ""; // Default xampp password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Loi: Khong tim thay Driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Loi: Khong the ket noi toi database!");
            e.printStackTrace();
        }
        return conn;
    }
}
