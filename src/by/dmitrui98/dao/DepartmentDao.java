package by.dmitrui98.dao;


import by.dmitrui98.entity.Department;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 25.02.2018.
 */
public class DepartmentDao extends AbstractDao<Department> {

    private Connection con;

    public DepartmentDao(Connection con) {
        this.con = con;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "select * from spr_department";

        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql) ) {
            while (rs.next()) {
                Department dep = new Department(rs.getString("name"));
                dep.setId(rs.getInt("id"));
                departments.add(dep);
            }
        }
        return departments;
    }

    @Override
    public Department getById(int id) throws SQLException {
        String sql = "select * from spr_department where id = ?";
        Department dep = null;
        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                dep = new Department(name);
                dep.setId(id);
            }
        }
        return dep;
    }
}
