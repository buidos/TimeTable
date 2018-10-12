package by.dmitrui98.entity;

import by.dmitrui98.data.TeachersHour;

import java.util.List;

/**
 * Created by Администратор on 10.10.2018.
 */
public class Load {
    private List<TeachersHour> teachersHourList;
    private Group group; // группа, для которой вставляется нагрузка

    public Load(List<TeachersHour> teachersHourList, Group group) {
        this.teachersHourList = teachersHourList;
        this.group = group;
    }

    public List<TeachersHour> getTeachersHourList() {
        return teachersHourList;
    }

    public void setTeachersHourList(List<TeachersHour> teachersHourList) {
        this.teachersHourList = teachersHourList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
