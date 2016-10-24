package by.dmitrui98.gui;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Администратор on 24.10.2016.
 */
public class TimetableMenuBar extends JMenuBar {

    public TimetableMenuBar() {
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

    class SaveMenuListener implements ActionListener {
        public  void actionPerformed(ActionEvent e) {

        }
    }

    class SaveHowMenuListener implements ActionListener {
        public  void actionPerformed(ActionEvent e) {

        }
    }

    class OpenMenuListener implements ActionListener {
        public  void actionPerformed(ActionEvent e) {

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
