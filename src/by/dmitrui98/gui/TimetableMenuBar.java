package by.dmitrui98.gui;

import by.dmitrui98.Main;
import by.dmitrui98.data.TeacherColumn;
import by.dmitrui98.data.WorkingTeacher;

import by.dmitrui98.gui.editDatabase.EditDatabaseFrame;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by Администратор on 24.10.2016.
 */
public class TimetableMenuBar extends JMenuBar {
    private Main main;

    private JFrame frame;
    private File file;
    private ArrayList<TeacherColumn> teacherColumns;
    private ArrayList<WorkingTeacher> workingTeachers;

    private JFrame tFrame;

    public TimetableMenuBar(Main m) {
        main = m;

        frame = m.getFrame();
        teacherColumns = m.getTeacherColumns();
        workingTeachers = m.getWorkingTeachers();

        JMenu fileMenu = new JMenu("файл");
        JMenuItem saveMenuItem = new JMenuItem("сохранить");
        saveMenuItem.addActionListener(new SaveMenuListener());
        JMenuItem saveHowMenuItem = new JMenuItem("сохранить как");
        saveHowMenuItem.addActionListener(new SaveHowMenuListener());
        JMenuItem openMenuItem = new JMenuItem("открыть");
        openMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveHowMenuItem);
        fileMenu.add(openMenuItem);

        JMenu editMenu = new JMenu("редактировать");
        JMenuItem clearMenuItem = new JMenuItem("очистить");
        clearMenuItem.addActionListener(new ClearMenuListener());
        editMenu.add(clearMenuItem);

        JMenu serviceMenu = new JMenu("сервис");

        JMenuItem teacherTableMenuItem = new JMenuItem("расписание преподавателей");
        teacherTableMenuItem.addActionListener(new TeacherMenuListener());

        JMenuItem editDatabaseMenuItem = new JMenuItem("редактировать базу данных");
        editDatabaseMenuItem.addActionListener(new EditDatabaseMenuListener());

        JMenuItem excelMenuItem = new JMenuItem("вывести в excel");
        excelMenuItem.addActionListener(new ExcelMenuListener());

        JMenuItem referenceMenuItem = new JMenuItem("справка");
        referenceMenuItem.addActionListener(new ReferenceMenuListener());

        serviceMenu.add(teacherTableMenuItem);
        serviceMenu.add(editDatabaseMenuItem);
        serviceMenu.add(referenceMenuItem);
        serviceMenu.add(excelMenuItem);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(serviceMenu);
    }

    private File saveHow() {
        JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(frame);
        File file = fc.getSelectedFile();

        return file;
    }

    public void save() {
        try {

            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(teacherColumns);
            os.writeObject(workingTeachers);

            os.close();
            fileStream.close();

            JOptionPane.showMessageDialog(frame, "сохранение выполнено успешно", "сохранились", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void open(File f) {

        try {

            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream is = new ObjectInputStream(fileIn);

            teacherColumns = (ArrayList<TeacherColumn>) is.readObject();
            workingTeachers = (ArrayList<WorkingTeacher>) is.readObject();

//            for (TeacherColumn tc : teacherColumns) {
//                System.out.println(tc.getGroup()+":");
//                for (Teacher t : tc.getTeacherList())
//                    System.out.println(t.getNames() + " " + t.getLoad());
//            }


            is.close();
            fileIn.close();

            main.setTeacherColumns(teacherColumns);
            main.setWorkingTeachers(workingTeachers);

            main.getNorthPanel().getBtnShow().doClick();
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "формат файла не соответствует", "несоответствие", JOptionPane.ERROR_MESSAGE);
        }

    }


    class SaveMenuListener implements ActionListener {
        public  void actionPerformed(ActionEvent e) {
            if (file != null) {
                save();
            } else {
                file = saveHow();
                if (file != null)
                    save();
            }

        }
    }

    class SaveHowMenuListener implements ActionListener {
        public  void actionPerformed(ActionEvent e) {
            file = saveHow();
            if (file != null)
                save();
        }
    }

    class OpenMenuListener implements ActionListener {
        public  void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(frame);

            File f = fc.getSelectedFile();
            if (f != null)
                open(f);
        }
    }

    class ClearMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            workingTeachers.clear();
            main.readTeacherColumns();
            main.getNorthPanel().getBtnShow().doClick();
        }
    }

    class TeacherMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            tFrame = new TeacherRaspFrame("Расписание преподавателей", main);

            tFrame.setSize(new Dimension(900, 400));
            tFrame.setVisible(true);
        }
    }

    class EditDatabaseMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new EditDatabaseFrame("Редактирование базы данных", main);


            frame.setSize(new Dimension(1200, 600));
            frame.setVisible(true);
        }
    }

    class ReferenceMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame ref = new JFrame("Справка");

            ref.setSize(400, 400);
            ref.setVisible(true);
        }
    }

    private class ExcelMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exportExcel();
        }
    }

    private void exportExcel() {

        JTable loadTable = main.getNorthPanel().getTf().getLoadTable();
        JTable dayTable = main.getNorthPanel().getTf().getDayTable();
        JTable pairTable = main.getNorthPanel().getTf().getPairTable();

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Расписание");
        Row row = sheet.createRow(0);
        Cell cell;

        int i = 0;
        for (int j = 0; j < dayTable.getModel().getColumnCount(); j++, i++) {
            cell = row.createCell(i);
            cell.setCellValue(dayTable.getModel().getColumnName(j));
        }

        for (int j = 0; j < pairTable.getModel().getColumnCount(); j++, i++) {
            cell = row.createCell(i);
            cell.setCellValue(pairTable.getModel().getColumnName(j));
        }

        for (int j = 0; j < loadTable.getModel().getColumnCount(); j++, i++) {
            cell = row.createCell(i);
            cell.setCellValue(loadTable.getModel().getColumnName(j));
        }



        for (int z = 0; z < loadTable.getModel().getRowCount(); z++) {
            row = sheet.createRow(z + 1);
            for (int j = 0; j < loadTable.getModel().getColumnCount(); j++) {
                cell = row.createCell(j+2);
                cell.setCellValue(loadTable.getModel().getValueAt(z, j).toString());
                sheet.autoSizeColumn(j);
            }
        }

        for (int z = 0; z < pairTable.getModel().getRowCount(); z++) {
            row = sheet.getRow(z + 1);
            for (int j = 0; j < pairTable.getModel().getColumnCount(); j++) {
                cell = row.createCell(j+1);
                cell.setCellValue(Integer.parseInt(pairTable.getModel().getValueAt(z, j).toString()));
                sheet.autoSizeColumn(j);
            }
        }

        // объединяем дни
        CellStyle style = workbook.createCellStyle();
        for (int j = 0, z = 0; z < dayTable.getModel().getRowCount(); j+= 7, z++) {
            CellRangeAddress region = new CellRangeAddress(j+1, j+7, 0, 0);
            sheet.addMergedRegion(region);

            row = sheet.getRow(j+1);
            cell = row.createCell(0);
            cell.setCellValue(dayTable.getModel().getValueAt(z, 0).toString());

            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setCellStyle(style);
        }


//
        try (FileOutputStream fileExcel = new FileOutputStream("Table.xls")) {
            workbook.write(fileExcel);
//            Pump.frame.message(this,
//                    "Таблица успешно записана в файл: Table.xls\n" + "адрес: "
//                            + System.getProperty("user.dir"),
//                    "Справочник насосов для ИТП", Message.information);
        } catch (IOException e) {
//            Pump.frame.message(this, "Невозможно выполнить данную операцию.",
//                    "Ошибка", Message.error);
            System.out.println("output excel error");
        }
    }

}
