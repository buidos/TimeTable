package by.dmitrui98.gui;

import by.dmitrui98.Main;
import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.TeacherColumn;
import by.dmitrui98.data.WorkingTeacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Администратор on 24.10.2016.
 */
public class TimetableMenuBar extends JMenuBar {
    Main main;

    private JFrame frame;
    File file;
    private ArrayList<TeacherColumn> teacherColumns;
    private ArrayList<WorkingTeacher> workingTeachers;

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
        JMenuItem referenceMenuItem = new JMenuItem("справка");
        referenceMenuItem.addActionListener(new ReferenceMenuListener());
        serviceMenu.add(teacherTableMenuItem);
        serviceMenu.add(referenceMenuItem);

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
            teacherColumns = main.getTeacherColumns();
            workingTeachers = main.getWorkingTeachers();

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

        }
    }

    class TeacherMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class ReferenceMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
