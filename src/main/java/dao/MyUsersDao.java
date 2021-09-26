package dao;

import models.MyUsers;

import java.util.List;

public interface MyUsersDao {
    List<MyUsers> getAllUsers();
    void addUser(MyUsers myUsers);
    MyUsers findUserById(int id);
    void updateUser(MyUsers myUsers, String name, String position, String role, int departmentId);
    void clearAllUsers();
}
