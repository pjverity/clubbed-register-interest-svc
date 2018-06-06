package uk.co.vhome.clubbed.svc.enquiryhandler.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper
{
	private static final Logger LOGGER = LogManager.getLogger();

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
