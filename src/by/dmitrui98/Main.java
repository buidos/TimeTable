package by.dmitrui98;

import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.TeacherColumn;
import by.dmitrui98.data.WorkingTeacher;
import by.dmitrui98.enums.TypeHour;
import by.dmitrui98.gui.TimetableNorthPanel;
import by.dmitrui98.gui.TimetableMenuBar;
import by.dmitrui98.utils.SQLiteConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static final int HOUR_WIDTH = 10;
    public static final int COUNT_PAIR = 7;

    private Connection con;
    private JFrame frame;

    private ArrayList <TeacherColumn> teacherColumns = new ArrayList<>();
    private ArrayList<WorkingTeacher> workingTeachers = new ArrayList<>();

    TimetableNorthPanel northPanel;

    public static void main(String[] args) {
        Main pr = new Main();
        pr.go();
    }

    public void go() {
        frame = new JFrame("Составляем расписание");

        connectToDatabase();

        readTeacherColumns();

        TimetableMenuBar menuBar = new TimetableMenuBar(this);

        northPanel = new TimetableNorthPanel(this);

        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new MyWindowListener());

        //frame.setLocationRelativeTo(null);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

    private class MyWindowListener implements WindowListener {

        @Override
        public void windowClosing(WindowEvent e) {
            Object[] options = { "Да", "Нет" };
            int n = JOptionPane.showOptionDialog(e.getWindow(), "Закрыть программу?",
                            "Несохраненные данные будут потеряны", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n == 0) {
                e.getWindow().setVisible(false);

                try {
                    if (con != null)
                        con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.exit(0);
            }
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

    public ArrayList<TeacherColumn> getTeacherColumns() {
        return teacherColumns;
    }

    public ArrayList<WorkingTeacher> getWorkingTeachers() {
        return workingTeachers;
    }

    public Connection getCon() {
        return con;
    }

    public JFrame getFrame() {
        return frame;
    }

    private void connectToDatabase() {
        // создание подключения к базе данных по пути, указанному в урле
        String url = "jdbc:sqlite:timetable1.db";
        con = SQLiteConnection.getConnection(url, "org.sqlite.JDBC");
        // активируем каскадность во внешних ключах
        try {
            con.prepareStatement("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readTeacherColumns() {
        teacherColumns.clear();

        try {
            Statement st = con.createStatement();
            ResultSet allGroup = st.executeQuery("SELECT * FROM spr_group");
            ArrayList<String> listGroup = new ArrayList<>();
            while (allGroup.next())
                listGroup.add((String) allGroup.getObject("value_"));

            for (String group : listGroup) {
                ArrayList<Teacher> teacherRowList = new ArrayList<Teacher>();

                ResultSet result = st.executeQuery("SELECT * FROM mainTable WHERE group_value = " + group + " ORDER BY id");

                int id1, id = -1;
                Teacher t = new Teacher();
                while (result.next()) {
                    id1 = id;
                    id = (int) result.getObject("id");

                    if (id1 != id) {
                        t = new Teacher((String) result.getObject("surname"), (int) result.getObject("load"));
                        teacherRowList.add(t);
                    } else {
                        t.add((String) result.getObject("surname"));
                    }

                }
                teacherColumns.add(new TeacherColumn(teacherRowList, group));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setTeacherColumns(ArrayList<TeacherColumn> teacherColumns) {
        this.teacherColumns = teacherColumns;
    }

    public void setWorkingTeachers(ArrayList<WorkingTeacher> workingTeachers) {
        this.workingTeachers = workingTeachers;
    }

    public TimetableNorthPanel getNorthPanel() {
        return northPanel;
    }

    public static String format(String str, TypeHour th) {
        StringBuilder sb = new StringBuilder(str);

        if (th == TypeHour.NUMERATOR)
            sb.append("(числ)");
        if (th == TypeHour.DENOMINATOR)
            sb.append("(знам)");
        if (th == TypeHour.COMBO)
            sb.append("(2 часа)");
        return sb.toString();
    }

    public static String format(String one, String two, TypeHour th) {
        StringBuffer sb = new StringBuffer(two);
        //sb.delete(sb.indexOf("("), sb.length());
        two = sb.toString();

        if (th == TypeHour.NUMERATOR) {
            return one + "(числ)|" + two + "(знам)";
        } else if (th == TypeHour.DENOMINATOR) {
            return two + "(числ)|" + one + "(знам)";
        }
        return null;
    }

    public static boolean isThere(ArrayList<String> a, ArrayList<String> b) {
        for (String name : a) {
            for (String n : b) {
                if (name.equals(n))
                    return true;
            }
        }
        return false;
    }
}
