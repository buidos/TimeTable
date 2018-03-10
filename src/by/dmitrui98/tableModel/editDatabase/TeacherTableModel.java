package by.dmitrui98.tableModel.editDatabase;

import by.dmitrui98.dao.TeacherDao;
import by.dmitrui98.entity.Teacher;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Администратор on 30.10.2016.
 */
public class TeacherTableModel extends AbstractTableModel {

    private Object[][] contents; //хранит данные
    private String[] columnNames;//хранит имена столбцов

    public TeacherTableModel(TeacherDao teacherDao) {
        super();
        List<Teacher> teachers = teacherDao.getAll();
        int countColumns = 4;

        contents = new Object[teachers.size()][countColumns];
        for (int i = 0; i < contents.length; i++) {
            contents[i][0] = teachers.get(i).getId();
            contents[i][1] = teachers.get(i).getSurname();
            contents[i][2] = teachers.get(i).getMiddlename();
            contents[i][3] = teachers.get(i).getLastname();
        }

        columnNames = new String[countColumns];
        columnNames[0] = "id";
        columnNames[1] = "фамилия";
        columnNames[2] = "имя";
        columnNames[3] = "отчество";
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
}
