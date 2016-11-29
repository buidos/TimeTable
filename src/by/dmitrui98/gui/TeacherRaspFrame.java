package by.dmitrui98.gui;

import by.dmitrui98.Main;
import by.dmitrui98.data.Pair;
import by.dmitrui98.data.WorkingTeacher;
import by.dmitrui98.enums.TypeHour;
import by.dmitrui98.tableModel.TeacherRaspModel;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import static by.dmitrui98.Main.format;

/**
 * Created by Администратор on 28.11.2016.
 */
public class TeacherRaspFrame extends JFrame {
    private JTable table;
    private HintTextField tfMask;
    private ArrayList<WorkingTeacher> workingTeachers;

    private ArrayList<String> teachers;
    private JList list;

    public TeacherRaspFrame(String name, Main main) {
        super(name);

        workingTeachers = main.getWorkingTeachers();
        Connection con = main.getCon();

        TableModel tm = new TeacherRaspModel();
        table = new JTable(tm);

        table.getColumnModel().getColumn(0).setMaxWidth(40);
        for (int i = 1; i < tm.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setMinWidth(100);
        }

        JScrollPane scroller = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);


        teachers = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet allPrep = st.executeQuery("SELECT * FROM spr_teacher ORDER BY surname");

            while (allPrep.next())
                teachers.add((String) allPrep.getObject("surname"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        list = new JList(teachers.toArray());

        list.setSize(new Dimension(100, 300));

        list.addListSelectionListener(new MyListSelectionListener());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sc = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tfMask = new HintTextField("найти учителя");
        tfMask.addKeyListener(new MyKeyListener());
        tfMask.setMaximumSize(new Dimension(list.getWidth(), 30));

        panel.add(tfMask);
        panel.add(sc);

        this.getContentPane().add(BorderLayout.CENTER, scroller);
        this.getContentPane().add(BorderLayout.EAST, panel);

    }

    private class MyListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                JList l = (JList) e.getSource();
                String t = (String) l.getSelectedValue();
                if (t != null) {

                    TeacherRaspModel tm = (TeacherRaspModel) table.getModel();
                    tm.clear();

                    ArrayList<Pair> pairs = new ArrayList<Pair>();
                    for (WorkingTeacher wt : workingTeachers) {
                        if (wt.getNames().contains(t)) {
                            for (Pair p : wt.getPairs())
                                pairs.add(p);
                        }
                    }

                    for (Pair p : pairs) {
                        int r = getRow(p.getRow());
                        int c = getCol(p.getRow());

                        String group = Integer.toString(p.getGroup());
                        if (!table.getValueAt(r, c).equals("")) {
                            String one = (String) table.getValueAt(r,c);
                            table.setValueAt(format(group, one, p.getTypeHour()), r, c);
                        } else {

                            TypeHour th = p.getTypeHour();


                            table.setValueAt(format(group, th), r, c);
                        }
                    }
                    table.repaint();
                }
            }
        }
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            String str = tfMask.getText().toLowerCase();

            ArrayList<String> t = new ArrayList<>();
            for (String s : teachers) {

                if (s.toLowerCase().contains(str)) {
                    t.add(s);
                }
            }

            list.setModel(new ListModel() {
                private Object[] contents = t.toArray();

                @Override
                public int getSize() {
                    return contents.length;
                }

                @Override
                public Object getElementAt(int index) {
                    return contents[index];
                }

                @Override
                public void addListDataListener(ListDataListener l) {

                }

                @Override
                public void removeListDataListener(ListDataListener l) {

                }
            });
        }
    }


    int getRow(int row) {
        return row % Main.COUNT_PAIR;
    }

    int getCol(int row) {
        return row / Main.COUNT_PAIR + 1;
    }
}
