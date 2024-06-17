/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import classes.DB_User;
import classes.users.User;
import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anna.rinkevica.PR-21
 * @since 17.06.2024
 * 
 */
public class DB_UserTest {
    
    /*
    Šis JUnit pārbauda, vai programma var izveidot sev kontu
    un veikt visas pamata metodes, lai apstrādātu kontu.
    */
    
    public DB_UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DB_User.getConnection();
        DB_User.getUserList();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Connection result = DB_User.getConnection();
        assertNotNull(result);
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        String login = "a";
        String password = "a";
        boolean rights = false;
        DB_User.addUser(login, password, rights);
        assertTrue(DB_User.isLoginExists(login));
        assertTrue(DB_User.isPasswordCorrect(login, password));
        DB_User.delete(DB_User.getUser(login, password).getId());
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        String login = "a";
        String password = "a";
        String firstName = "A";
        String lastName = "B";
        String eMail = "a@gmail.com";
        boolean rights = false;
        DB_User.addUser(login, password, rights);
        User user = DB_User.getUser(login, password);
        assertNotNull(user);
        DB_User.updateUser(user.getId(), firstName, lastName, eMail);
        user = DB_User.getUser(login, password);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(eMail, user.getEmail());
        DB_User.delete(user.getId());
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        String login = "a";
        String password = "a";
        boolean rights = false;
        DB_User.addUser(login, password, rights);
        DB_User.delete(DB_User.getUser(login, password).getId());
        assertFalse(DB_User.isLoginExists(login));
    }

    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String login = "a";
        String password = "a";
        boolean rights = false;
        DB_User.addUser(login, password, rights);
        User user = DB_User.getUser(login, password);
        assertNotNull(user);
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
        DB_User.delete(DB_User.getUser(login, password).getId());
    }

    @Test
    public void testIsLoginExists() {
        System.out.println("isLoginExists");
        String login = "a";
        String password = "a";
        boolean rights = false;
        DB_User.addUser(login, password, rights);
        assertTrue(DB_User.isLoginExists(login));
        DB_User.delete(DB_User.getUser(login, password).getId());
    }

    @Test
    public void testIsPasswordCorrect() {
        System.out.println("isPasswordCorrect");
        String login = "a";
        String password = "a";
        boolean rights = false;
        DB_User.addUser(login, password, rights);
        assertTrue(DB_User.isPasswordCorrect(login, password));
        DB_User.delete(DB_User.getUser(login, password).getId());
    }
    
}
