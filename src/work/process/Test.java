package work.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main (String args[])
	{
		String s = "cdefcabfg";
		String web = "";
        String re = "((?!ab).)*";
		
        Pattern pattern = Pattern.compile(re, Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = pattern.matcher(s);
		
		boolean test = matcher.matches();
		if(test)
		{
			
			System.out.println(s);
		}
		else
		{
			System.out.println("no matches found!!!!");
		}
		
		
	}
}
