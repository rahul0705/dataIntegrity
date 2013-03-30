import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class HMAC {
	private static final int keySize = 64;
	private static byte ipad[] = {'\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\',
		'\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\',
		'\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\',
		'\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\', '\\'};
	private static byte opad[] = {'6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
		'6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
		'6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
		'6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6'};
	public static byte[] hmac(String message, String key){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			byte [] kIpad = new byte[keySize];
			for(int i = 0; i < keySize; i++){
				kIpad[i] = (byte)(key.charAt(i) ^ ipad[i]);
			}
			byte[] kIpadM = Arrays.copyOf(kIpad, kIpad.length + message.length());
			System.arraycopy(message.getBytes(), 0, kIpadM, kIpad.length, message.length());
			md.update(kIpadM);
			byte[] HkIpadM = md.digest();
			byte[] kOpad = new byte[keySize];
			for(int i = 0; i < keySize; i++){
				kOpad[i] = (byte)(key.charAt(i) ^ opad[i]);
			}
			byte[] result = Arrays.copyOf(kOpad, kOpad.length + HkIpadM.length);
			System.arraycopy(HkIpadM, 0, result, kOpad.length, HkIpadM.length);
			md.reset();
			md.update(result);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
