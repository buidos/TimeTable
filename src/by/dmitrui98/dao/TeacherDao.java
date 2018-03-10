package by.dmitrui98.dao;



import by.dmitrui98.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 28.01.2018.
 */
public class TeacherDao {
    private Connection con;

    public TeacherDao(Connection con) {
        this.con = con;
    }

    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();

        String sql = "select * from spr_teacher ORDER BY surname";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getString("surname"),
                        rs.getString("name"), rs.getString("patronymic"));
                teacher.setId(rs.getInt("id"));
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return teachers;
    }

    public Teacher getById(int id) {
        String sql = "select * from spr_teacher where id = ?";
        Teacher teacher = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String surname = rs.getString("surname");
                String middlename = rs.getString("name");
                String lastname = rs.getString("patronymic");
                teacher = new Teacher(surname, middlename, lastname);
                teacher.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }


    public void insert(Teacher o) {
        String sql = "INSERT INTO spr_teacher(surname, name, patronymic) VALUES(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, o.getSurname());
            ps.setString(2, o.getMiddlename());
            ps.setString(3, o.getLastname());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void update(Teacher t) {
        String sql = "UPDATE spr_teacher SET surname = ? , "
                + "name = ?, "
                + "patronymic = ? "
                + "WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getSurname());
            ps.setString(2, t.getMiddlename());
            ps.setString(3, t.getLastname());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Teacher o) {
        String sql = "DELETE FROM spr_teacher where id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
