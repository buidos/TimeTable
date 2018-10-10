package by.dmitrui98.gui;

import by.dmitrui98.Main;
import by.dmitrui98.data.Pair;
import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.WorkingTeacher;
import by.dmitrui98.enums.TypeHour;
import by.dmitrui98.tableModel.TeacherTableModel;
import by.dmitrui98.utils.TableFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Администратор on 18.11.2016.
 */
public class LoadPopupMenu extends JPopupMenu {
    JTable teacherTable;
    JTable loadTable;

    private Teacher selectedTeacher;

    private int loadCol;
    private int loadRow;
    private int teacherCol;
    private int teacherRow;

    private ArrayList<Integer> dayCriteria;

    private ArrayList<WorkingTeacher> workingTeachers;

    private String groupCol;


    public LoadPopupMenu(TableFactory tf) {
        teacherTable = tf.getTeacherTable();
        loadTable = tf.getLoadTable();
        selectedTeacher = tf.getSelectedTeacher();
        loadCol = tf.getLoadCol();
        loadRow = tf.getLoadRow();
        teacherCol = tf.getTeacherCol();
        teacherRow = tf.getTeacherRow();
        dayCriteria = tf.getDayCriteria();
        workingTeachers = tf.getWorkingTeachers();

        groupCol = (String) loadTable.getTableHeader().getColumnModel().getColumn(loadCol).getHeaderValue();


        JMenuItem item = new JMenuItem("числитель");
        item.addActionListener(new NumActionListener());
        this.add(item);

        item = new JMenuItem("знаменатель");
        item.addActionListener(new DenomActionListener());
        this.add(item);

        item = new JMenuItem("del 2 часа");
        item.addActionListener(new DelComboActionListener());
        this.add(item);

        item = new JMenuItem("del числитель");
        item.addActionListener(new DelNumActionListener());
        this.add(item);

        item = new JMenuItem("del знаменатель");
        item.addActionListener(new DelDenomActionListener());
        this.add(item);

        JMenuItem[] items = new JMenuItem[this.getSubElements().length];
        for (int i = 0; i < items.length; i++) {
            items[i] = (JMenuItem) this.getSubElements()[i];
        }

        for (JMenuItem el : items) {
            el.setEnabled(false);
        }

        if (selectedTeacher != null) {

            if (loadCol == (teacherCol / 2)) {
                if (isEmpty(TypeHour.NUMERATOR)) {
                    items[0].setEnabled(true);
                }

                if (isEmpty(TypeHour.DENOMINATOR)) {
                    items[1].setEnabled(true);
                }
            }
        }

        if (canDel(TypeHour.COMBO))
            items[2].setEnabled(true);


        if (canDel(TypeHour.NUMERATOR))
            items[3].setEnabled(true);


        if (canDel(TypeHour.DENOMINATOR))
            items[4].setEnabled(true);

    }

    public boolean isEmpty(TypeHour typeHour) {
        ArrayList<String> names = selectedTeacher.getNames();
        int load = selectedTeacher.getLoad();
        int row = defineRow(loadRow);

        if (load < 1)
            return false;

        // проверяем, чтобы учитель был не занят
        for (WorkingTeacher wt : workingTeachers) {
            if (Main.isThere(wt.getNames(), names)) {
                ArrayList<Pair> pairs = wt.getPairs();
                for (Pair pair : pairs) {
                    if (pair.getRow() == row) {
                        TypeHour th = pair.getTypeHour();
                        if ((th.equals(TypeHour.COMBO)) || (th.equals(typeHour)))
                            return false;
                    }

                }
            }
        }

        // проверяем, чтобы данная ячейка была свободна
        Object o = loadTable.getValueAt(loadRow, loadCol);
        if (o instanceof WorkingTeacher){
            WorkingTeacher wt = (WorkingTeacher) o;
            boolean fl1 = (wt.getPair(defineRow(loadRow), groupCol, typeHour) != null);
            boolean fl2 = (wt.getPair(defineRow(loadRow), groupCol, TypeHour.COMBO) != null);
            if (fl1 || fl2)
                return false;
        } else if (o instanceof ArrayList)
            return false;

        return true;
    }

    public boolean canDel(TypeHour typeHour) {
        Object o = loadTable.getValueAt(loadRow,loadCol);
        if (o instanceof WorkingTeacher) {
            WorkingTeacher wt = (WorkingTeacher) o;
            Pair pair = wt.getPair(defineRow(loadRow), groupCol, typeHour);

            if (pair != null) {
                TypeHour th = pair.getTypeHour();

                if (th.equals(typeHour))
                    return true;
            }
        } else if (o instanceof ArrayList)
            if (typeHour == TypeHour.NUMERATOR || typeHour == TypeHour.DENOMINATOR)
                return true;

        return false;
    }

    private int defineRow(int row) {
        // определяем действительную строку
        int r = row;
        int c = 0;
        while (r >= Main.COUNT_PAIR) {
            r -= Main.COUNT_PAIR;
            c++;
        }
        return r + dayCriteria.get(c) * Main.COUNT_PAIR;
    }

    private class NumActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
            load--;
            teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

            boolean fl = true;
            WorkingTeacher workingTeacher = null;
            for (WorkingTeacher wt : workingTeachers) {
                if (wt.getNames().equals(selectedTeacher.getNames())) {
                    wt.addPair(new Pair(defineRow(loadRow), groupCol, TypeHour.NUMERATOR));
                    workingTeacher = wt;
                    fl = false;
                    break;
                }
            }

            if (fl) {
                workingTeacher = new WorkingTeacher(selectedTeacher.getNames(),
                        new Pair(defineRow(loadRow), groupCol, TypeHour.NUMERATOR));
                workingTeachers.add(workingTeacher);
            }

            Object o = loadTable.getValueAt(loadRow, loadCol);
            if ((o instanceof WorkingTeacher)) {
                ArrayList<WorkingTeacher> list = new ArrayList<WorkingTeacher>();
                list.add((WorkingTeacher) o);
                list.add(workingTeacher);
                loadTable.setValueAt(list, loadRow, loadCol);
            } else {
                loadTable.setValueAt(workingTeacher, loadRow, loadCol);
            }

            loadTable.repaint();
            teacherTable.repaint();
        }
    }

    private class DenomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
            load--;
            teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

            WorkingTeacher workingTeacher = null;
            boolean fl = true;
            for (WorkingTeacher wt : workingTeachers) {
                if (wt.getNames().equals(selectedTeacher.getNames())) {
                    wt.addPair(new Pair(defineRow(loadRow), groupCol, TypeHour.DENOMINATOR));
                    workingTeacher = wt;
                    fl = false;
                    break;
                }
            }

            if (fl) {
                workingTeacher = new WorkingTeacher(selectedTeacher.getNames(),
                        new Pair(defineRow(loadRow), groupCol, TypeHour.DENOMINATOR));
                workingTeachers.add(workingTeacher);
            }

            Object o = loadTable.getValueAt(loadRow, loadCol);
            if ((o instanceof WorkingTeacher)) {
                ArrayList<WorkingTeacher> list = new ArrayList<WorkingTeacher>();
                list.add((WorkingTeacher) o);
                list.add(workingTeacher);
                loadTable.setValueAt(list, loadRow, loadCol);
            } else {
                loadTable.setValueAt(workingTeacher, loadRow, loadCol);
            }

            loadTable.repaint();
            teacherTable.repaint();
        }
    }

    private class DelComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WorkingTeacher wt = (WorkingTeacher) loadTable.getValueAt(loadRow, loadCol);
            int teacherCol = loadCol * 2;
            int teacherRow = findTeacherRow(wt.getNames(), teacherCol);

            int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
            load += 2;
            teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

            loadTable.setValueAt("", loadRow, loadCol);

            wt.removePair(new Pair(defineRow(loadRow), groupCol, TypeHour.COMBO));

            loadTable.repaint();
            teacherTable.repaint();
        }
    }

    private class DelNumActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int teacherCol = loadCol * 2;

            Object o = loadTable.getValueAt(loadRow, loadCol);
            if (o instanceof WorkingTeacher) {
                WorkingTeacher wt = (WorkingTeacher) o;

                int teacherRow = findTeacherRow(wt.getNames(), teacherCol);

                int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
                load++;
                teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

                loadTable.setValueAt("", loadRow, loadCol);

                wt.removePair(new Pair(defineRow(loadRow), groupCol, TypeHour.NUMERATOR));
            } else if (o instanceof ArrayList) {
                ArrayList<WorkingTeacher> list = (ArrayList<WorkingTeacher>) o;

                for (int i = 0; i < list.size(); i++) {
                    WorkingTeacher wt = list.get(i);
                    if (wt.getPair(defineRow(loadRow), groupCol, TypeHour.NUMERATOR) != null) {

                        int teacherRow = findTeacherRow(wt.getNames(), teacherCol);
                        int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
                        load++;
                        teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

                        wt.removePair(new Pair(defineRow(loadRow), groupCol, TypeHour.NUMERATOR));
                    } else
                        loadTable.setValueAt(wt, loadRow, loadCol);
                }
            }

            loadTable.repaint();
            teacherTable.repaint();
        }
    }

    private int findTeacherRow(ArrayList<String> names, int teacherCol) {
        for (int r = 0; r < TeacherTableModel.ROW; r++) {
            Object o = teacherTable.getValueAt(r, teacherCol);
            if (o instanceof Teacher) {
                Teacher t = (Teacher) o;
                if (t.getNames().equals(names))
                    return r;
            }
        }
        System.out.println("имя не найдено: " + names.get(0));
        return -1;
    }

    private class DelDenomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int teacherCol = loadCol * 2;

            Object o = loadTable.getValueAt(loadRow, loadCol);
            if (o instanceof WorkingTeacher) {

                WorkingTeacher wt = (WorkingTeacher) o;
                int teacherRow = findTeacherRow(wt.getNames(), teacherCol);

                int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
                load++;
                teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

                loadTable.setValueAt("", loadRow, loadCol);

                wt.removePair(new Pair(defineRow(loadRow), groupCol, TypeHour.DENOMINATOR));
            } else if (o instanceof ArrayList) {
                ArrayList<WorkingTeacher> list = (ArrayList<WorkingTeacher>) o;

                for (int i = 0; i < list.size(); i++) {
                    WorkingTeacher wt = list.get(i);
                    if (wt.getPair(defineRow(loadRow), groupCol, TypeHour.DENOMINATOR) != null) {

                        int teacherRow = findTeacherRow(wt.getNames(), teacherCol);
                        int load = (int) teacherTable.getValueAt(teacherRow, teacherCol + 1);
                        load++;
                        teacherTable.setValueAt(load, teacherRow, teacherCol + 1);

                        wt.removePair(new Pair(defineRow(loadRow), groupCol, TypeHour.DENOMINATOR));
                    } else
                        loadTable.setValueAt(wt, loadRow, loadCol);
                }
            }
            loadTable.repaint();
            teacherTable.repaint();
        }
    }
}
