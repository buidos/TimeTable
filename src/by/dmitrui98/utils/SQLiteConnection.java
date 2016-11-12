package by.dmitrui98.utils;

import java.sql.*;

/**
 * Created by Администратор on 28.10.2016.
 */
public class SQLiteConnection {

    private static Connection con; // хранит соединение с базой данных

    public static Connection getConnection(String url, String driverName) {

        try {
            // динамическая регистрация драйвера sqlite
            Driver d = (Driver) Class.forName(driverName).newInstance();

            if (con == null)
                con = DriverManager.getConnection(url);

            return con;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
