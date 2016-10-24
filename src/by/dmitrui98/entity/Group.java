package by.dmitrui98.entity;

import by.dmitrui98.enums.DepartmentType;

/**
 * Created by Администратор on 25.10.2016.
 */
public class Group {
    private int value;
    private int course;
    private DepartmentType department;

    public Group(int value, int course, DepartmentType department) {
        this.value = value;
        this.course = course;
        this.department = department;
    }

    public int getValue() {
        return value;
    }

    public int getCourse() {
        return course;
    }

    public DepartmentType getDepartment() {
        return department;
    }
}
