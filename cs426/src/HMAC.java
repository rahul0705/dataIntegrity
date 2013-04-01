import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
	public static byte[] encode(byte[] message, byte[] key){
		SecretKeySpec k = new SecretKeySpec(key,"HmacSHA256");
		Mac mac = null;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(k);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return mac.doFinal(message);
	}
}
