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

package se.vgregion.webbtidbok.crypto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.sun.mail.util.BASE64EncoderStream;

/**
 * 
 * @author David, Nhi och Örjan
 * 
 */
public class StringEncrypter {

    private String keyStoreType = "pkcs12";
    private KeyStore keyStore;
    private Resource keyStoreFile;
    private String keyAlias;
    private String keyPassWord;
    private Cipher cipher;
    private Logger logger = LoggerFactory.getLogger(StringEncrypter.class);

    /**
     * Keystore to use.
     * 
     * @param keyStore
     *            {@link KeyStore}
     */
    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Keystore file to use.
     * 
     * @param keyStoreFile
     *            resource.
     */
    public void setKeyStoreFile(Resource keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    /**
     * Alias of the key to use.
     * 
     * @param keyAlias
     *            string alias.
     */
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    /**
     * Password for the key to use.
     * 
     * @param keyPassWord
     *            string password.
     */
    public void setKeyPassWord(String keyPassWord) {
        this.keyPassWord = keyPassWord;
    }

    /**
     * Set keystore type to use
     * 
     * @param keyStoreType
     *            in string
     */
    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    /**
     * Encrypts a string using a predefined key store. Returns a base64 encoded string.
     * 
     * @param stringToEncrypt
     * @return encrypted byte array
     * @throws Exception
     */
    public byte[] encrypt(String stringToEncrypt) throws Exception {
        byte[] encoded = getCipher().doFinal(stringToEncrypt.getBytes("utf-8"));
        return encoded;
    }

    private Cipher getCipher() {
        // If keystore is not initiated.
        if (keyStore == null) {
            try {
                keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(keyStoreFile.getInputStream(), keyPassWord.toCharArray());

            } catch (KeyStoreException e) {
                logger.error(e.getMessage(), e);
            } catch (NoSuchAlgorithmException e) {
                logger.error(e.getMessage(), e);
            } catch (CertificateException e) {
                logger.error(e.getMessage(), e);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        try {
            Key key = keyStore.getKey(keyAlias, keyPassWord.toCharArray());
            cipher = null;
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (UnrecoverableKeyException e) {
            logger.error(e.getMessage(), e);
        } catch (KeyStoreException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            logger.error(e.getMessage(), e);
        }

        return cipher;

    }

    /**
     * 
     * @param byteArray
     *            to encode to base64
     * @return byte[] array
     * @throws MessagingException
     * @throws IOException
     */
    public byte[] encode(byte[] byteArray) throws MessagingException, IOException {
        byte[] encode = BASE64EncoderStream.encode(byteArray);
        return encode;
        // ByteArrayOutputStream baos = new ByteArrayOutputStream(byteArray.length);
        // OutputStream b64os = MimeUtility.encode(baos, "base64");
        // b64os.write(byteArray);
        // b64os.close();
        // return baos.toByteArray();
    }

}
