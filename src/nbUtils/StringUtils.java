package nbUtils;

public class StringUtils {

	public static String removeDuplicatedChars(String target, String duplicatedString, String replacement){
		int preLength = target.length();
		String tmp = target.replace(duplicatedString, replacement);
		int newLength = tmp.length();
		
		while(newLength != preLength){
			tmp = tmp.replace(duplicatedString, replacement);
			preLength = newLength;
			newLength = tmp.length();
		}
		
		return tmp;
	}
}
