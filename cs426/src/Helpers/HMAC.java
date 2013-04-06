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
	
	public static String toBitString(byte[] hmac) {
		int length = hmac.length;
		String bit_string = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < 8; j++) {
				bit_string += getBit(hmac[i], j);
			}
		}
		
		return bit_string;
	}
	
	private static int getBit(byte value, int position) {
		return (value >> position) & 1;
	}
}
