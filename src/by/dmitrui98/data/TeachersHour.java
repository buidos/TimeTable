package by.dmitrui98.data;

import by.dmitrui98.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 10.03.2018.
 */
public class TeachersHour {
    List<Teacher> teachers = new ArrayList<>();
    int hour;

    public TeachersHour() {
    }

    public TeachersHour(List<Teacher> teachers, int hour) {
        this.teachers = teachers;
        this.hour = hour;
    }

    public TeachersHour(Teacher teacher, int hour) {
        teachers.add(teacher);
        this.hour = hour;
    }

    public void add(Teacher teacher) {
        teachers.add(teacher);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
