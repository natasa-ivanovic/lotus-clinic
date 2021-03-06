package isamrs.tim17.lotus.util;

import java.util.Random;

public class RandomUtil {
	public static Random random = new Random();
	
	public static String buildAuthString() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 30;	 
		String generatedString = random.ints(leftLimit, rightLimit + 1)
			      .limit(targetStringLength)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
		return generatedString;
	}
}
