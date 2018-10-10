package by.dmitrui98.tableModel;

import by.dmitrui98.Main;
import by.dmitrui98.data.Pair;
import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.TeacherColumn;
import by.dmitrui98.data.WorkingTeacher;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Администратор on 30.10.2016.
 */
public class LoadTableModel extends AbstractTableModel {

    private Object[][] contents; //хранит данные
    private String[] columnNames;//хранит имена столбцов

    ArrayList<Integer> dayCriteria;

    public LoadTableModel(ArrayList<WorkingTeacher> workingTeachers, ArrayList<Integer> dayCriteria,
                          ArrayList<String> selectedGroup) {
        super();

        this.dayCriteria = dayCriteria;
        int row = dayCriteria.size() * Main.COUNT_PAIR;

        contents = new Object[row][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = new Object[selectedGroup.size()];
            for (int j = 0; j < contents[i].length; j++)
                contents[i][j] = "";
        }

        columnNames = new String[selectedGroup.size()];
        for (int i = 0; i < selectedGroup.size(); i++) {
            columnNames[i] = selectedGroup.get(i);
        }

        for (WorkingTeacher wt : workingTeachers) {
            for (Pair p : wt.getPairs()) {
                int r = defineRow(p.getRow());
                if (r != -1) {
                    int c = selectedGroup.indexOf(p.getGroup());
                    // если группа есть в отображаемой таблице
                    if (c != -1) {
                        if (!contents[r][c].equals("")) {
                            ArrayList<WorkingTeacher> list = new ArrayList<WorkingTeacher>();
                            list.add((WorkingTeacher) contents[r][c]);
                            list.add(wt);
                            contents[r][c] = list;
                        } else
                            contents[r][c] = wt;
                    }
                }
            }
        }
    }

    public int getRowCount() {
        return contents.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int column) {
        return contents[row][column];
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contents[rowIndex][columnIndex] = aValue;
    }

    // определяем фактическую строку
    private int defineRow(int row) {

        int day = row / Main.COUNT_PAIR;
        int pair = row % Main.COUNT_PAIR;
        for (int i = 0; i < dayCriteria.size(); i++) {
            if (day == dayCriteria.get(i)) {
                return i * Main.COUNT_PAIR + pair;
            }
        }

        return -1;

    }
}
