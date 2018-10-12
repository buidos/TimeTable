package by.dmitrui98.dao;


import by.dmitrui98.entity.Department;
import by.dmitrui98.entity.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 25.02.2018.
 */
public class GroupDao extends AbstractDao<Group> {

    private Connection con;

    private DepartmentDao depDao ;

    public GroupDao(Connection con) {
        this.con = con;
        depDao = new DepartmentDao(con);
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();

        String sql = "select * from spr_group";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Group group = new Group(rs.getString("value_"),
                        rs.getInt("course"));
                Department dep = depDao.getById(rs.getInt("department_id"));
                group.setDepartment(dep);
                group.setId(rs.getInt("id"));
                groups.add(group);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return groups;
    }

    @Override
    public void insert(Group o) throws SQLException {
        String sql = "INSERT INTO spr_group (value_, course, department_id) VALUES(?,?,?)";
        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getName());
            ps.setInt(2, o.getCourse());
            ps.setInt(3, o.getDepartment().getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Group g) throws SQLException {
        String sql = "UPDATE spr_group SET value_ = ? , "
                + "course = ?, "
                + "department_id = ? "
                + "WHERE id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, g.getName());
            ps.setInt(2, g.getCourse());
            ps.setInt(3, g.getDepartment().getId());
            ps.setInt(4, g.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Group o) throws SQLException {
        String sql = "DELETE FROM spr_group where id = ?";

        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, o.getId());
            ps.executeUpdate();
        }
    }
}
