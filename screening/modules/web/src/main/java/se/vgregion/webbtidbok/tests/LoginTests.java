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
	public void testFaultyValuesSmallPnr(){
		State credentials = new State();
		credentials.setPasswd("4444");
		credentials.setPnr("760909");
		
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
		
		Login screen = new Login();
		boolean loggedIn = screen.login(credentials);
		
		if(loggedIn == true){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertFalse(true);
		}
		
		
	
	}
	
	
	
	

}
