package by.dmitrui98.utils;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Администратор on 28.10.2016.
 */
public class MyTableModel extends AbstractTableModel {

    private Object[][] contents; //хранит данные
    private String[] columnNames; //хранит имена столбцов
    private Class[] columnClasses; //хранит типы столбцов

    public MyTableModel(Connection con, String tableName) throws SQLException {
        super();
        getTableContents(con, tableName);
    }

    public MyTableModel() {
        super();
    }

    private void getTableContents(Connection con, String tableName) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();

        ResultSet rs = meta.getColumns(null, null, tableName, null); // получить метаданные по столбцам

        ArrayList colNamesList = new ArrayList(); // список имен столбцов
        ArrayList colTypesList = new ArrayList(); // список типов столбцов

        // цикл по всем  столбцам таблицы
        // для каждого столбца определить имя и тип
        while (rs.next()) {

            colNamesList.add(rs.getString("COLUMN_NAME")); // добавить в список имя столбца

            int dbType = rs.getInt("DATA_TYPE"); // определить тип столбца

            // выбрать нужный тип
            switch (dbType) {
                case Types.INTEGER:
                    colTypesList.add(Integer.class);
                    break;
                case Types.FLOAT:
                    colTypesList.add(Float.class);
                    break;
                case Types.DOUBLE:
                case Types.REAL:
                    colTypesList.add(Double.class);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    colTypesList.add(java.sql.Date.class);
                    break;
                default:
                    colTypesList.add(String.class);
                    break;
            }
        }

        // имена столбцов сохранить в отдельный массив
        columnNames = new String[colNamesList.size()];
        colNamesList.toArray(columnNames);

        // типы столбцов сохранить в отдельный массив
        columnClasses = new Class[colTypesList.size()];
        colTypesList.toArray(columnClasses);

        Statement statement = con.createStatement();
        rs = statement.executeQuery("SELECT * FROM " + tableName);

        ArrayList rowList = new ArrayList(); // хранит записи из таблицы

        // цикл по всем записям таблицы
        while (rs.next()) {
            ArrayList cellList = new ArrayList(); // хранит данные по каждому столбцу(ячейки)

            for (int i = 0; i < columnClasses.length; i++) {
                Object cellValue = null;

                if (columnClasses[i] == String.class) cellValue = rs.getString(columnNames[i]);
                else if (columnClasses[i] == Integer.class) cellValue = new Integer(rs.getInt(columnNames[i]));
                else if (columnClasses[i] == Double.class) cellValue = new Double(rs.getDouble(columnNames[i]));
                else if (columnClasses[i] == Float.class) cellValue = new Float(rs.getFloat(columnNames[i]));
                else if (columnClasses[i] == java.sql.Date.class) cellValue = rs.getDate(columnNames[i]);
                else System.out.println("Не могу определить тип поля" + columnNames[i]);

                cellList.add(cellValue);
            }

            Object[] cells = cellList.toArray();
            rowList.add(cells);
        }

        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = (Object[]) rowList.get(i);
        }

        if (rs != null)
            rs.close();

        if (statement != null)
            statement.close();
    }

    @Override
    public int getRowCount() {
        return contents.length;
    }

    @Override
    public int getColumnCount() {
        if (contents.length == 0) {
            return 0;
        } else {
            return contents[0].length;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return contents[rowIndex][columnIndex];
    }

    @Override
    public Class getColumnClass(int col) {
        return columnClasses[col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        }

        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contents[rowIndex][columnIndex] = aValue;
    }


}
