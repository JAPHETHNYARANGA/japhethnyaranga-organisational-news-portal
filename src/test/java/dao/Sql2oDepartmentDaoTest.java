package dao;

import models.Department;
import models.DepartmentNews;
import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {

    private static  Sql2oDepartmentDao dptDao;
    private static Sql2oUserDao userDao;
    private static Sql2oNewsDao newsDao;
    private static Connection con;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionStr="jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionStr,"japhethnyaranga","34120648");

        dptDao = new Sql2oDepartmentDao (sql2o);
        userDao = new Sql2oUserDao (sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        con = sql2o.open();

        dptDao.clearAllDepartments(); //start with empty table
        userDao.clearAllUsers();
    }

    @After
    public void tearDown() throws Exception { dptDao.clearAllDepartments(); userDao.clearAllUsers();}

    @AfterClass
    public static void shutDown() throws Exception { con.close(); }

    @Test
    public void getAllDepartments_ReturnsAllDepartments_True() {
        Department d1 = setupDepartment();
        Department d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        assertEquals(2, dptDao.getAllDepartments().size());
        assertTrue(dptDao.getAllDepartments().containsAll(Arrays.asList(d1,d2)));
    }

    @Test
    public void getDepartmentUsersById_ReturnsDepartmentsUsers_True() {

        Department d1 = setupDepartment();
        Department d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        User user1 = setupUser();
        User user2 = setupUser();
        User user3 = setupUser();
        User user4 = setupUser();
        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.addUser(user3);
        userDao.addUser(user4);

        userDao.updateUser(user1,user1.getName(),user1.getPosition(),user1.getRole(),d1.getId());
        userDao.updateUser(user2,user2.getName(),user2.getPosition(),user2.getRole(),d1.getId());
        userDao.updateUser(user3,user3.getName(),user3.getPosition(),user3.getRole(),d2.getId());

        int dc = dptDao.getAllDepartments().size();
        int uc = userDao.getAllUsers().size();

        assertEquals(2, dptDao.getDepartmentUsersById(d1.getId()).size());
        assertTrue(dptDao.getDepartmentUsersById(d1.getId()).containsAll(Arrays.asList(user1,user2)));


        assertEquals(1, dptDao.getDepartmentUsersById(d2.getId()).size());
        assertTrue(dptDao.getDepartmentUsersById(d2.getId()).containsAll(Arrays.asList(user3)));
        assertFalse(dptDao.getDepartmentUsersById(d2.getId()).contains(user1));
        assertFalse(dptDao.getDepartmentUsersById(d2.getId()).contains(user2));
        assertFalse(dptDao.getDepartmentUsersById(d2.getId()).contains(user4));
    }

    @Test
    public void getDepartmentNewsById_ReturnsDepartmentsNews_True() {
        Department d1 = setupDepartment();
        Department d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        DepartmentNews dn = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();
        DepartmentNews dn3 = setupDepartmentNews();
        DepartmentNews dn4 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn);
        newsDao.addDepartmentNews(dn2);
        newsDao.addDepartmentNews(dn3);

        newsDao.updateDepartmentNews(dn,dn.getUserId(),dn.getContent(),d1.getId());
        newsDao.updateDepartmentNews(dn2,dn2.getUserId(),dn2.getContent(),d1.getId());
        newsDao.updateDepartmentNews(dn3,dn3.getUserId(),dn3.getContent(),d2.getId());

        assertEquals(2,dptDao.getDepartmentNewsById(d1.getId()).size());
        assertTrue(dptDao.getDepartmentNewsById(d1.getId()).containsAll(Arrays.asList(dn,dn2)));

        assertEquals(1,dptDao.getDepartmentNewsById(d2.getId()).size());
        assertTrue(dptDao.getDepartmentNewsById(d2.getId()).contains(dn3));
        assertFalse(dptDao.getDepartmentNewsById(d2.getId()).contains(dn));
        assertFalse(dptDao.getDepartmentNewsById(d2.getId()).contains(dn2));
        assertFalse(dptDao.getDepartmentNewsById(d2.getId()).contains(dn4));

    }

    @Test
    public void addDepartment_addsDepartmentSetsId_True() {

        Department d1 = setupDepartment();
        Department d2 = setupDepartment();

        int ol_id = d1.getId();
        int ol_id2= d2.getId();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        assertNotEquals(ol_id,d1.getId());
        assertNotEquals(ol_id2,d2.getId());
        assertTrue(d2.getId() >  d1.getId());
        assertTrue(1 == d2.getId() - d1.getId());

    }

    @Test
    public void findDepartmentById_findsCorrectDepartment_True() {
        Department d1 = setupDepartment();
        Department d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        Department foundDpt = dptDao.findDepartmentById(d1.getId());

        assertEquals(foundDpt,d1);
    }

    @Test
    public void updateDepartment_updatesNameDescription_True() {
        Department d = setupDepartment();
        Department d2 = setupDepartment();

        dptDao.addDepartment(d);
        dptDao.addDepartment(d2);

        String ol_name = d.getName();
        String ol_desc =d.getDescription();

        String ol_name2 = d2.getName();
        String ol_desc2 =d2.getDescription();

        dptDao.updateDepartment(d,"Technology","Efficiency");

        assertNotEquals(ol_name,d.getName());
        assertNotEquals(ol_desc,d.getDescription());

        assertEquals(ol_name2,d2.getName());
        assertEquals(ol_desc2,d2.getDescription());
    }

    @Test
    public void clearAllDepartments() {
        Department d = setupDepartment();
        Department d2 = setupDepartment();

        dptDao.addDepartment(d);
        dptDao.addDepartment(d2);

        assertEquals(2,dptDao.getAllDepartments().size());
        dptDao.clearAllDepartments();
        assertEquals(0,dptDao.getAllDepartments().size());
    }

    private Department setupDepartment(){
        return new Department(1,"Finance","Everything accounting");
    }

    private User setupUser(){
        return new User(1,"Ann","Senior","CFO",1);
    }

    private DepartmentNews setupDepartmentNews(){
        return new DepartmentNews(1,1,Sql2oNewsDao.DEPARTMENT_NEWS,"Oceans",new Timestamp(new Date().getTime()),1);
    }
}