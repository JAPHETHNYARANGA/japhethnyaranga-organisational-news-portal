package dao;

import models.MyUser;

import java.util.List;

public interface MyUserDao {
    List<MyUser> getAllUsers();
    void addUser(MyUser myUser);
    MyUser findUserById(int id);
    void updateUser(MyUser myUser, String name, String position, String role, int departmentId);
    void clearAllUsers();
}
