package security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper
{
	private static final Log LOGGER = LogFactory.getLog(MD5Helper.class);

	public static String hash(String data)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] digest = md5.digest(data.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte aDigest : digest)
			{
				sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			LOGGER.error(e);
			return "";
		}
	}
}
