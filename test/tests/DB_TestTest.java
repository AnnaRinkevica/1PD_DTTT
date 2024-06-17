/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import classes.DB_Test;
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
public class DB_TestTest {
    
    /*
    Šis JUnit pārbauda, vai lietotājs ar atļaujām var
    pievienot testus, dzēst testus un rediģēt testus.
    */
    
    public DB_TestTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
        Connection result = DB_Test.getConnection();
        assertNotNull(result);
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        String name = "Paraugs";
        int[] questionidlist = {1,2,3,4,5};
        DB_Test.addTest(name, questionidlist);
        int id = 0;
        for (int i = 0; i < DB_Test.getTestList().size(); i++) {
            if (DB_Test.getTestList().get(i).getName().equals(name)) {
                assertTrue(DB_Test.getTestList().get(i).getName().equals(name));
                id = DB_Test.getTestList().get(i).getId();
            }
        }
        DB_Test.delete(id);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        String name = "Paraugs";
        int[] questionidlist = {1,2,3,4,5};
        DB_Test.addTest(name, questionidlist);
        int id = 0;
        for (int i = 0; i < DB_Test.getTestList().size(); i++) {
            if (DB_Test.getTestList().get(i).getName().equals(name)) {
                id = DB_Test.getTestList().get(i).getId();
            }
        }
        DB_Test.delete(id);
        assertFalse(DB_Test.isTestExists(id));
    }
    
    @Test
    public void isTestExists() {
        System.out.println("isTestExists");
        String name = "Paraugs";
        int[] questionidlist = {1,2,3,4,5};
        DB_Test.addTest(name, questionidlist);
        int id = 0;
        for (int i = 0; i < DB_Test.getTestList().size(); i++) {
            if (DB_Test.getTestList().get(i).getName().equals(name)) {
                id = DB_Test.getTestList().get(i).getId();
            }
        }
        assertTrue(DB_Test.isTestExists(id));
        DB_Test.delete(id);
    }
}
