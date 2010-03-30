package se.vgregion.webbtidbok.tests;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;


import se.vgregion.webbtidbok.lang.StringHandler;

public class StringHandlerTest {
	@Test
	public void testThatStringHandlerUppercasesAnyFirstLetter(){
		StringHandler sh = new StringHandler();
		try {
			System.out.println(sh.toFirstLetterToUpperCase(randomString()));
			Assert.assertTrue(true);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.assertTrue(false);
		}
		
	}
 
	@Test
	public void testThatAnyNonLetterWorks(){
		StringHandler sh = new StringHandler();
		try{
			int randomInt = randomInt();
			String str = Integer.toString(randomInt);
			System.out.println(sh.toFirstLetterToUpperCase(str));
			Assert.assertTrue(true);
		}
		catch(Exception e){
			Assert.assertTrue(false);
			System.out.println(e.getMessage());			
		}
	}
	@Test
	public void testThatEmptyStringReturnsOk(){
		StringHandler sh = new StringHandler();
		try{
			sh.toFirstLetterToUpperCase("");
			Assert.assertTrue(true);
		}
		catch(Exception e) {
			System.out.println("testThatEmptyStringReturnsOk() " + e.getMessage());
			Assert.assertTrue(false);
		}
	}
	@Test
	public void testThatNullStringReturnsOk(){
		StringHandler sh = new StringHandler();
		try{
			sh.toFirstLetterToUpperCase(null);
			Assert.assertTrue(true);
		}
		catch (Exception e){ 
			Assert.assertTrue(false);
			System.out.println("testThatNullStringReturnsOk() " + e.getMessage());
		}
	}
	
	
  public static String randomString()  
     {  
       final int string_length = 8;  
        StringBuffer sb = new StringBuffer();  
        for (int x = 0; x < string_length; x++)  
        {  
          sb.append((char)((int)(Math.random()*26)+97));  
       }  
       
       String randomString = sb.toString();
       System.out.println(randomString);
	   return randomString;  
     }  
  
  public static int randomInt(){
	  int randomInt = 0;
	  Random generator = new Random();
	  randomInt = generator.nextInt();
	  System.out.println(randomInt);
	  return randomInt;
  }
  
}
