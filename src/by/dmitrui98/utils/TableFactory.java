package by.dmitrui98.utils;

import by.dmitrui98.Main;
import by.dmitrui98.data.Pair;
import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.TeacherColumn;
import by.dmitrui98.data.WorkingTeacher;
import by.dmitrui98.enums.TypeHour;
import by.dmitrui98.gui.LoadPopupMenu;
import by.dmitrui98.tableModel.*;
import by.dmitrui98.tableRenderer.LoadCellRenderer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Администратор on 17.11.2016.
 */
public class TableFactory {

    private JTable teacherTable;
    private JTable loadTable;

    private ArrayList<WorkingTeacher> workingTeachers;

    public TableFactory(ArrayList<WorkingTeacher> workingTeachers) {
        this.workingTeachers = workingTeachers;
    }

    public JTable createPair(int colDay) {
        JTable pairTable = new JTable(new PairTableModel(colDay * Main.COUNT_PAIR));
        pairTable.setRowSelectionAllowed(false);

        return pairTable;
    }

    private ArrayList<Integer> dayCriteria;
    public JTable createDay(ArrayList<Integer> dayCriteria, int heigh) {
        this.dayCriteria = dayCriteria;

        Object[] dayName = {"дни"};
        Object[][] days = new Object[dayCriteria.size()][];

        for (int i = 0; i < days.length; i++) {
            days[i] = new Object[] {getDay(dayCriteria.get(i))};
        }

        JTable dayTable = new JTable(new DayTableModel(days, dayName));
        dayTable.setRowSelectionAllowed(false);
        dayTable.setRowHeight(heigh * Main.COUNT_PAIR);

        return dayTable;
    }

    public String getDay(int index) {
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

    public JTable createTeacher(ArrayList<TeacherColumn> teacherColList) {
        teacherTable = new JTable(new TeacherTableModel(teacherColList));
        teacherTable.setColumnSelectionAllowed(true);

        teacherTable.addMouseListener(new TeacherMouseListener());
        teacherTable.getModel().addTableModelListener(new TeacherTableModelListener());


        TableModel tModel = teacherTable.getModel();

        for (int i = 1; i < tModel.getColumnCount(); i+=2) {
            teacherTable.getColumnModel().getColumn(i).setPreferredWidth(Main.HOUR_WIDTH);
        }

        return teacherTable;
    }

    public JTable createLoad(ArrayList<Integer> selectedGroup, ArrayList<Integer> dayCriteria) {
        loadTable = new JTable(new LoadTableModel(workingTeachers, dayCriteria, selectedGroup));
        loadTable.setRowSelectionAllowed(false);
        loadTable.setDefaultRenderer(Object.class, new LoadCellRenderer(this));

        loadTable.addMouseListener(new LoadMouseListener());

        return loadTable;
    }

    public boolean isEmptyCombo(int r) {
        ArrayList<String> names = selectedTeacher.getNames();
        int row = defineRow(r);
        // проверяем, чтобы учитель был не занят
        for (WorkingTeacher wt : workingTeachers) {
            if (wt.getNames().equals(names)) {
                ArrayList<Pair> pairs = wt.getPairs();
                for (Pair p : pairs) {
                    if (p.getRow() == row)
                        return false;
                }
            }
        }


        return true;
    }


    private int teacherCol = -1;
    private int teacherRow = -1;
    private Teacher selectedTeacher;
    private class TeacherMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                teacherRow = teacherTable.getSelectedRow();
                teacherCol = teacherTable.getSelectedColumn();

                try {
                    if (teacherCol % 2 != 0)
                        teacherCol--;

                    selectedTeacher = (Teacher) teacherTable.getValueAt(teacherRow, teacherCol);

                    teacherTable.setColumnSelectionInterval(teacherCol, teacherCol + 1);
                    teacherTable.setRowSelectionInterval(teacherRow, teacherRow);

                    loadTable.repaint();
                } catch (Exception ex) {

                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private TableFactory getThis() {
        return this;
    }


    private LoadPopupMenu popup;

    private int loadCol = -1;
    private int loadRow = -1;
    private class LoadMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                int row = loadTable.getSelectedRow();
                int col = loadTable.getSelectedColumn();
                int groupCol = Integer.parseInt((String) loadTable.getTableHeader().getColumnModel().getColumn(col).getHeaderValue());

                if (loadTable.getValueAt(row, col).equals("")) {
                    if ((selectedTeacher != null) && (teacherCol / 2 == col)) {

                        int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
                        if (load > 1) {
                            if (isEmptyCombo(row)) {

                                load -= 2;
                                teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

                                boolean fl = true;
                                for (WorkingTeacher wt : workingTeachers) {
                                    if (wt.getNames().equals(selectedTeacher.getNames())) {
                                        wt.addPair(new Pair(defineRow(row), groupCol, TypeHour.COMBO));
                                        loadTable.setValueAt(wt, row, col);
                                        fl = false;
                                    }
                                }

                                if (fl) {
                                    WorkingTeacher workingTeacher = new WorkingTeacher(selectedTeacher.getNames(),
                                            new Pair(defineRow(row), groupCol, TypeHour.COMBO));
                                    workingTeachers.add(workingTeacher);
                                    loadTable.setValueAt(workingTeacher, row, col);
                                }


                                loadTable.repaint();
                                teacherTable.repaint();
                            } else {
                                System.out.println("учитель в это время занят");
                            }
                        } else {
                            System.out.println("меньше 2");
                        }
                    }
                } else
                    System.out.println("ячейка занята");
            } else if (e.getButton() == MouseEvent.BUTTON3) {

                loadCol = loadTable.columnAtPoint(e.getPoint());
                loadRow = loadTable.rowAtPoint(e.getPoint());

                popup = new LoadPopupMenu(getThis());

                popup.show(loadTable, e.getX(), e.getY());

            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public int defineRow(int row) {
        // определяем действительную строку
        int r = row;
        int c = 0;
        while (r >= Main.COUNT_PAIR) {
            r -= Main.COUNT_PAIR;
            c++;
        }
        return r + dayCriteria.get(c) * Main.COUNT_PAIR;
    }

    private class TeacherTableModelListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            int col = e.getFirstRow();
            int row = e.getColumn();

            if (col % 2 != 0) {
                TableModel tm = (TableModel) e.getSource();
                int load = (int) tm.getValueAt(row, col);
                Teacher t = (Teacher) tm.getValueAt(row, col-1);
                t.setLoad(load);
            }
        }
    }


    public JTable getTeacherTable() {
        return teacherTable;
    }

    public JTable getLoadTable() {
        return loadTable;
    }

    public ArrayList<WorkingTeacher> getWorkingTeachers() {
        return workingTeachers;
    }

    public ArrayList<Integer> getDayCriteria() {
        return dayCriteria;
    }

    public int getTeacherCol() {
        return teacherCol;
    }

    public int getTeacherRow() {
        return teacherRow;
    }

    public Teacher getSelectedTeacher() {
        return selectedTeacher;
    }

    public int getLoadCol() {
        return loadCol;
    }

    public int getLoadRow() {
        return loadRow;
    }

    public LoadPopupMenu getPopup() {
        return popup;
    }
}
