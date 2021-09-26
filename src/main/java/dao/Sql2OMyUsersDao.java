package dao;

import models.MyUsers;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2OMyUsersDao implements MyUsersDao {

    private final Sql2o sql2o;
    public Sql2OMyUsersDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<MyUsers> getAllUsers() {
        String sql="select * from users";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).executeAndFetch(MyUsers.class);
        }

    }

    @Override
    public void addUser(MyUsers myUsers) {
        String sql ="insert into users (name, position, role, departmentId) values (:name, :position, :role, :departmentId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .bind(myUsers)
                    .executeUpdate()
                    .getKey();
            myUsers.setId(id);
        }
    }

    @Override
    public MyUsers findUserById(int id) {
        String sql ="select * from users where id = :id ";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(MyUsers.class);
        }
    }

    @Override
    public void updateUser(MyUsers myUsers, String name, String position, String role, int departmentId) {
        String sql = "update users set  (name,position,role,departmentId) = (:name,:position,:role,:departmentId) where id= :id ";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("position",position)
                    .addParameter("role",role)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id", myUsers.getId())
                    .executeUpdate();

            myUsers.setName(name);
            myUsers.setPosition(position);
            myUsers.setRole(role);
            myUsers.setDepartmentId(departmentId);
        }
    }

    @Override
    public void clearAllUsers() {
        String sql = "delete from users";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
}
