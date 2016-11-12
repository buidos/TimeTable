package by.dmitrui98.data;

import java.util.ArrayList;

/**
 * Created by Администратор on 11.11.2016.
 */
public class TeacherData {
    private ArrayList <String> teacherList = new ArrayList<String>();
    private ArrayList <Integer> loadList = new ArrayList<Integer>();

    public TeacherData(ArrayList<String> teacherList, ArrayList<Integer> loadList) {
        this.teacherList = teacherList;
        this.loadList = loadList;
    }

    public ArrayList<String> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<String> teacherList) {
        this.teacherList = teacherList;
    }

    public ArrayList<Integer> getLoadList() {
        return loadList;
    }

    public void setLoadList(ArrayList<Integer> loadList) {
        this.loadList = loadList;
    }
}
