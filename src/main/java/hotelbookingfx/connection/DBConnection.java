package hotelbookingfx.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection connection() {
        final String URL = "jdbc:mysql://localhost:3306/hotelMasterDB";
        final String USER = "root";
        final String PASSWORD = "9999";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database successfully");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Driver not found");
        }
        return connection;
    }
}