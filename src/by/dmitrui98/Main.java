package by.dmitrui98;

import by.dmitrui98.gui.TimetableNorthPanel;
import by.dmitrui98.gui.TimetableMenuBar;
import by.dmitrui98.utils.SQLiteConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;

public class Main {
    public static final int HOUR_WIDTH = 10;
    public static final int COUNT_PAIR = 7;

    Connection con;

    public static void main(String[] args) {
        Main pr = new Main();
        pr.go();
    }

    public void go() {
        JFrame frame = new JFrame("Составляем расписание");

        TimetableMenuBar menuBar = new TimetableMenuBar();

        try {
            // создание подключения к базе данных по пути, указанному в урле
            String url = "jdbc:sqlite:d:\\курсачJAVA\\1\\timetable1.db";
            con = SQLiteConnection.getConnection(url, "org.sqlite.JDBC");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        TimetableNorthPanel northPanel = new TimetableNorthPanel(frame, con);

        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new MyWindowListener());

        frame.pack();
        frame.setVisible(true);
    }

    private class MyWindowListener implements WindowListener {

        @Override
        public void windowClosing(WindowEvent e) {
//            Object[] options = { "Да", "Нет!" };
//            int n = JOptionPane.showOptionDialog(e.getWindow(), "Закрыть окно?",
//                            "Подтверждение", JOptionPane.YES_NO_OPTION,
//                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//            if (n == 0) {
//                e.getWindow().setVisible(false);


                try {
                    if (con != null)
                        con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.exit(0);
            //}
        }

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }
    }
}
