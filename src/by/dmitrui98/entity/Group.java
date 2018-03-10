package by.dmitrui98.entity;

/**
 * Created by Администратор on 25.02.2018.
 */
public class Group {
    private int id;
    private String name;
    private int course;
    private Department department;

    public Group() {
    }

    public Group(int id) {
        this.id = id;
    }

    public Group(String name, int course) {
        this.name = name;
        this.course = course;
    }

    public Group(int id, String name, int course, Department department) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return getName();
    }


}
