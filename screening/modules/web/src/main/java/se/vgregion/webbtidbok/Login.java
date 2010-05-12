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
package se.vgregion.webbtidbok;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.crypto.StringEncrypter;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ObjectFactory;

@Service
public class Login {
  private final ObjectFactory objectFactory = new ObjectFactory();
  private WebServiceHelper webServiceHelper;
  BookingResponse bookingRequest;
  BookingRequest b;
  BookingResponse debugResponse = new BookingResponse();
  BookingResponse response;

  public void setWebServiceHelper(WebServiceHelper webServiceHelper) {
    this.webServiceHelper = webServiceHelper;
  }

  public String message = "";
  private StringEncrypter encrypter;

  public void setEncrypter(StringEncrypter encrypter) {
    this.encrypter = encrypter;
  }

  public String getPnr() {
    return response.getPnr().toString();
  }

  public String getBesDat() {
    return response.getBokadTid().toString();
  }

  public void logout(State loginCredentials) {
    loginCredentials.setPnr("");
    loginCredentials.setPasswd("");
    loginCredentials.setLoggedIn(false);
  }

  public boolean login(State loginCredentials) throws Exception {

    BookingRequest request = objectFactory.createBookingRequest();

    byte[] encrypted = encrypter.signString(loginCredentials.getPasswd());
    String encoded = encrypter.encodeToBase64(encrypted);
    // "parameters"
    JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
    JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
    JAXBElement<String> key = objectFactory.createBookingRequestKey(loginCredentials.getPasswd());
    JAXBElement<String> cryptedKey = objectFactory.createBookingRequestCryptedKey(encoded);

    JAXBElement<String> cert = objectFactory.createBookingRequestCert("YES");

    // setup request object
    request.setPnr(pnr);
    request.setPin(pin);
    request.setKey(key);
    request.setCryptedKey(cryptedKey);
    request.setCert(cert);

    // make web service call
    // CentralBookingWS centralBookingWS = new CentralBookingWS();
    // ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();

    response = webServiceHelper.getQueryWS(request);
    // response = ws.getBooking(request);
    System.out.println("*********Login.login(State loginCredentials): ************");
    System.out.println("Login.login(State loginCredentials): " + " response.ltidbokid: " + response.getCentralTidbokID());
    System.out.println("*********Login.login(State loginCredentials): ************");

    // loginCredentials.setBookingResponse(response);
    loginCredentials.setBookingResponse(response);

    if (response != null) {
      loginCredentials.setLoggedIn(true);
      return true;
    } else {
      loginCredentials.setLoggedIn(false);
      return false;
    }
  }

  private void debug(BookingRequest r) {
    Log4JLogger log = new Log4JLogger();

    log.debug(r.toString());
  }
}
