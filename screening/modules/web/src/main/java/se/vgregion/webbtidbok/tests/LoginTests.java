/**
 * Copyright 2009 Vastra Gotalandsregionen
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
 */
/**
 * 
 */
package se.vgregion.webbtidbok.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.*;


import se.vgregion.webbtidbok.*;

/**
 * @author conpem
 *
 */
public class LoginTests {

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
	 * 
	 */
	@Test
	public void testLoginBlanks(){
		State credentials = new State();
		credentials.setPasswd("");
		credentials.setPnr("");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	
	/*
	 * Test logging in with blank pwd, should return false if able to login with blank pwd
	 * 
	 */
	@Test
	public void testLoginBlankPassword(){
		State credentials = new State();
		credentials.setPasswd("");
		credentials.setPnr("19121212-1212");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	
	/*
	 * Test logging in with blank pnr, should return false if able to login with blank pnr
	 * 
	 */
	@Test
	public void testLoginBlankPnr(){
		State credentials = new State();
		credentials.setPasswd("Zs12JzIW");
		credentials.setPnr("");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	
	/*
	 * Test logging in with null values on pwd, should return false if able to login with null
	 * password
	 */ 
	@Test
	public void testLoginNullPassword(){
		State credentials = new State();
		credentials.setPasswd(null);
		credentials.setPnr("19121212-1212");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	
	/*
	 * Test logging in with null values on pnr, should return false if able to login with null
	 * pnr
	 */ 
	@Test
	public void testLoginNullPnr(){
		State credentials = new State();
		credentials.setPasswd("Zs12JzIW");
		credentials.setPnr(null);
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	
	
	/*
	 * Test logging in with null values on pid and pwd, should return false if able to login with blank
	 * 
	 */
	@Test
	public void testLoginNullValues(){
		State credentials = new State();
		credentials.setPasswd(null);
		credentials.setPnr(null);
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	
	/*
	 * Test logging in with faulty values on pid and pwd, should return false if able to login with faulty values
	 * 
	 */
	@Test
	public void testFaultyValuesSmallPnr(){
		State credentials = new State();
		credentials.setPasswd("4444");
		credentials.setPnr("760909");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertFalse(true);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	/*
	 * Test logging in with correct  values on pid and pwd, should return false if not able to login
	 * 
	 * testvalues:
	 * pwd sekel pnr
	 * Zs12JzIW 19 121212-1212
	 *	Y8PBZRUr 19 960103-2395
	 *	bQwkdRrG 19 910104-2399
	 *	fje5rnXG 19 910104-2399
	 *	u63MvXTx 19 660223-3196
	 *	2td3XrGx 19 030303-9804
	 * 
	 * 
	 */
	@Test
	public void testCorrectValues(){
		State credentials = new State();
		credentials.setPasswd("Zs12JzIW");
		credentials.setPnr("19121212-1212");
		
		System.out.println(credentials.toString());
		
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertFalse(true);
		}
		
		
	
	}
	
	
	/*
	 * Test logging in with correct values on pid, should return false if able to login
	 * 
	 * testvalues:
	 * pwd sekel pnr
	 * Zs12JzIW 19 121212-1212
	 *	Y8PBZRUr 19 960103-2395
	 *	bQwkdRrG 19 910104-2399
	 *	fje5rnXG 19 910104-2399
	 *	u63MvXTx 19 660223-3196
	 *	2td3XrGx 19 030303-9804
	 * 
	 * 
	 */
	@Test
	public void testFaultyPassword(){
		State credentials = new State();
		credentials.setPasswd("Zs1UYTREWQQQQ2JzIW");
		credentials.setPnr("19121212-1212");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertTrue(false);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	/*
	 * Test logging in with correct values on pid, should return false if able to login
	 * 
	 * testvalues:
	 * pwd sekel pnr
	 * Zs12JzIW 19 121212-1212
	 *	Y8PBZRUr 19 960103-2395
	 *	bQwkdRrG 19 910104-2399
	 *	fje5rnXG 19 910104-2399
	 *	u63MvXTx 19 660223-3196
	 *	2td3XrGx 19 030303-9804
	 * 
	 * 
	 */
	@Test
	public void testFaultyPnr(){
		State credentials = new State();
		credentials.setPasswd("Zs12JzIW");
		credentials.setPnr("191212121212");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertTrue(false);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	/*
	 * Test logging in with correct values on pid, and lowercase value on password on one letter , should return false if able to login
	 * 
	 * testvalues:
	 * pwd sekel pnr
	 * Zs12JzIW 19 121212-1212
	 *	Y8PBZRUr 19 960103-2395
	 *	bQwkdRrG 19 910104-2399
	 *	fje5rnXG 19 910104-2399
	 *	u63MvXTx 19 660223-3196
	 *	2td3XrGx 19 030303-9804
	 * 
	 * 
	 */
	@Test
	public void testFaultyLowerCasePassword(){
		State credentials = new State();
		credentials.setPasswd("Zs12jzIW");
		credentials.setPnr("19121212-1212");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertTrue(false);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	
	/*
	 * Test logging in with correct values on pid, and uppercase value on password on one letter , should return false if able to login
	 * 
	 * testvalues:
	 * pwd sekel pnr
	 * Zs12JzIW 19 121212-1212
	 *	Y8PBZRUr 19 960103-2395
	 *	bQwkdRrG 19 910104-2399
	 *	fje5rnXG 19 910104-2399
	 *	u63MvXTx 19 660223-3196
	 *	2td3XrGx 19 030303-9804
	 * 
	 * 
	 */
	@Test
	public void testFaultyUppercaseCasePassword(){
		State credentials = new State();
		credentials.setPasswd("ZS12JzIW");
		credentials.setPnr("19121212-1212");
		System.out.println(credentials.toString());
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertTrue(false);
		}
		else{
			Assert.assertTrue(true);
		}
		
		
	
	}
	

}
