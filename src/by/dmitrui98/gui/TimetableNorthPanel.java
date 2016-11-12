package by.dmitrui98.gui;

import by.dmitrui98.Main;
import by.dmitrui98.data.TeacherData;
import by.dmitrui98.utils.CheckModel;
import by.dmitrui98.utils.LoadTableModel;
import by.dmitrui98.utils.PairTableModel;
import by.dmitrui98.utils.TeacherTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
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
    private JFrame groupFrame = new JFrame("Отображаемые группы");
    private JFrame frame;

    private ArrayList <JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    private ArrayList <Integer> listGroup = new ArrayList<Integer>();

    private CheckModel groupModel = new CheckModel();
    private Connection con;
    private boolean isChangedCriteria;

    private ArrayList <TeacherData> colData = new ArrayList<TeacherData>();


    public TimetableNorthPanel(JFrame frame, Connection con) {
        this.con = con;
        this.frame = frame;

        buildGUI();
    }

    private void buildGUI() {

        createCheckBoxes();

        createButtons();

        groupFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        groupFrame.setSize(300,300);
    }

    class ButtonDisplayGroupListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isChangedCriteria) {
                isChangedCriteria = false;

                listGroup.clear();
                groupFrame.getContentPane().removeAll();

                ArrayList<Integer> courseCriteria = new ArrayList<Integer>();
                for (int i = 0; i < 4; i++) {
                    if (checkBoxList.get(i).isSelected())
                        courseCriteria.add(new Integer(i + 1));
                }

                ArrayList<Integer> depCriteria = new ArrayList<Integer>();
                for (int i = 4; i < 6; i++) {
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
                groupFrame.pack();
            }
            groupFrame.setVisible(true);
        }
    }

    ArrayList <Integer> selectedGroup = new ArrayList<Integer>();

    class ButtonShowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Нажали");
            TreeSet<Integer> sg = groupModel.getSelectedGroup();

            try {
                for (Integer gr : sg) {

                    ArrayList<String> teacherList = new ArrayList<String>();
                    ArrayList<Integer> loadList = new ArrayList<Integer>();


                    Statement st = con.createStatement();
                    ResultSet result = st.executeQuery("SELECT * FROM mainTable WHERE group_value = " + listGroup.get(gr) + " ORDER BY id");

                    int id1, id = -1;
                    int i = 0;
                    while (result.next()) {
                        id1 = id;
                        id = (int) result.getObject(1);
                        if (id1 != id) {
                            teacherList.add((String) result.getObject(2));
                            loadList.add((Integer) result.getObject(5));
                            i++;
                        } else {
                            teacherList.set(--i, teacherList.get(i)+" "+(String) result.getObject(2));
                        }

                    }
                    colData.add(new TeacherData(teacherList, loadList));
                    selectedGroup.add(listGroup.get(gr));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            showTable();
        }
    }


    private void showTable() {

        ArrayList <Integer> dayCriteria = new ArrayList<Integer>();
        for (int i = 6; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isSelected())
                dayCriteria.add(new Integer(i-6));
        }

        JTable loadTable = new JTable(new LoadTableModel(dayCriteria.size() * Main.COUNT_PAIR, selectedGroup));
        JTable pairTable = new JTable(new PairTableModel(dayCriteria.size() * Main.COUNT_PAIR));

        Object[] dayName = {"дни"};
        Object[][] days = new Object[dayCriteria.size()][];

        for (int i = 0; i < days.length; i++) {
            days[i] = new Object[] {getDay(dayCriteria.get(i))};
        }

        JTable dayTable = new JTable(days, dayName);
        dayTable.setRowHeight(pairTable.getRowHeight() * Main.COUNT_PAIR);


        JTable teacherTable = new JTable(new TeacherTableModel(colData));
        TableModel tModel = teacherTable.getModel();

        for (int i = 1; i < tModel.getColumnCount(); i+=2) {
            teacherTable.getColumnModel().getColumn(i).setPreferredWidth(Main.HOUR_WIDTH);
        }


        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JTableHeader tableHeader = dayTable.getTableHeader();
        c.gridx = 0;
        c.gridy = 0;
        //c.ipadx = 0;
        mainPanel.add(tableHeader, c);
        c.gridx = 0;
        c.gridy = 1;
        //c.ipadx = 0;
        mainPanel.add(dayTable, c);

        tableHeader = pairTable.getTableHeader();
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 50;
        mainPanel.add(tableHeader, c);
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(pairTable, c);

        tableHeader = loadTable.getTableHeader();
        c.gridx = 2;
        c.gridy = 0;
        c.ipadx = 50;
        mainPanel.add(tableHeader, c);
        c.gridx = 2;
        c.gridy = 1;
        mainPanel.add(loadTable, c);

        c.gridx = 2;
        c.gridy = 2;
        mainPanel.add(teacherTable, c);

        JScrollPane scroller = new JScrollPane(mainPanel);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(BorderLayout.CENTER, scroller);
        frame.repaint();
        frame.revalidate();
    }

    private String getDay(int index) {
        switch (index) {
            case 0 : return "ПН";
            case 1 : return "ВТ";
            case 2 : return "СР";
            case 3 : return "ЧТ";
            case 4 : return "ПТ";
            case 5 : return "СБ";
        }
        return null;
    }


    private void createButtons() {
        JButton btnDisplayGroup = new JButton("Отображаемые группы");
        btnDisplayGroup.addActionListener(new ButtonDisplayGroupListener());
        this.add(btnDisplayGroup);

        JButton btnShow = new JButton("Показать");
        btnShow.addActionListener(new ButtonShowListener());
        this.add(btnShow);
    }

    private void createCheckBoxes() {
        JCheckBox checkBox;
        MyChangeListener changeListener = new MyChangeListener();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        checkBox = new JCheckBox("1 курс");
        panel.add(checkBox, c);
        checkBox.addChangeListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 0;
        c.gridy = 1;
        checkBox = new JCheckBox("2 курс");
        panel.add(checkBox, c);
        checkBox.addChangeListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 1;
        c.gridy = 0;
        checkBox = new JCheckBox("3 курс");
        panel.add(checkBox, c);
        checkBox.addChangeListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 1;
        c.gridy = 1;
        checkBox = new JCheckBox("4 курс");
        panel.add(checkBox, c);
        checkBox.addChangeListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 2;
        c.gridy = 0;
        checkBox = new JCheckBox("радио");
        panel.add(checkBox, c);
        checkBox.addChangeListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 2;
        c.gridy = 1;
        checkBox = new JCheckBox("комп");
        panel.add(checkBox, c);
        checkBox.addChangeListener(changeListener);
        checkBoxList.add(checkBox);

        c.gridx = 3;
        c.gridy = 0;
        checkBox = new JCheckBox("пн");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 3;
        c.gridy = 1;
        checkBox = new JCheckBox("вт");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 4;
        c.gridy = 0;
        checkBox = new JCheckBox("ср");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 4;
        c.gridy = 1;
        checkBox = new JCheckBox("чт");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 5;
        c.gridy = 0;
        checkBox = new JCheckBox("пт");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        c.gridx = 5;
        c.gridy = 1;
        checkBox = new JCheckBox("сб");
        panel.add(checkBox, c);
        checkBoxList.add(checkBox);

        this.add(panel);
    }

    private class MyChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent ev) {
            isChangedCriteria = true;
        }
    }
}
