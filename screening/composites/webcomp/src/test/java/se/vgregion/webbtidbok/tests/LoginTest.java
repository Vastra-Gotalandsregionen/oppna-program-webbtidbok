/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

/**
 * 
 */
package se.vgregion.webbtidbok.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.Login;
import se.vgregion.webbtidbok.State;

/**
 * @author conpem
 * 
 */
public class LoginTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /*
   * Test logging in with blank pid and pwd, should return false if able to login with blank
   */
  @Ignore
  @Test
  public void testLoginBlanks() throws Exception {
    State credentials = new State();
    credentials.setPasswd("");
    credentials.setPnr("");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with blank pwd, should return false if able to login with blank pwd
   */
  @Ignore
  @Test
  public void testLoginBlankPassword() throws Exception {
    State credentials = new State();
    credentials.setPasswd("");
    credentials.setPnr("19121212-1212");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with blank pnr, should return false if able to login with blank pnr
   */
  @Ignore
  @Test
  public void testLoginBlankPnr() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Zs12JzIW");
    credentials.setPnr("");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with null values on pwd, should return false if able to login with null password
   */
  @Ignore
  @Test
  public void testLoginNullPassword() throws Exception {
    State credentials = new State();
    credentials.setPasswd(null);
    credentials.setPnr("19121212-1212");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with null values on pnr, should return false if able to login with null pnr
   */
  @Ignore
  @Test
  public void testLoginNullPnr() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Zs12JzIW");
    credentials.setPnr(null);
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with null values on pid and pwd, should return false if able to login with blank
   */
  @Ignore
  @Test
  public void testLoginNullValues() throws Exception {
    State credentials = new State();
    credentials.setPasswd(null);
    credentials.setPnr(null);
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with faulty values on pid and pwd, should return false if able to login with faulty values
   */
  @Ignore
  @Test
  public void testFaultyValuesSmallPnr() throws Exception {
    State credentials = new State();
    credentials.setPasswd("4444");
    credentials.setPnr("760909");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with correct values on pid and pwd, should return false if not able to login
   * 
   * testvalues: pwd sekel pnr Zs12JzIW 19 121212-1212 Y8PBZRUr 19 960103-2395 bQwkdRrG 19 910104-2399 fje5rnXG 19 910104-2399 u63MvXTx 19 660223-3196 2td3XrGx 19 030303-9804
   */
  @Ignore
  @Test
  public void testCorrectValues() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Y8PBZRUr");
    credentials.setPnr("19960103-2395");

    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertTrue(screen.login(credentials));
  }

  /*
   * Test logging in with correct values on pid, should return false if able to login
   * 
   * testvalues: pwd sekel pnr Zs12JzIW 19 121212-1212 Y8PBZRUr 19 960103-2395 bQwkdRrG 19 910104-2399 fje5rnXG 19 910104-2399 u63MvXTx 19 660223-3196 2td3XrGx 19 030303-9804
   */
  @Ignore
  @Test
  public void testFaultyPassword() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Zs1UYTREWQQQQ2JzIW");
    credentials.setPnr("19121212-1212");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with correct values on pid, should return false if able to login
   * 
   * testvalues: pwd sekel pnr Zs12JzIW 19 121212-1212 Y8PBZRUr 19 960103-2395 bQwkdRrG 19 910104-2399 fje5rnXG 19 910104-2399 u63MvXTx 19 660223-3196 2td3XrGx 19 030303-9804
   */
  @Ignore
  @Test
  public void testFaultyPnr() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Zs12JzIW");
    credentials.setPnr("191212121212");
    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with correct values on pid, and lowercase value on password on one letter , should return false if able to login
   * 
   * testvalues: pwd sekel pnr Zs12JzIW 19 121212-1212 Y8PBZRUr 19 960103-2395 bQwkdRrG 19 910104-2399 fje5rnXG 19 910104-2399 u63MvXTx 19 660223-3196 2td3XrGx 19 030303-9804
   */
  @Ignore
  @Test
  public void testFaultyLowerCasePassword() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Y8PBZRUR");
    credentials.setPnr("19960103-2395");

    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

  /*
   * Test logging in with correct values on pid, and uppercase value on password on one letter , should return false if able to login
   * 
   * testvalues: pwd sekel pnr Zs12JzIW 19 121212-1212 Y8PBZRUr 19 960103-2395 bQwkdRrG 19 910104-2399 fje5rnXG 19 910104-2399 u63MvXTx 19 660223-3196 2td3XrGx 19 030303-9804
   */
  @Ignore
  @Test
  public void testFaultyUppercasePassword() throws Exception {
    State credentials = new State();
    credentials.setPasswd("Y8PbZRUr");
    credentials.setPnr("19960103-2395");

    System.out.println(credentials.toString());

    Login screen = new Login();
    Assert.assertFalse(screen.login(credentials));
  }

}
