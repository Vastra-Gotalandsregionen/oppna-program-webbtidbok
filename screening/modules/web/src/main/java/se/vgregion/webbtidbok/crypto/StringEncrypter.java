package se.vgregion.webbtidbok.crypto;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.mail.internet.MimeUtility;

public class StringEncrypter {

	/**
	 * Encrypts a string using a predefined key store.
	 * Returns a base64 encoded string.
	 * 
	 * @param stringToEncrypt
	 * @return encryptedString
	 * @throws Exception
	 */
	public String encrypt(String stringToEncrypt) throws Exception {
		String encryptedString = null;

		KeyStore pfx = KeyStore.getInstance("pkcs12");
		pfx.load(new FileInputStream("e://webtidbok/asd.pfx"), "asd".toCharArray());
		Key key = pfx.getKey("a6c21dcdd9534d742aa1bd4afae16210_956e2a3a-b426-49f4-a107-72c603d2f58c", "asd"
				.toCharArray());
		Cipher ecipher = null;
		ecipher = Cipher.getInstance("RSA");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encoded = ecipher.doFinal(stringToEncrypt.getBytes("utf-8"));
		encryptedString = new String(encode(encoded));            
        
		return encryptedString;
    }

    private byte[] encode(byte[] b) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream b64os = MimeUtility.encode(baos, "base64");
        b64os.write(b);
        b64os.close();
        return baos.toByteArray();
    }
}
