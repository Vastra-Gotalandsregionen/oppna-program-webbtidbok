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

package se.vgregion.webbtidbok.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class StringEncrypterTest {

    private static final String alias = "a6c21dcdd9534d742aa1bd4afae16210_956e2a3a-b426-49f4-a107-72c603d2f58c";

    private static final String MESSAGE_TO_ENCRYPT = "Message to encrypt";
    private StringEncrypter stringEncrypter;
    private DefaultResourceLoader defaultResourceLoader;
    private Resource resource;

    @Before
    public void setUp() throws Exception {
        stringEncrypter = new StringEncrypter();
        stringEncrypter.setKeyAlias(alias);
        stringEncrypter.setKeyPassWord("asd");
        defaultResourceLoader = new DefaultResourceLoader();
        resource = defaultResourceLoader.getResource("classpath:asdtest.pfx");
        stringEncrypter.setKeyStoreFile(resource);
    }


    @Test
    public void testEncrypt() throws Exception {
        byte[] encryptedStr = stringEncrypter.encrypt(MESSAGE_TO_ENCRYPT);
        assertNotNull(encryptedStr);
    }


    @Test
    public void testSignString() throws Exception {
        String signValueResult = "X50NI38KYMCBna3+cW/w6FFkQPZpN5/BvI8LbLM0+rbc2MrvKRtSp+e8dyO8dHBnLIiWSSfMzlcudyyfKUoK4r0PlAxZVG3rbGHCpdlYBUofJWNbdaLKkorMuedu5REcLzx0yxsOcjxULz3Zi4aAZtrc6i0082NAhvVTrzz6YFE=";
        byte[] signString = stringEncrypter.signString("knowit");
        String result = stringEncrypter.encodeToBase64(signString);
        assertEquals(signValueResult, result);
    }

    @Test
    public void testEncryption() throws Exception {
        // stringEncrypter.setKeyStore(getKeyStore());
        Resource resource = defaultResourceLoader.getResource("classpath:keystore.jks");
        stringEncrypter.setKeyAlias("knowit");
        stringEncrypter.setKeyStoreFile(resource);
        stringEncrypter.setKeyPassWord("knowit");
        stringEncrypter.setKeyStoreType(KeyStore.getDefaultType());

        byte[] encrypt = stringEncrypter.encrypt(MESSAGE_TO_ENCRYPT);
        byte[] result = getCipher().doFinal(encrypt);
        assertEquals(MESSAGE_TO_ENCRYPT, new String(result));
    }

    @Test
    public void testBase64Encoding() {
        String result = stringEncrypter.encodeToBase64("Test".getBytes());
        byte[] decodeBase64 = Base64.decodeBase64(result);
        assertEquals("Test", new String(decodeBase64));
    }

    private KeyStore getKeyStore() throws NoSuchAlgorithmException, KeyStoreException, CertificateException,
            IOException, UnrecoverableKeyException, javax.security.cert.CertificateException,
            InvalidKeySpecException {

        Resource resource = defaultResourceLoader.getResource("classpath:keystore.jks");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(resource.getInputStream(), "knowit".toCharArray());
        return keyStore;
    }

    private Cipher getCipher() {
        Cipher cipher = null;
        try {
            KeyStore keyStore = getKeyStore();
            PublicKey publicKey2 = keyStore.getCertificate("knowit").getPublicKey();
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey2);

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (javax.security.cert.CertificateException e) {
            e.printStackTrace();
        }

        return cipher;
    }

}
