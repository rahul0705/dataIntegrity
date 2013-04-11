package Helpers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
	public static String cgtEncode(ArrayList<String> data, String key){
		int countHMAC = (int)(Math.log10(data.size())/Math.log10(2));
		String mark = new String();
		String message = new String();
		for(int t = 0; t < data.size(); t++){
			message+=data.get(t);
		}		
		mark = toBitString(encode(message.getBytes(),key));
		message = new String();
		for(int j = 0; j < countHMAC; j++){
			for(int i=0;i<data.size(); i++){
				String index = Integer.toBinaryString(i + 1);
				if(index.length()<countHMAC){
					String leftPad = "";
					for(int k=0;k<countHMAC-index.length();k++){
						leftPad+="0";
					}
					index=leftPad+index;
				}
				if(index.charAt(index.length() - j - 1) == '1'){
					message+=data.get(i);
				}
			}
			mark+=toBitString(encode(message.getBytes(),key));
			message = new String();
		}
		return mark;
	}
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
			for (int j = 7; j >= 0; j--) {
				bit_string += getBit(hmac[i], j);
			}
		}
		
		return bit_string;
	}
	
	private static int getBit(byte value, int position) {
		return (value >> position) & 1;
	}
}
