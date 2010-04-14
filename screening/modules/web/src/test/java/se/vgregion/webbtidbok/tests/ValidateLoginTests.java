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
package se.vgregion.webbtidbok.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.*;

import se.vgregion.webbtidbok.*;

public class ValidateLoginTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testValidationPnr(){
		
		ValidationLogin vL = new ValidationLogin();
		String result = vL.validatePnr("19998877-9090");
		System.out.println("result: " + result);
		if(result.equals("")){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertFalse(true);
		}
	}
	
	@Test
	public void testValidationFaultyPnr(){
		
		ValidationLogin vL = new ValidationLogin();
		String result = vL.validatePnr("199988779090");
		System.out.println("result: " + result);
		if(result.equals("")){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertFalse(true);
		}
	}
	
}
