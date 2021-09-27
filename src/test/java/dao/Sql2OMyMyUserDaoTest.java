package dao;

import models.MyUser;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2OMyMyUserDaoTest {

    private static MySql2OMyUserDao userDao;
    private static Connection con;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionStr="jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionStr,"japhethnyaranga","34120648");

        userDao = new MySql2OMyUserDao(sql2o);
        con = sql2o.open();
        userDao.clearAllUsers(); //start with empty table
    }

    @After
    public void tearDown() throws Exception { userDao.clearAllUsers(); }

    @AfterClass
    public static void shutDown() throws Exception { con.close(); }

    @Test
    public void getAllUsers_ReturnsAllUsers_True() {
        MyUser u1 = setUpUser();
        MyUser u2 = setUpUser();

        userDao.addUser(u1);
        userDao.addUser(u2);

        assertEquals(2,userDao.getAllUsers().size());
        assertTrue(userDao.getAllUsers().containsAll(Arrays.asList(u1,u2)));

    }

    @Test
    public void Return_correct_user_from_id() {
        MyUser user1 = setUpUser();
        MyUser user2 = setUpUser();
        userDao.addUser(user1);
        userDao.addUser(user2);

        MyUser foundMyUser = userDao.findUserById(user1.getId());
        assertEquals(user1, foundMyUser);

    }


    @Test
    public void clearAllUsers_True() {
        MyUser user1 = setUpUser();
        MyUser user2 = setUpUser();
        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.clearAllUsers();

        assertEquals(0, userDao.getAllUsers().size());
    }

    private MyUser setUpUser(){return  new MyUser(6,"japhethnyaranga","serior manager","Head of Admin",5); }
}