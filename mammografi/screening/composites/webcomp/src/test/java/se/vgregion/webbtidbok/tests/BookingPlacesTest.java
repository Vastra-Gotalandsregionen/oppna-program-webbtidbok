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

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.crypto.StringEncrypter;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;

public class BookingPlacesTest {

  private static WebServiceHelper ws;

  @BeforeClass
  public static void setup() {
    ws = getWebServiceHelper();
  }

  /*
   * Test logging in with blank pid and pwd, should return false if able to login with blank
   */
  @Test
  public void testBookingPlaces() {

    State credentials = new State();
    credentials.setPasswd("6wHRDtKa"); // Old pw: "fje5rnXG"
    credentials.setPnr("19910104-2399");

    BookingRequest request = ws.getQueryWSRequest(credentials);
    ArrayOfBookingPlace places = ws.getQueryWSRequestPlaces(request);
    List<BookingPlace> placeList = places.getBookingPlace();
    if (placeList == null) {
      Assert.assertFalse(true);

    } else {

      if (placeList.isEmpty()) {
        Assert.assertFalse(true);
      } else {

        for (BookingPlace bp : placeList) {
          System.out.println(bp.getAddress().getValue());
          System.out.println(bp.getCentralTidbokID());
          System.out.println(bp.getMottagning().getValue());

        }
      }

    }
  }

  @After
  public void tearDown() throws Exception {
  }

  public static WebServiceHelper getWebServiceHelper() {
    WebServiceHelper webServiceHelper = new WebServiceHelper();
    StringEncrypter stringEncrypter = new StringEncrypter();
    stringEncrypter.setKeyAlias("a6c21dcdd9534d742aa1bd4afae16210_956e2a3a-b426-49f4-a107-72c603d2f58c");
    stringEncrypter.setKeyPassWord("asd");
    DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    Resource resource = defaultResourceLoader.getResource("classpath:asd.pfx");
    stringEncrypter.setKeyStoreFile(resource);
    webServiceHelper.setEncrypter(stringEncrypter);
    return webServiceHelper;
  }
}
