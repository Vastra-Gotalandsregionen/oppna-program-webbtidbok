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

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import se.vgregion.webbtidbok.lang.StringHandler;

public class StringHandlerTest {

  @Test
  public void testThatStringHandlerUppercasesAnyFirstLetter() {
    String toBeVerified = randomString();
    try {
      System.out.println(StringHandler.toFirstLetterToUpperCase(toBeVerified));
      toBeVerified = StringHandler.toFirstLetterToUpperCase(toBeVerified);
      String charVerified = toBeVerified.substring(0, 1);
      if (charVerified.matches("[A-Z]")) {
        Assert.assertTrue(true);
      } else {
        Assert.assertTrue(false);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.assertTrue(false);
    }

  }

  @Test
  public void testCapitalizeEmptyString() {
    String toBeVerified = "";
    String processedName = null;
    try {
      processedName = StringHandler.capitalizeName(toBeVerified);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.assertTrue(false);
    }
    Assert.assertEquals("", processedName);
  }

  @Test
  public void testCapitalizeNameWithSpaces() {
    String toBeVerified = "nils hansson lake";
    String processedName = null;
    try {
      processedName = StringHandler.capitalizeName(toBeVerified);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.assertTrue(false);
    }
    Assert.assertEquals("Nils Hansson Lake", processedName);
  }

  @Test
  public void testCapitalizeNameWithMinus() {
    String toBeVerified = "nils hansson-lake";
    String processedName = null;
    try {
      processedName = StringHandler.capitalizeName(toBeVerified);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.assertTrue(false);
    }
    Assert.assertEquals("Nils Hansson-Lake", processedName);
  }

  @Test
  public void testCapitalizeNameWithComma() {
    String toBeVerified = "hansson,nils";
    String processedName = null;
    try {
      processedName = StringHandler.capitalizeName(toBeVerified);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.assertTrue(false);
    }
    Assert.assertEquals("Hansson,Nils", processedName);
  }

  @Test
  public void testNameWithSwedishCharacters() {
    String toBeVerified = "GRONEFALK,oSTEN";
    String processedName = null;
    try {
      processedName = StringHandler.capitalizeName(toBeVerified);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.assertTrue(false);
    }
    Assert.assertEquals("Gronefalk,Osten", processedName);
  }

  @Test
  public void testThatEmptyStringReturnsNOk() {

    try {
      String s = StringHandler.toFirstLetterToUpperCase("");
      String charVerified = s.substring(0, 1);
      if (charVerified.matches("[A-Z]")) {
        Assert.assertTrue(false);
      } else {
        Assert.assertTrue(true);
      }
    } catch (Exception e) {
      System.out.println("testThatEmptyStringReturnsOk() " + e.getMessage());
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testThatNullStringReturnsNOk() {
    try {

      String s = StringHandler.toFirstLetterToUpperCase(null);
      String charVerified = s.substring(0, 1);
      if (charVerified.matches("[A-Z]")) {
        Assert.assertTrue(false);
      } else {
        Assert.assertTrue(true);
      }

    } catch (Exception e) {
      Assert.assertTrue(true);
      System.out.println("testThatNullStringReturnsOk() " + e.getMessage());
    }
  }

  public static String randomString() {
    final int string_length = 8;
    StringBuffer sb = new StringBuffer();
    for (int x = 0; x < string_length; x++) {
      sb.append((char) ((int) (Math.random() * 26) + 97));
    }

    String randomString = sb.toString();
    System.out.println(randomString);
    return randomString;
  }

  public static int randomInt() {
    int randomInt = 0;
    Random generator = new Random();
    randomInt = generator.nextInt();
    System.out.println(randomInt);
    return randomInt;
  }

}
