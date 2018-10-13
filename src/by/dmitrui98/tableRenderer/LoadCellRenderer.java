package by.dmitrui98.tableRenderer;

import by.dmitrui98.Main;
import by.dmitrui98.data.Pair;
import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.WorkingTeacher;
import by.dmitrui98.enums.TypeHour;
import by.dmitrui98.utils.TableFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Администратор on 25.11.2016.
 */
public class LoadCellRenderer extends DefaultTableCellRenderer {
    TableFactory tb;


    public LoadCellRenderer(TableFactory tb) {
        this.tb = tb;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(Color.WHITE);
        c.setFont(new Font("TimesRoman", Font.ITALIC, 13));

        JLabel l = (JLabel) c;
        l.setToolTipText(format(table, value, row, column));


        int teacherCol;
        if (tb.getTeacherCol() != -1)
            teacherCol = tb.getTeacherCol() / 2;
        else
            teacherCol = -1;

        String groupCol = (String) table.getTableHeader().getColumnModel().getColumn(column).getHeaderValue();

        if (value instanceof WorkingTeacher) {
            WorkingTeacher wt = (WorkingTeacher) value;
            if (wt.getPair(tb.defineRow(row), groupCol).getTypeHour() == TypeHour.COMBO) {
                c.setBackground(Color.RED);
                return c;
            }
            else
                c.setBackground(Color.YELLOW);
        } else if (value instanceof ArrayList) {
            c.setBackground(Color.RED);
            return c;
        } else if (teacherCol == column) {
            c.setBackground(Color.GREEN);
        }

        if (teacherCol == column) {
            Teacher selectedTeacher = tb.getSelectedTeacher();
            if (selectedTeacher != null) {
                boolean fl = false;
                for (WorkingTeacher wt : tb.getWorkingTeachers())
                    if (Main.isThere(wt.getNames(), selectedTeacher.getSurnames())) {

                        for (Pair p : wt.getPairs())
                            if (p.getRow() == tb.defineRow(row))
                                if ((p.getTypeHour() == TypeHour.COMBO) || fl) {
                                    c.setBackground(Color.RED);
                                    break;
                                }
                                else {
                                    c.setBackground(Color.YELLOW);
                                    fl = true;
                                }
                    }
            }
        }

        return c;
    }



    private String format(JTable table, Object value, int r, int c) {
        int row = tb.defineRow(r);

        String group = (String) table.getTableHeader().getColumnModel().getColumn(c).getHeaderValue();

        if (value instanceof WorkingTeacher) {
           WorkingTeacher wt = (WorkingTeacher) value;
           Pair p = wt.getPair(row, group);
           return Main.format(wt.getNames().toString(), p.getTypeHour());
       } else if (value instanceof ArrayList) {
           ArrayList<WorkingTeacher> l = (ArrayList<WorkingTeacher>) value;
           Pair p = l.get(0).getPair(row, group);
           return Main.format(l.get(0).getNames().toString(), l.get(1).getNames().toString(), p.getTypeHour());
       }
       return null;
    }

}
