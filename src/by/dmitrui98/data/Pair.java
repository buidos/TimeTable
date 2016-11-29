package by.dmitrui98.data;

import by.dmitrui98.enums.TypeHour;

import java.io.Serializable;

/**
 * Created by Администратор on 18.11.2016.
 */
public class Pair implements Serializable {
    private int row;
    private int group;
    private TypeHour typeHour;

    public Pair(int row, int group, TypeHour typeHour) {
        this.row = row;
        this.group = group;
        this.typeHour = typeHour;
    }

    public int getRow() {
        return row;
    }

    public int getGroup() {
        return group;
    }

    public TypeHour getTypeHour() {
        return typeHour;
    }

    public void setTypeHour(TypeHour typeHour) {
        this.typeHour = typeHour;
    }

    @Override
    public boolean equals(Object obj) {
        Pair pair = (Pair) obj;
        return (this.getRow() == pair.getRow()) && (this.getGroup() == pair.getGroup()) && (this.getTypeHour() == pair.getTypeHour());
    }


}
