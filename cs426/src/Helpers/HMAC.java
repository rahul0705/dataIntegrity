package Helpers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
	public static byte[] encode(byte[] message, String key){
		byte[] hmac = null;
		try {
			SecretKeySpec k = new SecretKeySpec(key.getBytes(),"HmacSHA256");
			Mac mac = null;
			mac = Mac.getInstance("HmacSHA256");
			mac.init(k);
			hmac = mac.doFinal(message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return hmac;
	}
}
