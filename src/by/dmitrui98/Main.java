package by.dmitrui98;

import by.dmitrui98.gui.TimetableMainPanel;
import by.dmitrui98.gui.TimetableMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    JButton button;

    public static void main(String[] args) {
        Main pr = new Main();
        pr.go();
    }

    public void go() {
        JFrame frame = new JFrame("Составляем расписание");

        // создаем главное меню
        TimetableMenuBar menuBar = new TimetableMenuBar();

        TimetableMainPanel mainPanel = new TimetableMainPanel();

        button = new JButton("Подключиться к БД");
        button.addActionListener(new MyActionListener());

        JTable table = new JTable(10, 5);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scroller = new JScrollPane(table);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.NORTH, mainPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scroller);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void connectToDatabase() {
        Connection con = null; // хранит соединение с базой данных
        Statement st = null; // хранит и выполняет sql запросы
        ResultSet res = null; // получает результаты выполнения sql запросов

        try {
            // динамическая регистрация драйвера sqlite
            Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
            // DriverManager.registerDriver(d);

            // создание подключения к базе данных по пути, указанному в урле
            String url = "jdbc:sqlite:d:\\курсачJAVA\\1\\timetable1.db";
            con = DriverManager.getConnection(url);

            // подготовка SQL запроса
            String sql = "SELECT * FROM spr_teacher";
            st = con.createStatement();

            // выполнение SQL запроса
            res = st.executeQuery(sql);

            while (res.next()) {
                System.out.println(res.getObject("surname") + " - " + res.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) res.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (Exception ex) {

            }
        }
    }

    class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            button.setText("Подключение к базе данных...");
            connectToDatabase();
        }
    }
}
