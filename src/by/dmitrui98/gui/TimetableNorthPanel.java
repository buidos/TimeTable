package by.dmitrui98.gui;

import by.dmitrui98.Main;
import by.dmitrui98.data.TeacherColumn;
import by.dmitrui98.data.WorkingTeacher;
import by.dmitrui98.tableModel.CheckModel;
import by.dmitrui98.utils.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;


/**
 * Created by Администратор on 24.10.2016.
 */
public class TimetableNorthPanel extends JPanel {
    Main m;

    private JFrame groupFrame = new JFrame("Отображаемые группы");

    private ArrayList <JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    private ArrayList <Integer> listGroup = new ArrayList<Integer>();

    private CheckModel groupModel = new CheckModel();
    private boolean isChangedCriteria;

    private ArrayList <TeacherColumn> teacherColumns;
    private ArrayList<WorkingTeacher> workingTeachers;
    private JFrame frame;
    private Connection con;

    private JButton btnShow;

    public TimetableNorthPanel(Main m) {
        this.m = m;
        con = m.getCon();
        frame = m.getFrame();


        teacherColumns = m.getTeacherColumns();
        workingTeachers = m.getWorkingTeachers();

        buildGUI();
    }

    private void buildGUI() {

        createCheckBoxes();

        createButtons();

        groupFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        groupFrame.setSize(300,300);
    }

    private void createButtons() {
        JButton btnDisplayGroup = new JButton("Отображаемые группы");
        btnDisplayGroup.addActionListener(new ButtonDisplayGroupListener());
        this.add(btnDisplayGroup);

        btnShow = new JButton("Показать");
        btnShow.addActionListener(new ButtonShowListener());
        this.add(btnShow);
    }

    private void createCheckBoxes() {
        JCheckBox checkBox;
        MyChangeListener changeListener = new MyChangeListener();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Установите необходимые критерии:"));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        checkBox = new JCheckBox("1 курс");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 0;
        c.gridy = 1;
        checkBox = new JCheckBox("2 курс");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 1;
        c.gridy = 0;
        checkBox = new JCheckBox("3 курс");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 1;
        c.gridy = 1;
        checkBox = new JCheckBox("4 курс");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 2;
        c.gridy = 0;
        checkBox = new JCheckBox("радио");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 2;
        c.gridy = 1;
        checkBox = new JCheckBox("комп");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 3;
        c.gridy = 0;
        checkBox = new JCheckBox("моб");
        panel.add(checkBox, c);
        checkBox.addActionListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 4;
        c.gridy = 0;
        checkBox = new JCheckBox("пн");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 4;
        c.gridy = 1;
        checkBox = new JCheckBox("вт");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 5;
        c.gridy = 0;
        checkBox = new JCheckBox("ср");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 5;
        c.gridy = 1;
        checkBox = new JCheckBox("чт");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 6;
        c.gridy = 0;
        checkBox = new JCheckBox("пт");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 6;
        c.gridy = 1;
        checkBox = new JCheckBox("сб");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        this.add(panel);
    }

    TableFactory tf;

    JScrollPane scrollerPred;
    private void showTable(ArrayList<TeacherColumn> teacherColumns) {
        ArrayList <Integer> dayCriteria = new ArrayList<>();
        for (int i = 7; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isSelected())
                dayCriteria.add(new Integer(i-7));
        }

        int colDay = dayCriteria.size();


        JScrollPane scroller = null;
        if (colDay > 0 && selectedGroup.size() > 0) {
            tf = new TableFactory(workingTeachers);

            JTable loadTable = tf.createLoad(selectedGroup, dayCriteria);
            JTable pairTable = tf.createPair(colDay);
            JTable dayTable = tf.createDay(dayCriteria, pairTable.getRowHeight());
            JTable teacherTable = tf.createTeacher(teacherColumns);


            JPanel mainPanel = new JPanel();

            mainPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            JTableHeader tableHeader = dayTable.getTableHeader();
            c.gridx = 0;
            c.gridy = 0;
            mainPanel.add(tableHeader, c);
            c.gridx = 0;
            c.gridy = 1;
            mainPanel.add(dayTable, c);

            tableHeader = pairTable.getTableHeader();
            c.gridx = 1;
            c.gridy = 0;
            //c.ipadx = 50;
            mainPanel.add(tableHeader, c);
            c.gridx = 1;
            c.gridy = 1;
            mainPanel.add(pairTable, c);

            tableHeader = loadTable.getTableHeader();
            c.gridx = 2;
            c.gridy = 0;
            //c.ipadx = 50;
            mainPanel.add(tableHeader, c);
            c.gridx = 2;
            c.gridy = 1;
            mainPanel.add(loadTable, c);

            c.gridx = 2;
            c.gridy = 2;
            mainPanel.add(teacherTable, c);

            scroller = new JScrollPane(mainPanel);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.getVerticalScrollBar().setUnitIncrement(16);


            frame.getContentPane().add(BorderLayout.CENTER, scroller);
        }

        if (scrollerPred != null) {
            frame.remove(scrollerPred);
        }

        frame.repaint();
        frame.revalidate();

        scrollerPred = scroller;
    }

    private class MyChangeListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            isChangedCriteria = true;

            listGroup.clear();
            ArrayList<Integer> courseCriteria = new ArrayList<Integer>();
            for (int i = 0; i < 4; i++) {
                if (checkBoxList.get(i).isSelected())
                    courseCriteria.add(new Integer(i + 1));
            }

            ArrayList<Integer> depCriteria = new ArrayList<Integer>();
            for (int i = 4; i < 7; i++) {
                if (checkBoxList.get(i).isSelected())
                    depCriteria.add(new Integer(i - 3));
            }

            try {

                Statement st = con.createStatement();
                ResultSet result = st.executeQuery("SELECT * FROM spr_group");

                // формируем список групп согласно критериям(отделение && курс)
                while (result.next()) {
                    Integer course = (Integer) result.getObject("course");
                    Integer dep = (Integer) result.getObject("department_id");
                    if ((courseCriteria.contains(course)) && (depCriteria.contains(dep)))
                        listGroup.add((Integer) result.getObject("value_"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            groupModel = new CheckModel(listGroup.size());
            groupFrame.dispose();
        }
    }

    private class ButtonDisplayGroupListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isChangedCriteria) {
                isChangedCriteria = false;

                groupFrame.getContentPane().removeAll();


                JTable table = new JTable(groupModel) {
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        JCheckBox jcb = (JCheckBox) super.prepareRenderer(renderer, row, column);
                        jcb.setText(String.valueOf(listGroup.get(row))); // задаем значение группы JCheckBox в ячейке
                        return jcb;
                    }
                };

                groupFrame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(table));

                groupFrame.revalidate();
                groupFrame.repaint();
            }
            groupFrame.setVisible(true);
        }
    }

    ArrayList <Integer> selectedGroup = new ArrayList<Integer>();
    class ButtonShowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            teacherColumns = m.getTeacherColumns();
            workingTeachers = m.getWorkingTeachers();


            TreeSet<Integer> sg = groupModel.getSelectedGroup();
            selectedGroup.clear();
            for (Integer gr : sg)
                selectedGroup.add(listGroup.get(gr));

            ArrayList<TeacherColumn> tcFilter = new ArrayList<TeacherColumn>();
            for (TeacherColumn tc : teacherColumns) {
                if (selectedGroup.contains(tc.getGroup()))
                    tcFilter.add(tc);
            }

            showTable(tcFilter);
        }
    }

    public JButton getBtnShow() {
        return btnShow;
    }

    public TableFactory getTf() {
        return tf;
    }
}
